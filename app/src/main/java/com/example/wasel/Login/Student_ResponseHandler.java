package com.example.wasel.Login;


import org.json.JSONObject;


public class Student_ResponseHandler {

    private String response;
    String message;
    int success;
     public  static String username,year,department;


  public  int getSuccess() {
        return success;
    }



    public Student_ResponseHandler(String response) {
        this.response = response;
    }


 public String Handler(){

     try {
         JSONObject jsonObject = new JSONObject(response);

        success = jsonObject.getInt("success");
        message =jsonObject.getString("message");

         if (success == 1){

             username = jsonObject.getString("username");
              year = jsonObject.getString("year");
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