package com.igaitapp.virtualmd.igait;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextLastName;
    private EditText editTextFirstName;
    private EditText editTextEmail;
    private EditText editTextOfficePhoneNumber;
    private EditText editTextOfficeAddress;
    private EditText editTextOfficeCity;
    private EditText editTextOfficeState;
    private EditText editTextOfficeZipCode;
    private EditText editTextPassword;
    private EditText editTextRePassword;

    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextOfficePhoneNumber = (EditText) findViewById(R.id.editTextOfficePhoneNumber);
        editTextOfficeAddress = (EditText) findViewById(R.id.editTextOfficeAddress);
        editTextOfficeCity = (EditText) findViewById(R.id.editTextOfficeCity);
        editTextOfficeState = (EditText) findViewById(R.id.editTextOfficeState);
        editTextOfficeZipCode = (EditText) findViewById(R.id.editTextOfficeZipCode);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextRePassword = (EditText) findViewById(R.id.editTextRePassword);

        buttonSubmit = (Button)findViewById(R.id.submitButton);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                ContactInfo contactInfo = new ContactInfo();

                String lastName = editTextLastName.getText().toString().trim();
                String firstName = editTextFirstName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String officePhoneNumber = editTextOfficePhoneNumber.getText().toString().trim();
                String officeAddress = editTextOfficeAddress.getText().toString().trim();
                String officeCity = editTextOfficeCity.getText().toString().trim();
                String officeState = editTextOfficeState.getText().toString().trim();
                String officeZipCode = editTextOfficeZipCode.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String rePassword = editTextRePassword.getText().toString().trim();


                if (!InputCheck.name(lastName)) {
                    Toast.makeText(RegisterActivity.this, "Invalid last name.", Toast.LENGTH_SHORT).show();
                    editTextLastName.requestFocus();
                }
                else if (!InputCheck.name(firstName)) {
                    Toast.makeText(RegisterActivity.this, "Invalid first name.", Toast.LENGTH_SHORT).show();
                    editTextFirstName.requestFocus();
                }
                else if (!InputCheck.email(email)) {
                    Toast.makeText(RegisterActivity.this, "Invalid e-mail address.", Toast.LENGTH_SHORT).show();
                    editTextEmail.requestFocus();
                }
                else if (!InputCheck.phoneNumber(officePhoneNumber)) {
                    Toast.makeText(RegisterActivity.this, "Invalid phone number.", Toast.LENGTH_SHORT).show();
                    editTextOfficePhoneNumber.requestFocus();
                }
                else if (!InputCheck.address(officeAddress)) {
                    Toast.makeText(RegisterActivity.this, "Invalid office address.", Toast.LENGTH_SHORT).show();
                    editTextOfficeAddress.requestFocus();
                }
                else if (!InputCheck.city(officeCity)) {
                    Toast.makeText(RegisterActivity.this, "Invalid office city.", Toast.LENGTH_SHORT).show();
                    editTextOfficeCity.requestFocus();
                }
                else if (!InputCheck.state(officeState)) {
                    Toast.makeText(RegisterActivity.this, "Invalid office state.", Toast.LENGTH_SHORT).show();
                    editTextOfficeState.requestFocus();
                }
                else if (!InputCheck.zipCode(officeZipCode)) {
                    Toast.makeText(RegisterActivity.this, "Invalid office zip code.", Toast.LENGTH_SHORT).show();
                    editTextOfficeZipCode.requestFocus();
                }
                else if (!InputCheck.newPassword(password)) {
                    Toast.makeText(RegisterActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                    editTextPassword.requestFocus();
                }
                else if (!InputCheck.rePassword(password, rePassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    editTextRePassword.requestFocus();
                }
                else {
                    long officePhoneNumberLong, officeZipCodeLong;

                    officePhoneNumberLong = Long.parseLong(officePhoneNumber.replaceAll("\\D+",""));
                    officeZipCodeLong = Long.parseLong(officeZipCode.replaceAll("\\D+",""));

                    contactInfo = new ContactInfo(officePhoneNumberLong, email, officeAddress, officeCity, officeState, officeZipCodeLong);

                    user = new User(lastName, firstName, contactInfo);

                    Toast.makeText(RegisterActivity.this, "Successfully registered.", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });
    }
}
