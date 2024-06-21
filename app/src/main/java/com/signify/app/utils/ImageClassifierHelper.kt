package com.signify.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.os.SystemClock
import android.util.Log
import androidx.camera.core.ImageProxy
import com.signify.app.ml.SibiNewMl
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ImageClassifierHelper(
    val context: Context,
    val classifierListener: ClassifierListener?
) {

    private var imageClassifier: SibiNewMl? = null
    private val inputImageWidth = 128
    private val inputImageHeight = 128
    private val inputImageChannels = 1

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        try {
            imageClassifier = SibiNewMl.newInstance(context)
        } catch (e: Exception) {
            classifierListener?.onError("Failed to load model: ${e.message}")
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyImage(image: ImageProxy) {
        if (imageClassifier == null) {
            setupImageClassifier()
        }

        val bitmap = toBitmap(image)
        val resizedBitmap = Bitmap.createScaledBitmap(
            bitmap,
            inputImageWidth,
            inputImageHeight,
            true
        )
        val byteBuffer = convertBitmapToByteBuffer(resizedBitmap)
        val inputFeature0 = TensorBuffer.createFixedSize(
            intArrayOf(1, inputImageWidth, inputImageHeight, 3),
            DataType.FLOAT32
        )
        inputFeature0.loadBuffer(byteBuffer)

        val inferenceTime = SystemClock.uptimeMillis()
        val outputs = imageClassifier?.process(inputFeature0)
        val inferenceDuration = SystemClock.uptimeMillis() - inferenceTime

        val outputFeature0 = outputs?.outputFeature0AsTensorBuffer
        val results = parseResults(outputFeature0)
        Log.d(TAG, "classifyImage: $results")
        classifierListener?.onResults(results, inferenceDuration)

        image.close()
    }

    private fun toBitmap(image: ImageProxy): Bitmap {
        val buffer = image.planes[0].buffer
        buffer.rewind()
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)

        val yuvImage =
            YuvImage(bytes, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, image.width, image.height), 100, out)
        val yuvBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(yuvBytes, 0, yuvBytes.size)
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val inputShape = intArrayOf(1, 128, 128, 3)
        val byteBuffer =
            ByteBuffer.allocateDirect(inputShape.reduce { acc, i -> acc * i } * 4)
        byteBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(128 * 128)
        bitmap.getPixels(pixels, 0, 128, 0, 0, 128, 128)

        for (pixelValue in pixels) {
            val normalizedPixelValue = (pixelValue and 0xFF) / 255.0f
            byteBuffer.putFloat(normalizedPixelValue)
        }

        return byteBuffer
    }

    private fun parseResults(outputBuffer: TensorBuffer?): List<Classifications> {
        val scores = outputBuffer?.floatArray ?: return emptyList()
        if (scores.isNotEmpty()) {
            printOutputBuffer(outputBuffer)
        }

        val classifications = mutableListOf<Classifications>()
        for (i in scores.indices) {
            val label = INDEX_TO_LABEL.getOrElse(i) { "Unknown" }
            classifications.add(Classifications(label, scores[i], i))
        }
        return classifications.sortedByDescending { it.score }
    }

    private fun printOutputBuffer(outputBuffer: TensorBuffer) {
        val floatArray = outputBuffer.floatArray
        val shapeArray = outputBuffer.shape
        val shape = shapeArray.toUIntArray()
        outputBuffer.buffer

        // Assuming a single batch for simplicity
        val numClasses = shape[1]

        Log.d(TAG, "Output Buffer Shape: ${shape.contentToString()}")

        for (classIndex in 0 until numClasses.toInt()) {
            val value = floatArray[classIndex]
            Log.d(TAG, "Class $classIndex: $value")
        }
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(results: List<Classifications>, inferenceTime: Long)
    }

    data class Classifications(
        val label: String,
        val score: Float,
        val index: Int
    )

    companion object {
        private const val TAG = "ImageClassifierHelper"
        private val INDEX_TO_LABEL = listOf(
            "none", "P", "S", "Y", "U", "Q", "X", "T", "V", "R",
            "W", "I", "E", "L", "N", "O", "F", "G", "M", "H",
            "K", "B", "D", "A", "C"
        )
    }
}
