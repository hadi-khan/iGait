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
    // The one patient.
    private Patient patient;

    // The selected date.
    private Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // The date format that will be used for the activity title.
        DateFormat df = new SimpleDateFormat("EEE, MMM dd yyyy");

        // Grab the patient and selected date that were passed as extras.
        patient = (Patient) getIntent().getSerializableExtra(MainActivity.EXTRA_PATIENT);
        selectedDate = (Date) getIntent().getSerializableExtra(CalendarActivity.EXTRA_SELECTED_DATE);

        // Format the selected date as a string and set it as the title.
        setTitle(df.format(selectedDate));

        // Populate the list view of the one patient.
        // Populate the list view of the date's health events.
        populateListViewPatient();
        populateListViewEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // When the activity is resumed and the patient listmap is not empty, populate the listview
        //  that holds the one patient.This is done to reflect any updates to the patient's profile.
        if (!Session.getPatientListMap().isEmpty()) {
            patient = Session.getPatientListMap().getUsingKey(patient.getId());
            populateListViewPatient();
        }
    }

    private void populateListViewPatient() {
        // The patient list that will hold the one patient.
        List<Patient> patientList = new ArrayList<>();

        // The listview and adapter that will display the one patient.
        ListView list = (ListView) findViewById(R.id.listViewPatientEvent);
        PatientListAdapter adapter;

        // Add the one patient to the patient list.
        patientList.add(patient);

        // Set the adapter using the patient list and the listview.
        adapter = new PatientListAdapter(this, patientList);
        list.setAdapter(adapter);
    }

    private void populateListViewEvents() {
        // The simple date format used to compare dates.
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        // The one patient's health list and event list.
        List<GaitHealth> gaitHealthList = patient.getGaitHealth(), eventList = new ArrayList<>();

        // This will hold a single health event.
        GaitHealth gaitHealth = new GaitHealth();

        // The listview an adapter dthat will display the events.
        ListView list = (ListView) findViewById(R.id.listViewEvents);
        EventListAdapter adapter;

        // The current date.
        Date currentDate;

        // For every health in the health list.
        for (int i = 0; i < gaitHealthList.size(); i++) {
            // Grab the current health and current date.
            gaitHealth = gaitHealthList.get(i);
            currentDate = gaitHealth.getStartTime();

            // "Clean" the current and selected dates.
            try {
                currentDate = df.parse(df.format(currentDate));
                selectedDate = df.parse(df.format(selectedDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // If the current date equals the selected date, add it to the event list.
            if (currentDate.equals(selectedDate)) {
                eventList.add(gaitHealth);
            }
        }

        // Set the adapter using the event list and the listview.
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
            // Override pressing the home button in the actionbar, this goes to the last visited
            //  activity. Needed to resume calendar activity without passing extras.
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
