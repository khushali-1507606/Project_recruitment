package com.admin.nidhi_khushali;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.win_10.newapp.R;
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


public class ExamHomeAct4 extends AppCompatActivity {

    ListView listView;

    ArrayList<ShowQuestions> arListExam;
    Context context;
    ShowQuestions showQuestions;

    ShowQuesAdapter showQuesAdapter;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_home_act4);
        context=this;
        listView=findViewById(R.id.listViewShowTest);
        session=new Session(this);
        showQuestions=new ShowQuestions("123","ad34","div","post","120");

        arListExam=new ArrayList<>();
//        arListExam.add(showQuestions);
        GetAssignedExam getExamObj=new GetAssignedExam();
        getExamObj.execute();


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    class GetAssignedExam extends AsyncTask {
        String response;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getjson2();
            return null;
        }

        public String getjson2(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.url_GetAssignedExam;
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
                data= URLEncoder.encode("email","UTF-8") + "=" +URLEncoder.encode(session.getEmail(),"UTF-8");
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


        @Override
        protected void onPostExecute(Object o){
            super.onPostExecute(o);
            progressDialog.dismiss();
            try
            {
                JSONArray jarr = new JSONArray(response);
                if(jarr.length()>0) {
                    for (int i = 0; i < jarr.length(); i++) {

                        JSONObject job = jarr.getJSONObject(0);
                        String id=job.getString("Id");
                        String advNo=job.getString("AdvertisementNo");
                        String div=job.getString("Division");
                        String post=job.getString("Post");
                        String time=job.getString("Time");
                        ShowQuestions showQueObj=new ShowQuestions(id,advNo,div,post,time);
                        arListExam.add(showQueObj);
                        //String pass = job.getString("pass");
                    }

                    showQuesAdapter=new ShowQuesAdapter(context,R.layout.list_show_questions,arListExam);

                    listView.setAdapter(showQuesAdapter);
                }
                else {
                    //Toast.makeText(ExamHomeAct4.this, "No tests available...Here's static data", Toast.LENGTH_LONG).show();
                    //static data for testing purpose
                    showQuestions=new ShowQuestions("123","ad34","div","post","120");
                    arListExam.add(showQuestions);
                    showQuesAdapter=new ShowQuesAdapter(context,R.layout.list_show_questions,arListExam);
                    listView.setAdapter(showQuesAdapter);
                }
            }
            catch(Exception e)
            {
                Toast.makeText(ExamHomeAct4.this, "Oops!catch executed..."+e.getMessage(), Toast.LENGTH_SHORT).show();
                //static data for testing purpose
                showQuestions=new ShowQuestions("123","ad34","div","post","120");
                arListExam.add(showQuestions);
                showQuesAdapter=new ShowQuesAdapter(context,R.layout.list_show_questions,arListExam);
                listView.setAdapter(showQuesAdapter);
            }

            super.onPostExecute(o);
        }

    }

}
