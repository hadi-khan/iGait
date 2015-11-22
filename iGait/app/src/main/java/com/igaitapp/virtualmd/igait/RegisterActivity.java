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

        buttonSubmit = (Button) findViewById(R.id.submitButton);

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

    public static String POST(String urlPaths, User user) {
        InputStream inputStream = null;
        String result = "";
        String jsonString = "";

        try {
            URL url = new URL(urlPaths);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            JSONObject jsonObjectUser = new JSONObject();
            JSONObject jsonObjectName = new JSONObject();
            JSONObject jsonObjectContact = new JSONObject();
            try {
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

                jsonString = jsonObjectUser.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(30000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setInstanceFollowRedirects(false);
            conn.connect();

            OutputStream os = conn.getOutputStream();
            os.write(jsonString.getBytes("UTF-8"));
            os.close();

            inputStream = new BufferedInputStream(conn.getInputStream());
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = "Failure connecting.";
            }

            return result;
        } catch (IOException e) {
            Log.d("Server", "IOException reading data" + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (SecurityException e) {
            Log.d("Server", "SecException: needs permisssion");
            return null;
        }
    }

    private class ServerConnect extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            User user = new User(urls[1], urls[2], new ContactInfo(Long.parseLong(urls[3]), urls[4],
                    urls[5], urls[6], urls[7], Long.parseLong(urls[8])));

            user.setPassword(urls[9]);

            return POST(urls[0], user);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String success = "";

            try {
                JSONObject jsonObject = new JSONObject(result);
                success = jsonObject.getString("success").toString();

                if (success.equals("true")) {

                    Toast.makeText(RegisterActivity.this, "Successfully registered.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Could not register.", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";

        while((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        inputStream.close();
        return result;
    }
}
