package com.example.login.controller

import android.view.View
import com.example.login.view.LoginButton
import javax.inject.Inject

class LoginButtonController @Inject constructor(private val mainView: LoginButton) {
    fun setOnClickListener(clickListener: View.OnClickListener) {
        mainView.setOnClickListener(clickListener)
    }

    fun updateEnable(buttonEnable: Boolean) {
        mainView.isEnabled = buttonEnable
    }
}