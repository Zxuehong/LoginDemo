package com.example.login.entry

class User(var userName: String, var userPwd: String) {
    override fun toString(): String {
        return "User Name: $userName, User pwd: $userPwd"
    }
}