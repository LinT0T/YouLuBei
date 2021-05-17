package com.youlubei.youlubei.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.youlubei.youlubei.R;
import com.youlubei.youlubei.ui.LoginActivity;

import java.util.Objects;


public class LoginFragment2 extends Fragment {


    public LoginFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login2, container, false);
        TextView textView = view.findViewById(R.id.tv_login_to_register);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) Objects.requireNonNull(getActivity())).changeToRegisterFragment();
            }
        });
        return view;
    }
}