package com.test.vodafone.ui.dialog

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.vodafone.R

class LoginDialogFragment : BottomSheetDialogFragment(R.layout.dialog_fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username_et: EditText = view.findViewById(R.id.username_input)
        val password_et: EditText = view.findViewById(R.id.password_input)
        val loginButton = view.findViewById<View>(R.id.login_button)

        loginButton.setOnClickListener {
            if (username_et.text.toString().isEmpty() || password_et.text.toString().isEmpty()) {
                dismiss()
            } else {
                findNavController().navigate(R.id.action_login_fragment_to_offers_fragment)
            }
        }


    }
}