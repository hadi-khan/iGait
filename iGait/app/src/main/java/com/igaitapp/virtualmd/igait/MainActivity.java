package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String EXTRA_PATIENT = "com.igaitapp.virtualmd.igait.PATIENT";
    static final String EXTRA_PATIENT_LIST = "com.igaitapp.virtualmd.igait.PATIENT_LIST";
    static final String EXTRA_USER = "com.igaitapp.virtualmd.igait.USER";
    static final String EXTRA_RESULT = "com.igaitapp.virtualmd.igait.USER_RESULT";

    private User user = new User();
    private List<Patient> patientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (User) getIntent().getSerializableExtra(EXTRA_USER);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new ServerConnect().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/patient",
                "http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/patient/health/");
    }

    @Override public void onBackPressed(){
        Toast.makeText(getBaseContext(), "Logout to visit the login screen.", Toast.LENGTH_LONG).show();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        User userResult = user;
//
//        if (requestCode == 1) {
//            if(resultCode == Activity.RESULT_OK) {
//                userResult = (User) data.getSerializableExtra("result");
//            }
//        }
//
//        Log.d(":(", userResult.getLastName());
//        user = new User();
//        user = userResult;
//    }

    private void populateListViewPatients() {
        if (!patientList.isEmpty()) {
            ListView list = (ListView) findViewById(R.id.listViewPatients);
            PatientListAdapter adapter = new PatientListAdapter(this, patientList);

            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Patient tappedPatient = patientList.get(position);
                    Intent intent = new Intent(MainActivity.this, CalendarActivity.class);

                    intent.putExtra(EXTRA_PATIENT, tappedPatient);

                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(getBaseContext(), "User has no patients.", Toast.LENGTH_LONG).show();
        }
    }

    private class ServerConnect extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0], urls[1], user.getToken(), user.getId());
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
            SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");

            try {
                JSONObject jsonObject = new JSONObject(result);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                if (success.equals("true")) {
                    patientList = new ArrayList<>();

                    JSONArray jsonArrayPatients = jsonObject.getJSONArray("message");
                    for (int i = 0; i < jsonArrayPatients.length(); i++) {
                        JSONObject patient = jsonArrayPatients.getJSONObject(i);
                        JSONObject name = patient.getJSONObject("name");
                        JSONObject contact = patient.getJSONObject("contact");
                        JSONObject expectedWalkTime = patient.getJSONObject("expectedWalkTime");
                        JSONArray healthArray = patient.getJSONArray("health");

                        ContactInfo ci = new ContactInfo(contact.getLong("zipcode"), contact.getString("email"),
                                contact.getString("address"), contact.getString("city"), contact.getString("state"),
                                contact.getLong("zipcode"));

                        List<GaitHealth> gil = new ArrayList<>();
                        for (int o = 0; o < healthArray.length(); o++) {
                            JSONObject health = healthArray.getJSONObject(i);

                            gil.add(new GaitHealth(health.getInt("health"), df.parse(health.getString("start_time")),
                                    df.parse(health.getString("end_time")), false));
                        }

                        Date ewt = tf.parse(expectedWalkTime.getString("hour") + ":" +
                                expectedWalkTime.getString("minute") + ":" +
                                expectedWalkTime.getString("second"));

                        patientList.add(new Patient(name.getString("last"), name.getString("first"),
                                ewt, df.parse(patient.getString("dateOfBirth")),
                                patient.getString("gender").charAt(0), ci, gil,
                                patient.getBoolean("priority")));
                    }

                    populateListViewPatients();
                } else if (success.equals("error")) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Could not sync patients.", Toast.LENGTH_LONG).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static String GET(String urlPaths, String urlPaths1, String token, String id) {
        String result = "";

        try {
            URL url = new URL(urlPaths);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", token);
            conn.setRequestProperty("id", id);
            conn.setConnectTimeout(50000);
            conn.connect();

            InputStream inputStream = new BufferedInputStream(conn.getInputStream());
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                JSONObject jsonObject = new JSONObject(result);
                String success = jsonObject.getString("success");

                if (success.equals("true")) {
                    JSONArray jsonArrayPatients = jsonObject.getJSONArray("message");
                    for (int i = 0; i < jsonArrayPatients.length(); i++) {
                        url = new URL(urlPaths1 + jsonArrayPatients.getJSONObject(i).getString("_id"));
                        conn = (HttpURLConnection) url.openConnection();

                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Authorization", token);
                        conn.setRequestProperty("id", id);
                        conn.setConnectTimeout(50000);
                        conn.connect();

                        inputStream = new BufferedInputStream(conn.getInputStream());
                        if (inputStream != null) {
                            result = convertInputStreamToString(inputStream);

                            JSONObject jsonObjectHealth = new JSONObject(result);
                            success = jsonObjectHealth.getString("success");

                            if (success.equals("true")) {
                                JSONArray jsonArrayHealth = jsonObjectHealth.getJSONArray("message");
                                jsonArrayPatients.getJSONObject(i).put("health", jsonArrayHealth);
                            } else {
                                JSONArray jsonArrayHealth = new JSONArray("[]");
                                jsonArrayPatients.getJSONObject(i).put("health", jsonArrayHealth);
                            }
                        }
                    }

                    jsonObject.put("message", jsonArrayPatients);
                    result = jsonObject.toString();
                }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_patient) {
            Intent intent = new Intent(MainActivity.this, NewPatientActivity.class);
            intent.putExtra(EXTRA_USER, user);
//            startActivityForResult(intent, 1);

            startActivity(intent);
            intent.removeExtra(EXTRA_USER);

            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra(EXTRA_USER, user);
            intent.putExtra(EXTRA_PATIENT_LIST, (Serializable) patientList);

            startActivity(intent);

            return true;
        } else if (id == R.id.action_account) {
            Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
            intent.putExtra(EXTRA_USER, user);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
