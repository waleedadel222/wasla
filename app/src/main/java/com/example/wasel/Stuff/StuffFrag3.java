package com.example.wasel.Stuff;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

public class StuffFrag3 extends Fragment {

    JSONArray own_News ;
    String stuff_own_news_url = "http://10.0.2.2:80/androidtest/own_news.php";
   ArrayList<HashMap<String, String>> News_own_Data;
    ListAdapter adapterArray;
    ListView news_own_list;
    String p;
    Button addNews ;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.stuff_frag3, container, false);

        p = Stuff_ResponseHandler.username;
        News_own_Data = new ArrayList<HashMap<String, String>>();
        news_own_list = (ListView) view.findViewById(R.id.own_news);
        new  GetStuff_own_news().execute(p);


        addNews = (Button) view .findViewById(R.id.addNews);

        addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater;
                inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup root = (ViewGroup) v.findViewById(R.id.relative_layout);
                View view =inflater.inflate(R.layout.add_news_activity ,root , false);

                final Dialog dialog = new Dialog(getActivity());
                dialog.setTitle("Add News");
                dialog.setContentView(view);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                //get objects
                final EditText add_title = (EditText) view.findViewById(R.id.add_title);
                final EditText add_year = (EditText) view.findViewById(R.id.add_year);
                final EditText add_department = (EditText) view.findViewById(R.id.add_department);
                final EditText add_post = (EditText) view.findViewById(R.id.add_post);

                 Button add = (Button) view.findViewById(R.id.add_button);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String t = add_title.getText().toString();
                        String y = add_year.getText().toString();
                        String d =add_department.getText().toString();
                        String p = add_post.getText().toString();
                        if (t.equals("")||y.equals("")|| d.equals("")||p.equals("")){
                            Toast.makeText(getActivity() ," please fill all fields ..!", Toast.LENGTH_LONG).show();
                        }
                        else {



                        }
                    }
                });
            }
        });



		return view;
	}

    public  class  GetStuff_own_news extends AsyncTask<String ,String ,Boolean> {
        String publisher, result,  message;
        int success;
        JSONObject jsonObject;
        // ProgressDialog
        private android.app.ProgressDialog ProgressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog = ProgressDialog.show(getActivity(), "Processing...",
                    "Getting Own News", false, false);
        }

        @Override
        protected Boolean doInBackground(String... params) {

            publisher =params[0];
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("publisher", publisher));

            JsonReader j = new JsonReader(stuff_own_news_url, pairs);

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
                        own_News = jsonObject.getJSONArray("posts");
                        for (int i = 0; i < own_News.length(); i++) {
                            JSONObject c = own_News.getJSONObject(i);

                            String title = c.getString("title");
                            String date = c.getString("date");
                            String publisher = c.getString("publisher");
                            String body = c.getString("body");

                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("title", title);
                            map.put("date", date);
                            map.put("publisher", publisher);
                            map.put("body", body);
                            News_own_Data.add(map);

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
                        R.layout.stuff_list_row, News_own_Data);
                news_own_list.setAdapter(adapterArray);
            }
        }
    }

    //public  class  Add_News extends  AsyncTask<String ,String ,Boolean>{


   // }
}

