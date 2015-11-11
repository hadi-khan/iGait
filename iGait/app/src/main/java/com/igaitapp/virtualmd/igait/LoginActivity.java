package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUserName;
    private EditText editTextPassword;
    private Button buttonSubmit;
    private Button buttonRegister;
    private Button buttonForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonForgotPassword = (Button) findViewById(R.id.buttonForgotPassword);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUserName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (!InputCheck.email(username)) {
                    Toast.makeText(LoginActivity.this, "Invalid e-mail address.", Toast.LENGTH_SHORT).show();
                    editTextUserName.requestFocus();
                }
                else if(!InputCheck.password(password)) {
                    Toast.makeText(LoginActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                    editTextPassword.requestFocus();
                }
                else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();

                    finish();
                    startActivity(intent);
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
