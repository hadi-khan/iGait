package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    private TextView textViewLastName;
    private TextView textViewFirstName;
    private TextView textViewEmail;
    private TextView textViewOfficePhoneNumber;
    private TextView textViewOfficeAddress;
    private TextView textViewOfficeCity;
    private TextView textViewOfficeState;
    private TextView textViewOfficeZipCode;
    private TextView textViewPassword;
    private TextView textViewRePasswordCatg;

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

    private boolean editable = false;

    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = (User) getIntent().getSerializableExtra(MainActivity.EXTRA_USER);

        populateViews();
        editable();
    }

    private void populateViews() {
        textViewLastName = (TextView) findViewById(R.id.textViewLastName);
        textViewFirstName = (TextView) findViewById(R.id.textViewFirstName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewOfficePhoneNumber = (TextView) findViewById(R.id.textViewOfficePhoneNumber);
        textViewOfficeAddress = (TextView) findViewById(R.id.textViewOfficeAddress);
        textViewOfficeCity = (TextView) findViewById(R.id.textViewOfficeCity);
        textViewOfficeState = (TextView) findViewById(R.id.textViewOfficeState);
        textViewOfficeZipCode = (TextView) findViewById(R.id.textViewOfficeZipCode);
        textViewPassword = (TextView) findViewById(R.id.textViewPassword);

        textViewRePasswordCatg = (TextView) findViewById(R.id.textViewRePasswordCatg);

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

        textViewLastName.setText(user.getLastName());
        textViewFirstName.setText(user.getFirstName());
        textViewEmail.setText(user.getContactInfo().getEmail());
        textViewOfficePhoneNumber.setText(user.getContactInfo().getPhoneNumber().toString());
        textViewOfficeAddress.setText(user.getContactInfo().getAddress());
        textViewOfficeCity.setText(user.getContactInfo().getCity());
        textViewOfficeState.setText(user.getContactInfo().getState());
        textViewOfficeZipCode.setText(Long.toString(user.getContactInfo().getZipCode()));

        editTextLastName.setText(user.getLastName());
        editTextFirstName.setText(user.getFirstName());
        editTextEmail.setText(user.getContactInfo().getEmail());
        editTextOfficePhoneNumber.setText(user.getContactInfo().getPhoneNumber().toString());
        editTextOfficeAddress.setText(user.getContactInfo().getAddress());
        editTextOfficeCity.setText(user.getContactInfo().getCity());
        editTextOfficeState.setText(user.getContactInfo().getState());
        editTextOfficeZipCode.setText(Long.toString(user.getContactInfo().getZipCode()));
        editTextPassword.setText("");
        editTextRePassword.setText("");
    }

    private void editable() {
        if (!editable) {
            textViewLastName.setVisibility(View.VISIBLE);
            textViewFirstName.setVisibility(View.VISIBLE);
            textViewEmail.setVisibility(View.VISIBLE);
            textViewOfficePhoneNumber.setVisibility(View.VISIBLE);
            textViewOfficeAddress.setVisibility(View.VISIBLE);
            textViewOfficeCity.setVisibility(View.VISIBLE);
            textViewOfficeState.setVisibility(View.VISIBLE);
            textViewOfficeZipCode.setVisibility(View.VISIBLE);
            textViewPassword.setVisibility(View.VISIBLE);

            textViewRePasswordCatg.setVisibility(View.GONE);

            editTextLastName.setVisibility(View.GONE);
            editTextFirstName.setVisibility(View.GONE);
            editTextEmail.setVisibility(View.GONE);
            editTextOfficePhoneNumber.setVisibility(View.GONE);
            editTextOfficeAddress.setVisibility(View.GONE);
            editTextOfficeCity.setVisibility(View.GONE);
            editTextOfficeState.setVisibility(View.GONE);
            editTextOfficeZipCode.setVisibility(View.GONE);
            editTextPassword.setVisibility(View.GONE);
            editTextRePassword.setVisibility(View.GONE);
        } else {
            textViewLastName.setVisibility(View.GONE);
            textViewFirstName.setVisibility(View.GONE);
            textViewEmail.setVisibility(View.GONE);
            textViewOfficePhoneNumber.setVisibility(View.GONE);
            textViewOfficeAddress.setVisibility(View.GONE);
            textViewOfficeCity.setVisibility(View.GONE);
            textViewOfficeState.setVisibility(View.GONE);
            textViewOfficeZipCode.setVisibility(View.GONE);
            textViewPassword.setVisibility(View.GONE);

            textViewRePasswordCatg.setVisibility(View.VISIBLE);

            editTextLastName.setVisibility(View.VISIBLE);
            editTextFirstName.setVisibility(View.VISIBLE);
            editTextEmail.setVisibility(View.VISIBLE);
            editTextOfficePhoneNumber.setVisibility(View.VISIBLE);
            editTextOfficeAddress.setVisibility(View.VISIBLE);
            editTextOfficeCity.setVisibility(View.VISIBLE);
            editTextOfficeState.setVisibility(View.VISIBLE);
            editTextOfficeZipCode.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);
            editTextRePassword.setVisibility(View.VISIBLE);
        }
    }

    private boolean checkChanges() {
        List<String> changes = new ArrayList<>();
        List<String> original = new ArrayList<>();
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
        boolean result = false;

        changes.add(lastName);
        changes.add(firstName);
        changes.add(email);
        changes.add(officePhoneNumber);
        changes.add(officeAddress);
        changes.add(officeCity);
        changes.add(officeState);
        changes.add(officeZipCode);

        original.add(user.getLastName());
        original.add(user.getFirstName());
        original.add(user.getContactInfo().getEmail());
        original.add(Long.toString(user.getContactInfo().getPhoneNumber()));
        original.add(user.getContactInfo().getAddress());
        original.add(user.getContactInfo().getCity());
        original.add(user.getContactInfo().getState());
        original.add(Long.toString(user.getContactInfo().getZipCode()));

        if (InputCheck.noChanges(changes, original) && InputCheck.unChangedPassword(password)) {
            Toast.makeText(UserProfileActivity.this, "Cannot save. No changes have been made.", Toast.LENGTH_SHORT).show();
        } else if (!InputCheck.name(lastName)) {
            Toast.makeText(UserProfileActivity.this, "Invalid last name.", Toast.LENGTH_SHORT).show();
            editTextLastName.requestFocus();
        } else if (!InputCheck.name(firstName)) {
            Toast.makeText(UserProfileActivity.this, "Invalid first name.", Toast.LENGTH_SHORT).show();
            editTextFirstName.requestFocus();
        } else if (!InputCheck.email(email)) {
            Toast.makeText(UserProfileActivity.this, "Invalid e-mail address.", Toast.LENGTH_SHORT).show();
            editTextEmail.requestFocus();
        } else if (!InputCheck.phoneNumber(officePhoneNumber)) {
            Toast.makeText(UserProfileActivity.this, "Invalid phone number.", Toast.LENGTH_SHORT).show();
            editTextOfficePhoneNumber.requestFocus();
        } else if (!InputCheck.address(officeAddress)) {
            Toast.makeText(UserProfileActivity.this, "Invalid office address.", Toast.LENGTH_SHORT).show();
            editTextOfficeAddress.requestFocus();
        } else if (!InputCheck.city(officeCity)) {
            Toast.makeText(UserProfileActivity.this, "Invalid office city.", Toast.LENGTH_SHORT).show();
            editTextOfficeCity.requestFocus();
        } else if (!InputCheck.state(officeState)) {
            Toast.makeText(UserProfileActivity.this, "Invalid office state.", Toast.LENGTH_SHORT).show();
            editTextOfficeState.requestFocus();
        } else if (!InputCheck.zipCode(officeZipCode)) {
            Toast.makeText(UserProfileActivity.this, "Invalid office zip code.", Toast.LENGTH_SHORT).show();
            editTextOfficeZipCode.requestFocus();
        } else if (!InputCheck.unChangedPassword(password)) {
            if (!InputCheck.newPassword(password)) {
                Toast.makeText(UserProfileActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                editTextRePassword.requestFocus();
            } else if (!InputCheck.rePassword(password, rePassword)) {
                Toast.makeText(UserProfileActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                editTextRePassword.requestFocus();
            } else {
                result = true;
            }

        } else {
            result = true;
        }

        return result;
    }

    private void saveChanges() {
        user.setLastName(editTextLastName.getText().toString().trim());
        user.setFirstName(editTextFirstName.getText().toString().trim());
        user.getContactInfo().setEmail(editTextEmail.getText().toString().trim());
        user.getContactInfo().setPhoneNumber(Long.parseLong(editTextOfficePhoneNumber.getText().toString().trim()));
        user.getContactInfo().setAddress(editTextOfficeAddress.getText().toString().trim());
        user.getContactInfo().setCity(editTextOfficeCity.getText().toString().trim());
        user.getContactInfo().setState(editTextOfficeState.getText().toString().trim());
        user.getContactInfo().setZipCode(Long.parseLong(editTextOfficeZipCode.getText().toString().trim()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!editable) {
            getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_user_profile_edit, menu);
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

            UserProfileActivity.this.supportInvalidateOptionsMenu();
            setTitle(R.string.title_activity_user_profile_edit);

            return true;
        } else if (id == R.id.action_logout) {
            Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Toast.makeText(UserProfileActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();

            startActivity(intent);

            return true;
        } else if (id == R.id.action_save_edit_profile) {
            if (checkChanges()) {
                editable = !editable;
                editable();

                UserProfileActivity.this.supportInvalidateOptionsMenu();
                setTitle(R.string.title_activity_user_profile);

                saveChanges();
                Toast.makeText(UserProfileActivity.this, "Changes saved.", Toast.LENGTH_SHORT).show();
                populateViews();
            }

            return true;
        } else if (id == R.id.action_cancel_edit_profile) {
            editable = !editable;
            editable();

            UserProfileActivity.this.supportInvalidateOptionsMenu();
            setTitle(R.string.title_activity_user_profile);

            populateViews();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
