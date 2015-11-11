package com.igaitapp.virtualmd.igait;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();

                if (!InputCheck.email(email)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid e-mail address.", Toast.LENGTH_SHORT).show();
                    emailEditText.requestFocus();
                }
                else {
                    Toast.makeText(ForgotPasswordActivity.this, "Recovery instructions sent to e-mail address.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}