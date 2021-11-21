package com.example.login.dragger2.module

import android.content.Context
import com.example.login.MainActivity
import com.example.login.R
import com.example.login.view.LoginButton
import com.example.login.view.UserInfoView
import dagger.Module
import dagger.Provides

@Module
class LoginModule(private val activity: MainActivity) {

    @Provides
    fun provideContext(): Context {
        return activity.applicationContext
    }

    @Provides
    fun provideLoginView(): UserInfoView {
        return activity.findViewById(R.id.login_view)
    }

    @Provides
    fun provideLoginButton(): LoginButton {
        return activity.findViewById(R.id.confirm)
    }
}