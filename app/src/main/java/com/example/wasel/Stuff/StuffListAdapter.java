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
 * Created by waleed on 2015-07-04.
 */
public class StuffListAdapter extends ArrayAdapter<HashMap<String, String>> {

    private ArrayList<HashMap<String, String>> Data;

    int resourceId;
    Context context;

    public StuffListAdapter(Context context, int resource, ArrayList<HashMap<String, String>> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resourceId = resource;
        this.Data = objects;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.listviewlayout, parent, false);
        }

        TextView title = (TextView) view.findViewById(R.id.stuff_title);
        TextView date = (TextView) view.findViewById(R.id.stuff_date);
        TextView body = (TextView) view.findViewById(R.id.stuff_body);
        TextView publisher = (TextView) view.findViewById(R.id.stuff_publisher);


        title.setText( Data.get(i).get("title"));
        date.setText( Data.get(i).get("date"));
        publisher.setText(Data.get(i).get("publisher"));
        body.setText(Data.get(i).get("body"));


        return view;
    }

}
