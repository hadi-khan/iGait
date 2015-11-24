package com.igaitapp.virtualmd.igait;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Button buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        Button buttonForgotPassword = (Button) findViewById(R.id.buttonForgotPassword);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUserName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (!InputCheck.email(username)) {
                    Toast.makeText(LoginActivity.this, "Invalid e-mail address.", Toast.LENGTH_SHORT).show();
                    editTextUserName.requestFocus();
                } else if (!InputCheck.password(password)) {
                    Toast.makeText(LoginActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                    editTextPassword.requestFocus();
                } else {
                    new ServerConnect().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/authentication",
                            username, password);
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private class ServerConnect extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0], urls[1], urls[2]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if (success.equals("true")) {
                    JSONObject jsonObjectUser = new JSONObject(message);
                    JSONObject jsonObjectContact = jsonObjectUser.getJSONObject("contact");
                    JSONObject jsonObjectName = jsonObjectUser.getJSONObject("name");

                    String firstName = jsonObjectName.getString("first");
                    String lastName = jsonObjectName.getString("last");
                    Long officePhoneNumber = jsonObjectContact.getLong("officenumber");
                    String email = jsonObjectUser.getString("email");
                    String officeAddress = jsonObjectContact.getString("officeaddress");
                    String officeCity = jsonObjectContact.getString("city");
                    String officeState = jsonObjectContact.getString("state");
                    Long officeZipcode = jsonObjectContact.getLong("zipcode");
                    String token = jsonObjectUser.getString("accessToken");

                    ContactInfo contactInfo = new ContactInfo(officePhoneNumber, email,
                            officeAddress, officeCity, officeState, officeZipcode);
                    User user = new User(lastName, firstName, contactInfo);
                    user.setToken(token);

                    Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.EXTRA_USER, user);
                    startActivity(intent);

                    finish();
                } else if (success.equals("error")) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Invalid e-mail or password.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static String POST(String urlPaths, String username, String password) {
        String result = "";

        try {
            URL url = new URL(urlPaths);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", username);
            jsonObject.put("password", password);
            String jsonString = jsonObject.toString();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(50000);
            conn.connect();

            OutputStream os = conn.getOutputStream();
            os.write(jsonString.getBytes("UTF-8"));
            os.close();

            String token = conn.getHeaderField("Authorization");

            InputStream inputStream = new BufferedInputStream(conn.getInputStream());
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                jsonObject = new JSONObject(result);

                JSONObject jsonObjectUser = jsonObject.getJSONObject("message");
                jsonObjectUser.put("accessToken", token);

                jsonObject.put("message", jsonObjectUser);
                result = jsonObject.toString();
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

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        inputStream.close();
        return result;
    }
}
