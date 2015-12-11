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
    // The context and resource.
    private Context mContext;
    private Resources mResource;

    // The list of gait health.
    private List<Patient> patientList;

    PatientListAdapter(Context context, List<Patient> patientList) {
        // Assign the context and resource.
        this.mContext = context;
        this.mResource = mContext.getResources();

        // Assign the list of gait health.
        this.patientList = patientList;
    }

    @Override
    public int getCount() {
        // Return the size of the gait health list.
        return patientList.size();
    }

    @Override
    public Patient getItem(int position) {
        // Return the gait health at the given position.
        return patientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Return the position.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Date formats to find to extract hours, minutes, and seconds from the gait health and
        //  expected walk time.
        SimpleDateFormat tfh = new SimpleDateFormat("H");
        SimpleDateFormat tfm = new SimpleDateFormat("m");
        SimpleDateFormat tfs = new SimpleDateFormat("s");

        // The patient at the tapped position.
        final Patient patient = getItem(position);

        // The patient's health list.
        List<GaitHealth> gaitHealthList = patient.getGaitHealth();

        // Dates to find weekly averages of health.
        Calendar prevDate = Calendar.getInstance(), currDate = Calendar.getInstance();

        // A lone gait health.
        GaitHealth gaitHealth;

        // The gait health sum and count. The confidence sum and count.
        int gaitHealthSum = 0, gaitHealthCount = 0, confidenceSum = 0, confidenceCount = 0;;

        // Four weeks of health averages.
        int[] health = {0, 0, 0, 0};

        // The hours, minutes, and seconds are used to find confidence.
        // The confidence percent holds if the data is reliable or not. High yes, low no.
        int hours = 0, minutes = 0, seconds = 0, diff = 0;
        double confidencePercent = 0d;

        // Inflate the patient_item xml.
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.patient_item, null);

        // The image view patient hold the patient image.
        // The image view confidence hold the confidence of the health averages.
        // The image view healths each represent health averages for 4 weeks.
        // The info left and info right holds the first initial, the last name of the patient, and age.
        // The image view priority notif appears when the patient is a priority.
        ImageView imageViewPatientImage = (ImageView) view.findViewById(R.id.imageViewPatientImage);
        ImageView imageViewConfidence = (ImageView) view.findViewById(R.id.imageViewConfidence);
        ImageView[] imageViewHealths = {(ImageView) view.findViewById(R.id.imageViewHealth1),
                (ImageView) view.findViewById(R.id.imageViewHealth2),
                (ImageView) view.findViewById(R.id.imageViewHealth3),
                (ImageView) view.findViewById(R.id.imageViewHealth4)};
        TextView textViewInfoLeft = (TextView) view.findViewById(R.id.textViewInfoLeft);
        TextView textViewInfoRight = (TextView) view.findViewById(R.id.textViewInfoRight);
        ImageView imageViewPriorityNotif = (ImageView) view.findViewById(R.id.imageViewPriorityNotif);

        // If the gait health list is not empty, average the health.
        if (!gaitHealthList.isEmpty()) {
            // The lone gait health of the current day.
            gaitHealth = gaitHealthList.get(0);

            // The previous day, which at first will be the current day.
            prevDate.setTime(gaitHealth.getStartTime());

            for (int i = 0; i < gaitHealthList.size(); i++) {
                // The lone gait health of the current day.
                gaitHealth = gaitHealthList.get(i);

                // The current day.
                 currDate.setTime(gaitHealth.getStartTime());

                // If the previous week is not the current week, begin averaging.
                if (prevDate.get(Calendar.WEEK_OF_YEAR) != currDate.get(Calendar.WEEK_OF_YEAR)) {
                    // Four weeks of health averages are going to be displayed, from right to left.
                    // Shift the health by 1 element to the left.
                    for (int o = 0; o < 3; o++) {
                        health[o] = health[o + 1];
                    }

                    // Find the average for this week.
                    if (Math.round(gaitHealthSum / gaitHealthCount) == 3) {
                        health[3] = 3;
                    } else if (Math.round(gaitHealthSum / gaitHealthCount) == 2) {
                        health[3] = 2;
                    } else if (Math.round(gaitHealthSum / gaitHealthCount) == 1) {
                        health[3] = 1;
                    } else {
                        health[3] = 0;
                    }

                    // The previous date is now the current date.
                    prevDate.setTime(gaitHealth.getStartTime());

                    // Reset the totals.
                    gaitHealthSum = 0;
                    gaitHealthCount = 0;
                }

                // Increment the sum and count.
                gaitHealthSum += gaitHealth.getHealth();
                gaitHealthCount += 1;

                // If the previous day is different from the previous year, increment the confidence count.
                if (prevDate.get(Calendar.DAY_OF_YEAR) == currDate.get(Calendar.DAY_OF_YEAR)  || (i + 1) == gaitHealthList.size()) {
                    confidenceCount += 1;
                }

                // If the date is the last of the list, begin averaging.
                if (i == (gaitHealthList.size() - 1)) {
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
                }

                // Computer the start time in seconds.
                seconds = Integer.parseInt(tfs.format(gaitHealth.getEndTime()));
                minutes = Integer.parseInt(tfm.format(gaitHealth.getEndTime())) * 60 + seconds;
                hours = Integer.parseInt(tfh.format(gaitHealth.getEndTime())) * 60 * 60 + minutes;
                diff = hours;

                // Compute the end time in seconds.
                seconds = Integer.parseInt(tfs.format(gaitHealth.getStartTime()));
                minutes = Integer.parseInt(tfm.format(gaitHealth.getStartTime())) * 60 + seconds;
                hours = Integer.parseInt(tfh.format(gaitHealth.getStartTime())) * 60 * 60 + minutes;

                // The difference between the two is added to the confidence sum.
                confidenceSum += diff - hours;
            }
        }


        // If the confidence is not 0, compute the confidence percentage.
        if (confidenceCount != 0) {
            // Compute the expected walk time in seconds.
            seconds = Integer.parseInt(tfs.format(patient.getExpectedWalkTime()));
            minutes = Integer.parseInt(tfm.format(patient.getExpectedWalkTime())) * 60 + seconds;
            hours = Integer.parseInt(tfh.format(patient.getExpectedWalkTime())) * 60 * 60 + minutes;

            // The confidence percent found using the confidence sum, count, and expected walk time.
            confidencePercent = ((double) confidenceSum / confidenceCount / hours) * 100d;

            // Show the corresponding image for each percentage.
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

        // Show the corresponding image color for each week average.
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

        // A click listener for the image view patient.
        imageViewPatientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The intent that opens the patient profile.
                Intent intent = new Intent(mContext, PatientProfileActivity.class);

                // Put the patient as an extra.
                intent.putExtra(MainActivity.EXTRA_PATIENT, patient);

                // Start the patient profile activity.
                mContext.startActivity(intent);
            }
        });

        // The text view info left holds the first initial and last name.
        // The text view info right holds the age.
        textViewInfoLeft.setText(patient.getFirstName().substring(0, 1) + ". " + patient.getLastName());
        textViewInfoRight.setText("" + ((new Date().getTime() - patient.getBirthday().getTime()) / 1000 / 60 / 60 / 24 / 365));

        // If the patient is a priority show the image view priority notif.
        if (!patient.isPriority()) {
            imageViewPriorityNotif.setVisibility(View.GONE);
        }
        else {
            imageViewPriorityNotif.setVisibility(View.VISIBLE);
        }

        return view;
    }
}