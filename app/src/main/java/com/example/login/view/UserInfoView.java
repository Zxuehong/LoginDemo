package com.example.login.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.login.R;
import com.example.login.entry.User;

public class UserInfoView extends RelativeLayout {
    private EditText mUserName;
    private EditText mUserPwd;

    public UserInfoView(Context context) {
        super(context);
    }

    public UserInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public UserInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mUserName = findViewById(R.id.user_name);
        mUserPwd = findViewById(R.id.pwd);
    }

    public User getUserInput() {
        return new User(mUserName.getText().toString(), mUserPwd.getText().toString());
    }
}
