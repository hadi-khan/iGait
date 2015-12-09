package com.igaitapp.virtualmd.igait;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;


public class EventListAdapter extends BaseAdapter {
    private Context mContext;
    private Resources mResource;

    private List<GaitHealth> gaitHealthList;

    EventListAdapter(Context context, List<GaitHealth> gaitHealthList)
    {
        this.mContext = context;
        this.mResource = mContext.getResources();

        this.gaitHealthList = gaitHealthList;
    }

    @Override
    public int getCount()
    {
        return gaitHealthList.size();
    }

    @Override
    public GaitHealth getItem(int position)
    {
        return gaitHealthList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        View view;

        ImageView imageViewHealth;
        TextView textViewInfoLeft, textViewInfoRight;
        ImageView imageViewVideoNotif;

        GaitHealth gaitHealth;
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.event_item, null);

        imageViewHealth = (ImageView) view.findViewById(R.id.imageViewHealth1);
        textViewInfoLeft = (TextView) view.findViewById(R.id.textViewInfoLeft);
        textViewInfoRight = (TextView) view.findViewById(R.id.textViewInfoRight);
        imageViewVideoNotif = (ImageView) view.findViewById(R.id.imageViewVideoNotif);

        gaitHealth = getItem(position);

        if (gaitHealth.getHealth() == 3) {
            imageViewHealth.setImageResource(R.drawable.health_green);
        }
        else if (gaitHealth.getHealth() == 2) {
            imageViewHealth.setImageResource(R.drawable.health_orange);
        }
        else if (gaitHealth.getHealth() == 1) {
            imageViewHealth.setImageResource(R.drawable.health_red);
        }
        else {
            imageViewHealth.setImageResource(R.drawable.health_grey);
        }

        if (!gaitHealth.hasVideo()) {
            imageViewVideoNotif.setVisibility(View.INVISIBLE);
        }
        else {
            imageViewVideoNotif.setVisibility(View.VISIBLE);
        }

        textViewInfoLeft.setText(timeFormat.format(gaitHealth.getStartTime()));
        textViewInfoRight.setText(timeFormat.format(gaitHealth.getEndTime()));

        return view;
    }
}