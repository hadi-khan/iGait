package com.igaitapp.virtualmd.igait;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PatientListAdapter extends BaseAdapter {
    private Context mContext;
    private Resources mResource;

    private List<Patient> patientList;

    PatientListAdapter(Context context, List<Patient> patientList) {
        this.mContext = context;
        this.mResource = mContext.getResources();

        this.patientList = patientList;
    }

    @Override
    public int getCount() {
        return patientList.size();
    }

    @Override
    public Patient getItem(int position) {
        return patientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleDateFormat tfh = new SimpleDateFormat("H");
        SimpleDateFormat tfm = new SimpleDateFormat("m");
        SimpleDateFormat tfs = new SimpleDateFormat("s");

        final Patient patient = getItem(position);
        List<GaitHealth> gaitHealthList = patient.getGaitHealth();
        Calendar prevDate = Calendar.getInstance(), currDate = Calendar.getInstance();
        GaitHealth gaitHealth;

        int gaitHealthSum = 0, gaitHealthCount = 0, confidenceSum = 0, confidenceCount = 0;;
        int[] health = {0, 0, 0, 0};
        int hours = 0, minutes = 0, seconds = 0, diff = 0;
        double confidencePercent = 0d;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.patient_item, null);

        ImageView imageViewPatientImage = (ImageView) view.findViewById(R.id.imageViewPatientImage);
        ImageView imageViewConfidence = (ImageView) view.findViewById(R.id.imageViewConfidence);
        ImageView[] imageViewHealths = {(ImageView) view.findViewById(R.id.imageViewHealth1),
                (ImageView) view.findViewById(R.id.imageViewHealth2),
                (ImageView) view.findViewById(R.id.imageViewHealth3),
                (ImageView) view.findViewById(R.id.imageViewHealth4)};
        TextView textViewInfoLeft = (TextView) view.findViewById(R.id.textViewInfoLeft);
        TextView textViewInfoRight = (TextView) view.findViewById(R.id.textViewInfoRight);
        ImageView imageViewPriorityNotif = (ImageView) view.findViewById(R.id.imageViewPriorityNotif);

        if (!gaitHealthList.isEmpty()) {
            gaitHealth = gaitHealthList.get(0);
            prevDate.setTime(gaitHealth.getStartTime());
            for (int i = 0; i < gaitHealthList.size(); i++) {
                gaitHealth = gaitHealthList.get(i);
                currDate.setTime(gaitHealth.getStartTime());

                if (prevDate.get(Calendar.DAY_OF_YEAR) == currDate.get(Calendar.DAY_OF_YEAR)  || (i + 1) == gaitHealthList.size()) {
                    confidenceCount += 1;
                }

                if (prevDate.get(Calendar.WEEK_OF_YEAR) != currDate.get(Calendar.WEEK_OF_YEAR) || (i + 1) == gaitHealthList.size()) {
                    prevDate.setTime(gaitHealth.getStartTime());

                    for (int o = 0; o < 3; o++) {
                        health[o] = health[o + 1];
                    }

                    if (Math.round(gaitHealthSum / gaitHealthCount) == 3) {
                        health[3] = 3;
                    } else if (Math.round(gaitHealthSum / gaitHealthCount) == 2) {
                        health[3] = 2;
                    } else if (Math.round(gaitHealthSum / gaitHealthCount) == 1) {
                        health[3] = 1;
                    } else {
                        health[3] = 0;
                    }

                    gaitHealthSum = 0;
                    gaitHealthCount = 0;
                }

                gaitHealthSum += gaitHealth.getHealth();
                gaitHealthCount += 1;

                seconds = Integer.parseInt(tfs.format(gaitHealth.getEndTime()));
                minutes = Integer.parseInt(tfm.format(gaitHealth.getEndTime())) * 60 + seconds;
                hours = Integer.parseInt(tfh.format(gaitHealth.getEndTime())) * 60 * 60 + minutes;

                diff = hours;

                seconds = Integer.parseInt(tfs.format(gaitHealth.getStartTime()));
                minutes = Integer.parseInt(tfm.format(gaitHealth.getStartTime())) * 60 + seconds;
                hours = Integer.parseInt(tfh.format(gaitHealth.getStartTime())) * 60 * 60 + minutes;

                confidenceSum += diff - hours;
            }
        }

        if (confidenceCount != 0) {
            seconds = Integer.parseInt(tfs.format(patient.getExpectedWalkTime()));
            minutes = Integer.parseInt(tfm.format(patient.getExpectedWalkTime())) * 60 + seconds;
            hours = Integer.parseInt(tfh.format(patient.getExpectedWalkTime())) * 60 * 60 + minutes;

            confidencePercent = ((double) confidenceSum / confidenceCount / hours) * 100d;

            if (confidencePercent > 75) {
                imageViewConfidence.setImageResource(R.drawable.conf_100);
            }
            else if (confidencePercent > 50) {
                imageViewConfidence.setImageResource(R.drawable.conf_75);
            }
            else if (confidencePercent > 25) {
                imageViewConfidence.setImageResource(R.drawable.conf_50);
            }
            else {
                imageViewConfidence.setImageResource(R.drawable.conf_0);
            }
        }

        for (int i = 0; i < health.length; i++) {
            if (health[i] == 3) {
                imageViewHealths[i].setImageResource(R.drawable.health_green);
            }
            else if (health[i] == 2) {
                imageViewHealths[i].setImageResource(R.drawable.health_orange);
            }
            else if (health[i] == 1) {
                imageViewHealths[i].setImageResource(R.drawable.health_red);
            }
            else {
                imageViewHealths[i].setImageResource(R.drawable.health_grey);
            }
        }

        imageViewPatientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                intent = new Intent(mContext, PatientProfileActivity.class);

                intent.putExtra(MainActivity.EXTRA_PATIENT, patient);

                mContext.startActivity(intent);
            }
        });

        textViewInfoLeft.setText(patient.getFirstName().substring(0, 1) + ". " + patient.getLastName());
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