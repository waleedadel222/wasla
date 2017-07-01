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

public class StuffFrag4 extends Fragment {

    JSONArray own_Exam ;
    String stuff_own_exam_url = "http://10.0.2.2:80/androidtest/own_exam.php";
    ArrayList<HashMap<String, String>> Exam_own_Data;
    ListAdapter adapterArray;
    ListView exam_own_list;
    String p;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.stuff_frag4, container, false);

        p = Stuff_ResponseHandler.username;
        Exam_own_Data = new ArrayList<HashMap<String, String>>();
        exam_own_list = (ListView) view.findViewById(R.id.own_exam);

        new  GetStuff_own_exam().execute(p);

   	return view;
	}

    public  class  GetStuff_own_exam extends AsyncTask<String ,String ,Boolean> {
        String publisher, result,  message;
        int success;
        JSONObject jsonObject;
        // ProgressDialog
        private android.app.ProgressDialog ProgressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog = ProgressDialog.show(getActivity(), "Processing...",
                    "Getting Own Exam", false, false);
        }

        @Override
        protected Boolean doInBackground(String... params) {

            publisher =params[0];
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("publisher", publisher));

            JsonReader j = new JsonReader(stuff_own_exam_url, pairs);

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
                        own_Exam = jsonObject.getJSONArray("posts");
                        for (int i = 0; i < own_Exam.length(); i++) {
                            JSONObject c = own_Exam.getJSONObject(i);

                            String title = c.getString("title");
                            String date = c.getString("date");
                            String publisher = c.getString("publisher");
                            String body = c.getString("body");

                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("title", title);
                            map.put("date", date);
                            map.put("publisher", publisher);
                            map.put("body", body);
                            Exam_own_Data.add(map);

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

                adapterArray = new com.example.wasel.Student.ListAdapter(getActivity().getBaseContext(),
                        R.layout.stuff_list_row, Exam_own_Data);
                exam_own_list.setAdapter(adapterArray);
            }
        }
    }

}
