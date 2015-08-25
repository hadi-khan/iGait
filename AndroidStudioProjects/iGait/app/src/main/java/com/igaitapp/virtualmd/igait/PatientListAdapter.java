package com.igaitapp.virtualmd.igait;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jesus on 8/21/15.
 */
public class PatientListAdapter extends BaseAdapter {
    private Context mContext;
    private Resources mResource;

    private List<Patient> patientList;

    PatientListAdapter(Context context, List<Patient> patientList)
    {
        this.mContext = context;
        this.mResource = mContext.getResources();

        this.patientList = patientList;
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
        LayoutInflater inflater;
        View view;
        Patient patient;
        List gaitHealth;
        TextView[] textViewG = new TextView[4];
        TextView textViewInfoLeft, textViewInfoRight;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.patient_item, null);

        patient = getItem(position);

        textViewG[0]=  (TextView) view.findViewById(R.id.textViewG1);
        textViewG[1] =  (TextView) view.findViewById(R.id.textViewG2);
        textViewG[2] =  (TextView) view.findViewById(R.id.textViewG3);
        textViewG[3] =  (TextView) view.findViewById(R.id.textViewG4);
        textViewInfoLeft = (TextView) view.findViewById(R.id.textViewInfoLeft);
        textViewInfoRight = (TextView) view.findViewById(R.id.textViewInfoRight);

        textViewInfoLeft.setText(patient.getFirstName().substring(0, 1) + ". " + patient.getLastName());
        textViewInfoRight.setText("" + patient.getAge());

        gaitHealth = patient.getGaitHealth();
        for (int i = 0; i < gaitHealth.size(); i++) {
            if ((int) gaitHealth.get(i) == 1) {
                textViewG[i].setTextColor(Color.parseColor("#ffed3b3b"));
            }
            else if((int) gaitHealth.get(i) == 2) {
                textViewG[i].setTextColor(Color.parseColor("#ffef6c00"));
            }
            else {
                textViewG[i].setTextColor(Color.parseColor("#ff689f38"));
            }
        }

        return view;
    }
}