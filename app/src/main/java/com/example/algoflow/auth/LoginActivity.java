package com.example.algoflow.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.algoflow.R;
import com.example.algoflow.activities.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView signupLink, forgotPassword;

    private FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.lo_email_input);
        edtPassword = findViewById(R.id.lo_password_input);
        btnLogin = findViewById(R.id.login_button);
        signupLink = findViewById(R.id.signup_link);
        forgotPassword = findViewById(R.id.forgot_password);

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(v -> loginUser());

        forgotPassword.setOnClickListener(v -> forgotPassword());

        token();
    }

    private void loginUser() {
        String email, password;
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "You must enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void token() {
        if(mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    private void forgotPassword(){
        String email = edtEmail.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Please enter email to reset password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(this, "Password reset email has been sent to " + email, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "The reset email could not be sent. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}