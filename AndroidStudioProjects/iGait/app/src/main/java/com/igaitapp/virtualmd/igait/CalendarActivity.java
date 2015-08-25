package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Intent intent;
        Patient patient;

        intent = getIntent();
        patient = (Patient) intent.getSerializableExtra(MainActivity.EXTRA_PATIENT);

        populateLinearLayoutPatient(patient);
    }

    private void populateLinearLayoutPatient(Patient patient) {
        ListView view;
        PatientListAdapter adapter;
        List<Patient> patientList = new ArrayList<Patient>();

        patientList.add(patient);

        view = (ListView) findViewById(R.id.listViewPatient);
        adapter = new PatientListAdapter(this, patientList);
        view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
