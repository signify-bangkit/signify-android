package com.signify.app.utils

import com.signify.app.R
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(
    private val requestHeaders: HashMap<String, String>,
    private val preferenceManager: PreferenceManager,
) : Interceptor {

    @Throws(IOException::class, TokenExpiredException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        mapRequestHeaders()

        val request = mapHeaders(chain)
        val response = chain.proceed(request)

        return response
    }

    private fun mapRequestHeaders() {
        val token = preferenceManager.getToken
        requestHeaders["Authorization"] = "Bearer $token"
    }

    private fun mapHeaders(chain: Interceptor.Chain): Request {
        val original = chain.request()

        val requestBuilder = original.newBuilder()

        for ((key, value) in requestHeaders) {
            requestBuilder.addHeader(key, value)
        }
        return requestBuilder.build()
    }

}

class TokenExpiredException(message: String) : IOException(message)