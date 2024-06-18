package com.signify.app.presentation.fragment.profile.edit

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentEditProfileBinding
import com.signify.app.utils.PreferenceManager
import org.koin.android.ext.android.inject

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentEditProfileBinding {
        return FragmentEditProfileBinding.inflate(inflater, container, false)
    }

    private val pref: PreferenceManager by inject()

    override fun beforeSomething() {
        super.beforeSomething()
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 400
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 400
        }
    }

    override fun doSomething() {
        super.doSomething()

        initAnimation()
        initListener()
    }

    private fun initListener() {
        with(binding) {
            itemAvatarLabel.text =
                pref.getFirstName.first().toString().uppercase()

            edName.setText(pref.getFirstName)
            edNameLast.setText(pref.getLastName)
            emailPlaceholder.text = pref.getEmail
        }
    }

    private fun initAnimation() {

        val labelName =
            ObjectAnimator.ofFloat(binding.labelName, View.ALPHA, 1f)
                .setDuration(50)
        val inputName =
            ObjectAnimator.ofFloat(binding.edName, View.ALPHA, 1f)
                .setDuration(50)
        val labelNameLast =
            ObjectAnimator.ofFloat(binding.labelNameLast, View.ALPHA, 1f)
                .setDuration(50)
        val inputNameLast =
            ObjectAnimator.ofFloat(binding.edNameLast, View.ALPHA, 1f)
                .setDuration(50)
        val labelEmail =
            ObjectAnimator.ofFloat(binding.labelEmail, View.ALPHA, 1f)
                .setDuration(50)
        val inputEmail =
            ObjectAnimator.ofFloat(binding.edEmail, View.ALPHA, 1f)
                .setDuration(50)
        val signUpButton =
            ObjectAnimator.ofFloat(binding.btnUpdate, View.ALPHA, 1f)
                .setDuration(50)

        AnimatorSet().apply {
            playSequentially(
                labelName,
                inputName,
                labelNameLast,
                inputNameLast,
                labelEmail,
                inputEmail,
                signUpButton,
            )
        }.start()
    }

}