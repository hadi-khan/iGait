package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PatientProfileActivity extends AppCompatActivity {
    private TextView textViewLastName;
    private TextView textViewFirstName;
    private TextView textViewEmail;
    private TextView textViewPhoneNumber;
    private TextView textViewExpectedWalkTime;
    private TextView textViewBirthday;
    private TextView textViewSex;
    private TextView textViewAddress;
    private TextView textViewCity;
    private TextView textViewState;
    private TextView textViewZipCode;

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

    private Switch switchPriority;

    private boolean editable = false;

    private Patient patient = new Patient();

    private DateFormat tf = new SimpleDateFormat("HH:mm:ss");
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        patient = (Patient) getIntent().getSerializableExtra(MainActivity.EXTRA_PATIENT);

        populateViews();
        editable();
    }

    private void populateViews() {
        textViewLastName = (TextView) findViewById(R.id.textViewLastName);
        textViewFirstName = (TextView) findViewById(R.id.textViewFirstName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewPhoneNumber = (TextView) findViewById(R.id.textViewPhoneNumber);
        textViewExpectedWalkTime = (TextView) findViewById(R.id.textViewExpectedWalkTime);
        textViewBirthday = (TextView) findViewById(R.id.textViewBirthday);
        textViewSex = (TextView) findViewById(R.id.textViewSex);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewCity = (TextView) findViewById(R.id.textViewCity);
        textViewState = (TextView) findViewById(R.id.textViewState);
        textViewZipCode = (TextView) findViewById(R.id.textViewZipCode);

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

        switchPriority = (Switch) findViewById(R.id.switchPriority);

        textViewLastName.setText(patient.getLastName());
        textViewFirstName.setText(patient.getFirstName());
        textViewEmail.setText(patient.getContactInfo().getEmail());
        textViewPhoneNumber.setText(patient.getContactInfo().getPhoneNumber().toString());
        textViewExpectedWalkTime.setText(tf.format(patient.getExpectedWalkTime().getTime()));
        textViewBirthday.setText(df.format(patient.getBirthday()));
        textViewSex.setText(Character.toString(patient.getSex()));
        textViewAddress.setText(patient.getContactInfo().getAddress());
        textViewCity.setText(patient.getContactInfo().getCity());
        textViewState.setText(patient.getContactInfo().getState());
        textViewZipCode.setText(Long.toString(patient.getContactInfo().getZipCode()));

        editTextLastName.setText(patient.getLastName());
        editTextFirstName.setText(patient.getFirstName());
        editTextEmail.setText(patient.getContactInfo().getEmail());
        editTextPhoneNumber.setText(patient.getContactInfo().getPhoneNumber().toString());
        editTextExpectedWalkTime.setText(tf.format(patient.getExpectedWalkTime().getTime()));
        editTextBirthday.setText(df.format(patient.getBirthday()));
        editTextSex.setText(Character.toString(patient.getSex()));
        editTextAddress.setText(patient.getContactInfo().getAddress());
        editTextCity.setText(patient.getContactInfo().getCity());
        editTextState.setText(patient.getContactInfo().getState());
        editTextZipCode.setText(Long.toString(patient.getContactInfo().getZipCode()));

        switchPriority.setChecked(patient.isPriority());
    }

    private void editable() {
        if (!editable) {
            textViewLastName.setVisibility(View.VISIBLE);
            textViewFirstName.setVisibility(View.VISIBLE);
            textViewEmail.setVisibility(View.VISIBLE);
            textViewPhoneNumber.setVisibility(View.VISIBLE);
            textViewExpectedWalkTime.setVisibility(View.VISIBLE);
            textViewBirthday.setVisibility(View.VISIBLE);
            textViewSex.setVisibility(View.VISIBLE);
            textViewAddress.setVisibility(View.VISIBLE);
            textViewCity.setVisibility(View.VISIBLE);
            textViewState.setVisibility(View.VISIBLE);
            textViewZipCode.setVisibility(View.VISIBLE);

            editTextLastName.setVisibility(View.GONE);
            editTextFirstName.setVisibility(View.GONE);
            editTextEmail.setVisibility(View.GONE);
            editTextPhoneNumber.setVisibility(View.GONE);
            editTextExpectedWalkTime.setVisibility(View.GONE);
            editTextBirthday.setVisibility(View.GONE);
            editTextSex.setVisibility(View.GONE);
            editTextAddress.setVisibility(View.GONE);
            editTextCity.setVisibility(View.GONE);
            editTextState.setVisibility(View.GONE);
            editTextZipCode.setVisibility(View.GONE);

            switchPriority.setEnabled(false);
        } else {
            textViewLastName.setVisibility(View.GONE);
            textViewFirstName.setVisibility(View.GONE);
            textViewEmail.setVisibility(View.GONE);
            textViewPhoneNumber.setVisibility(View.GONE);
            textViewExpectedWalkTime.setVisibility(View.GONE);
            textViewBirthday.setVisibility(View.GONE);
            textViewSex.setVisibility(View.GONE);
            textViewAddress.setVisibility(View.GONE);
            textViewCity.setVisibility(View.GONE);
            textViewState.setVisibility(View.GONE);
            textViewZipCode.setVisibility(View.GONE);

            editTextLastName.setVisibility(View.VISIBLE);
            editTextFirstName.setVisibility(View.VISIBLE);
            editTextEmail.setVisibility(View.VISIBLE);
            editTextPhoneNumber.setVisibility(View.VISIBLE);
            editTextExpectedWalkTime.setVisibility(View.VISIBLE);
            editTextBirthday.setVisibility(View.VISIBLE);
            editTextSex.setVisibility(View.VISIBLE);
            editTextAddress.setVisibility(View.VISIBLE);
            editTextCity.setVisibility(View.VISIBLE);
            editTextState.setVisibility(View.VISIBLE);
            editTextZipCode.setVisibility(View.VISIBLE);

            switchPriority.setEnabled(true);
        }
    }

    private boolean checkChanges() {
        List<String> changes = new ArrayList<>();
        List<String> original = new ArrayList<>();
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
        boolean priority = switchPriority.isChecked();
        boolean result = false;

        changes.add(lastName);
        changes.add(firstName);
        changes.add(email);
        changes.add(phoneNumber);
        changes.add(expectedWalkTime);
        changes.add(birthday);
        changes.add(sex);
        changes.add(address);
        changes.add(city);
        changes.add(state);
        changes.add(zipCode);

        original.add(patient.getLastName());
        original.add(patient.getFirstName());
        original.add(patient.getContactInfo().getEmail());
        original.add(Long.toString(patient.getContactInfo().getPhoneNumber()));
        original.add(tf.format(patient.getExpectedWalkTime().getTime()));
        original.add(df.format(patient.getBirthday()));
        original.add(Character.toString(patient.getSex()));
        original.add(patient.getContactInfo().getAddress());
        original.add(patient.getContactInfo().getCity());
        original.add(patient.getContactInfo().getState());
        original.add(Long.toString(patient.getContactInfo().getZipCode()));

        if (InputCheck.noChanges(changes, original) && priority == patient.isPriority()) {
            Toast.makeText(PatientProfileActivity.this, "Cannot save. No changes have been made.", Toast.LENGTH_SHORT).show();
        } else if (!InputCheck.name(lastName)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid last name.", Toast.LENGTH_SHORT).show();
            editTextLastName.requestFocus();
        } else if (!InputCheck.name(firstName)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid first name.", Toast.LENGTH_SHORT).show();
            editTextFirstName.requestFocus();
        } else if (!InputCheck.email(email)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid e-mail address.", Toast.LENGTH_SHORT).show();
            editTextEmail.requestFocus();
        } else if (!InputCheck.phoneNumber(phoneNumber)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid phone number.", Toast.LENGTH_SHORT).show();
            editTextPhoneNumber.requestFocus();
        } else if (!InputCheck.expectedWalkTime(expectedWalkTime)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid expected walk time.", Toast.LENGTH_SHORT).show();
            editTextExpectedWalkTime.requestFocus();
        } else if (!InputCheck.birthday(birthday)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid birthday.", Toast.LENGTH_SHORT).show();
            editTextBirthday.requestFocus();
        } else if (!InputCheck.sex(sex)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid sex.", Toast.LENGTH_SHORT).show();
            editTextSex.requestFocus();
        } else if (!InputCheck.address(address)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid office address.", Toast.LENGTH_SHORT).show();
            editTextAddress.requestFocus();
        } else if (!InputCheck.city(city)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid office city.", Toast.LENGTH_SHORT).show();
            editTextCity.requestFocus();
        } else if (!InputCheck.state(state)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid office state.", Toast.LENGTH_SHORT).show();
            editTextState.requestFocus();
        } else if (!InputCheck.zipCode(zipCode)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid office zip code.", Toast.LENGTH_SHORT).show();
            editTextZipCode.requestFocus();
        } else {
            result = true;
        }

        return result;
    }

    private void saveChanges() {
        patient.setLastName(editTextLastName.getText().toString().trim());
        patient.setFirstName(editTextFirstName.getText().toString().trim());
        patient.getContactInfo().setEmail(editTextEmail.getText().toString().trim());
        patient.getContactInfo().setPhoneNumber(Long.parseLong(editTextPhoneNumber.getText().toString().trim()));

        try {
            patient.setExpectedWalkTime(tf.parse(editTextExpectedWalkTime.getText().toString().trim()));
            patient.setBirthday(df.parse(editTextBirthday.getText().toString().trim()));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        patient.setSex(editTextSex.getText().toString().charAt(0));
        patient.getContactInfo().setAddress(editTextAddress.getText().toString().trim());
        patient.getContactInfo().setCity(editTextCity.getText().toString().trim());
        patient.getContactInfo().setState(editTextState.getText().toString().trim());
        patient.getContactInfo().setZipCode(Long.parseLong(editTextZipCode.getText().toString().trim()));

        patient.setPriority(switchPriority.isChecked());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!editable) {
            getMenuInflater().inflate(R.menu.menu_patient_profile, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_patient_profile_edit, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_profile) {
            editable = !editable;
            editable();

            PatientProfileActivity.this.supportInvalidateOptionsMenu();
            setTitle(R.string.title_activity_patient_profile_edit);

            return true;
        } else if (id == R.id.action_delete_profile) {

            return true;
        } else if (id == R.id.action_save_edit_profile) {
            if (checkChanges()) {
                editable = !editable;
                editable();

                PatientProfileActivity.this.supportInvalidateOptionsMenu();
                setTitle(R.string.title_activity_patient_profile);

                saveChanges();
                Toast.makeText(PatientProfileActivity.this, "Changes saved.", Toast.LENGTH_SHORT).show();
                populateViews();
            }

            return true;
        } else if (id == R.id.action_cancel_edit_profile) {
            editable = !editable;
            editable();

            PatientProfileActivity.this.supportInvalidateOptionsMenu();
            setTitle(R.string.title_activity_patient_profile);

            populateViews();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
