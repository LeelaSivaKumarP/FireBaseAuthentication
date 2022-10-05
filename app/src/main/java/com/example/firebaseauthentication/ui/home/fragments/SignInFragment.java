package com.example.firebaseauthentication.ui.home.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebaseauthentication.R;
import com.example.firebaseauthentication.listeners.home.SignInSubmitButtonClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SignInSubmitButtonClick signInSubmitButtonClick;
    EditText emailET, passwordET;
    Button mSubmitButton;

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance(String param1, String param2, SignInSubmitButtonClick signInSubmitButtonClick) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        signInSubmitButtonClick = (SignInSubmitButtonClick) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    private void initializeViews(View view) {
        emailET = view.findViewById(R.id.email_edit_text);
        passwordET = view.findViewById(R.id.password_edit_text);
        mSubmitButton = view.findViewById(R.id.submit_btn);
        mSubmitButton.setOnClickListener(view1 -> {

            if (!TextUtils.isEmpty(emailET.getText()) && !TextUtils.isEmpty(passwordET.getText())) {
                signInSubmitButtonClick.onSignInSubmitButtonClickAction(emailET.getText().toString(), passwordET.getText().toString());
            }

        });
    }
}