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

    private List<Patient> patientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Thread clientThread = new Thread(new ClientThread());

        //clientThread.start();
        populatePatientList();
        populateListViewPatientsMain();
    }

    private void populatePatientList() {
        List<GaitHealth> bw = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.US);
        DateFormat ex = new SimpleDateFormat("kk:mm:ss", Locale.US);

        try {
            bw.add(new GaitHealth(3, df.parse("Wed Sep 2 08:10:00 CST 2015"), df.parse("Wed Sep 2 08:11:00 CST 2015"), true));
            bw.add(new GaitHealth(3, df.parse("Wed Sep 2 12:20:00 CST 2015"), df.parse("Wed Sep 2 12:25:00 CST 2015"), false));
            bw.add(new GaitHealth(3, df.parse("Thu Sep 3 08:09:00 CST 2015"), df.parse("Thu Sep 3 08:10:00 CST 2015"), false));
            bw.add(new GaitHealth(2, df.parse("Thu Sep 3 15:00:00 CST 2015"), df.parse("Thu Sep 3 15:03:00 CST 2015"), true));
            bw.add(new GaitHealth(2, df.parse("Fri Sep 4 08:10:00 CST 2015"), df.parse("Fri Sep 4 08:14:00 CST 2015"), false));
            bw.add(new GaitHealth(1, df.parse("Fri Sep 4 12:10:00 CST 2015"), df.parse("Fri Sep 4 12:16:00 CST 2015"), true));
            bw.add(new GaitHealth(1, df.parse("Fri Sep 4 15:10:00 CST 2015"), df.parse("Fri Sep 4 15:12:00 CST 2015"), false));
            bw.add(new GaitHealth(1, df.parse("Sat Sep 5 08:10:00 CST 2015"), df.parse("Sat Sep 5 08:18:00 CST 2015"), false));
            bw.add(new GaitHealth(2, df.parse("Sat Sep 5 12:10:00 CST 2015"), df.parse("Sat Sep 5 12:19:00 CST 2015"), true));
            bw.add(new GaitHealth(3, df.parse("Sun Sep 6 08:10:00 CST 2015"), df.parse("Sun Sep 6 08:12:00 CST 2015"), false));
            bw.add(new GaitHealth(3, df.parse("Sun Sep 6 13:10:00 CST 2015"), df.parse("Sun Sep 6 13:14:00 CST 2015"), false));
            bw.add(new GaitHealth(3, df.parse("Sun Sep 6 15:10:00 CST 2015"), df.parse("Sun Sep 6 15:15:00 CST 2015"), false));
            bw.add(new GaitHealth(0, df.parse("Mon Sep 7 08:10:00 CST 2015"), df.parse("Mon Sep 7 08:11:00 CST 2015"), true));
            bw.add(new GaitHealth(0, df.parse("Mon Sep 7 13:10:00 CST 2015"), df.parse("Mon Sep 7 13:18:00 CST 2015"), true));

            patientList.add(new Patient(1234567890, "Shutter", "Selma", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Whitby", "Phoebe", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Boardman", "Kay", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Masser", "Ivan", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Labarge", "Soraya", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Sasaki", "Chanelle", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Orosz", "Latarsha", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Macdonald", "Cathrine", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Trivett", "Cammy", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Archambault", "Ulrike", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Durante", "Tracy", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Blakey", "Marylee", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Reich", "Darrel", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Rane", "Kallie", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Shealey", "Floria", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Smithson", "Carina", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Coffey", "Beverley", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Olivera", "Charlesetta", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Beckwith", "Erlinda", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Spires", "Diane", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Borda", "Cherlyn", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Dasher", "Estella", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Campoverde", "Billie", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Greb", "Coletta", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Bonnet", "Ramona", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Sneller", "Warren", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Watford", "Franklin", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Poss", "Lonna", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Northrup", "Shirlee", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Pangburn", "Drucilla", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Jeon", "Lu", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Pool", "Ebonie", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Bateman", "America", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Talbert", "Donna", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Mcandrews", "Iluminada", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Lippert", "Nickolas", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Washburn", "Tandy", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Kurland", "Elenore", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Dennett", "Maira", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Elkin", "Rosy", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Machado", "Cinderella", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Merry", "Tyler", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Aylor", "Eunice", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Garett", "Houston", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Yokoyama", "Ezra", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Urban", "Valarie", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Meuser", "Juliette", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Cantin", "Veronique", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Rueter", "Tracie", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
            patientList.add(new Patient(1234567890, "Gamache", "Darrin", df.parse("Fri Feb 03 07:00:00 CST 1989"), 'm', new ContactInfo("example@gmail.com", "1234 Example St.", "Example", "TX", 12345, "123-456-7890"), ex.parse("00:03:00"), bw, true));
        }
        catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    private void populateListViewPatientsMain() {
        ListView list;
        PatientListAdapter adapter;

        list = (ListView) findViewById(R.id.listViewPatientsMain);
        adapter = new PatientListAdapter(this, patientList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Patient tappedPatient;
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
        if (id == R.id.action_search) {
            Intent intent;

            intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra(EXTRA_PATIENT_LIST, (Serializable) patientList);

            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_account) {
            return true;
        }

        return super.onOptionsItemSelected(item);
        }
}
