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
    // The hashmap of patients, this holds the patients along with a search key.
    private HashMap<String, List<Patient>> patientMap = new HashMap<>();

    // The list of patient and the list of pateints as a result of a search query.
    private List<Patient> patientList = new ArrayList<>(), patientResultList = new ArrayList<>();

    // The filters the search has.
    private String lastName = "Last Name", firstName = "First Name", age = "Age", selectedFilter = lastName;

    // The search box and the button to search.
    private EditText editTextSearch;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Assign the edit text search and button search.
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);

        // The list of patients taken from the session.
        patientList = Session.getPatientListMap().getList();

        // Populate the spinner, this holds the filters.
        populateSpinnerFilter();
    }

    @Override
    protected void onPause(){
        super.onPause();

        // When the activity is left, close the keyboard.
        closeKB();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // When the activity is resumed and the patient listmap is not empty, update the patient list
        //  and re-perform the search. This is done just in case a patient is edited while the search is still shown.
        if (!Session.getPatientListMap().isEmpty()) {
            patientList = Session.getPatientListMap().getList();

            if (editTextSearch.getText().toString().trim().length() != 0) {
                buttonSearch.performClick();
            }
        }
    }

    private void closeKB() {
        // Close the keyboard.
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        editTextSearch.requestFocus();
        imm.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
    }

    private void populateSpinnerFilter() {
        // Assign the spinner.
        Spinner spinner = (Spinner) findViewById(R.id.spinnerFilter);

        // A list that will hold the the filters.
        List<String> filters = new ArrayList<>();

        // Add the filters to the list.
        filters.add(lastName);
        filters.add(firstName);
        filters.add(age);

        // The array adapter for the spinner, using the filters list and the spinner xml.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, filters);
        spinner.setAdapter(adapter);

        // When the spinner is tapped.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                // Set the selected filter variable to what was tapped.
                selectedFilter = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // This happens if nothing was tapped.
                selectedFilter = lastName;
                hashifyLastName();
            }
        });
    }

    public void performSearch(View v) {
        // The search query is grabbed from the text box.
        String query = editTextSearch.getText().toString().trim().toLowerCase(), queryKey;

        // A lone patient, used to hold matching patients.
        Patient patient = new Patient();

        // The count of exact matches.
        int exactCount = 0;

        //
        if (selectedFilter.equals(lastName) && InputCheck.searchQueryName(query)) {
            // If the last name filter is tapped, create a hash map for patients with their last names
            //  as keys.
            hashifyLastName();

            // Repeated characters are removed from the query.
            // The query is converted into a key.
            query = removeRepeats(query);
            queryKey = convertToKey(query);

            // If the query key is in the patient map, move the results into the results list.
            if (patientMap.get(queryKey) != null) {
                // The patient result list is assigned the matching list of patients.
                patientResultList = patientMap.get(queryKey);

                for (int i = 0; i < patientResultList.size(); i++) {
                    // Grab the patients individually.
                    patient = patientResultList.get(i);

                    // Remove the repeats from the patient's last name, then check if it matches the query
                    if (removeRepeats(patient.getLastName().trim().toLowerCase()).equals(query)) {
                        // There is a match, so set the exact match at the top of the list.
                        patientResultList.set(i, patientResultList.get(exactCount));

                        // The top of the list is the next available spot at the top.
                        patientResultList.set(exactCount, patient);

                        // Increase the exact count, this is the next available spot at the top.
                        exactCount += 1;
                    }
                }

                // Populate the listview with the result list.
                populateListViewPatientsSearch();
            } else {
                hideListViewPatientsSearch();
            }
        } else if (selectedFilter.equals(firstName) && InputCheck.searchQueryName(query)) {
            // Same as the last name, just using the first name,
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
            // If the age is tapped, create a hash map for patients with their age
            //  as keys.
            hashifyAge();

            // The query is made the query key.
            queryKey = query;

            // If the patient map has a match with query key.
            if (patientMap.get(queryKey) != null) {
                // Pass over the list from the hash map to the patient result list.
                patientResultList = patientMap.get(queryKey);

                // Populate the list view with the result list.
                populateListViewPatientsSearch();
            } else {
                hideListViewPatientsSearch();
            }
        } else {
            // If there the input check fails, toast the user that the query is invalid.
            Toast.makeText(getApplicationContext(), getString(R.string.invalid_query), Toast.LENGTH_SHORT).show();
        }
    }

    private void hashifyLastName() {
        // The key.
        String patientKey;

        // List of patients.
        List<Patient> patientValueList;

        // A lone patient.
        Patient patient = new Patient();

        // The patient map and result list is reset.
        patientMap = new HashMap<>();
        patientResultList = new ArrayList<>();

        // For all the patients in the list.
        for (int i = 0; i < patientList.size(); i++) {
            // Get a patient.
            patient = patientList.get(i);

            // Remove the repeats of the last name.
            patientKey = removeRepeats(patient.getLastName().toLowerCase());

            // Convert the key to a real key.
            patientKey = convertToKey(patientKey);

            // The list of patients is reset.
            patientValueList = new ArrayList<>();

            if (patientMap.get(patientKey) == null) {
                // If the patient map is not null for the key, add the patient to the list.
                patientValueList.add(patient);

                // Put the patient key as the key and the patient list as the value.
                patientMap.put(patientKey, patientValueList);
            } else {
                // Since the map already has a value for the key, get the list for that key.
                patientValueList = patientMap.get(patientKey);

                // Add the patient to the list.
                patientValueList.add(patient);

                // Place the list back into the map.
                patientMap.put(patientKey, patientValueList);
            }
        }
    }

    private void hashifyFirstName() {
        // Same as the last name, but for first name.
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
        // Same as last name, but for age.
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
        // The temp char.
        char tempChar = ' ';

        // The list of repeat chars.
        List<String> repeatChars = new ArrayList<>();

        // Loop through the key.
        for (int i = 0; i < key.length(); i++) {
            // If the tempchar equals the current char.
            if (tempChar == key.charAt(i)) {
                // Add the char to the list of repeated chars.
                repeatChars.add(Character.toString(tempChar));
            } else {
                // The temp char is now the current char.
                tempChar = key.charAt(i);
            }
        }

        // If the list of repeated char is not empty.
        if (!repeatChars.isEmpty()) {
            for (int i = 0; i < repeatChars.size(); i++) {
                // Repeated chars are replaced with just the single char.
                key = key.replaceAll(repeatChars.get(i) + repeatChars.get(i), repeatChars.get(i));
            }
        }

        // Return the new and improved key.
        return key;
    }

    private String convertToKey(String key) {
        // A key is made by taking the first char of the query.
        return key.substring(0, 1).toLowerCase();
    }

    private void populateListViewPatientsSearch() {
        // The list view and adapter is assigned.
        ListView list = (ListView) findViewById(R.id.listViewPatientsSearch);
        PatientListSearchAdapter adapter = new PatientListSearchAdapter(this, patientResultList);

        // The query not found text view.
        TextView notFound = (TextView) findViewById(R.id.textViewNotFound);

        // Since there are results, the list view is visible, but the not found text view is gone.
        list.setVisibility(View.VISIBLE);
        notFound.setVisibility(View.GONE);

        // Set the adapter the list.
        list.setAdapter(adapter);

        // The the list is tapped.
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // The tapped patient.
                Patient tappedPatient = patientResultList.get(position);

                // The intent for the calendat activity.
                Intent intent = new Intent(SearchActivity.this, CalendarActivity.class);

                // Put the tapped patient as an extra in the intent.
                intent.putExtra(MainActivity.EXTRA_PATIENT, tappedPatient);

                // Start the calendar activity.
                startActivity(intent);
            }
        });
    }

    private void hideListViewPatientsSearch() {
        // The list view for the patients search.
        ListView list = (ListView) findViewById(R.id.listViewPatientsSearch);

        // The query not found text view.
        TextView notFound = (TextView) findViewById(R.id.textViewNotFound);

        // Since there were not results, the list is gone.
        // // The query not found text view is visible.
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
            // Go back the last activity.
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
