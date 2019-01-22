package com.example.win_10.newapp;

import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.service.autofill.RegexValidator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mail_sender.GMailSender;
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
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText edUSer;
    Context context=this;
     Dialog dialog;
    EditText edCode;

    EditText edpass;
    EditText edrepass;
    Alert alert;
    int code;
    EditText edemail;
    Random random;
    Session session;
    EditText edPhone;
    String name,email,password,phone,repass;
    Button btnRegister;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alert=new Alert();
        awesomeValidation= new AwesomeValidation(ValidationStyle.BASIC);
        edemail=(EditText)findViewById(R.id.editTextUsername);
        edpass=(EditText)findViewById(R.id.editTextPassword);
        edrepass=(EditText)findViewById(R.id.editTextPasswordc);
        edUSer=(EditText)findViewById(R.id.editTextName);
        edPhone=(EditText)findViewById(R.id.editTextPhone);
        btnRegister=(Button)findViewById(R.id.buttonRegister);

        session=new Session(context);


        awesomeValidation.addValidation(this, R.id.editTextName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.editTextUsername, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.editTextPhone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);



        btnRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                code=RandomOtp();
                name = edUSer.getText().toString().trim();
                email = edemail.getText().toString().trim();
                password = edpass.getText().toString().trim();
                repass = edrepass.getText().toString().trim();
                phone = edPhone.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    edUSer.setError("This field is required");
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty ....Please fill all the details", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(email)){

                    edemail.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){

                    edpass.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(repass)){

                    edrepass.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(phone)){

                    edPhone.setError("This field is required");
                    return;
                }
                if(edpass.getText().toString().length()<8 &&!isValidPassword(edpass.getText().toString())){
                    System.out.println("Not Valid ..Password should be atleast 8 characters long and should contain atleast one special character ,number and alphabet");
                    Toast.makeText(context, "Not Valid ..Password should be atleast 8 characters long and should contain atleast one special character ,number and alphabet", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    System.out.println("Valid");
                }

                if (!password.equals(repass)) {
                    edpass.setError("Passwords don't match");
                    edrepass.setError("Passwords don't match");
                    Toast.makeText(getApplicationContext(), "Passwords don't match ...Please fill carefully", Toast.LENGTH_SHORT).show();
                    return;


                }

                if (awesomeValidation.validate()) {

                     dialog=new Dialog(Register.this);
                    dialog.setContentView(R.layout.dialog_verification);

                    Button btnVerify=dialog.findViewById(R.id.buttonVerify);
                    EditText editText=dialog.findViewById(R.id.editTextCode);
                    CheckUserStatus checkUserStatus=new CheckUserStatus();
                    checkUserStatus.execute();
                  //  dialog.show();
                    btnVerify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String otp=editText.getText().toString().trim();

                            session.setOtp(otp);
                            if (otp.equals(String.valueOf(code))){

                                Toast.makeText(Register.this, "Registered", Toast.LENGTH_SHORT).show();
                                User3 user3 = new User3();
                                user3.execute();

                            }
                            else {

                                Toast.makeText(Register.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }








            }
        });



    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }



    public int RandomOtp(){
        random=new Random();
        int num=random.nextInt((9999-1000)+1)+1000;
        return num;
    }


    class User3 extends AsyncTask {
        ProgressDialog progressDialog;
        String response;
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Verifying....");
            progressDialog.show();
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
            //Toast.makeText(context, "Data "+name+"-"+email+"-"+password+"-"+phone, Toast.LENGTH_SHORT).show();


            try {
                JSONObject jarr = new JSONObject(response);

if (jarr.length()>0) {

    Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show();
    for (int i = 0; i < jarr.length(); i++) {

        String Return = jarr.getString("return");
        if (Integer.parseInt(Return) > 2) {

            Toast.makeText(context, "User with same credentials are already registered", Toast.LENGTH_SHORT).show();
            return;
        } else if (Integer.parseInt(Return) == 2) {
            StatusActive statusActive=new StatusActive();
            statusActive.execute();

        }
    }
}
            else{

                    Toast.makeText(context, "Not successful", Toast.LENGTH_LONG).show();
                }}
            catch(Exception e)
            {

                Toast.makeText(context, "catch block"+e.toString(), Toast.LENGTH_SHORT).show();
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
                        +URLEncoder.encode(name,"UTF-8")+"&"+URLEncoder.encode("email","UTF-8") + "=" +URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8") + "=" +URLEncoder.encode(password,"UTF-8")+"&"+URLEncoder.encode("mobile","UTF-8") + "=" +URLEncoder.encode(phone,"UTF-8")+"&"+URLEncoder.encode("mode","UTF-8") + "=" +URLEncoder.encode("A","UTF-8")+"&"+URLEncoder.encode("act_code","UTF-8") + "=" +URLEncoder.encode(String.valueOf(code),"UTF-8")+"&"+URLEncoder.encode("userId","UTF-8") + "=" +URLEncoder.encode("1013","UTF-8");
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


    class CheckUserStatus extends AsyncTask {
        ProgressDialog progressDialog;
        String response;
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Verifying....");
            progressDialog.show();
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
           // Toast.makeText(context, "response "+response, Toast.LENGTH_SHORT).show();


            try {
                JSONObject jarr = new JSONObject(response);

                if (jarr.length()>0) {

                    for (int i = 0; i < jarr.length(); i++) {

                        String Return = jarr.getString("return");
                        if (Integer.parseInt(Return) > 1) {

                            Toast.makeText(context, "User with same credentials are already registered", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if (Integer.parseInt(Return) ==1){
                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        GMailSender sender = new GMailSender("kapoor.khushali07@gmail.com","kapoorrocks07"
                                        );
                                        sender.sendMail("Hello from PRSC e-Recruitments", "Your activation code is "+code+"\n Regards,\n PRSC",
                                                "kapoor.khushali07@gmail.com", email);
                                    } catch (Exception e) {
                                        alert.showDialog(Register.this,"Message service not working");

                                        Log.e("SendMail", e.getMessage(), e);
                                    }
                                }

                            }).start();
                            dialog.show();
                        }
                    }
                }
             }
            catch(Exception e)
            {

                Toast.makeText(context, "catch block"+e.toString(), Toast.LENGTH_SHORT).show();
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
                        +URLEncoder.encode(name,"UTF-8")+"&"+URLEncoder.encode("email","UTF-8") + "=" +URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8") + "=" +URLEncoder.encode(password,"UTF-8")+"&"+URLEncoder.encode("mobile","UTF-8") + "=" +URLEncoder.encode(phone,"UTF-8")+"&"+URLEncoder.encode("mode","UTF-8") + "=" +URLEncoder.encode("A","UTF-8")+"&"+URLEncoder.encode("act_code","UTF-8") + "=" +URLEncoder.encode(String.valueOf(code),"UTF-8")+"&"+URLEncoder.encode("userId","UTF-8") + "=" +URLEncoder.encode("1013","UTF-8");
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


    class StatusActive extends AsyncTask {
        ProgressDialog progressDialog;
        String response;
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Verifying....");
            progressDialog.show();
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
        //    Toast.makeText(context, "Data "+name+"-"+email+"-"+password+"-"+phone, Toast.LENGTH_SHORT).show();


            try {
                JSONObject jarr = new JSONObject(response);

                if (jarr.length()>0) {

                    Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show();
                   if (jarr.getString("return").equals("1")){

                       startActivity(new Intent(Register.this,Login.class));
                       finish();

                   }
                }
                else{

                    Toast.makeText(context, "Not successful", Toast.LENGTH_LONG).show();
                }}
            catch(Exception e)
            {

                Toast.makeText(context, "catch block"+e.toString(), Toast.LENGTH_SHORT).show();
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
                        +URLEncoder.encode(name,"UTF-8")+"&"+URLEncoder.encode("email","UTF-8") + "=" +URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8") + "=" +URLEncoder.encode(password,"UTF-8")+"&"+URLEncoder.encode("mobile","UTF-8") + "=" +URLEncoder.encode(phone,"UTF-8")+"&"+URLEncoder.encode("mode","UTF-8") + "=" +URLEncoder.encode("U","UTF-8")+"&"+URLEncoder.encode("act_code","UTF-8") + "=" +URLEncoder.encode(String.valueOf(code),"UTF-8")+"&"+URLEncoder.encode("userId","UTF-8") + "=" +URLEncoder.encode("1013","UTF-8");
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
