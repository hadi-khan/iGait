package com.igaitapp.virtualmd.igait;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ForgotPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        final Button submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!InputCheck.email(email)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid e-mail address.", Toast.LENGTH_SHORT).show();
                    emailEditText.requestFocus();
                } else if (!InputCheck.password(password)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid e-mail address.", Toast.LENGTH_SHORT).show();
                    passwordEditText.requestFocus();
                } else {
                    new ServerConnect().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/reset",
                            email, password);
                }
            }
        });
    }

    public static String POST(String urlPath, String email, String password) {
        String result = "";

        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
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
            return "{\"success\":error,\"message\":\"Data error.\"}";
        }

        return result;
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
                    Toast.makeText(getBaseContext(), "Password reset.", Toast.LENGTH_LONG).show();
                    finish();
                } else if (success.equals("error")) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Invalid e-mail or password.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getBaseContext(), "Data error.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
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