package com.example.wasel.Student;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.wasel.JSON.JsonReader;
import com.example.wasel.Login.Student_ResponseHandler;
import com.example.wasel.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment2 extends Fragment {


    JSONArray Exam;
    String exam_url ="http://10.0.2.2:80/androidtest/readexam.php";

    ArrayList<HashMap<String, String>> ExamData;
    ListAdapter adapterArray;
    ListView exam_lst;
    String y, d;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag2, container, false);

       y = Student_ResponseHandler.year;
     d= Student_ResponseHandler.department;

        ExamData = new ArrayList<HashMap<String, String>>();
        exam_lst = (ListView) view.findViewById(R.id.exam_listview);

        new GetExams().execute(y, d);

        return view;
    }

    class GetExams extends AsyncTask<String, String, Boolean> {


        String year, department, result, message;
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
        protected Boolean doInBackground(String... params) {

            year = params[0];
            department = params[1];
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("year", year));
            pairs.add(new BasicNameValuePair("department", department));


            JsonReader j = new JsonReader(exam_url, pairs);

            result = j.sendRequest();

            try {
                jsonObject = new JSONObject(result);
                success = jsonObject.getInt("success");
                message = jsonObject.getString("message");

            } catch (JSONException e) { e.printStackTrace(); }

            switch (success) {
                case 0:
                        if (message == null) { message = "error in connection "; }
                     return false;

               case 1:

                    try {
                        Exam = jsonObject.getJSONArray("posts");

                        for (int i = 0; i < Exam.length(); i++)
                        {
                            JSONObject c = Exam.getJSONObject(i);

                            String title = c.getString("title");
                            String date = c.getString("date");
                            String publisher = c.getString("publisher");
                            String body = c.getString("body");

                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("title", title);
                            map.put("date", date);
                            map.put("publisher", publisher);
                            map.put("body", body);
                            ExamData.add(map);

                        }
                    }
                    catch (JSONException e) {  e.printStackTrace(); }
                   return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

          ProgressDialog.dismiss();

            if(aBoolean) {

                adapterArray = new ListAdapter(getActivity().getBaseContext() , R.layout.listviewlayout , ExamData);
               exam_lst.setAdapter(adapterArray);
            }
        }
    }

}
