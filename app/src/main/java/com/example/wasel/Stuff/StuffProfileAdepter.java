package com.example.wasel.Stuff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wasel.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by waleed on 2015-07-05.
 */
public class StuffProfileAdepter extends ArrayAdapter<HashMap<String, String>> {

    private ArrayList<HashMap<String, String>> profileData;
    int resourceId;
    Context context;

    public StuffProfileAdepter(Context context, int resource, ArrayList<HashMap<String, String>> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resourceId = resource;
        this.profileData = objects;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.stuff_profile_row, parent, false);
        }

        TextView username = (TextView) view.findViewById(R.id.stuff_username);
        TextView department = (TextView) view.findViewById(R.id.stuff_department);
        TextView degree = (TextView) view.findViewById(R.id.stuff_degree);
        TextView specailization = (TextView) view.findViewById(R.id.stuff_specailization);
        TextView projects = (TextView) view.findViewById(R.id.stuff_projects);
        TextView events = (TextView) view.findViewById(R.id.stuff_events);


        username.setText( profileData.get(i).get("username"));
        department.setText(profileData.get(i).get("department"));
        degree.setText(profileData.get(i).get("degree"));
        specailization.setText(profileData.get(i).get("specailization"));
        projects.setText( profileData.get(i).get("projects"));
        events.setText( profileData.get(i).get("events"));


        return view;
    }
}
