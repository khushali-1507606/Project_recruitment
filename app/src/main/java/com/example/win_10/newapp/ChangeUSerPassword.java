package com.example.win_10.newapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.util.Alert;
import com.util.Session;
import com.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;


public class ChangeUSerPassword extends AppCompatActivity {

    TextView txtName;
    Alert alert;
    TextView txtMobile;
    String response;
    TextView txtEmail;
    TextView txtPassword;
    Button btnSave;
    String id;
    Session session;
    EditText edOld;
    String name,password,email,phone,newpass,renewpass;
LinearLayout layout;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        alert=new Alert();
        txtName=(TextView)findViewById(R.id.textViewname);
        txtEmail=(TextView)findViewById(R.id.textViewEmail);
        txtMobile=(TextView)findViewById(R.id.textViewMobile);;
        session=new Session(this);
        id=session.getId();
        Toast.makeText(context, "id "+id, Toast.LENGTH_SHORT).show();



      Intent intent=getIntent();
       Bundle bundle= intent.getBundleExtra("keyBundle2");
        name=bundle.getString("keyName");
        email=bundle.getString("keyEmail");
        phone=bundle.getString("keyPhone");

       // Toast.makeText(context, "name:"+name+" email "+email+" password "+password+" phone "+phone, Toast.LENGTH_SHORT).show();
        txtName.setText(name);
        txtEmail.setText(email);
        txtMobile.setText(phone);
       // txtEmail.setText("keshav@gmail.com");
        //txtPassword.setText("keshav");
        //txtMobile.setText("8285910956");
layout=(LinearLayout)findViewById(R.id.layout_change);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.activity_change_user_password);
                 edOld=(EditText) dialog.findViewById(R.id.oldpass);
                EditText edNew=(EditText) dialog.findViewById(R.id.newpass);
                EditText edReNew=(EditText) dialog.findViewById(R.id.renewpass);
                Button btnSaveC=(Button) dialog.findViewById(R.id.buttonSaveC);

                btnSaveC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                   //     Toast.makeText(ChangeUSerPassword.this, "Enter block", Toast.LENGTH_SHORT).show();
                        newpass=edNew.getText().toString().trim();
                        renewpass=edReNew.getText().toString().trim();
                        if (newpass.isEmpty()||renewpass.isEmpty()){
                            alert.showDialog(ChangeUSerPassword.this,"Fill the password first");

                        }
                        if (newpass.equals(renewpass)){
                            //alert.showDialog(ChangeUSerPassword.this,"Passwords matched");
                          //  Toast.makeText(ChangeUSerPassword.this, "Passwords matched", Toast.LENGTH_SHORT).show();
                            ChangePassword changePassword=new ChangePassword();
                            changePassword.execute();
                            //FInalChangePass fInalChangePass=new FInalChangePass();
                            //fInalChangePass.execute();
                        }
                        else {
                            alert.showDialog(ChangeUSerPassword.this,"Invalid password");

                          //  Toast.makeText(ChangeUSerPassword.this, "...", Toast.LENGTH_SHORT).show();
                            return;

                        }
                    }
                });

                dialog.show();
            }
        });



    }





     class ChangePassword extends AsyncTask{


        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Checking credentials.....please wait");
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
             response=getJson3();
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            Log.i("Post","testing..."+email.length());
            progressDialog.dismiss();
            //Toast.makeText(context, "Before try", Toast.LENGTH_SHORT).show();

            try
            {
                JSONArray jarr = new JSONArray(response);

                if (jarr.length() > 0) {
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject job = jarr.getJSONObject(i);
                        String pass = job.getString("Password");
               //         Toast.makeText(context," old password "+pass, Toast.LENGTH_SHORT).show();

                        if (edOld.getText().toString().trim().equals(pass)){

                          //  Toast.makeText(context, "Old password vrified", Toast.LENGTH_SHORT).show();
                            FInalChangePass pass1=new FInalChangePass();
                            pass1.execute();


                        }
                        else {
                            alert.showDialog(ChangeUSerPassword.this,"Entered password is wrong");

                            //Toast.makeText(context, "unsuccessful :(", Toast.LENGTH_SHORT).show();

                        }


                    }

                }

            }

            catch(Exception e)
            {
                alert.showDialog(ChangeUSerPassword.this,e.toString());

              //  Toast.makeText(context, "catch block "+e.toString(), Toast.LENGTH_SHORT).show();
            }

        }


        public String getJson3(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.validate_password;
            String data="";
            try {
                URL url1=new URL(url);

                HttpURLConnection httpURLConnection=(HttpURLConnection) url1.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS=null;
                OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=null;
                bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data= URLEncoder.encode("id","UTF-8") + "=" +URLEncoder.encode(id,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                InputStreamReader isr=new InputStreamReader(httpURLConnection.getInputStream());
                Scanner scanner=new Scanner(isr);
                while (scanner.hasNext()){
                    stringBuilder.append(scanner.nextLine());

                }


                return stringBuilder.toString();






            } catch (Exception e) {
                e.printStackTrace();
            }


            return stringBuilder.toString();

        }
    }

    class FInalChangePass extends AsyncTask {
        ProgressDialog progressDialog;
        String response;
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Verifying....");
            progressDialog.show();
          //  Toast.makeText(ChangeUSerPassword.this, "otp "+session.getOtp(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJson();
            System.out.print("Output is"+ response);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
           // Toast.makeText(context, "Data "+name+"-"+email+"-"+password+"-"+phone, Toast.LENGTH_SHORT).show();
            //Toast.makeText(ChangeUSerPassword.this, "New Password: "+newpass+"response "+response, Toast.LENGTH_SHORT).show();
            if (response.length()>0){
                alert.showDialog(ChangeUSerPassword.this,"Password successfully changed");


                //Toast.makeText(context,"Password successfully changed",Toast.LENGTH_LONG).show();


            }
            else {

               // Toast.makeText(context,"Not successful"+response,Toast.LENGTH_LONG).show();
            }


        }
        public String getJson(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.register;
            String data="";
            try {
                URL url1=new URL(url);

                HttpURLConnection httpURLConnection=(HttpURLConnection) url1.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS=null;
                OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=null;
                bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data= URLEncoder.encode("name","UTF-8") + "="
                        +URLEncoder.encode(name,"UTF-8")+"&"+URLEncoder.encode("email","UTF-8") + "=" +URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8") + "=" +URLEncoder.encode(newpass,"UTF-8")+"&"+URLEncoder.encode("mobile","UTF-8") + "=" +URLEncoder.encode(phone,"UTF-8")+"&"+URLEncoder.encode("mode","UTF-8") + "=" +URLEncoder.encode("M","UTF-8")+"&"+URLEncoder.encode("act_code","UTF-8") + "=" +URLEncoder.encode(session.getOtp(),"UTF-8")+"&"+URLEncoder.encode("userId","UTF-8") + "=" +URLEncoder.encode(session.getId(),"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                InputStreamReader isr=new InputStreamReader(httpURLConnection.getInputStream());
                Scanner scanner=new Scanner(isr);
                while (scanner.hasNext()){
                    stringBuilder.append(scanner.nextLine());

                }


                return stringBuilder.toString();






            } catch (Exception e) {
                e.printStackTrace();
            }


            return stringBuilder.toString();

        }
    }



}
