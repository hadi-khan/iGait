package com.igaitapp.virtualmd.igait;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        patient = (Patient) getIntent().getSerializableExtra(CalendarActivity.EXTRA_PATIENT_CALENDAR);
        selectedDate = (Date) getIntent().getSerializableExtra(CalendarActivity.EXTRA_SELECTED_DATE_CALENDAR);

        dateString = df.format(selectedDate);
        setTitle(dateString);

        populateListViewPatient();
        populateListViewEvents();
    }

    private void populateListViewPatient () {
        ListView list;
        PatientListAdapter adapter;
        List<Patient> patientList = new ArrayList<>();

        patientList.add(patient);

        list = (ListView) findViewById(R.id.listViewPatientEvent);
        adapter = new PatientListAdapter(this, patientList);
        list.setAdapter(adapter);
    }

    private void populateListViewEvents () {
        ListView list;
        EventListAdapter adapter;
        List<GaitHealth> gaitHealthList, eventList = new ArrayList<>();
        GaitHealth gaitHealth = new GaitHealth();
        Date currentDate;
        SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy");

        gaitHealthList = patient.getGaitHealth();

        for (int i = 0; i < gaitHealthList.size(); i++) {
            gaitHealth = gaitHealthList.get(i);
            currentDate = gaitHealth.getStartTime();

            try {
                currentDate = df.parse(df.format(currentDate));
                selectedDate = df.parse(df.format(selectedDate));
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

            if (currentDate.equals(selectedDate)) {
                eventList.add(gaitHealth);
            }
        }

        list = (ListView) findViewById(R.id.listViewEvents);
        adapter = new EventListAdapter(this, eventList);
        list.setAdapter(adapter);
    }
}
