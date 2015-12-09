package com.igaitapp.virtualmd.igait;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    private HashMap<String, String> changesMade = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        patient = (Patient) getIntent().getSerializableExtra(MainActivity.EXTRA_PATIENT);

        populateViews();
        editable();
    }

    private void populateViews() {
        DecimalFormat dfp = new DecimalFormat("#0000000000");
        DecimalFormat dfz = new DecimalFormat("#00000");
        DateFormat tf = new SimpleDateFormat("HH:mm:ss");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

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
        textViewPhoneNumber.setText(dfp.format(patient.getContactInfo().getPhoneNumber()));
        textViewExpectedWalkTime.setText(tf.format(patient.getExpectedWalkTime().getTime()));
        textViewBirthday.setText(df.format(patient.getBirthday()));
        textViewSex.setText(Character.toString(patient.getSex()));
        textViewAddress.setText(patient.getContactInfo().getAddress());
        textViewCity.setText(patient.getContactInfo().getCity());
        textViewState.setText(patient.getContactInfo().getState());
        textViewZipCode.setText(dfz.format(patient.getContactInfo().getZipCode()));

        editTextLastName.setText(patient.getLastName());
        editTextFirstName.setText(patient.getFirstName());
        editTextEmail.setText(patient.getContactInfo().getEmail());
        editTextPhoneNumber.setText(dfp.format(patient.getContactInfo().getPhoneNumber()));
        editTextExpectedWalkTime.setText(tf.format(patient.getExpectedWalkTime().getTime()));
        editTextBirthday.setText(df.format(patient.getBirthday()));
        editTextSex.setText(Character.toString(patient.getSex()));
        editTextAddress.setText(patient.getContactInfo().getAddress());
        editTextCity.setText(patient.getContactInfo().getCity());
        editTextState.setText(patient.getContactInfo().getState());
        editTextZipCode.setText(dfz.format(patient.getContactInfo().getZipCode()));

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

    @Override
    protected void onPause(){
        super.onPause();

        closeKB();
    }

    private void closeKB() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        editTextLastName.requestFocus();
        imm.hideSoftInputFromWindow(editTextLastName.getWindowToken(), 0);
    }

    private boolean checkChanges() {
        DateFormat tf = new SimpleDateFormat("HH:mm:ss");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        HashMap<String, String> changesMade = new HashMap<>();
        boolean result = false;

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

        if (!lastName.equals(patient.getLastName())){
            changesMade.put("last", lastName);
        }
        if (!firstName.equals(patient.getFirstName())){
            changesMade.put("first", firstName);
        }
        if (!email.equals(patient.getContactInfo().getEmail())){
            changesMade.put("email", email);
        }
        if (!phoneNumber.equals(Long.toString(patient.getContactInfo().getPhoneNumber()))) {
            changesMade.put("mobilenumber", phoneNumber);
        }
        if (!expectedWalkTime.equals(tf.format(patient.getExpectedWalkTime()))) {
            changesMade.put("expectedWalkTime", expectedWalkTime);
        }
        if (!birthday.equals(df.format(patient.getBirthday()))) {
            changesMade.put("dateOfBirth", birthday);
        }
        if (!sex.equals(Character.toString(patient.getSex()))) {
            changesMade.put("gender", sex);
        }
        if (!address.equals(patient.getContactInfo().getAddress())){
            changesMade.put("address", address);
        }
        if (!city.equals(patient.getContactInfo().getCity())) {
            changesMade.put("city", city);
        }
        if (!state.equals(patient.getContactInfo().getState())) {
            changesMade.put("state", state);
        }
        if (!zipCode.equals(Long.toString(patient.getContactInfo().getZipCode()))) {
            changesMade.put("zipcode", zipCode);
        }
        if (priority != patient.isPriority()) {
            changesMade.put("priority", Boolean.toString(priority));
        }

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
            Toast.makeText(PatientProfileActivity.this, "Invalid address.", Toast.LENGTH_SHORT).show();
            editTextAddress.requestFocus();
        } else if (!InputCheck.city(city)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid city.", Toast.LENGTH_SHORT).show();
            editTextCity.requestFocus();
        } else if (!InputCheck.state(state)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid state.", Toast.LENGTH_SHORT).show();
            editTextState.requestFocus();
        } else if (!InputCheck.zipCode(zipCode)) {
            Toast.makeText(PatientProfileActivity.this, "Invalid zip code.", Toast.LENGTH_SHORT).show();
            editTextZipCode.requestFocus();
        } else {
            result = true;
            this.changesMade = changesMade;
        }

        return result;
    }

    private class ServerConnect extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... urls) {
            String keyWord = (String) urls[0];
            String urlPaths;
            String userToken;
            String userId;

            if(keyWord.equals("DELETE")){
                urlPaths = (String) urls[1];
                HashMap<String, String> changesMade = (HashMap<String, String>) urls[2];
                userToken = (String) urls[3];
                userId = (String) urls[4];
            } else {
                keyWord = "EDIT";
                urlPaths = (String) urls[0];
                HashMap<String, String> changesMade = (HashMap<String, String>) urls[1];
                userToken = (String) urls[2];
                userId = (String) urls[3];
            }

            try {
                return PUT(keyWord,urlPaths, changesMade, userToken, userId);
            } catch (JSONException e) {
                Toast.makeText(getBaseContext(), "Data error.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            return keyWord;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if (success.equals("true")) {
                    if(!(message.equals("patient deleted"))){
                        saveChanges();
                        populateViews();
                        changesMade = new HashMap<>();
                        Toast.makeText(getBaseContext(), "Changes saved.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Patient deleted.", Toast.LENGTH_LONG).show();

                        Session.getPatientListMap().removeUsingKey(patient.getId());

                        Intent intent = new Intent(PatientProfileActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } else if (success.equals("error")) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Invalid patient changes.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getBaseContext(), "Data error.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    public  static String PUT(String key,String urlPaths, HashMap<String, String> changesMade, String userToken, String userID) throws JSONException {
        String result = "";

        if(key.equals("EDIT")){
            DateFormat tf = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat tfh = new SimpleDateFormat("H");
            SimpleDateFormat tfm = new SimpleDateFormat("m");
            SimpleDateFormat tfs = new SimpleDateFormat("s");

            try {
                JSONObject jsonObject = new JSONObject();
                if(changesMade.get("last") != null){
                    jsonObject.put("name.last", changesMade.get("last"));
                }
                if(changesMade.get("first") != null){
                    jsonObject.put("name.first", changesMade.get("first"));
                }
                if(changesMade.get("email") != null){
                    jsonObject.put("contact.email", changesMade.get("email"));
                }
                if(changesMade.get("mobilenumber") != null){
                    jsonObject.put("contact.mobilenumber", Long.parseLong(changesMade.get("mobilenumber")));
                }
                if(changesMade.get("expectedWalkTime") != null) {
                    Date expectedWalkTime = tf.parse(changesMade.get("expectedWalkTime"));

                    int hour = Integer.parseInt(tfh.format(expectedWalkTime));
                    int minute = Integer.parseInt(tfm.format(expectedWalkTime));
                    int second = Integer.parseInt(tfs.format(expectedWalkTime));

                    jsonObject.put("expectedWalkTime.hour", hour);
                    jsonObject.put("expectedWalkTime.minute", minute);
                    jsonObject.put("expectedWalkTime.second", second);
                }
                if(changesMade.get("dateOfBirth") != null){
                    jsonObject.put("dateOfBirth", changesMade.get("dateOfBirth"));
                }
                if(changesMade.get("gender") != null){
                    jsonObject.put("gender", changesMade.get("gender"));
                }
                if(changesMade.get("address") != null){
                    jsonObject.put("contact.address", changesMade.get("address"));
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
                if(changesMade.get("priority") != null){
                    jsonObject.put("priority", Boolean.parseBoolean(changesMade.get("priority")));
                }
                String jsonString = jsonObject.toString();


                URL url = new URL(urlPaths);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Authorization", userToken);
                conn.setRequestProperty("Id", userID);
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
                return "{\"success\":error,\"message\":\"Data error.\"}";
            } catch (ParseException pe) {
                pe.printStackTrace();
                return "{\"success\":error,\"message\":\"Data error.\"}";
            }

        } else {
            try {
                JSONObject jsonObject = new JSONObject();
                String jsonString = jsonObject.toString();
                URL url = new URL(urlPaths);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Authorization", userToken);
                conn.setRequestProperty("id", userID);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setConnectTimeout(50000);
                conn.connect();

                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {
                    result = "{\"success\":true,\"message\":\"patient deleted\"}";
                } else {
                    result = "{\"success\":false,\"message\":\"patient not deleted\"}";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "{\"success\":error,\"message\":\"Connection error.\"}";
            } catch (SecurityException e) {
                e.printStackTrace();
                return "{\"success\":error,\"message\":\"Connection error.\"}";
            }
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
        DateFormat tf = new SimpleDateFormat("HH:mm:ss");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

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

        Session.getPatientListMap().replaceUsingKey(patient.getId(), patient);

        closeKB();
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
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_edit_profile) {
            editable = !editable;
            editable();

            PatientProfileActivity.this.supportInvalidateOptionsMenu();
            setTitle(R.string.title_activity_patient_profile_edit);

            return true;
        } else if (id == R.id.action_delete_profile) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            alertDialog.setTitle("Confirm Delete...");

            // Setting Dialog Message
            alertDialog.setMessage("Delete this patient?");

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Patient will be deleted
                    Toast.makeText(getApplicationContext(), "Deleting Patient", Toast.LENGTH_SHORT).show();
                    new ServerConnect().execute("DELETE", "http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/patient/" + patient.getId(),
                            changesMade, patient.getUserToken(), patient.getUserId());
                    finish();
                }

            });

            // Patient will not be deleted
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Close the dialog.
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();
            return true;
        } else if (id == R.id.action_save_edit_profile) {
            if (checkChanges()) {
                editable = !editable;
                editable();

                PatientProfileActivity.this.supportInvalidateOptionsMenu();
                setTitle(R.string.title_activity_patient_profile);

                new ServerConnect().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/patient/" + patient.getId(),
                        changesMade, patient.getUserToken(), patient.getUserId());
            }

            return true;
        } else if (id == R.id.action_cancel_edit_profile) {
            editable = !editable;
            editable();

            PatientProfileActivity.this.supportInvalidateOptionsMenu();
            setTitle(R.string.title_activity_patient_profile);

            closeKB();
            populateViews();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
