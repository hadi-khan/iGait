package com.igaitapp.virtualmd.igait;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    private Patient patient;
    private Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        DateFormat df = new SimpleDateFormat("EEE, MMM dd yyyy");
        String dateString;

        patient = (Patient) getIntent().getSerializableExtra(MainActivity.EXTRA_PATIENT);
        selectedDate = (Date) getIntent().getSerializableExtra(CalendarActivity.EXTRA_SELECTED_DATE);

        dateString = df.format(selectedDate);
        setTitle(dateString);

        populateListViewPatient();
        populateListViewEvents();
    }

    private void populateListViewPatient() {
        List<Patient> patientList = new ArrayList<>();
        ListView list = (ListView) findViewById(R.id.listViewPatientEvent);
        PatientListAdapter adapter;

        patientList.add(patient);

        adapter = new PatientListAdapter(this, patientList);

        list.setAdapter(adapter);
    }

    private void populateListViewEvents() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        ListView list = (ListView) findViewById(R.id.listViewEvents);
        List<GaitHealth> gaitHealthList = patient.getGaitHealth(), eventList = new ArrayList<>();
        GaitHealth gaitHealth = new GaitHealth();
        EventListAdapter adapter;
        Date currentDate;

        for (int i = 0; i < gaitHealthList.size(); i++) {
            gaitHealth = gaitHealthList.get(i);
            currentDate = gaitHealth.getStartTime();

            try {
                currentDate = df.parse(df.format(currentDate));
                selectedDate = df.parse(df.format(selectedDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (currentDate.equals(selectedDate)) {
                eventList.add(gaitHealth);
            }
        }

        adapter = new EventListAdapter(this, eventList);
        list.setAdapter(adapter);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
