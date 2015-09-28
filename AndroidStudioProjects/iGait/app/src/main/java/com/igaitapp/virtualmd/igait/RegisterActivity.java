package com.igaitapp.virtualmd.igait;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private TextView signUpTextView;
    private EditText nameEditText;
    private EditText userNameEditText;
    private EditText emailEditText;
    private EditText mobileEditText;
    private EditText addressEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button submitRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signUpTextView = (TextView) findViewById(R.id.signUptextView);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        mobileEditText = (EditText)findViewById(R.id.mobileEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordRegisterEditText);
        confirmPasswordEditText = (EditText)findViewById(R.id.reenterPasswordRegisterEditText);
        submitRegisterButton = (Button)findViewById(R.id.btnRegisterSubmit);

        submitRegisterButton.setOnClickListener(new View.OnClickListener() {
            NewUserDetails nud = new NewUserDetails();
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                nud.setName(name);
                String email = emailEditText.getText().toString();
                nud.setEmail(email);
                String mobile = mobileEditText.getText().toString();
                nud.setMobile(mobile);
                String address = addressEditText.getText().toString();
                nud.setAddress(address);
                String password = passwordEditText.getText().toString();
                nud.setPassword(password);
                String reEnterPassword = confirmPasswordEditText.getText().toString();
                nud.setReEnterPassword(reEnterPassword);
            }
        });
    }
}
