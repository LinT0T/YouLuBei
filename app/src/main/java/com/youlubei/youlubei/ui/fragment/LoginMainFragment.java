package com.youlubei.youlubei.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.youlubei.youlubei.R;
import com.youlubei.youlubei.ui.LoginActivity;

import java.util.Objects;


public class LoginMainFragment extends Fragment {

    private Button button, button2;

    public LoginMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        button = view.findViewById(R.id.btn_login);
        button2 = view.findViewById(R.id.btn_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((LoginActivity) Objects.requireNonNull(getActivity())).changeToLoginFragment();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) Objects.requireNonNull(getActivity())).changeToRegisterFragment();
            }
        });
        return view;
    }


}