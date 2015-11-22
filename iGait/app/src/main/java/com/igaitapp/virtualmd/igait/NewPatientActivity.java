package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

        final User user = (User) getIntent().getSerializableExtra(MainActivity.EXTRA_USER);

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
                    new ServerConnect().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/patient",
                            lastName, firstName, expectedWalkTime, birthday, sex,
                            phoneNumber, email, address, city, state, zipCode, user.getToken(),
                            user.getContactInfo().getEmail());
                }
            }
        });
    }

    public static String POST(String urlPaths, NewPatient newPatient, String userToken, String userEmail) {
        InputStream inputStream = null;
        String result = "";
        String jsonString = "";

        try {
            URL url = new URL(urlPaths);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            JSONObject jsonObjectNewPatient = new JSONObject();
            JSONObject jsonObjectName = new JSONObject();
            JSONObject jsonObjectContact = new JSONObject();
            try {
                jsonObjectNewPatient.put("email", newPatient.getContactInfo().getEmail());
                jsonObjectName.put("first", newPatient.getFirstName());
                jsonObjectName.put("last", newPatient.getLastName());
//                jsonObjectContact.put("mobilnumber", newPatient.getContactInfo().getPhoneNumber());
                jsonObjectContact.put("number", newPatient.getContactInfo().getPhoneNumber());
                jsonObjectContact.put("address", newPatient.getContactInfo().getAddress());
//                jsonObjectContact.put("city", newPatient.getContactInfo().getCity());
//                jsonObjectContact.put("state", newPatient.getContactInfo().getState());
//                jsonObjectContact.put("zipcode", newPatient.getContactInfo().getZipCode());

                jsonObjectNewPatient.put("name", jsonObjectName);
                jsonObjectNewPatient.put("contact", jsonObjectContact);

                jsonString = jsonObjectNewPatient.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", userToken);
            conn.setRequestProperty("Email", userEmail);
//            conn.setDoOutput(true);
//            conn.setConnectTimeout(30000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
//            conn.setInstanceFollowRedirects(false);
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

            Log.d("SERVER", result);

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
            DateFormat tf = new SimpleDateFormat("HH:mm:ss");
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            NewPatient newPatient = new NewPatient();

            try {
                newPatient = new NewPatient(urls[1], urls[2], tf.parse(urls[3]), df.parse(urls[4]),
                    urls[5].charAt(0), new ContactInfo(Long.parseLong(urls[6]), urls[7], urls[8], urls[9], urls[10],
                            Long.parseLong(urls[11])));
            } catch (ParseException pe) {
                pe.printStackTrace();
            }

            return POST(urls[0], newPatient, urls[12], urls[13]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String success = "";

            try {
                JSONObject jsonObject = new JSONObject(result);
                success = jsonObject.getString("success").toString();

                if (success.equals("true")) {
                    Toast.makeText(NewPatientActivity.this, "New patient added.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Could not add new patient.", Toast.LENGTH_LONG).show();
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

    private Intent getParentActivityIntentImpl() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_PARENT_ID, "npprofile");

        return intent;
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
