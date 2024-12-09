package com.test.vodafone.ui.dialog

import android.animation.AnimatorInflater
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.test.vodafone.R

class LoginDialogFragment : BottomSheetDialogFragment(R.layout.dialog_fragment_login) {

    private var callback: LoginDialogResultListener? = null

    fun setLoginDialogResultListener(listener: LoginDialogResultListener) {
        callback = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isFrontSideVisible = true
        var isShowTermsAndConditions = false

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val radius = 48f
        val shape = GradientDrawable()
        shape.cornerRadii = floatArrayOf(
            radius, radius,
            radius, radius,
            0f, 0f,
            0f, 0f
        )
        shape.setColor(Color.TRANSPARENT)

        dialog?.window?.setBackgroundDrawable(shape)

        val username: EditText = view.findViewById(R.id.username_input)
        val password: EditText = view.findViewById(R.id.password_input)
        val loginButton = view.findViewById<Button>(R.id.login_button)
        val signUp = view.findViewById<TextView>(R.id.sign_up)
        val cardContainer = view.findViewById<FrameLayout>(R.id.card_container)
        val frontSide = view.findViewById<LinearLayout>(R.id.front_side)
        val backSide = view.findViewById<LinearLayout>(R.id.back_side)
        val termsAndConditions = view.findViewById<ConstraintLayout>(R.id.termsAndConditions)
        val usernameSignUp: EditText = view.findViewById(R.id.username_sign_up)
        val passwordSignUp1: EditText = view.findViewById(R.id.password_sign_up)
        val passwordSignUp2: EditText = view.findViewById(R.id.password_sign_up_again)
        val agree: CheckBox = view.findViewById(R.id.terms_and_conditions)
        val usernameTil: TextInputLayout = view.findViewById(R.id.username_til)
        val passwordTil: TextInputLayout = view.findViewById(R.id.password_til)
        val usernameTilSignUp: TextInputLayout = view.findViewById(R.id.username_sign_up_til)
        val passwordTilSignUp1: TextInputLayout = view.findViewById(R.id.password_sign_up_til)
        val passwordTilSignUp2: TextInputLayout = view.findViewById(R.id.password_sign_up_again_til)
        val termsAndConditionsButton = view.findViewById<TextView>(R.id.terms_and_conditions_text)
        val termsAndConditionsBack = view.findViewById<TextView>(R.id.aszf_back)

        termsAndConditionsButton.setOnClickListener{
            isShowTermsAndConditions = flipCard(
                cardContainer,
                termsAndConditions,
                backSide,
                isShowTermsAndConditions
            )
        }
        termsAndConditionsBack.setOnClickListener{
            isShowTermsAndConditions = flipCard(
                cardContainer,
                termsAndConditions,
                backSide,
                isShowTermsAndConditions
            )
        }

        loginButton.setOnClickListener {
            if(isFrontSideVisible) {
                if (username.text.toString().isEmpty() || password.text.toString().isEmpty()) {
                    dismiss()
                } else {
                    callback?.onDialogResult(username.text.toString(), password.text.toString())
                    dismiss()
                }
            }
            else{
                Toast.makeText(requireContext(), R.string.login_sign_up_fail, Toast.LENGTH_LONG).show()
            }
        }

        signUp.setOnClickListener{
            isFrontSideVisible = flipCard(cardContainer, frontSide, backSide, isFrontSideVisible)
            loginButton.text = if(isFrontSideVisible) getString(R.string.login_login) else getString(R.string.login_sign_up)
            signUp.text = if(isFrontSideVisible) getString(R.string.login_sign_up) else getString(R.string.login_login)
            if(isFrontSideVisible){
                checkLogin(username, password, usernameTil, passwordTil, loginButton)
            }
            else{
                checkSignUp(usernameSignUp, passwordSignUp1, passwordSignUp2, usernameTilSignUp, passwordTilSignUp1, passwordTilSignUp2, agree, loginButton)
            }
        }

        username.addTextChangedListener {
            wrongUsername(username, usernameTil)
            checkLogin(username, password, usernameTil, passwordTil, loginButton)
        }
        password.addTextChangedListener {
            wrongPassword(password, passwordTil, isFrontSideVisible)
            checkLogin(username, password, usernameTil, passwordTil, loginButton)
        }

        usernameSignUp.addTextChangedListener {
            wrongUsername(usernameSignUp, usernameTilSignUp)
            checkSignUp(usernameSignUp, passwordSignUp1, passwordSignUp2, usernameTilSignUp, passwordTilSignUp1, passwordTilSignUp2, agree, loginButton)
        }
        passwordSignUp1.addTextChangedListener {
            wrongPassword(passwordSignUp1, passwordTilSignUp1, isFrontSideVisible)
            if(passwordTilSignUp1.error == null){
                differentPassword(passwordSignUp1, passwordSignUp2, passwordTilSignUp1, passwordTilSignUp2)
            }
            checkSignUp(usernameSignUp, passwordSignUp1, passwordSignUp2, usernameTilSignUp, passwordTilSignUp1, passwordTilSignUp2, agree, loginButton)
        }
        passwordSignUp2.addTextChangedListener {
            wrongPassword(passwordSignUp2, passwordTilSignUp2, isFrontSideVisible)
            if(passwordTilSignUp2.error == null){
                differentPassword(passwordSignUp1, passwordSignUp2, passwordTilSignUp1, passwordTilSignUp2)
            }
            checkSignUp(usernameSignUp, passwordSignUp1, passwordSignUp2, usernameTilSignUp, passwordTilSignUp1, passwordTilSignUp2, agree, loginButton)
        }
        agree.setOnCheckedChangeListener { _, _ ->
            checkSignUp(usernameSignUp, passwordSignUp1, passwordSignUp2, usernameTilSignUp, passwordTilSignUp1, passwordTilSignUp2, agree, loginButton)
        }
    }

    private fun flipCard(container: FrameLayout, front: View, back: View, isFrontSideVisible: Boolean) : Boolean {
        val scale = requireContext().resources.displayMetrics.density
        container.cameraDistance = 8000 * scale

        val outAnim = AnimatorInflater.loadAnimator(requireContext(), R.animator.card_flip_out)
        val inAnim = AnimatorInflater.loadAnimator(requireContext(), R.animator.card_flip_in)

        if (isFrontSideVisible) {
            outAnim.setTarget(front)
            inAnim.setTarget(back)
            outAnim.start()
            inAnim.start()
            front.visibility = View.GONE
            back.visibility = View.VISIBLE
        } else {
            outAnim.setTarget(back)
            inAnim.setTarget(front)
            outAnim.start()
            inAnim.start()
            back.visibility = View.GONE
            front.visibility = View.VISIBLE
        }

        return !isFrontSideVisible
    }

    private fun checkLogin(username: EditText, password: EditText, usernameTil: TextInputLayout, passwordTil: TextInputLayout, loginButton: Button) {
        if(
            username.text.isEmpty() ||
            password.text.isEmpty() ||
            usernameTil.error!=null ||
            passwordTil.error!=null
            ){
            loginButton.isEnabled = false
            loginButton.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_grey)
            loginButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_light))
        } else {
            loginButton.isEnabled = true
            loginButton.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_white)
            loginButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.primery))
        }
    }

    private fun checkSignUp(usernameSignUp: EditText, passwordSignUp1: EditText, passwordSignUp2: EditText,usernameTilSignUp: TextInputLayout, passwordTilSignUp1: TextInputLayout, passwordTilSignUp2: TextInputLayout, agree: CheckBox, loginButton: Button) {
        if(
            usernameSignUp.text.isEmpty() ||
            passwordSignUp1.text.isEmpty() ||
            passwordSignUp2.text.isEmpty() ||
            usernameTilSignUp.error !=null ||
            passwordTilSignUp1.error != null ||
            passwordTilSignUp2.error != null ||
            !agree.isChecked){
            loginButton.isEnabled = false
            loginButton.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_grey)
            loginButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_light))
        } else {
            loginButton.isEnabled = true
            loginButton.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_white)
            loginButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.primery))
        }
    }


    private fun wrongUsername(username: EditText, usernameTil: TextInputLayout){
        if(username.text.isEmpty()){
            usernameTil.error = getString(R.string.login_fail_username)
            username.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_white)
        } else {
            if(Patterns.EMAIL_ADDRESS.matcher(username.text).matches()) {
                usernameTil.error = null
                username.background = ContextCompat.getDrawable(requireContext(), R.drawable.edit_text_white)
            }
            else{
                usernameTil.error = getString(R.string.login_username_not_email)
                username.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_white)
            }
        }
    }

    private fun wrongPassword(password: EditText, passwordTil: TextInputLayout, isFrontSideVisible: Boolean){
        if(password.text.isEmpty()){
            passwordTil.error = getString(R.string.login_fail_password)
            password.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_white)
        } else {
            if(isValidPassword(password.text.toString()) || isFrontSideVisible) {
                passwordTil.error = null
                password.background = ContextCompat.getDrawable(requireContext(), R.drawable.edit_text_white)
            }
            else{
                passwordTil.error = getString(R.string.login_wrong_password)
                password.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_white)
            }
        }
    }

    private fun differentPassword(password: EditText, password2: EditText, passwordTil1: TextInputLayout, passwordTil2: TextInputLayout){
        if(password.text.isNotEmpty() && password2.text.isNotEmpty() && password.text.toString() != password2.text.toString()){
            passwordTil1.error = getString(R.string.login_different_password)
            password.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_white)
            passwordTil2.error = getString(R.string.login_different_password)
            password2.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_white)
        }
        else{
            password.background = ContextCompat.getDrawable(requireContext(), R.drawable.edit_text_white)
            passwordTil1.error = null
            password2.background = ContextCompat.getDrawable(requireContext(), R.drawable.edit_text_white)
            passwordTil2.error = null
        }
    }

    private fun isValidPassword(password: String): Boolean {
        val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()_+={}|\\[\\]:;<>,.?/~`]).{8,}$"
        return password.matches(regex.toRegex())
    }
}