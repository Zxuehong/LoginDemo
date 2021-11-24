package com.example.login.dagger2.compoment

import com.example.login.MainActivity
import com.example.login.dagger2.module.LoginModule
import dagger.Component

@Component(modules = [LoginModule::class])
interface LoginComponent {
    fun inject(activity: MainActivity)
}