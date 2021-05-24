package com.youlubei.youlubei.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.youlubei.youlubei.R;
import com.youlubei.youlubei.ui.fragment.LoginFragment;
import com.youlubei.youlubei.ui.fragment.LoginFragment2;
import com.youlubei.youlubei.ui.fragment.RegisterFragment;
import com.youlubei.youlubei.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    LoginFragment loginFragment;
    LoginFragment2 loginFragment2;
    RegisterFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utils.initBar(this);
        ImageView backImageView = findViewById(R.id.img_back_login);
        loginFragment = new LoginFragment();
        loginFragment2 = new LoginFragment2();
        registerFragment = new RegisterFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in,
                R.anim.slide_left_out,
                R.anim.slide_left_in,
                R.anim.slide_right_out).replace(R.id.fl_container, loginFragment).commit();
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void changeToLoginFragment() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in,
                R.anim.slide_left_out,
                R.anim.slide_left_in,
                R.anim.slide_right_out).replace(R.id.fl_container, loginFragment2).addToBackStack("fragment").commit();
    }

    public void changeToRegisterFragment() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_left_in,
                R.anim.slide_right_out,
                R.anim.slide_right_in,
                R.anim.slide_left_out).replace(R.id.fl_container, registerFragment).addToBackStack("fragment").commit();
    }
}