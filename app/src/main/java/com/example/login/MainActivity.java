package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.login.controller.LoginManager;
import com.example.login.dragger2.compoment.DaggerLoginComponent;
import com.example.login.dragger2.module.LoginModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDragger();
        init();
    }

    private void initDragger() {
        DaggerLoginComponent.builder()
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    private void init() {
        mLoginManager.init();
    }
}