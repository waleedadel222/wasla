package com.example.wasel.Stuff;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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


public class ProfileActivity  extends Activity {


    JSONArray profile ;
    String stuff_profile_url = "http://10.0.2.2:80/androidtest/stuff_profile.php";
    ArrayList<HashMap<String, String>> Data;
    StuffProfileAdepter adapterArray;
    ListView profile_list;
    String n;



    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stuff_profile);

       n = Stuff_ResponseHandler.username;
        Data = new ArrayList<HashMap<String, String>>();
        profile_list = (ListView)findViewById(R.id.stuff_profile_listView);

        new  GetStuff_profile().execute(n);

}

    public  class  GetStuff_profile extends AsyncTask<String ,String ,Boolean> {

        String username, result,  message;
        int success;
        JSONObject jsonObject;
        // ProgressDialog
        private android.app.ProgressDialog ProgressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog = ProgressDialog.show(ProfileActivity.this, "Processing...",
                    "Getting Data", false, false);
        }

        @Override
        protected Boolean doInBackground(String... params) {

            username =params[0];
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("username", username));

            JsonReader j = new JsonReader(stuff_profile_url, pairs);

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
                        profile = jsonObject.getJSONArray("posts");
                        for (int i = 0; i < profile.length(); i++) {
                            JSONObject c = profile.getJSONObject(i);

                            String username = c.getString("username");
                            String specailization = c.getString("specailization");
                            String department  = c.getString("department");
                            String degree = c.getString("degree");
                            String projects = c.getString("projects");
                            String events = c.getString("events");

                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("username", username);
                            map.put("department",department);
                            map.put("specailization", specailization);
                            map.put("degree", degree);
                            map.put("projects", projects);
                            map.put("events",events);
                            Data.add(map);

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

                adapterArray = new StuffProfileAdepter(ProfileActivity.this, R.layout.stuff_profile_row, Data);
               profile_list.setAdapter(adapterArray);
            }
        }
    }
}