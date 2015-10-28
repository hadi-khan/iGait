package com.igaitapp.virtualmd.igait;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private String lName = "Last Name", fName = "First Name", age = "Age", selectedFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        patientList = (List<Patient>) getIntent().getSerializableExtra(MainActivity.EXTRA_PATIENT_LIST);

        selectedFilter = lName;
        hashifyLastName();

        populateSpinnerFilter();
    }

    private void populateSpinnerFilter() {
        Spinner spinner;
        List<String> filters = new ArrayList<>();
        ArrayAdapter<String> adapter;

        spinner = (Spinner) findViewById(R.id.spinnerFilter);

        filters.add(lName);
        filters.add(fName);
        filters.add(age);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, filters);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                String newFilter = parent.getItemAtPosition(position).toString();

                if (!selectedFilter.equals(newFilter)) {
                    if (newFilter.equals(lName)) {
                        hashifyLastName();
                    }
                    else if (newFilter.equals(fName)) {
                        hashifyFirstName();
                    }
                    else if (newFilter.equals(age)) {
                        hashifyAge();
                    }

                }

                selectedFilter = newFilter;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedFilter = lName;
                hashifyLastName();
            }
        });
    }

    public void performSearch(View v) {
        EditText editTextSearch;
        String query, queryKey;
        List<Patient> topList = new ArrayList<>();
        Patient patient;
        int exactCount = 0;

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        query = editTextSearch.getText().toString().trim().toLowerCase();

        if (query.length() > 0) {
            if (selectedFilter.equals(lName)) {
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
                }
                else {
                    hideListViewPatientsSearch();
                }
            }
            else if (selectedFilter.equals(fName)) {
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
                }
                else {
                    hideListViewPatientsSearch();
                }
            }
            else if (selectedFilter.equals(age)) {
                queryKey = query;

                if (patientMap.get(queryKey) != null) {
                    patientResultList = patientMap.get(queryKey);

                    populateListViewPatientsSearch();
                }
                else {
                    hideListViewPatientsSearch();
                }
            }
        }
        else {
            Toast.makeText(getApplicationContext(), getString(R.string.query_empty), Toast.LENGTH_SHORT).show();
        }
    }

    private void hashifyLastName() {
        String patientKey;
        List<Patient> patientValueList;
        Patient patient;

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
            }
            else {
                patientValueList = patientMap.get(patientKey);

                patientValueList.add(patient);

                patientMap.put(patientKey, patientValueList);
            }
        }
    }

    private void hashifyFirstName() {
        String patientKey;
        List<Patient> patientValueList;
        Patient patient;

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
        Patient patient;
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
            }
            else {
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
            }
            else {
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
        if (key.length() >= 1) {
            key = key.substring(0, 1).toLowerCase();
        }

        return key;
    }

    private void populateListViewPatientsSearch() {
        ListView list;
        PatientListSearchAdapter adapter;
        TextView notFound;

        list = (ListView) findViewById(R.id.listViewPatientsSearch);
        list.setVisibility(View.VISIBLE);
        notFound = (TextView) findViewById(R.id.textViewNotFound);
        notFound.setVisibility(View.INVISIBLE);
        adapter = new PatientListSearchAdapter(this, patientResultList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Patient tappedPatient;
                Intent intent;

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                tappedPatient = patientResultList.get(position);
                intent = new Intent(SearchActivity.this, CalendarActivity.class);
                intent.putExtra(MainActivity.EXTRA_PATIENT, tappedPatient);
                intent.putExtra(MainActivity.EXTRA_SEARCH_ID, true);

                startActivity(intent);
            }
        });
    }

    private void hideListViewPatientsSearch() {
        ListView list;
        TextView notFound;

        list = (ListView) findViewById(R.id.listViewPatientsSearch);
        list.setVisibility(View.INVISIBLE);

        notFound = (TextView) findViewById(R.id.textViewNotFound);
        notFound.setVisibility(View.VISIBLE);
    }
}
