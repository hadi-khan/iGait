package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String EXTRA_PATIENT = "com.igaitapp.virtualmd.igait.PATIENT";
    static final String EXTRA_PATIENT_LIST = "com.igaitapp.virtualmd.igait.PATIENT_LIST";
    static final String EXTRA_PARENT_ID = "com.igaitapp.virtualmd.igait.PARENT_ID";
    static final String EXTRA_USER = "com.igaitapp.virtualmd.igait.USER";

    private List<Patient> patientList = new ArrayList<>();
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String parent = getIntent().getStringExtra(EXTRA_PARENT_ID);

        if (parent.equals("login") || parent.equals("pprofile")) {
            user = (User) getIntent().getSerializableExtra(EXTRA_USER);
            new ServerConnect().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/patient");
        }
        else if (parent.equals("uprofile") || parent.equals("npprofile")) {
            new ServerConnect().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/patient");
        }
        else {
            // timer
        }
    }

    private void populateListViewPatients() {
        ListView list = (ListView) findViewById(R.id.listViewPatients);
        PatientListAdapter adapter = new PatientListAdapter(this, patientList, "main");

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Patient tappedPatient = patientList.get(position);
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);

                intent.putExtra(EXTRA_PATIENT, tappedPatient);
                intent.putExtra(EXTRA_PARENT_ID, "main");

                startActivity(intent);
            }
        });
    }

    public static String GET(String urlPaths, String email, String token) {
        InputStream inputStream = null;
        String result = "";

        try {
            URL url = new URL(urlPaths);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", token);
            conn.setRequestProperty("Email", email);
            conn.connect();

            inputStream = new BufferedInputStream(conn.getInputStream());
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = "Failure connecting.";
            }

            return result;
        } catch (IOException e) {
            Log.d("Server", "IOException reading data " + e.getMessage());
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
            String email = user.getContactInfo().getEmail(),
                    token = user.getToken();

            return GET(urls[0], email, token);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String success = "";

            SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            try {
                JSONObject jsonObject = new JSONObject(result);
                success = jsonObject.getString("success").toString();

                JSONArray jsonArrayPatients = jsonObject.getJSONArray("message");
                JSONObject patient = new JSONObject(), name = new JSONObject(), contact = new JSONObject();

                if (success.equals("true")) {
                    for (int i = 0; i < jsonArrayPatients.length(); i++) {
                        patient = jsonArrayPatients.getJSONObject(i);
                        name = patient.getJSONObject("name");
                        contact = patient.getJSONObject("contact");

                        ContactInfo ci = new ContactInfo(1234567890l, "example@example.com",
                                "Real St.", contact.getString("city"), contact.getString("state"),
                                contact.getLong("zipcode"));

                        List<GaitHealth> gil = new ArrayList<GaitHealth>();
                        gil.add(new GaitHealth(3, df.parse("11/21/2015 8:00:00"), df.parse("11/21/2015 8:02:00"), false));

                        patientList.add(new Patient(name.getString("last"), name.getString("first"),
                                tf.parse("00:03:00"), df.parse("12/25/1980"),
                                patient.getString("gender").charAt(0), ci,
                                gil, patient.getBoolean("priority")));
                    }

                    Log.d(":)", patientList.size() + "");
                    populateListViewPatients();
                } else {
                    Toast.makeText(getBaseContext(), "Could not sync patients.", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
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

            startActivity(intent);

            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
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
