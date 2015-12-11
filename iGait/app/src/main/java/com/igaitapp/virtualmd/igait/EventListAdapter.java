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
    // The context and resource.
    private Context mContext;
    private Resources mResource;

    // The list of gait health.
    private List<GaitHealth> gaitHealthList;

    EventListAdapter(Context context, List<GaitHealth> gaitHealthList)
    {
        // Assign the context and resource.
        this.mContext = context;
        this.mResource = mContext.getResources();

        // Assign the list of gait health.
        this.gaitHealthList = gaitHealthList;
    }

    @Override
    public int getCount()
    {
        // Return the size of the gait health list.
        return gaitHealthList.size();
    }

    @Override
    public GaitHealth getItem(int position)
    {
        // Return the gait health at the given position.
        return gaitHealthList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        // Return the position.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Date format used to display the event times.
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");

        // Gait health at the tapped position.
        GaitHealth gaitHealth = getItem(position);

        // Inflate the event_item xml.
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.event_item, null);

        // The image view health displays a calendar image, signifying an event.
        // The text view info left displays the start time.
        // The text view info right displays the end time.
        // The image view video notif is diplayed if a video is offered for this certain event.
        ImageView imageViewHealth = (ImageView) view.findViewById(R.id.imageViewHealth1);
        TextView textViewInfoLeft = (TextView) view.findViewById(R.id.textViewInfoLeft);
        TextView textViewInfoRight = (TextView) view.findViewById(R.id.textViewInfoRight);
        ImageView imageViewVideoNotif = (ImageView) view.findViewById(R.id.imageViewVideoNotif);

        // Assign colors to the health events.
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

        // If a video exists, show the image view notif.
        if (!gaitHealth.hasVideo()) {
            imageViewVideoNotif.setVisibility(View.INVISIBLE);
        }
        else {
            imageViewVideoNotif.setVisibility(View.VISIBLE);
        }

        // Set the start and end time in the corresponding text views.
        textViewInfoLeft.setText(timeFormat.format(gaitHealth.getStartTime()));
        textViewInfoRight.setText(timeFormat.format(gaitHealth.getEndTime()));

        return view;
    }
}