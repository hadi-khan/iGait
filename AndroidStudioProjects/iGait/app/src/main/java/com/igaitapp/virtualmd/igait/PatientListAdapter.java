package com.igaitapp.virtualmd.igait;

import android.content.Context;
import android.content.res.Resources;
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
        TextView textViewPatientName, textViewPatientAge;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.patient_item, null);

        patient = getItem(position);

        textViewPatientName = (TextView) view.findViewById(R.id.textViewInfoLeft);
        textViewPatientAge = (TextView) view.findViewById(R.id.textViewInfoRight);

        textViewPatientName.setText(patient.getFirstName().substring(0, 1) + ". " + patient.getLastName());
        textViewPatientAge.setText("" + patient.getAge());

        return view;
    }
}