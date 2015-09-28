package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private CheckBox checkBoxEditText;
    private TextView showPasswordTextView;
    private Button submitBtn;
    private Button registerBtn;
    private Button forgotPasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcomeTextView = (TextView) findViewById(R.id.welcomTextView);
        userNameEditText = (EditText) findViewById(R.id.userNameLoginEditText);
        passwordEditText = (EditText) findViewById(R.id.enterPasswordLoginEditText);
        checkBoxEditText = (CheckBox) findViewById(R.id.checkBox);
        showPasswordTextView = (TextView) findViewById(R.id.enterPasswordLoginEditText);
        submitBtn = (Button) findViewById(R.id.btnLoginSubmit);
        registerBtn = (Button) findViewById(R.id.btnRegister);
        forgotPasswordBtn = (Button)findViewById(R.id.btnForgotPassword);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if ((userID.length()<=0) && (password.length()>=1)) {
                    Toast.makeText(LoginActivity.this, "Please enter e-mail.", Toast.LENGTH_SHORT).show();
                    userNameEditText.requestFocus();
                } else if((password.length()<=0) && (userID.length()>=1)) {
                    Toast.makeText(LoginActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                    passwordEditText.requestFocus();
                } else if(userID.equals("admin")) {
                    Toast.makeText(LoginActivity.this, "Loging in...", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter a valid e-mail and/or password.", Toast.LENGTH_SHORT).show();
                    userNameEditText.requestFocus();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getPasswordIntent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(getPasswordIntent);
            }
        });
    }
}
