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

public class SignUpActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private Button mSignUpButton;
    private TextView mAlreadyUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setViewIds();
        //Setting Firebase Authentication
        setUpFireBase();
        //Sign Up button On click listener
        setUpSignUpButton();
        setUpAlreadyUserTextView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void setUpFireBase() {
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                if(user !=null){
                    Toast.makeText(SignUpActivity.this, "You are logged in!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(SignUpActivity.this, "Please Sign Up!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    private void setUpAlreadyUserTextView() {
        mAlreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }

    private void setUpSignUpButton() {
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString();
                //Check if email and password are empty. If so then make a toast
                if(email.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Email is invalid",Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Password is invalid",Toast.LENGTH_SHORT).show();
                }else{
                    //Create user using Firebase
                    mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Authentication Unsuccessful", Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            }
                        }
                    });
                }

            }
        });
    }

    private void setViewIds(){
        mEmail = findViewById(R.id.email_edit_text);
        mPassword = findViewById(R.id.password_edit_text);
        mSignUpButton = findViewById(R.id.sign_up_button);
        mAlreadyUser = findViewById(R.id.already_sign_in);
    }
}
