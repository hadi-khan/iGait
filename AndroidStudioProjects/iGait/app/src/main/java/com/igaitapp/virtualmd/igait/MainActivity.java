package com.igaitapp.virtualmd.igait;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Patient> patientList = new ArrayList<Patient>();

    private ListView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populatePatientList();
        populateListViewPatients();
    }

    private void populatePatientList()
    {
        // Hard coded gait health integers.
        List<Integer> bw = new ArrayList<Integer>();
        bw.add(2);
        bw.add(1);
        bw.add(3);

        List<Integer> ck = new ArrayList<Integer>();
        ck.add(3);
        ck.add(1);
        ck.add(2);

        List<Integer> pp = new ArrayList<Integer>();
        pp.add(3);
        pp.add(2);
        pp.add(2);

        // Hard coded patients.
        patientList.add(new Patient(143256790, "Wayne", "Bruce", 26, bw));
        patientList.add(new Patient(887832468, "Kent", "Clark", 30, ck));
        patientList.add(new Patient(246804525, "Parker", "Peter", 20, pp));
    }

    private void populateListViewPatients()
    {
        PatientListAdapter adapter;

        view = (ListView) findViewById(R.id.listViewPatients);
        adapter = new PatientListAdapter(this, patientList);
        view.setAdapter(adapter);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
