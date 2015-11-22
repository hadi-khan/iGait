package com.igaitapp.virtualmd.igait;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PatientListSearchAdapter extends BaseAdapter {
    private Context mContext;
    private Resources mResource;

    private List<Patient> patientList;
    private String activity;

    PatientListSearchAdapter(Context context, List<Patient> patientList, String activity)
    {
        this.mContext = context;
        this.mResource = mContext.getResources();

        this.patientList = patientList;
        this.activity = activity;
    }

    @Override
    public int getCount()
    {
        return patientList.size();
    }

    @Override
    public Patient getItem(int position)
    {
        return patientList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Patient patient = getItem(position);
        List<GaitHealth> gaitHealthList = patient.getGaitHealth();
        GaitHealth gaitHealth = gaitHealthList.get(0);
        Calendar weekPrev = Calendar.getInstance(), weekCurrent = Calendar.getInstance();

        int gaitHealthSum = 0, gaitHealthCount = 0;
        int[] health = {0, 0, 0, 0};

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.patient_item, null);

        ImageView imageViewPatientImage = (ImageView) view.findViewById(R.id.imageViewPatientImage);
        TextView textViewInfoLeft = (TextView) view.findViewById(R.id.textViewInfoLeft);
        TextView textViewInfoRight = (TextView) view.findViewById(R.id.textViewInfoRight);
        ImageView imageViewPriorityNotif = (ImageView) view.findViewById(R.id.imageViewPriorityNotif);

        weekPrev.setTime(gaitHealth.getStartTime());
        for (int i = 0; i < gaitHealthList.size(); i++) {
            gaitHealth = gaitHealthList.get(i);
            weekCurrent.setTime(gaitHealth.getStartTime());

            gaitHealthSum += gaitHealth.getHealth();
            gaitHealthCount += 1;

            if (weekPrev.get(Calendar.WEEK_OF_YEAR) != weekCurrent.get(Calendar.WEEK_OF_YEAR) || (i + 1) == gaitHealthList.size()) {
                weekPrev.setTime(gaitHealth.getStartTime());

                for (int o = 0; o < 3; o++) {
                    health[o] = health[o + 1];
                }

                if (Math.round(gaitHealthSum / gaitHealthCount) == 3) {
                    health[3] = 3;
                }
                else if(Math.round(gaitHealthSum / gaitHealthCount) == 2) {
                    health[3] = 2;
                }
                else if (Math.round(gaitHealthSum / gaitHealthCount) == 1) {
                    health[3] = 1;
                } else {
                    health[3] = 0;
                }

                gaitHealthSum = 0;
                gaitHealthCount = 0;
            }
        }

        imageViewPatientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                intent = new Intent(mContext, PatientProfileActivity.class);

                intent.putExtra(MainActivity.EXTRA_PATIENT, patient);
                intent.putExtra(MainActivity.EXTRA_PARENT_ID, activity);

                mContext.startActivity(intent);
            }
        });

        textViewInfoLeft.setText(patient.getFirstName() + " " + patient.getLastName());
        textViewInfoRight.setText("" + ((new Date().getTime() - patient.getBirthday().getTime()) / 1000 / 60 / 60 / 24 / 365));

        if (!patient.isPriority()) {
            imageViewPriorityNotif.setVisibility(View.GONE);
        }
        else {
            imageViewPriorityNotif.setVisibility(View.VISIBLE);
        }

        return view;
    }
}