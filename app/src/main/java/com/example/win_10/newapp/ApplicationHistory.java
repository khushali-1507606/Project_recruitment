package com.example.win_10.newapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.admin.nidhi_khushali.AdminNewMain;
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
import java.util.ArrayList;
import java.util.Scanner;

public class ApplicationHistory extends AppCompatActivity {



    ArrayList<AppHisClass> list;
    Context context;
    int count=0;
    Session session;
    String email;
    AppHistoryAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_history);
        listView=(ListView)findViewById(R.id.listViewAppHis);
        context=this;
        session=new Session(this);
        email=session.getEmail();

        // Toast.makeText(context, "email "+email, Toast.LENGTH_SHORT).show();
        list=new ArrayList<>();
        AppHis appHis=new AppHis();
        appHis.execute();
        //  AppHisClass class2=new AppHisClass("123","04/2015","Geology and water resources","Web Application Development","31-08-2018");
        //   AppHisClass class3=new AppHisClass("125","09/2015"," water resources","Assistant Programmer","31-08-2018");


        // list.add(class2);
        //list.add(class3);

        //adapter=new AppHistoryAdapter(this,R.layout.list_app_his,list);
        //listView.setAdapter(adapter);

    }

    class AppHis extends AsyncTask {
        ProgressDialog progressDialog;
        String response;
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Uploading history");
            progressDialog.show();
            Log.d("preexecute","prexecute");

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJson3();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
            //Toast.makeText(context, "response "+response, Toast.LENGTH_SHORT).show();
            //Toast.makeText(context, "response length "+response.length(), Toast.LENGTH_SHORT).show();

            //Toast.makeText(context, "Data", Toast.LENGTH_SHORT).show();
            try{
                JSONArray jarr = new JSONArray(response);
                //Toast.makeText(context, "Enter array........", Toast.LENGTH_SHORT).show();

                count=0;
                if(jarr.length()>0) {
                    for (int i = 0; i < jarr.length(); i++) {
                        //  Toast.makeText(context, "Json Length ........", Toast.LENGTH_SHORT).show();

                        JSONObject job = jarr.getJSONObject(i);
                     //   String id = job.getString("CandidateId");
                       count++;
                        String adv = job.getString("AdvertisementNo");
                        String div = job.getString("DivisionNameAppliedIn");
                        String post = job.getString("PostAppliedFor");
                        JSONObject jsonObject=job.getJSONObject("CreatedDate");
                        String aDate = jsonObject.getString("date");
                        AppHisClass class2=new AppHisClass(count,adv,div,post,aDate);
                        list.add(class2);
                    }
                    adapter=new AppHistoryAdapter(getApplicationContext(),R.layout.list_app_his,list);
                    listView.setAdapter(adapter);
                }

                else {
                    Toast.makeText(context, "Json response empty", Toast.LENGTH_SHORT).show();
                }
            }

            catch(Exception e)
            {

                Toast.makeText(context, "catch block"+e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }

        public String getJson3(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.app_his;
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
                data= URLEncoder.encode("email","UTF-8") + "=" +
                        URLEncoder.encode(session.getEmail(),"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                InputStreamReader isr=new InputStreamReader(httpURLConnection.getInputStream());
                Scanner scanner=new Scanner(isr);
                while (scanner.hasNext()){
                    stringBuilder.append(scanner.nextLine());

                }
                Log.d("response",stringBuilder.toString());

                return stringBuilder.toString();


            } catch (Exception e) {
                e.printStackTrace();
                Log.d("error catch",e.toString());

            }


            return stringBuilder.toString();

        }

    }

}
