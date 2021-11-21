package com.example.login.controller

import android.content.Context
import android.view.View
import android.widget.Toast
import java.util.function.Consumer
import javax.inject.Inject

class LoginManager @Inject constructor(private val context: Context,
        private val userInfoViewController: UserInfoViewController,
        private val loginButtonController: LoginButtonController): View.OnClickListener {

    fun init() {
        loginButtonController.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Toast.makeText(context, userInfoViewController.getUserInput(), Toast.LENGTH_LONG).show()
    }
}