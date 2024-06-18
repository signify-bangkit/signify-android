package com.signify.app.presentation.fragment.profile.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.DialogLogoutBinding
import com.signify.app.databinding.FragmentProfileBinding
import com.signify.app.utils.PreferenceManager
import org.koin.android.ext.android.inject
import java.util.Locale

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    private val viewModel: ProfileViewModel by inject()
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
        initListener()
    }

    private fun initListener() {
        with(binding) {
            itemAvatarLabel.text =
                pref.getFirstName.first().toString().uppercase(
                    Locale.getDefault()
                )
            nameText.text = "${pref.getFirstName} ${pref.getLastName}"
            emailText.text = pref.getEmail

            val extras = FragmentNavigatorExtras(
                contentLayoutWrapper to "content_layout_wrapper",
                mainLayout to "main_layout",
                itemAvatar to "item_avatar",
                itemAvatarLabel to "item_avatar_label",
                toolbarTitle to "title_app"
            )
            val directions = ProfileFragmentDirections

            // profile navigation
            btnEdit.setOnClickListener {
                findNavController().navigate(
                    directions.actionProfileFragmentToEditProfileFragment(),
                    extras
                )
            }
            btnTermsAndCondition.setOnClickListener {
                findNavController().navigate(
                    directions.actionProfileFragmentToTermsConditionFragment(),
                    extras
                )
            }
            btnAbout.setOnClickListener {
                findNavController().navigate(
                    directions.actionProfileFragmentToAboutFragment(), extras
                )
            }
            btnChangeLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            btnLogout.setOnClickListener {
                showLogoutDialog()
            }

            // navigation
            btnAnalyze.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    contentLayout to "content_layout_shared",
                    toolbarTitle to "title_app",
                )
                findNavController().navigate(
                    R.id.action_global_analyzeFragment, null, null, extras
                )
            }
            btnHome.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    toolbarTitle to "title_app",
                )
                findNavController().navigate(
                    R.id.action_global_homeFragment, null, null, extras
                )
            }
            btnHistory.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    contentLayout to "content_layout_shared",
                    toolbarTitle to "title_app",
                )
                findNavController().navigate(
                    R.id.action_global_historyFragment, null, null, extras
                )
            }
        }
    }

    private fun termsAndCondition() {

    }

    private fun showLogoutDialog() {
        val dbinding =
            DialogLogoutBinding.inflate(LayoutInflater.from(requireContext()))

        val dialog = MaterialAlertDialogBuilder(
            requireContext(),
            R.style.DialogAnimation
        )
            .setView(dbinding.root)
            .setCancelable(true)
            .create()

        dialog.show()

        dbinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dbinding.btnYes.setOnClickListener {
            dialog.dismiss()
            viewModel.logout().apply {
                val extras = FragmentNavigatorExtras(
                    binding.contentLayout to "content_layout_shared",
                    binding.toolbarTitle to "title_app",
                )
                if (this) {
                    findNavController().navigate(
                        R.id.action_global_loginFragment,
                        null,
                        null,
                        extras
                    )
                }
            }
        }
    }
}