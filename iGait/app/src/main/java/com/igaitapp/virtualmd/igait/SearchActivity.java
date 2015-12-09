package com.igaitapp.virtualmd.igait;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private HashMap<String, List<Patient>> patientMap = new HashMap<>();
    private List<Patient> patientList = new ArrayList<>(), patientResultList = new ArrayList<>();
    private String lastName = "Last Name", firstName = "First Name", age = "Age", selectedFilter = lastName;
    private EditText editTextSearch;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);

        patientList = Session.getPatientListMap().getList();

        populateSpinnerFilter();
    }

    @Override
    protected void onPause(){
        super.onPause();

        closeKB();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!Session.getPatientListMap().isEmpty()) {
            patientList = Session.getPatientListMap().getList();

            if (editTextSearch.getText().toString().trim().length() != 0) {
                buttonSearch.performClick();
            }
        }
    }

    private void closeKB() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        editTextSearch.requestFocus();
        imm.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
    }

    private void populateSpinnerFilter() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerFilter);
        List<String> filters = new ArrayList<>();
        ArrayAdapter<String> adapter;

        filters.add(lastName);
        filters.add(firstName);
        filters.add(age);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, filters);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                String newFilter = parent.getItemAtPosition(position).toString();

                if (!selectedFilter.equals(newFilter)) {
                    if (newFilter.equals(lastName)) {
                        hashifyLastName();
                    } else if (newFilter.equals(firstName)) {
                        hashifyFirstName();
                    } else if (newFilter.equals(age)) {
                        hashifyAge();
                    }
                }

                selectedFilter = newFilter;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedFilter = lastName;
                hashifyLastName();
            }
        });
    }

    public void performSearch(View v) {
        String query = editTextSearch.getText().toString().trim().toLowerCase(), queryKey;
        Patient patient = new Patient();
        int exactCount = 0;

        if (selectedFilter.equals(lastName) && InputCheck.searchQueryName(query)) {
            hashifyLastName();

            query = removeRepeats(query);
            queryKey = convertToKey(query);

            if (patientMap.get(queryKey) != null) {
                patientResultList = patientMap.get(queryKey);

                for (int i = 0; i < patientResultList.size(); i++) {
                    patient = patientResultList.get(i);

                    if (removeRepeats(patient.getLastName().trim().toLowerCase()).equals(query)) {
                        patientResultList.set(i, patientResultList.get(exactCount));
                        patientResultList.set(exactCount, patient);
                        exactCount += 1;
                    }
                }

                populateListViewPatientsSearch();
            } else {
                hideListViewPatientsSearch();
            }
        } else if (selectedFilter.equals(firstName) && InputCheck.searchQueryName(query)) {
            hashifyFirstName();

            query = removeRepeats(query);
            queryKey = convertToKey(query);

            if (patientMap.get(queryKey) != null) {
                patientResultList = patientMap.get(queryKey);

                for (int i = 0; i < patientResultList.size(); i++) {
                    patient = patientResultList.get(i);

                    if (removeRepeats(patient.getFirstName().trim().toLowerCase()).equals(query)) {
                        patientResultList.set(i, patientResultList.get(exactCount));
                        patientResultList.set(exactCount, patient);
                        exactCount += 1;
                    }
                }

                populateListViewPatientsSearch();
            } else {
                hideListViewPatientsSearch();
            }
        } else if (selectedFilter.equals(age) && InputCheck.searchQueryAge(query)) {
            hashifyAge();

            queryKey = query;

            if (patientMap.get(queryKey) != null) {
                patientResultList = patientMap.get(queryKey);

                populateListViewPatientsSearch();
            } else {
                hideListViewPatientsSearch();
            }
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.invalid_query), Toast.LENGTH_SHORT).show();
        }
    }

    private void hashifyLastName() {
        String patientKey;
        List<Patient> patientValueList;
        Patient patient = new Patient();

        patientMap = new HashMap<>();
        patientResultList = new ArrayList<>();

        for (int i = 0; i < patientList.size(); i++) {
            patient = patientList.get(i);

            patientKey = removeRepeats(patient.getLastName().toLowerCase());

            patientKey = convertToKey(patientKey);

            patientValueList = new ArrayList<>();

            if (patientMap.get(patientKey) == null) {
                patientValueList.add(patient);

                patientMap.put(patientKey, patientValueList);
            } else {
                patientValueList = patientMap.get(patientKey);

                patientValueList.add(patient);

                patientMap.put(patientKey, patientValueList);
            }
        }
    }

    private void hashifyFirstName() {
        String patientKey;
        List<Patient> patientValueList;
        Patient patient = new Patient();

        patientMap = new HashMap<>();
        patientResultList = new ArrayList<>();

        for (int i = 0; i < patientList.size(); i++) {
            patient = patientList.get(i);

            patientKey = removeRepeats(patient.getFirstName().toLowerCase());

            patientKey = convertToKey(patientKey);

            patientValueList = new ArrayList<>();

            if (patientMap.get(patientKey) == null) {
                patientValueList.add(patient);

                patientMap.put(patientKey, patientValueList);
            } else {
                patientValueList = patientMap.get(patientKey);

                patientValueList.add(patient);

                patientMap.put(patientKey, patientValueList);
            }
        }
    }

    private void hashifyAge() {
        String patientKey;
        List<Patient> patientValueList;
        Patient patient = new Patient();
        Date currentDate = new Date();

        patientMap = new HashMap<>();
        patientResultList = new ArrayList<>();

        for (int i = 0; i < patientList.size(); i++) {
            patient = patientList.get(i);

            patientKey = Long.toString((currentDate.getTime() - patient.getBirthday().getTime()) / 1000 / 60 / 60 / 24 / 365);

            patientValueList = new ArrayList<>();

            if (patientMap.get(patientKey) == null) {
                patientValueList.add(patient);

                patientMap.put(patientKey, patientValueList);
            } else {
                patientValueList = patientMap.get(patientKey);

                patientValueList.add(patient);

                patientMap.put(patientKey, patientValueList);
            }
        }
    }

    private String removeRepeats(String key) {
        char tempChar = ' ';
        List<String> repeatChars = new ArrayList<>();

        for (int i = 0; i < key.length(); i++) {
            if (tempChar == key.charAt(i)) {
                repeatChars.add(Character.toString(tempChar));
            } else {
                tempChar = key.charAt(i);
            }
        }

        if (!repeatChars.isEmpty()) {
            for (int i = 0; i < repeatChars.size(); i++) {
                key = key.replaceAll(repeatChars.get(i) + repeatChars.get(i), repeatChars.get(i));
            }
        }

        return key;
    }

    private String convertToKey(String key) {

        return key.substring(0, 1).toLowerCase();
    }

    private void populateListViewPatientsSearch() {
        ListView list = (ListView) findViewById(R.id.listViewPatientsSearch);
        PatientListSearchAdapter adapter = new PatientListSearchAdapter(this, patientResultList);
        TextView notFound = (TextView) findViewById(R.id.textViewNotFound);

        list.setVisibility(View.VISIBLE);
        notFound.setVisibility(View.GONE);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Patient tappedPatient = patientResultList.get(position);
                Intent intent = new Intent(SearchActivity.this, CalendarActivity.class);

                intent.putExtra(MainActivity.EXTRA_PATIENT, tappedPatient);

                startActivity(intent);
            }
        });
    }

    private void hideListViewPatientsSearch() {
        ListView list = (ListView) findViewById(R.id.listViewPatientsSearch);
        TextView notFound = (TextView) findViewById(R.id.textViewNotFound);

        list.setVisibility(View.GONE);
        notFound.setVisibility(View.VISIBLE);
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
