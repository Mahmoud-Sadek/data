package com.example.mahmoud.healthtag;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.Date;
import java.util.List;


/**
 * Created by Mahmoud on 6/22/2016.
 */
public class ActiveListAdapter extends BaseAdapter {
    Context mContext;
    List<Rojet> rojets;

    /**
     * Public constructor that initializes private instance variables when adapter is created
     */
    public ActiveListAdapter(Context activity, List<Rojet> rojets) {
        this.mContext = activity;
        this.rojets = rojets;
    }

    @Override
    public int getCount() {
        return rojets.size();
    }

    @Override
    public Object getItem(int i) {
        return rojets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.single_active_list, viewGroup, false);
        }

        TextView textViewList = (TextView) view.findViewById(R.id.text_view_list);
        TextView textViewSpecialty = (TextView) view.findViewById(R.id.specialty);
        TextView textViewDocName = (TextView) view.findViewById(R.id.docName);
        TextView timeText = (TextView) view.findViewById(R.id.text_view_edit_time);

        /* Set the list name and owner */
        textViewList.setText(rojets.get(i).getSigns() + "");
        textViewSpecialty.setText(rojets.get(i).getSpecialty() + "");
        textViewDocName.setText(rojets.get(i).getDoctorName() + "");
        timeText.setText(rojets.get(i).getTimestampCreated());
        return view;
    }
}