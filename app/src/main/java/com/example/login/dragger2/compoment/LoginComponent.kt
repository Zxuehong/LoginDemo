package com.example.login.dragger2.compoment

import com.example.login.MainActivity
import com.example.login.dragger2.module.LoginModule
import dagger.Component

@Component(modules = [LoginModule::class])
interface LoginComponent {
    fun inject(activity: MainActivity)
}