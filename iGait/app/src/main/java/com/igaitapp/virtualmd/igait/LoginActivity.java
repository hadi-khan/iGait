package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUserName;
    private EditText editTextPassword;
    private Button buttonSubmit;
    private Button buttonRegister;
    private Button buttonForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonForgotPassword = (Button) findViewById(R.id.buttonForgotPassword);

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
                    new ServerLogin().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/authentication",
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

    public static String POST(String urlPaths, String username, String password)  {
        InputStream inputStream = null;
        String jsonString = "";
        String result = "";
        String token = "";

        try {
            URL url = new URL(urlPaths);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", username);
            jsonObject.put("password", password);
            jsonString = jsonObject.toString();

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

            token = conn.getHeaderField("Authorization");

            inputStream = new BufferedInputStream(conn.getInputStream());
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
                jsonObject = new JSONObject(result);

                JSONObject jsonObjectUser = jsonObject.getJSONObject("message");
                jsonObjectUser.put("accessToken", token);

                jsonObject.put("message", jsonObjectUser);
                result = jsonObject.toString();
            }
            else {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    private class ServerLogin extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0], urls[1], urls[2]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String success = "";
            String message = "";

            try {
                JSONObject jsonObject = new JSONObject(result);
                success = jsonObject.getString("success").toString();

                if (success.equals("true")) {
                    message = jsonObject.getString("message").toString();

                    JSONObject jsonObjectUser = new JSONObject(message);
                    JSONObject jsonObjectContact = jsonObjectUser.getJSONObject("contact");
                    JSONObject jsonObjectName = jsonObjectUser.getJSONObject("name");

                    String firstName = jsonObjectName.getString("first").toString();
                    String lastName = jsonObjectName.getString("last").toString();
                    Long officePhoneNumber = jsonObjectContact.getLong("mobilenumber");
                    String email = jsonObjectUser.getString("email").toString();
                    String officeAddress = jsonObjectContact.getString("officeaddress").toString();
                    String officeCity = jsonObjectContact.getString("city").toString();
                    String officeState = jsonObjectContact.getString("state").toString();
                    Long officeZipcode = jsonObjectContact.getLong("zipcode");
                    String token = jsonObjectUser.getString("accessToken").toString();

                    User user = new User(lastName, firstName, new ContactInfo(officePhoneNumber, email,
                            officeAddress, officeCity, officeState, officeZipcode));

                    user.setToken(token);

                    Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.EXTRA_USER, user);
                    intent.putExtra(MainActivity.EXTRA_PARENT_ID, "login");
                    startActivity(intent);

                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Invalid e-mail or password.", Toast.LENGTH_LONG).show();
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
