package com.test.vodafone.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.vodafone.R
import com.test.vodafone.ui.dialog.LoginDialogResultListener
import com.test.vodafone.ui.dialog.LoginDialogFragment
import com.test.vodafone.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login), LoginDialogResultListener {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = view.findViewById<View>(R.id.login_button)
        val guestButton = view.findViewById<View>(R.id.guest_button)

        loginButton.setOnClickListener {
            showLoginFormBottomSheet()
        }

        guestButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_login_fragment_to_offers_fragment,
                Bundle().apply { putString("user", "guest") }
            )
        }

        viewModel.detail.observe(viewLifecycleOwner, { username ->
            if(username != "") {
                if (username != "fail") {
                    findNavController().navigate(
                        R.id.action_login_fragment_to_offers_fragment,
                        Bundle().apply { putString("user", username) }
                    )
                } else {
                    Toast.makeText(requireContext(), R.string.login_fail, Toast.LENGTH_LONG).show()
                }
            }
        })

    }

    private fun showLoginFormBottomSheet() {
        val bottomSheetFragment = LoginDialogFragment()
        bottomSheetFragment.setLoginDialogResultListener(this)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

    override fun onDialogResult(username: String, password: String) {
        viewModel.login(username, password)
    }


}