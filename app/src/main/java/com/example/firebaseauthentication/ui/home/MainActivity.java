package com.example.firebaseauthentication.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.firebaseauthentication.R;
import com.example.firebaseauthentication.application.CustomApplicationClass;
import com.example.firebaseauthentication.listeners.home.PerformSignOut;
import com.example.firebaseauthentication.listeners.home.SignInSubmitButtonClick;
import com.example.firebaseauthentication.ui.home.fragments.SignInFragment;
import com.example.firebaseauthentication.ui.home.fragments.UserHomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SignInSubmitButtonClick, PerformSignOut {

    private static final int CONTENT_VIEW_ID = 10101010;
    FirebaseAuth mFirebaseAuth;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth = ((CustomApplicationClass)getApplication()).getFirebaseAuth();
        if (mFirebaseAuth != null) {
            updateUser();
        }
    }

    private void initViews() {
        frameLayout = findViewById(R.id.main_frame);
    }

    private void updateUser() {
        if (mFirebaseAuth.getCurrentUser() == null) {
            openSignInScreen();
        } else {
            continueWithLoggedInUserFlow(mFirebaseAuth.getCurrentUser());
        }
    }

    private void openSignInScreen() {
        SignInFragment signInFragment = SignInFragment.newInstance(null, null, this::onSignInSubmitButtonClickAction);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, signInFragment);
        transaction.commit();
    }

    private void openUserHomeScreen(FirebaseUser firebaseUser) {
        UserHomeFragment userHomeFragment = UserHomeFragment.newInstance(null, null);
        userHomeFragment.setFirebaseUser(firebaseUser);
        userHomeFragment.setListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, userHomeFragment);
        transaction.commit();
    }

    private void continueWithLoggedInUserFlow(FirebaseUser firebaseUser) {
        Log.i("Dummy12345", "continueWithLoggedInUserFlow: "+firebaseUser.getEmail());
        openUserHomeScreen(firebaseUser);
    }

    @Override
    public void onSignInSubmitButtonClickAction(String email, String password) {
        Log.i("Dummy12345", "onSignInSubmitButtonClickAction: "+email+"  "+password);
        mFirebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    selectLoginMethod(Objects.requireNonNull(task.getResult().getSignInMethods()), email, password);
                } else {
                    Log.i("Dummy12345", "onComplete: else");
                }
            }
        });



    }

    private void selectLoginMethod(List<String> loginTypes, String email, String password) {
        if (loginTypes.isEmpty()) {
            createUser(email, password);
        } else {
            performLogin(email, password);
        }
    }

    private void createUser(String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        openUserHomeScreen(task.getResult().getUser());
                        Toast.makeText(getApplication(), "Successfully Added to firebase", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplication(), "UnSuccessfully", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void performLogin(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                openUserHomeScreen(task.getResult().getUser());
            }
        });
    }

    @Override
    public void signOut() {
        mFirebaseAuth.signOut();
        openSignInScreen();
    }
}