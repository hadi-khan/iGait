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
    static final String EXTRA_SELECTED_DATE = "com.igaitapp.virtualmd.igait.SELECTED_DATE";

    private Patient patient = new Patient();

    private CaldroidFragment caldroidFragment = new CaldroidFragment();

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

    private void populateListViewPatient() {
        List<Patient> patientList = new ArrayList<>();
        ListView list = (ListView) findViewById(R.id.listViewPatient);
        PatientListAdapter adapter;

        patientList.add(patient);

        adapter = new PatientListAdapter(this, patientList, 1);

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

        final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        final List<GaitHealth> gaitHealthList = patient.getGaitHealth();
        GaitHealth gaitHealth = gaitHealthList.get(0);
        int gaitHealthSum = 0, gaitHealthCount = 0;
        Date prevDay = gaitHealth.getStartTime(), currentDay;

        for (int i = 0; i < gaitHealthList.size(); i++) {
            gaitHealth = gaitHealthList.get(i);
            currentDay = gaitHealth.getStartTime();

            try {
                currentDay = df.parse(df.format(currentDay));
                prevDay = df.parse(df.format(prevDay));
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

            if (!(currentDay.equals(prevDay)) || (i + 1) == gaitHealthList.size()) {
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
                Date firstDate = gaitHealthList.get(0).getStartTime(),
                        lastDate = gaitHealthList.get(gaitHealthList.size() - 1).getStartTime(),
                        selectedDate = date;
                Intent intent;

                try {
                    firstDate = df.parse(df.format(firstDate));
                    lastDate = df.parse(df.format(lastDate));
                    selectedDate = df.parse(df.format(selectedDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if ((selectedDate.equals(firstDate) || selectedDate.equals(lastDate)) ||
                        (selectedDate.after(firstDate) && selectedDate.before(lastDate))) {
                    intent = new Intent(CalendarActivity.this, EventActivity.class);

                    intent.putExtra(MainActivity.EXTRA_PATIENT, patient);
                    intent.putExtra(EXTRA_SELECTED_DATE, date);

                    startActivity(intent);
                }
            }
        });
    }

    private void restoreCalendar(Bundle savedInstanceState) {
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

    private Intent getParentActivityIntentImpl() {
        Intent intent = null;

        if ((int) getIntent().getSerializableExtra(MainActivity.EXTRA_PARENT_ID) == 0) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, SearchActivity.class);
        }

        return intent;
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
