package com.igaitapp.virtualmd.igait;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
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

    private HashMap<String, String> changesMade = new HashMap<>();

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
        boolean result = false;

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

        if(!lastName.equals(user.getLastName())){
            changesMade.put("last", lastName);
        }
        if(!firstName.equals(user.getFirstName())){
            changesMade.put("first", firstName);
        }
        if(!email.equals(user.getContactInfo().getEmail())){
            changesMade.put("email", email);
        }
        if(!officePhoneNumber.equals(Long.toString(user.getContactInfo().getPhoneNumber()))){
            changesMade.put("officenumber", officePhoneNumber);
        }
        if(!officeAddress.equals(user.getContactInfo().getAddress())){
            changesMade.put("officeaddress", officeAddress);
        }
        if(!officeCity.equals(user.getContactInfo().getCity())){
            changesMade.put("city", officeCity);
        }
        if(!officeState.equals(user.getContactInfo().getState())){
            changesMade.put("state", officeState);
        }
        if(!officeZipCode.equals(Long.toString(user.getContactInfo().getZipCode()))){
            changesMade.put("zipcode", officeZipCode);
        }

        if (changesMade.isEmpty() && InputCheck.unChangedPassword(password)) {
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
                changesMade.put("password", password);
                changesMade.put("repassword", rePassword);;
                result = true;
            }
        } else {
            result = true;
        }

        return result;
    }

    private class ServerConnect extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... urls) {
            String urlPaths = (String) urls[0];
            HashMap<String, String> changesMade = (HashMap<String, String>) urls[1];
            String token = (String) urls[2];
            String id = (String) urls[3];

            return PUT(urlPaths, changesMade, token, id);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if (success.equals("true")) {
                    saveChanges();
                    populateViews();
                    Toast.makeText(getBaseContext(), "Changes saved.", Toast.LENGTH_LONG).show();
                } else if (success.equals("error")) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Invalid user changes.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public  static String PUT(String urlPaths, HashMap<String, String> changesMade, String token, String id) {
        String result = "";

        try {
            JSONObject jsonObject = new JSONObject();
            if(changesMade.get("last") != null){
                jsonObject.put("name.last", changesMade.get("last"));
            }
            if(changesMade.get("first") != null){
                jsonObject.put("name.first", changesMade.get("first"));
            }
            if(changesMade.get("email") != null){
                jsonObject.put("email", changesMade.get("email"));
            }
            if(changesMade.get("officenumber") != null){
                jsonObject.put("contact.officenumber", Long.parseLong(changesMade.get("officenumber")));
            }
            if(changesMade.get("officeaddress") != null){
                jsonObject.put("contact.officeaddress", changesMade.get("officeaddress"));
            }
            if(changesMade.get("city") != null){
                jsonObject.put("contact.city", changesMade.get("city"));
            }
            if(changesMade.get("state") != null){
                jsonObject.put("contact.state", changesMade.get("state"));
            }
            if(changesMade.get("zipcode") != null){
                jsonObject.put("contact.zipcode", Long.parseLong(changesMade.get("zipcode")));
            }
            if(changesMade.get("password") != null){
                jsonObject.put("password", changesMade.get("password"));
            }
            String jsonString = jsonObject.toString();


            URL url = new URL(urlPaths);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Authorization", token);
            conn.setRequestProperty("Id", id);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(50000);
            conn.connect();

            OutputStream os = conn.getOutputStream();
            os.write(jsonString.getBytes("UTF-8"));
            os.close();

            InputStream inputStream = new BufferedInputStream(conn.getInputStream());
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
            }
            else {
                result = "{\"success\":error,\"message\":\"Connection error.\"}";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"success\":error,\"message\":\"Connection error.\"}";
        } catch (SecurityException e) {
            e.printStackTrace();
            return "{\"success\":error,\"message\":\"Connection error.\"}";
        } catch (JSONException e) {
            e.printStackTrace();
            return "{\"success\":error,\"message\":\"Connection error.\"}";
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        inputStream.close();
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
        if (id == R.id.home || id == R.id.up) {
//            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
//            intent.putExtra(MainActivity.EXTRA_USER, user);
//            startActivity(intent);
            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
            intent.putExtra("result", user);
            setResult(Activity.RESULT_OK, intent);
            finish();

            return true;
        }
        else if (id == R.id.action_edit_profile) {
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

                new ServerConnect().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/account",
                        changesMade, user.getToken(), user.getId());
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
