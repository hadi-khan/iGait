package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_PATIENT = "com.igaitapp.virtualmd.igait.PATIENT_MAIN";
    public final static String EXTRA_PATIENT_LIST = "com.igaitapp.virtualmd.igait.PATIENT_LIST";
    public final static String EXTRA_SEARCH_ID = "com.igaitapp.virtualmd.igait.SEARCH_ID";
    public final static String EXTRA_USER = "com.igaitapp.virtualmd.igait.USER";

    private List<Patient> patientList = new ArrayList<>();
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUserAndPatients();
        populateListViewPatients();
    }

    private void getUserAndPatients() {
        List<GaitHealth> gh = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("MM dd yyyy kk:mm:ss");
        DateFormat tf = new SimpleDateFormat("kk:mm:ss");

        user = new User("Saunders", "Brian", new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345));

        try {
            gh.add(new GaitHealth(3, df.parse("10 30 2015 08:10:00"), df.parse("10 30 2015 08:11:00"), true));
            gh.add(new GaitHealth(3, df.parse("10 30 2015 12:20:00"), df.parse("10 30 2015 12:25:00"), false));
            gh.add(new GaitHealth(3, df.parse("10 31 2015 08:09:00"), df.parse("10 31 2015 08:10:00"), false));
            gh.add(new GaitHealth(2, df.parse("10 31 2015 15:00:00"), df.parse("10 31 2015 15:03:00"), true));
            gh.add(new GaitHealth(2, df.parse("11 01 2015 08:10:00"), df.parse("11 01 2015 08:14:00"), false));
            gh.add(new GaitHealth(1, df.parse("11 01 2015 12:10:00"), df.parse("11 01 2015 12:16:00"), true));
            gh.add(new GaitHealth(1, df.parse("11 01 2015 15:10:00"), df.parse("11 01 2015 15:12:00"), false));
            gh.add(new GaitHealth(1, df.parse("11 02 2015 08:10:00"), df.parse("11 02 2015 08:18:00"), false));
            gh.add(new GaitHealth(2, df.parse("11 02 2015 12:10:00"), df.parse("11 02 2015 12:19:00"), true));
            gh.add(new GaitHealth(3, df.parse("11 03 2015 08:10:00"), df.parse("11 03 2015 08:12:00"), false));
            gh.add(new GaitHealth(3, df.parse("11 03 2015 13:10:00"), df.parse("11 03 2015 13:14:00"), false));
            gh.add(new GaitHealth(3, df.parse("11 03 2015 15:10:00"), df.parse("11 03 2015 15:15:00"), false));
            gh.add(new GaitHealth(0, df.parse("11 04 2015 08:10:00"), df.parse("11 04 2015 08:11:00"), true));
            gh.add(new GaitHealth(0, df.parse("11 04 2015 13:10:00"), df.parse("11 04 2015 13:18:00"), true));

            patientList.add(new Patient(1234567890, "Shutter", "Selma", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Whitby", "Phoebe", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Boardman", "Kay", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Masser", "Ivan", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Labarge", "Soraya", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Sasaki", "Chanelle", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Orosz", "Latarsha", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Macdonald", "Cathrine", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Trivett", "Cammy", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Archambault", "Ulrike", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Durante", "Tracy", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Blakey", "Marylee", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Reich", "Darrel", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Rane", "Kallie", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Shealey", "Floria", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Smithson", "Carina", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Coffey", "Beverley", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Olivera", "Charlesetta", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Beckwith", "Erlinda", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Spires", "Diane", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Borda", "Cherlyn", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Dasher", "Estella", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Campoverde", "Billie", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Greb", "Coletta", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Bonnet", "Ramona", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Sneller", "Warren", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Watford", "Franklin", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Poss", "Lonna", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Northrup", "Shirlee", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Pangburn", "Drucilla", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Jeon", "Lu", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Pool", "Ebonie", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Bateman", "America", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Talbert", "Donna", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Mcandrews", "Iluminada", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Lippert", "Nickolas", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Washburn", "Tandy", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Kurland", "Elenore", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Dennett", "Maira", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Elkin", "Rosy", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Machado", "Cinderella", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Merry", "Tyler", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Aylor", "Eunice", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Garett", "Houston", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Yokoyama", "Ezra", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Urban", "Valarie", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Meuser", "Juliette", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Cantin", "Veronique", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Rueter", "Tracie", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
            patientList.add(new Patient(1234567890, "Gamache", "Darrin", tf.parse("00:03:00"), df.parse("02 21 1989 07:00:00"), 'm', new ContactInfo(1234567890, "example@gmail.com", "1234 Example St.", "Example", "TX", 12345), gh, true));
        }
        catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    private void populateListViewPatients() {
        ListView list;
        PatientListAdapter adapter;

        list = (ListView) findViewById(R.id.listViewPatients);
        adapter = new PatientListAdapter(this, patientList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Patient tappedPatient = new Patient();
                Intent intent;

                tappedPatient = patientList.get(position);
                intent = new Intent(MainActivity.this, CalendarActivity.class);
                intent.putExtra(EXTRA_PATIENT, tappedPatient);
                intent.putExtra(EXTRA_SEARCH_ID, false);

                startActivity(intent);
            }
        });
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
        if (id == R.id.action_add_patient) {
            Intent intent;

            intent = new Intent(MainActivity.this, NewPatientActivity.class);

            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_search) {
            Intent intent;

            intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra(EXTRA_PATIENT_LIST, (Serializable) patientList);

            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_account) {
            Intent intent;

            intent = new Intent(MainActivity.this, UserProfileActivity.class);
            intent.putExtra(EXTRA_USER, user);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
