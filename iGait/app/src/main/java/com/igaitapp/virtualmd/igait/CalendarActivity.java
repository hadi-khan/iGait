package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    public final static String EXTRA_PATIENT_CALENDAR = " com.igaitapp.virtualmd.igait.PATIENT_CALENDAR";
    public final static String EXTRA_SELECTED_DATE_CALENDAR = " com.igaitapp.virtualmd.igait.SELECTED_DATE_CALENDAR";

    private Patient patient;
    CaldroidFragment caldroidFragment = new CaldroidFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        patient = (Patient) getIntent().getSerializableExtra(MainActivity.EXTRA_PATIENT);

        populateListViewPatient();
        populateCalendarViewHealth();
        restoreCalendar(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }
    }

    private void populateListViewPatient () {
        ListView list;
        PatientListAdapter adapter;
        List<Patient> patientList = new ArrayList<>();

        patientList.add(patient);

        list = (ListView) findViewById(R.id.listViewPatientCalendar);
        adapter = new PatientListAdapter(this, patientList);
        list.setAdapter(adapter);
    }

    private void populateCalendarViewHealth() {
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putInt(CaldroidFragment.THEME_RESOURCE, R.style.CalendariGait);
        caldroidFragment.setArguments(args);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarViewHealth, caldroidFragment);
        t.commit();

        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        List<GaitHealth> gaitHealthList;
        GaitHealth gaitHealth;
        Date prevDay, currentDay;
        String prevDayString, currentDayString;
        int gaitHealthSum = 0, gaitHealthCount = 0;

        gaitHealthList = patient.getGaitHealth();
        gaitHealth = gaitHealthList.get(0);
        prevDay = gaitHealth.getStartTime();

        for(int i = 0; i < gaitHealthList.size(); i++) {
            gaitHealth = gaitHealthList.get(i);
            currentDay = gaitHealth.getStartTime();

            prevDayString = dateFormat.format(prevDay);
            currentDayString = dateFormat.format(currentDay);

            if (!(currentDayString.equals(prevDayString)) || (i + 1) == gaitHealthList.size()) {
                if (Math.round(gaitHealthSum / gaitHealthCount) == 3) {
                    caldroidFragment.setBackgroundResourceForDate(R.color.GameGreen, prevDay);
                    caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                }
                else if (Math.round(gaitHealthSum / gaitHealthCount) == 2) {
                    caldroidFragment.setBackgroundResourceForDate(R.color.MusicOrange, prevDay);
                    caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                }
                else if (Math.round(gaitHealthSum / gaitHealthCount) == 1) {
                    caldroidFragment.setBackgroundResourceForDate(R.color.MovieRed, prevDay);
                    caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                }
                else if ((gaitHealthSum / gaitHealthCount) > 0) {
                    caldroidFragment.setBackgroundResourceForDate(R.color.StarGrey, prevDay);
                    caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                }
                else {
                    caldroidFragment.setBackgroundResourceForDate(R.color.StarGrey, prevDay);
                    caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                }

                prevDay = currentDay;
                gaitHealthSum = 0;
                gaitHealthCount = 0;
            }

            gaitHealthSum += gaitHealth.getHealth();
            gaitHealthCount += 1;
        }

        caldroidFragment.setCaldroidListener(new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Intent intent;
                List<GaitHealth> gaitHealthList;
                Date firstDate, lastDate, selectedDate;
                DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

                gaitHealthList = patient.getGaitHealth();
                firstDate = gaitHealthList.get(0).getStartTime();
                lastDate = gaitHealthList.get(gaitHealthList.size() - 1).getStartTime();
                selectedDate = date;

                try {
                    firstDate = dateFormat.parse(dateFormat.format(firstDate));
                    lastDate = dateFormat.parse(dateFormat.format(lastDate));
                    selectedDate = dateFormat.parse(dateFormat.format(selectedDate));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                if ((selectedDate.equals(firstDate) || selectedDate.equals(lastDate)) || (selectedDate.after(firstDate) && selectedDate.before(lastDate))) {
                    intent = new Intent(CalendarActivity.this, EventActivity.class);

                    intent.putExtra(EXTRA_PATIENT_CALENDAR, patient);
                    intent.putExtra(EXTRA_SELECTED_DATE_CALENDAR, date);

                    startActivity(intent);
                }
            }
        });
    }

    private void restoreCalendar(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState, "CALDROID_SAVED_STATE");
        }
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);
            args.putInt(CaldroidFragment.THEME_RESOURCE, R.style.CalendariGait);
            caldroidFragment.setArguments(args);
        }
    }

    private Intent getParentActivityIntentImpl() {
        Intent i = null;

        if ((boolean) getIntent().getSerializableExtra(MainActivity.EXTRA_SEARCH_ID)) {
            i = new Intent(this, SearchActivity.class);
        } else {
            i = new Intent(this, MainActivity.class);
        }

        return i;
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
        if (id == R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}