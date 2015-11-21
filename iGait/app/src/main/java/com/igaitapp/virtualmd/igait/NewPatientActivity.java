package com.igaitapp.virtualmd.igait;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPatientActivity extends AppCompatActivity {
    private EditText editTextLastName;
    private EditText editTextFirstName;
    private EditText editTextEmail;
    private EditText editTextPhoneNumber;
    private EditText editTextExpectedWalkTime;
    private EditText editTextBirthday;
    private EditText editTextSex;
    private EditText editTextAddress;
    private EditText editTextCity;
    private EditText editTextState;
    private EditText editTextZipCode;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        editTextExpectedWalkTime = (EditText) findViewById(R.id.editTextExpectedWalkTime);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        editTextSex = (EditText) findViewById(R.id.editTextSex);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextCity = (EditText) findViewById(R.id.editTextCity);
        editTextState = (EditText) findViewById(R.id.editTextState);
        editTextZipCode = (EditText) findViewById(R.id.editTextZipCode);

        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat tf = new SimpleDateFormat("HH:mm:ss");
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                NewPatient newPatient = new NewPatient();
                ContactInfo contactInfo = new ContactInfo();

                String lastName = editTextLastName.getText().toString().trim();
                String firstName = editTextFirstName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                String expectedWalkTime = editTextExpectedWalkTime.getText().toString().trim();
                String birthday = editTextBirthday.getText().toString().trim();
                String sex = editTextSex.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String city = editTextCity.getText().toString().trim();
                String state = editTextState.getText().toString().trim();
                String zipCode = editTextZipCode.getText().toString().trim();

                if (!InputCheck.name(lastName)) {
                    Toast.makeText(NewPatientActivity.this, "Invalid last name.", Toast.LENGTH_SHORT).show();
                    editTextLastName.requestFocus();
                } else if (!InputCheck.name(firstName)) {
                    Toast.makeText(NewPatientActivity.this, "Invalid first name.", Toast.LENGTH_SHORT).show();
                    editTextFirstName.requestFocus();
                } else if (!InputCheck.email(email)) {
                    Toast.makeText(NewPatientActivity.this, "Invalid e-mail address.", Toast.LENGTH_SHORT).show();
                    editTextEmail.requestFocus();
                } else if (!InputCheck.phoneNumber(phoneNumber)) {
                    Toast.makeText(NewPatientActivity.this, "Invalid phone number.", Toast.LENGTH_SHORT).show();
                    editTextPhoneNumber.requestFocus();
                } else if (!InputCheck.expectedWalkTime(expectedWalkTime)) {
                    Toast.makeText(NewPatientActivity.this, "Invalid expected walk time.", Toast.LENGTH_SHORT).show();
                    editTextExpectedWalkTime.requestFocus();
                } else if (!InputCheck.birthday(birthday)) {
                    Toast.makeText(NewPatientActivity.this, "Invalid birthday.", Toast.LENGTH_SHORT).show();
                    editTextBirthday.requestFocus();
                } else if (!InputCheck.sex(sex)) {
                    Toast.makeText(NewPatientActivity.this, "Invalid sex.", Toast.LENGTH_SHORT).show();
                    editTextSex.requestFocus();
                } else if (!InputCheck.address(address)) {
                    Toast.makeText(NewPatientActivity.this, "Invalid office address.", Toast.LENGTH_SHORT).show();
                    editTextAddress.requestFocus();
                } else if (!InputCheck.city(city)) {
                    Toast.makeText(NewPatientActivity.this, "Invalid office city.", Toast.LENGTH_SHORT).show();
                    editTextCity.requestFocus();
                } else if (!InputCheck.state(state)) {
                    Toast.makeText(NewPatientActivity.this, "Invalid office state.", Toast.LENGTH_SHORT).show();
                    editTextState.requestFocus();
                } else if (!InputCheck.zipCode(zipCode)) {
                    Toast.makeText(NewPatientActivity.this, "Invalid office zip code.", Toast.LENGTH_SHORT).show();
                    editTextZipCode.requestFocus();
                } else {
                    Date expectedWalkTimeDate = new Date(), birthdayDate = new Date();
                    long phoneNumberLong = Long.parseLong(phoneNumber), zipCodeLong = Long.parseLong(zipCode);
                    char sexChar;

                    try {
                        expectedWalkTimeDate = tf.parse(expectedWalkTime);
                        birthdayDate = df.parse(birthday);
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    }

                    sexChar = sex.charAt(0);
                    contactInfo = new ContactInfo(phoneNumberLong, email, address, city, state, zipCodeLong);
                    newPatient = new NewPatient(lastName, firstName, expectedWalkTimeDate, birthdayDate, sexChar, contactInfo);

                    Toast.makeText(NewPatientActivity.this, "New patient added.", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });
    }
}
