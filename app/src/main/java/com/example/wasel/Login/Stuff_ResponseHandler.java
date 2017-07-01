package com.example.wasel.Login;

import org.json.JSONObject;

/**
 * Created by waleed on 2015-06-29.
 */
public class Stuff_ResponseHandler {
    private String response;
    String message;
    int success;
    public  static String username,department;


    public  int getSuccess() {
        return success;
    }


    public Stuff_ResponseHandler(String response) {
        this.response = response;
    }


 public String Handler(){

        try {
            JSONObject jsonObject = new JSONObject(response);
            success = jsonObject.getInt("success");
            message =jsonObject.getString("message");

            if (success == 1){
                 username =jsonObject.getString("username");
                department =jsonObject.getString("department");
            }

            return message;
        }

        catch (Exception e) {


            e.printStackTrace();
        }

        return message;
    }
}

