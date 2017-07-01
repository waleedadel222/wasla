package com.example.wasel.Student;

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
 * Created by waleed on 2015-07-04.
 */
public class ProfileAdapter extends ArrayAdapter<HashMap<String, String>> {

        private ArrayList<HashMap<String, String>> profileData;
        int resourceId;
        Context context;

        public ProfileAdapter(Context context, int resource, ArrayList<HashMap<String, String>> objects) {
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
                view = inflater.inflate(R.layout.profile_layout, parent, false);
            }

            TextView username = (TextView) view.findViewById(R.id.username);
            TextView year = (TextView) view.findViewById(R.id.year);
            TextView department = (TextView) view.findViewById(R.id.department);


            username.setText( profileData.get(i).get("username"));
            year.setText( profileData.get(i).get("year"));
            department.setText(profileData.get(i).get("department"));


            return view;
        }





}
