package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    // Unique string for placing an extra patient in intents.
    static final String EXTRA_SELECTED_DATE = "com.igaitapp.virtualmd.igait.SELECTED_DATE";

    // The tapped patient.
    private Patient patient = new Patient();

    // The calendar fragment.
    private CaldroidFragment caldroidFragment = new CaldroidFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Grab the extra patient from the intent.
        // Populate the listview that holds this one patient. This serves as the overall view.
        patient = (Patient) getIntent().getSerializableExtra(MainActivity.EXTRA_PATIENT);
        populateListViewPatient();

        // Populate the calendar fragment and restore the calendar fragment when rotating the screen.
        populateCalendarViewHealth();
        restoreCalendar(savedInstanceState);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // If the calendar fragment is not null, restore it.
        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }
    }

    private void populateListViewPatient() {
        // The patient list that will hold the one patient.
        List<Patient> patientList = new ArrayList<>();

        // The listview and adapter that will display the one patient.
        ListView list = (ListView) findViewById(R.id.listViewPatient);
        PatientListAdapter adapter;

        // Add the one patient to the patient list.
        patientList.add(patient);

        // Set the adapter using the patient list and the listview.
        adapter = new PatientListAdapter(this, patientList);
        list.setAdapter(adapter);
    }

    private void populateCalendarViewHealth() {
        // Create the calendar fragment, as explained in the caldroid github page.
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putInt(CaldroidFragment.THEME_RESOURCE, R.style.CalendariGait);
        caldroidFragment.setArguments(args);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarViewHealth, caldroidFragment);
        t.commit();

        // The simple date format used to compare dates.
        final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        // List of the one patient's gait health.
        final List<GaitHealth> gaitHealthList = patient.getGaitHealth();

        // A single gait health, will be used to extract the health values and start/end times.
        GaitHealth gaitHealth;

        // The health sum and health count, used to average daily health values.
        int gaitHealthSum = 0, gaitHealthCount = 0;

        // The previous and current day, used to know which health average belong to which days.
        Date prevDay, currentDay;

        // If the health list is not empty, average the health of each day.
        if (!gaitHealthList.isEmpty()) {
            // The lone gait health of the current day.
            gaitHealth = gaitHealthList.get(0);

            // The previous day, which at first will be the current day.
            prevDay = gaitHealth.getStartTime();

            for (int i = 0; i < gaitHealthList.size(); i++) {
                // The lone gait health of the current day.
                gaitHealth = gaitHealthList.get(i);

                // The current day.
                currentDay = gaitHealth.getStartTime();

                // "Clean" the dates. This is needed in order for comparisons to be accurate for some reason.
                try {
                    currentDay = df.parse(df.format(currentDay));
                    prevDay = df.parse(df.format(prevDay));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // If the current day doesn't equal the previous day, then we average the totals that have been calculated.
                if (!currentDay.equals(prevDay)) {
                    // Find the average and assign its color to the previous day. We use the previous day as that
                    //  is what the totals were counted for.
                    // 3 is green, 2 is orange, 1 is red, 0 is grey.
                    if (Math.round(gaitHealthSum / gaitHealthCount) == 3) {
                        caldroidFragment.setBackgroundResourceForDate(R.color.GameGreen, prevDay);
                        caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                    } else if (Math.round(gaitHealthSum / gaitHealthCount) == 2) {
                        caldroidFragment.setBackgroundResourceForDate(R.color.MusicOrange, prevDay);
                        caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                    } else if (Math.round(gaitHealthSum / gaitHealthCount) == 1) {
                        caldroidFragment.setBackgroundResourceForDate(R.color.MovieRed, prevDay);
                        caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                    } else if ((gaitHealthSum / gaitHealthCount) > 0) {
                        caldroidFragment.setBackgroundResourceForDate(R.color.StarGrey, prevDay);
                        caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                    } else {
                        caldroidFragment.setBackgroundResourceForDate(R.color.StarGrey, prevDay);
                        caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                    }

                    // The current day is now the previous day.
                    prevDay = currentDay;

                    // Reset the totals.
                    gaitHealthSum = 0;
                    gaitHealthCount = 0;
                }

                // Increment the sum and count.
                gaitHealthSum += gaitHealth.getHealth();
                gaitHealthCount += 1;

                // If the current day is the last in the health list, average the totals.
                // This needs to be done because the current day will never become a previous day if it the last
                //  day in the list.
                if (i == (gaitHealthList.size() - 1)) {
                    if (Math.round(gaitHealthSum / gaitHealthCount) == 3) {
                        caldroidFragment.setBackgroundResourceForDate(R.color.GameGreen, prevDay);
                        caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                    } else if (Math.round(gaitHealthSum / gaitHealthCount) == 2) {
                        caldroidFragment.setBackgroundResourceForDate(R.color.MusicOrange, prevDay);
                        caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                    } else if (Math.round(gaitHealthSum / gaitHealthCount) == 1) {
                        caldroidFragment.setBackgroundResourceForDate(R.color.MovieRed, prevDay);
                        caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                    } else if ((gaitHealthSum / gaitHealthCount) > 0) {
                        caldroidFragment.setBackgroundResourceForDate(R.color.StarGrey, prevDay);
                        caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                    } else {
                        caldroidFragment.setBackgroundResourceForDate(R.color.StarGrey, prevDay);
                        caldroidFragment.setTextColorForDate(R.color.PlainWhite, prevDay);
                    }
                }
            }
        }

        // The click listener for the calendar fragment.
        caldroidFragment.setCaldroidListener(new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                // The selected date, the first date, and the last date.
                Date selectedDate = date, firstDate, lastDate;

                // If the gait health list isn't empty, check if the selected date is valid.
                if (!gaitHealthList.isEmpty()) {
                    // Get the first dand last date of the gait health list.
                    firstDate = gaitHealthList.get(0).getStartTime();
                    lastDate = gaitHealthList.get(gaitHealthList.size() - 1).getStartTime();

                    // "Clean" the dates.
                    try {
                        firstDate = df.parse(df.format(firstDate));
                        lastDate = df.parse(df.format(lastDate));
                        selectedDate = df.parse(df.format(selectedDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // If the selected date is within (including) the first and last date
                    if ((selectedDate.equals(firstDate) || selectedDate.equals(lastDate)) ||
                            (selectedDate.after(firstDate) && selectedDate.before(lastDate))) {
                        // Create the intent for the event activity.
                        Intent intent = new Intent(CalendarActivity.this, EventActivity.class);

                        // Put the one patient and the selected date as an extra.
                        intent.putExtra(MainActivity.EXTRA_PATIENT, patient);
                        intent.putExtra(EXTRA_SELECTED_DATE, date);

                        // Start the event activity.
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void restoreCalendar(Bundle savedInstanceState) {
        // Restore the calendar fragment, as explained in the caldroid github page.
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState, "CALDROID_SAVED_STATE");
        } else {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // Override pressing the home button in the actionbar, this goes to the last visited
            //  activity. Needed for navigation between main->calendar and search->calendar.
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
