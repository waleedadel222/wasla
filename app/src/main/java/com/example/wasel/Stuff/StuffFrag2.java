package com.example.wasel.Stuff;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.wasel.JSON.JsonReader;
import com.example.wasel.Login.Stuff_ResponseHandler;
import com.example.wasel.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StuffFrag2 extends Fragment {

    JSONArray stuff_exams;
    String stuff_exam_url = "http://10.0.2.2:80/androidtest/read_exam_stuff.php";

    ArrayList<HashMap<String, String>> Exam_Data;
    ListAdapter adapterArray;
    ListView exam_list;
    String d;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.stuff_frag2, container, false);
        d = Stuff_ResponseHandler.department;
        Exam_Data = new ArrayList<HashMap<String, String>>();
        exam_list = (ListView) view.findViewById(R.id.stuff_exam_listView);
        new GetStuff_exam().execute(d);

        return view;
    }

    public class GetStuff_exam extends AsyncTask<String, String, Boolean> {


        String department, result, message;
        int success;
        JSONObject jsonObject;
        // ProgressDialog
        private android.app.ProgressDialog ProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ProgressDialog = ProgressDialog.show(getActivity(), "Processing...",
                    "Getting the Exams", false, false);
        }

        @Override
        protected Boolean doInBackground(String... params) {  department =params[0];
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("department", department));
          JsonReader j = new JsonReader(stuff_exam_url, pairs);

            result = j.sendRequest();

            try {
                jsonObject = new JSONObject(result);
                success= jsonObject.getInt("success");
                message =jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            switch (success) {
                case 0: if (message== null){message = "error in connection " ;}
                    return false;

                case 1:

                    try {
                        stuff_exams = jsonObject.getJSONArray("posts");
                        for (int i = 0; i < stuff_exams.length(); i++) {
                            JSONObject c = stuff_exams.getJSONObject(i);

                            String title = c.getString("title");
                            String date = c.getString("date");
                            String publisher = c.getString("publisher");
                            String body = c.getString("body");

                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("title", title);
                            map.put("date", date);
                            map.put("publisher", publisher);
                            map.put("body", body);
                            Exam_Data.add(map);

                        }
                    }catch(JSONException e){ e.printStackTrace();  }

                    return true;
            }
            return false;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            ProgressDialog.dismiss();
         if(aBoolean) {
             adapterArray = new com.example.wasel.Student.ListAdapter(getActivity().getBaseContext(), R.layout.stuff_list_row, Exam_Data);
                exam_list.setAdapter(adapterArray);
            }
        }
    }
}