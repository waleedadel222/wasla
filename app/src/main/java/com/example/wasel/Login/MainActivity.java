package com.example.wasel.Login;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wasel.JSON.JsonReader;
import com.example.wasel.R;
import com.example.wasel.Student.StudentTab;
import com.example.wasel.Stuff.StuffTab;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class MainActivity extends Activity {

    final String student_url ="http://10.0.2.2:80/androidtest/student_login.php";
    final  String stuff_url = "http://10.0.2.2:80/androidtest/stuff_login.php";

    RadioGroup radioGroup;
    EditText name , pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        name  = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String n ,p ;
                int isCheckID = radioGroup.getCheckedRadioButtonId();

                n = name.getText().toString();
                p = pass.getText().toString();

                if (n.equals("")||p.equals("")){

                    Toast.makeText(MainActivity.this , "Enter Your UserName  and Password " , Toast.LENGTH_SHORT).show();
                }

                else {
                    switch (isCheckID){

                        case R.id.stuff :  new Stuff_login().execute(n,p);  break;

                        case R.id.student:  new Student_login().execute(n,p);  break;

                        default: Toast.makeText(MainActivity.this , "Select Your Position" , Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });


    }

    // Student_login class
    public  class Student_login extends AsyncTask<String, Void, Void> {

        String username, password, result;

        //ProgressDialog
        private android.app.ProgressDialog ProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ProgressDialog = ProgressDialog.show(MainActivity.this, "Processing...",
                    "Check Username and password", false, false);
        }

        @Override
        protected Void doInBackground(String... params) {

            username = params[0];
            password = params[1];
            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("username", username));
            pairs.add(new BasicNameValuePair("password", password));


            JsonReader j = new JsonReader(student_url, pairs);

            result = j.sendRequest();


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            String message ;

            try {
                Student_ResponseHandler studentResponseHandler = new Student_ResponseHandler(result);
                message = studentResponseHandler.Handler();
                int success = studentResponseHandler.getSuccess();

                ProgressDialog.dismiss();

                switch (success){
                    case  0 :
                        if(message == null)
                        { Toast.makeText(MainActivity.this ,"error in internet connection" , Toast.LENGTH_SHORT).show();}
                        else {Toast.makeText(MainActivity.this ,message , Toast.LENGTH_SHORT).show();}
                        break;

                    case  1 : Toast.makeText(MainActivity.this , message , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this , StudentTab.class);
                        startActivity(intent);

                        break;

                    default: Toast.makeText(MainActivity.this ,"Internet error connection ", Toast.LENGTH_LONG).show();
                }
            }
            catch ( Exception e1) {
                e1.printStackTrace();
            }

        }}

    //Stuff_login class

    public  class Stuff_login extends AsyncTask<String, Void, Void> {

        String username, password, result;

        // ProgressDialog
        private ProgressDialog ProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ProgressDialog = ProgressDialog.show(MainActivity.this, "Processing...",
                    "Check Username and password", false, false);
        }
        @Override
        protected Void doInBackground(String... params) {

            username = params[0];
            password = params[1];
            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("username", username));
            pairs.add(new BasicNameValuePair("password", password));

            JsonReader j = new JsonReader(stuff_url, pairs);
            result = j.sendRequest();

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            String message ;

            try {

                Stuff_ResponseHandler stuff_responseHandler = new Stuff_ResponseHandler(result);
                message = stuff_responseHandler.Handler();
                int success = stuff_responseHandler.getSuccess();

                ProgressDialog.dismiss();

                switch (success){
                    case  0 :
                        if(message == null){ Toast.makeText(MainActivity.this ,"error in internet connection" , Toast.LENGTH_SHORT).show();}
                        else {Toast.makeText(MainActivity.this ,message , Toast.LENGTH_SHORT).show();}
                        break;

                    case  1 : Toast.makeText(MainActivity.this , message , Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this , StuffTab.class);
                        startActivity(intent);
                        break;

                    default: Toast.makeText(MainActivity.this, "Internet error connection ", Toast.LENGTH_LONG).show();


                }

            }
            catch ( Exception e1) {
                e1.printStackTrace();
            }
        }
    }

}

