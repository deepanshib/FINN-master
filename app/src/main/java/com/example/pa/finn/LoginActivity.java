package com.example.pa.finn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    login(user.getUid());
                } else {
                    new LoginActivityViewHolder();
                }
            }
        };
    }

    private class LoginActivityViewHolder {
        EditText etEmail, etPassword;
        Button btnLogin;
        ProgressBar pbLoading;
        TextView tvRegister;

        LoginActivityViewHolder() {
            etEmail = (EditText) findViewById(R.id.etEmail);
            etPassword = (EditText) findViewById(R.id.etPassword);
            btnLogin = (Button) findViewById(R.id.btnLogin);
            pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
            tvRegister = (TextView) findViewById(R.id.tvRegister);
            clickable(true);
            setOnClickListeners();
        }

        void setOnClickListeners() {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkInfo()) {
                        clickable(false);
                        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                            clickable(true);
                                        } else {
                                            login(task.getResult().getUser().getUid());
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            tvRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            });
        }

        void clickable(boolean b) {
            if (b) {
                btnLogin.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);
            } else {
                pbLoading.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
            }
        }

        boolean checkInfo() {
            return !(etEmail.getText().toString().isEmpty()
                    || etPassword.getText().toString().isEmpty()
                    || etPassword.getText().toString().length() < 6);
        }
    }

    public void login(String userId) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("userid", userId);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}