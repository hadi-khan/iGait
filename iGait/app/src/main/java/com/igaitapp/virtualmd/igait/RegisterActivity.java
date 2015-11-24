package com.igaitapp.virtualmd.igait;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        final EditText editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextOfficePhoneNumber = (EditText) findViewById(R.id.editTextOfficePhoneNumber);
        final EditText editTextOfficeAddress = (EditText) findViewById(R.id.editTextOfficeAddress);
        final EditText editTextOfficeCity = (EditText) findViewById(R.id.editTextOfficeCity);
        final EditText editTextOfficeState = (EditText) findViewById(R.id.editTextOfficeState);
        final EditText editTextOfficeZipCode = (EditText) findViewById(R.id.editTextOfficeZipCode);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        final EditText editTextRePassword = (EditText) findViewById(R.id.editTextRePassword);

        final Button buttonSubmit = (Button) findViewById(R.id.submitButton);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                } else if (!InputCheck.name(firstName)) {
                    Toast.makeText(RegisterActivity.this, "Invalid first name.", Toast.LENGTH_SHORT).show();
                    editTextFirstName.requestFocus();
                } else if (!InputCheck.phoneNumber(officePhoneNumber)) {
                    Toast.makeText(RegisterActivity.this, "Invalid phone number.", Toast.LENGTH_SHORT).show();
                    editTextOfficePhoneNumber.requestFocus();
                } else if (!InputCheck.email(email)) {
                    Toast.makeText(RegisterActivity.this, "Invalid e-mail address.", Toast.LENGTH_SHORT).show();
                    editTextEmail.requestFocus();
                } else if (!InputCheck.address(officeAddress)) {
                    Toast.makeText(RegisterActivity.this, "Invalid office address.", Toast.LENGTH_SHORT).show();
                    editTextOfficeAddress.requestFocus();
                } else if (!InputCheck.city(officeCity)) {
                    Toast.makeText(RegisterActivity.this, "Invalid office city.", Toast.LENGTH_SHORT).show();
                    editTextOfficeCity.requestFocus();
                } else if (!InputCheck.state(officeState)) {
                    Toast.makeText(RegisterActivity.this, "Invalid office state.", Toast.LENGTH_SHORT).show();
                    editTextOfficeState.requestFocus();
                } else if (!InputCheck.zipCode(officeZipCode)) {
                    Toast.makeText(RegisterActivity.this, "Invalid office zip code.", Toast.LENGTH_SHORT).show();
                    editTextOfficeZipCode.requestFocus();
                } else if (!InputCheck.newPassword(password)) {
                    Toast.makeText(RegisterActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                    editTextPassword.requestFocus();
                } else if (!InputCheck.rePassword(password, rePassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    editTextRePassword.requestFocus();
                } else {
                    new ServerConnect().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/register",
                            lastName, firstName, officePhoneNumber, email, officeAddress, officeCity, officeState,
                            officeZipCode, password);
                }
            }
        });
    }

    private class ServerConnect extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            ContactInfo contactInfo = new ContactInfo(Long.parseLong(urls[3]), urls[4], urls[5],
                    urls[6], urls[7], Long.parseLong(urls[8]));
            User user = new User(urls[1], urls[2], contactInfo);
            user.setPassword(urls[9]);

            return POST(urls[0], user);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if (success.equals("true")) {
                    Toast.makeText(RegisterActivity.this, "Successfully registered.", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (success.equals("error")) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Invalid user registration.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static String POST(String urlPaths, User user) {
        String result = "";

        try {
            URL url = new URL(urlPaths);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            JSONObject jsonObjectUser = new JSONObject();
            JSONObject jsonObjectName = new JSONObject();
            JSONObject jsonObjectContact = new JSONObject();
            jsonObjectUser.put("email", user.getContactInfo().getEmail());
            jsonObjectUser.put("password", user.getPassword());
            jsonObjectName.put("first", user.getFirstName());
            jsonObjectName.put("last", user.getLastName());
            jsonObjectContact.put("mobilenumber", user.getContactInfo().getPhoneNumber());
            jsonObjectContact.put("officenumber", user.getContactInfo().getPhoneNumber());
            jsonObjectContact.put("officeaddress", user.getContactInfo().getAddress());
            jsonObjectContact.put("city", user.getContactInfo().getCity());
            jsonObjectContact.put("state", user.getContactInfo().getState());
            jsonObjectContact.put("zipcode", user.getContactInfo().getZipCode());
            jsonObjectUser.put("name", jsonObjectName);
            jsonObjectUser.put("contact", jsonObjectContact);
            String jsonString = jsonObjectUser.toString();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(30000);
            conn.connect();

            OutputStream os = conn.getOutputStream();
            os.write(jsonString.getBytes("UTF-8"));
            os.close();

            InputStream inputStream = new BufferedInputStream(conn.getInputStream());
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
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

        while((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        inputStream.close();
        return result;
    }
}
