package com.test.vodafone.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test.vodafone.R
import com.test.vodafone.ui.dialog.LoginDialogFragment

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = view.findViewById<View>(R.id.login_button)
        val guestButton = view.findViewById<View>(R.id.guest_button)

        loginButton.setOnClickListener {
            showLoginFormBottomSheet()
        }

        guestButton.setOnClickListener {
            findNavController().navigate(R.id.action_login_fragment_to_offers_fragment)
        }

    }

    private fun showLoginFormBottomSheet() {
        val bottomSheetFragment = LoginDialogFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}