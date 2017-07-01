package com.example.wasel.Student;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class Fragment3 extends Fragment {


    String profile_url ="http://10.0.2.2:80/androidtest/profile.php";
    ArrayList<HashMap<String, String>>ProfileData;
    JSONArray profile;
    ProfileAdapter adapterArray;
    ListView profileView;
    String u;
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag3, container, false);
        ProfileData = new ArrayList<HashMap<String, String>>();
        profileView = (ListView) view.findViewById(R.id.profileListView);

        u = Student_ResponseHandler.username ;
       new Profile().execute(u);

		return view;
	}

    public  class  Profile  extends AsyncTask<String ,String ,Boolean>{

        String username , result;
        String message ;
        int success ;
        JSONObject jsonObject;
        // ProgressDialog
        private android.app.ProgressDialog ProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog = ProgressDialog.show(getActivity(), "Processing...",
                    "Getting Profile  Data", false, false);

        }

        @Override
        protected Boolean doInBackground(String... params) {

            username =params[0];
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("username", username));

            JsonReader j = new JsonReader(profile_url, pairs);

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
                        profile = jsonObject.getJSONArray("posts");
                      for (int i = 0; i < profile.length(); i++)
                        {
                            JSONObject c = profile.getJSONObject(i);

                            String username = c.getString("username");
                            String year = c.getString("year");
                            String department = c.getString("department");

                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("username", username);
                            map.put("year", year);
                            map.put("department", department);

                           ProfileData.add(map);

                        }
                    }
                    catch (JSONException e) {  e.printStackTrace(); }
                    return true;
            }

            return false;
        }

           @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);

                  ProgressDialog.dismiss();
                  if(b){
                      adapterArray = new ProfileAdapter(getActivity().getBaseContext() , R.layout.listviewlayout ,ProfileData);
                      profileView.setAdapter(adapterArray);
                  }
        }
    }}
