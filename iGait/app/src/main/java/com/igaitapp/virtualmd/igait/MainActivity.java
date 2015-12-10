package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Unique string for placing an extra patient in intents.
    static final String EXTRA_PATIENT = "com.igaitapp.virtualmd.igait.PATIENT";

    // The logged in user and their listmap of patients. They are stored in order to have backup data
    //  in case of a network failure.
    private User user = new User();
    private PatientListMap patientListMap = new PatientListMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Assign the user and patient listmap from the session.
        // If there is an error, at least the last session of user and patients is shown.
        user = Session.getUser();
        patientListMap = Session.getPatientListMap();

        // Connect to the server in order to pull the user's list of patients.
        new ServerConnect().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/patient",
                "http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/patient/health/");
    }

    @Override
    public void onBackPressed() {
        // If the user tries to go back to the login screen, toast them that they must logout.
        Toast.makeText(getBaseContext(), "Logout in order to visit the login screen.", Toast.LENGTH_LONG).show();
    }

    private void populateListViewPatients() {
        // If the user has no patients, then this textview will tell them.
        TextView textViewNoPatients = (TextView) findViewById(R.id.textViewNoPatients);

        // Check if the user has patients.
        if (!patientListMap.isEmpty()) {
            // Prepare the listview and adapter for the list of patients.
            ListView list = (ListView) findViewById(R.id.listViewPatients);
            PatientListAdapter adapter = new PatientListAdapter(this, patientListMap.getList());

            // Remove the textview telling the user they have no patients.
            textViewNoPatients.setVisibility(View.GONE);

            // Set the adapter to the listview.
            list.setAdapter(adapter);

            // Click listener for the listview that visits the calendar activity.
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    // The clicked (tapped) patient and the intent for the calendar activity.
                    Patient tappedPatient = patientListMap.get(position);
                    Intent intent = new Intent(MainActivity.this, CalendarActivity.class);

                    // Put the tapped patient as an extra in the intent.
                    intent.putExtra(EXTRA_PATIENT, tappedPatient);

                    // Start the activity in the intent.
                    startActivity(intent);
                }
            });
        } else {
            // Tell the user they have no patients.
            textViewNoPatients.setVisibility(View.VISIBLE);
        }
    }

    private class ServerConnect extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // Pass only the necessary variables to connect to the server.
            return GET(urls[0], urls[1], user.getToken(), user.getId());
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // The simple date formats that are needed to properly store birthday, gait health dates,
            //  and expected walk time.
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");

            try {
                // Convert the result into a json object and extra the success and message.
                JSONObject jsonObject = new JSONObject(result);
                String success = jsonObject.getString("success");
                String message = jsonObject.getString("message");

                // If the success is true, populate the user's patient list.
                if (success.equals("true")) {
                    // Clear the patient list
                    PatientListMap patientListMapTemp = new PatientListMap();

                    // Grab the json array of patients from message. Then loop through each patient,
                    //  adding it to the user's patient list.
                    JSONArray jsonArrayPatients = jsonObject.getJSONArray("message");
                    for (int i = 0; i < jsonArrayPatients.length(); i++) {
                        // Grab the current patient from the array and convert it to a json object.
                        JSONObject patient = jsonArrayPatients.getJSONObject(i);

                        // The various json subobjects in the patient. Health is a json subarray.
                        JSONObject name = patient.getJSONObject("name");
                        JSONObject contact = patient.getJSONObject("contact");
                        JSONObject expectedWalkTime = patient.getJSONObject("expectedWalkTime");
                        JSONArray healthArray = patient.getJSONArray("health");

                        // Grab and store the patient's contact info from the contact json subobject.
                        ContactInfo ci = new ContactInfo(contact.getLong("mobilenumber"), contact.getString("email"),
                                contact.getString("address"), contact.getString("city"), contact.getString("state"),
                                contact.getLong("zipcode"));

                        // Grab and store the patient's health list from the health json subarray.
                        List<GaitHealth> gil = new ArrayList<>();
                        for (int o = 0; o < healthArray.length(); o++) {
                            // Grab the current health and convert it to a json object.
                            JSONObject health = healthArray.getJSONObject(o);

                            // Store the health in the health list.
                            gil.add(new GaitHealth(health.getInt("health"), df.parse(health.getString("start_time")),
                                    df.parse(health.getString("end_time")), false));
                        }

                        // Grab and store the expected walk time from the expected walk time json subobject.
                        Date ewt = tf.parse(expectedWalkTime.getString("hour") + ":" +
                                expectedWalkTime.getString("minute") + ":" +
                                expectedWalkTime.getString("second"));

                        // Create a patient using the various gathered data and add the patient the user's
                        //  list of patients.
                        patientListMapTemp.add(new Patient(name.getString("last"), name.getString("first"),
                                ewt, df.parse(patient.getString("dateOfBirth")),
                                patient.getString("gender").charAt(0), ci, gil,
                                patient.getBoolean("priority"), patient.getString("_id"),
                                user.getId(), user.getToken()));
                    }

                    // Update the patient listmap.
                    patientListMap = patientListMapTemp;

                    // Update the patient listmap in the session.
                    Session.setPatientListMap(patientListMap);

                    // Display the patient list to the user.
                    populateListViewPatients();
                } else if (success.equals("error")) {
                    // If success is an error, toast the error message.
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    // If the succes is false, toast that the user's patients could not be synced.
                    Toast.makeText(getBaseContext(), "Could not sync patients.", Toast.LENGTH_LONG).show();
                }
            } catch (ParseException e) {
                Toast.makeText(getBaseContext(), "Data error.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (JSONException e) {
                Toast.makeText(getBaseContext(), "Data error.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    public static String GET(String urlPaths, String urlPaths1, String token, String id) {
        // The result of the connection to the server.
        String result = "";

        try {
            // First the patients are pulled, then each patient is assigned their health list.
            // The api sends patients and health separately, linked by patient id.

            // Create the URL from the first URL and create a connection.
            URL url = new URL(urlPaths);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set the connection properties, then connect.
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", token);
            conn.setRequestProperty("id", id);
            conn.setConnectTimeout(50000);
            conn.connect();

            // Grab the input stream, if it is not null create another connection in order to grab the health.
            InputStream inputStream = new BufferedInputStream(conn.getInputStream());
            if (inputStream != null) {
                // Convert the input stream into a string stored in result. Then convert result into a json
                //  object to hold the patients. Then extract success from the json object.
                result = convertInputStreamToString(inputStream);
                JSONObject jsonObject = new JSONObject(result);
                String success = jsonObject.getString("success");

                // If success is true, loop through the patients and assign their health.
                if (success.equals("true")) {
                    // Convert message into a json array to hold the array of patients.
                    // In the for loop a new connection is made to assign each patient their health.
                    JSONArray jsonArrayPatients = jsonObject.getJSONArray("message");
                    for (int i = 0; i < jsonArrayPatients.length(); i++) {
                        // Create the new url using also the patient's id and create the connection.
                        url = new URL(urlPaths1 + jsonArrayPatients.getJSONObject(i).getString("_id"));
                        conn = (HttpURLConnection) url.openConnection();

                        // Set the connection properties, then connect.
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Authorization", token);
                        conn.setRequestProperty("id", id);
                        conn.setConnectTimeout(50000);
                        conn.connect();

                        // Grab the input stream, if it is not null place the extracted health into
                        //  the json object holding patients.
                        inputStream = new BufferedInputStream(conn.getInputStream());
                        if (inputStream != null) {
                            // Convert the input stream to a string in result. Then convert result into
                            //  a json object to hold health. Then extract success from the json object.
                            result = convertInputStreamToString(inputStream);
                            JSONObject jsonObjectHealth = new JSONObject(result);
                            success = jsonObjectHealth.getString("success");

                            // If success is true, put the extracted health in the patient.
                            if (success.equals("true")) {
                                // Extract message, containing the health array.
                                // Then, using the json object holding health array,
                                //  place the health in the json object holding the patient.
                                JSONArray jsonArrayHealth = jsonObjectHealth.getJSONArray("message");
                                jsonArrayPatients.getJSONObject(i).put("health", jsonArrayHealth);
                                jsonObject.put("message", jsonArrayPatients);
                            } else {
                                // Success is false, so create an empty json array as health.
                                // Then, using the json object holding health array,
                                //  place the health in the json object holding the patient.
                                JSONArray jsonArrayHealth = new JSONArray("[]");
                                jsonArrayPatients.getJSONObject(i).put("health", jsonArrayHealth);
                                jsonObject.put("message", jsonArrayPatients);
                            }
                        } else {
                            // The input stream was null, create a json string with an error message.
                            // Break from the for loop, as there is incomplete data.
                            jsonObject = new JSONObject("{\"success\":error,\"message\":\"Connection error.\"}");
                            break;
                        }
                    }

                    // Convert the json object into a string stored in result. Result now holds patients
                    //  along with their health.
                    result = jsonObject.toString();
                }
            } else {
                // The input stream was null, create a json string with an error message.
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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        // Convert the input stream from the server into a string.
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
            // Make an intent for the new patient activity.
            Intent intent = new Intent(MainActivity.this, NewPatientActivity.class);

            // Open the new patient activity.
            startActivity(intent);

            return true;
        } else if (id == R.id.action_search) {
            // Make an intent for the search activity.
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);

            // Open the search activity.
            startActivity(intent);

            return true;
        } else if (id == R.id.action_account) {
            // Make an intent for the account activity.
            Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);

            // Open the user profile activity.
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
