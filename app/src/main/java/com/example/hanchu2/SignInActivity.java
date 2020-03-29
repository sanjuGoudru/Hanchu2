package com.example.hanchu2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mSignInButton;
    private TextView mDontHaveAccount;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setViewIds();
        //Setting Firebase Authentication
        setUpFireBase();
        //Setting up Sign In Button
        setUpSignInButton();
        //setting up Dont Have Account TextView
        setUpDontHaveAccount();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void setUpDontHaveAccount() {
        mDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    private void setUpSignInButton() {
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString();
                //Check if email and password are empty. If so then make a toast
                if(email.isEmpty()){
                    Toast.makeText(SignInActivity.this, "Email is invalid",Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(SignInActivity.this, "Password is invalid",Toast.LENGTH_SHORT).show();
                }else{
                    mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(SignInActivity.this, "Authentication Unsuccessful", Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            }
                        }
                    });
                }

            }
        });
    }

    private void setUpFireBase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                if(user !=null){
                    Toast.makeText(SignInActivity.this, "You are logged in!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(SignInActivity.this, "Please Login!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void setViewIds(){
        mEmail = findViewById(R.id.email_edit_text);
        mPassword = findViewById(R.id.password_edit_text);
        mSignInButton = findViewById(R.id.sign_in_button);
        mDontHaveAccount = findViewById(R.id.dont_have_account);
    }
}
