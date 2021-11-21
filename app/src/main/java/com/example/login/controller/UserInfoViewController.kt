package com.example.login.controller

import com.example.login.view.UserInfoView
import javax.inject.Inject

class UserInfoViewController @Inject constructor(private val mainView: UserInfoView) {
    fun getUserInput(): String {
        return mainView.userInput.toString()
    }
}