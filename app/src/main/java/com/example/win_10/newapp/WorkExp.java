package com.example.win_10.newapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.util.SessionReadCandiDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Scanner;
import java.util.TimeZone;

public class WorkExp extends AppCompatActivity implements View.OnClickListener{

    EditText edorg1,edorg2,edorg3,edorg4,edorg5;
    EditText edpos1, edpos2, edpos3, edpos4, edpos5;
    Context context;
    EditText sal1, sal2, sal3, sal4, sal5;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    EditText yi1,yi2,yi3,yi4,yi5;
    EditText yf1,yf2,yf3,yf4,yf5;
    SaveAppData saveAppData;
    Calendar calendar;
    int year,month,date1;

    String org1,org2,org3,org4,org5,pos1,pos2,pos3,pos4,pos5,sl1,sl2,sl3,sl4,sl5,iy1,iy2,iy3,iy4,iy5,fy1,fy2,fy3,fy4,fy5;

    SessionReadCandiDetails sessionReadCandiObj;
    String candiId;

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_exp);
        saveAppData=new SaveAppData();

        sessionReadCandiObj=new SessionReadCandiDetails(this);

        initViews();
        context=this;
        button=(Button)findViewById(R.id.buttonNextw);
        calendar=Calendar.getInstance(TimeZone.getDefault());
        preferences=getSharedPreferences("myFile",MODE_PRIVATE);
        editor=preferences.edit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getViews();
                Intent rcv=getIntent();
                if (rcv.hasExtra("keyCandiId")) {
                    //setDetails();
                    candiId=rcv.getStringExtra("keyCandiId");
                  //  Toast.makeText(context, "candi Id"+candiId, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(WorkExp.this,LastAppFill.class);
                    intent.putExtra("abc",candiId);
                    startActivity(intent);
                }
                else {
                    //Intent intent=new Intent(WorkExp.this,LastAppFill.class);
                    startActivity(new Intent(WorkExp.this, LastAppFill.class));


                }
            }
        });

       /* Intent rcv=getIntent();
        if (rcv.hasExtra("keyCandiId")){
        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent rcv=getIntent();
        if (rcv.hasExtra("keyCandiId")) {
            setDetails();
        }
        else {
          setDetails();

        }
    }

    public void initViews(){

        edorg1=(EditText)findViewById(R.id.o1);
        edorg2=(EditText)findViewById(R.id.o2);
        edorg3=(EditText)findViewById(R.id.o3);
        edorg4=(EditText)findViewById(R.id.o4);
        edorg5=(EditText)findViewById(R.id.o5);

        edpos1=(EditText)findViewById(R.id.p1);
        edpos2=(EditText)findViewById(R.id.p2);
        edpos3=(EditText)findViewById(R.id.p3);
        edpos4=(EditText)findViewById(R.id.p4);
        edpos5=(EditText)findViewById(R.id.p5);

        sal1=(EditText)findViewById(R.id.s1);
        sal2=(EditText)findViewById(R.id.s2);
        sal3=(EditText)findViewById(R.id.s3);
        sal4=(EditText)findViewById(R.id.s4);
        sal5=(EditText)findViewById(R.id.s5);

        yi1=(EditText)findViewById(R.id.y1);
        yi2=(EditText)findViewById(R.id.y2);
        yi3=(EditText)findViewById(R.id.y3);
        yi4=(EditText)findViewById(R.id.y4);
        yi5=(EditText)findViewById(R.id.y5);

        yf1=(EditText)findViewById(R.id.yf1);
        yf2=(EditText)findViewById(R.id.yf2);
        yf3=(EditText)findViewById(R.id.yf3);
        yf4=(EditText)findViewById(R.id.yf4);
        yf5=(EditText)findViewById(R.id.yf5);

        yi1.setOnClickListener(this);
        yi2.setOnClickListener(this);
        yi3.setOnClickListener(this);
        yi4.setOnClickListener(this);
        yi5.setOnClickListener(this);
        yf1.setOnClickListener(this);
        yf2.setOnClickListener(this);
        yf3.setOnClickListener(this);
        yf4.setOnClickListener(this);
        yf5.setOnClickListener(this);


    }

    public void setDetails(){

        if (!sessionReadCandiObj.getOrg1().toString().trim().equals("null")){
        edorg1.setText(sessionReadCandiObj.getOrg1());}

        if (!sessionReadCandiObj.getOrg2().toString().trim().equals("null")){

            edorg2.setText(sessionReadCandiObj.getOrg2());}
        if (!sessionReadCandiObj.getOrg3().toString().trim().equals("null")){

            edorg3.setText(sessionReadCandiObj.getOrg3());}
        if (!sessionReadCandiObj.getOrg4().toString().trim().equals("null")){

            edorg4.setText(sessionReadCandiObj.getOrg5());}
        if (!sessionReadCandiObj.getOrg5().toString().trim().equals("null")) {

            edorg5.setText(sessionReadCandiObj.getOrg5());
        }

        if (!sessionReadCandiObj.getPosHeld1().toString().trim().equals("null")){

            edpos1.setText(sessionReadCandiObj.getPosHeld1());}

        if (!sessionReadCandiObj.getPosHeld2().toString().trim().equals("null")){

            edpos2.setText(sessionReadCandiObj.getPosHeld2());}
        if (!sessionReadCandiObj.getPosHeld3().toString().trim().equals("null")){

            edpos3.setText(sessionReadCandiObj.getPosHeld3());}

        if (!sessionReadCandiObj.getPosHeld4().toString().trim().equals("null")){

            edpos4.setText(sessionReadCandiObj.getPosHeld4());}
        if (!sessionReadCandiObj.getPosHeld5().toString().trim().equals("null")) {

            edpos5.setText(sessionReadCandiObj.getPosHeld5());
        }


        if (!sessionReadCandiObj.getDoJ1().toString().trim().equals("null")) {

            yi1.setText(sessionReadCandiObj.getDoJ1());}
        if (!sessionReadCandiObj.getDoJ2().toString().trim().equals("null")) {

            yi2.setText(sessionReadCandiObj.getDoJ2());}
        if (!sessionReadCandiObj.getDoJ3().toString().trim().equals("null")) {

            yi3.setText(sessionReadCandiObj.getDoJ3());}
        if (!sessionReadCandiObj.getDoJ4().toString().trim().equals("null")) {

            yi4.setText(sessionReadCandiObj.getDoJ4());}
        if (!sessionReadCandiObj.getDoJ5().toString().trim().equals("null")) {

            yi5.setText(sessionReadCandiObj.getDoJ5());
        }

        if (!sessionReadCandiObj.getDoL1().toString().trim().equals("null")) {

            yf1.setText(sessionReadCandiObj.getDoL1());
        }
        if (!sessionReadCandiObj.getDoL2().toString().trim().equals("null")) {

            yf2.setText(sessionReadCandiObj.getDoL2());}
        if (!sessionReadCandiObj.getDoL3().toString().trim().equals("null")) {

            yf3.setText(sessionReadCandiObj.getDoL3());}
        if (!sessionReadCandiObj.getDoL4().toString().trim().equals("null")) {

            yf4.setText(sessionReadCandiObj.getDoL4());}
        if (!sessionReadCandiObj.getDoL5().toString().trim().equals("null")) {

            yf5.setText(sessionReadCandiObj.getDoL5());}


        if (!sessionReadCandiObj.getSalaryDrawn1().toString().trim().equals("null")) {

            sal1.setText(sessionReadCandiObj.getSalaryDrawn1());}
        if (!sessionReadCandiObj.getSalaryDrawn2().toString().trim().equals("null")) {

            sal2.setText(sessionReadCandiObj.getSalaryDrawn2());}
        if (!sessionReadCandiObj.getSalaryDrawn3().toString().trim().equals("null")) {

            sal3.setText(sessionReadCandiObj.getSalaryDrawn3());}
        if (!sessionReadCandiObj.getSalaryDrawn4().toString().trim().equals("null")) {

            sal4.setText(sessionReadCandiObj.getSalaryDrawn4());}
        if (!sessionReadCandiObj.getSalaryDrawn5().toString().trim().equals("null")) {

            sal5.setText(sessionReadCandiObj.getSalaryDrawn5());
        }
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){


            case R.id.y1:
                Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yi1.setText(i2+"/"+i1+"/"+i);


                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)

                );
                dialog.show();

                break;

            case R.id.y2:
                Calendar calendar1=Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog1=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yi2.setText(i2+"/"+i1+"/"+i);


                    }
                },calendar1.get(Calendar.YEAR),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.DAY_OF_MONTH)

                );
                dialog1.show();
                break;

            case R.id.y3:
                Calendar calendar2=Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog2=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yi3.setText(i2+"/"+i1+"/"+i);


                    }
                },calendar2.get(Calendar.YEAR),calendar2.get(Calendar.MONTH),calendar2.get(Calendar.DAY_OF_MONTH)

                );

                dialog2.show();
                break;
            case R.id.y4:
                Calendar calendar3=Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog3=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yi4.setText(i2+"/"+i1+"/"+i);


                    }
                },calendar3.get(Calendar.YEAR),calendar3.get(Calendar.MONTH),calendar3.get(Calendar.DAY_OF_MONTH)

                );
                dialog3.show();
                break;
            case R.id.y5:
                Calendar calendar4=Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog4=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yi5.setText(i2+"/"+i1+"/"+i);


                    }
                },calendar4.get(Calendar.YEAR),calendar4.get(Calendar.MONTH),calendar4.get(Calendar.DAY_OF_MONTH)

                );
                dialog4.show();
                break;
            case R.id.yf1:
                Calendar calendar5=Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog5=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yf1.setText(i2+"/"+i1+"/"+i);


                    }
                },calendar5.get(Calendar.YEAR),calendar5.get(Calendar.MONTH),calendar5.get(Calendar.DAY_OF_MONTH)

                );
                dialog5.show();
                break;
            case R.id.yf2:
                Calendar calendar6=Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog6=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yf2.setText(i2+"/"+i1+"/"+i);


                    }
                },calendar6.get(Calendar.YEAR),calendar6.get(Calendar.MONTH),calendar6.get(Calendar.DAY_OF_MONTH)

                );

                dialog6.show();
                break;
            case R.id.yf3:
                Calendar calendar7=Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog7=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yf3.setText(i2+"/"+i1+"/"+i);


                    }
                },calendar7.get(Calendar.YEAR),calendar7.get(Calendar.MONTH),calendar7.get(Calendar.DAY_OF_MONTH)

                );
                dialog7.show();
                break;
            case R.id.yf4:
                Calendar calendar8=Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog8=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yf4.setText(i2+"/"+i1+"/"+i);


                    }
                },calendar8.get(Calendar.YEAR),calendar8.get(Calendar.MONTH),calendar8.get(Calendar.DAY_OF_MONTH)

                );
                dialog8.show();
                break;
            case R.id.yf5:
                Calendar calendar9=Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog9=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yf5.setText(i2+"/"+i1+"/"+i);


                    }
                },calendar9.get(Calendar.YEAR),calendar9.get(Calendar.MONTH),calendar9.get(Calendar.DAY_OF_MONTH)

                );

                dialog9.show();
                break;

        }
    }


    public void getViews(){
        org1=edorg1.getText().toString().trim();

        // saveAppData.setOrg1(org1);
        editor.putString("org1",org1).commit();

        org2=edorg2.getText().toString().trim();
        //saveAppData.setOrg2(org2);
        editor.putString("org2",org2).commit();


        org3=edorg3.getText().toString().trim();
        // saveAppData.setOrg3(org3);
        editor.putString("org3",org3).commit();


        org4=edorg4.getText().toString().trim();
        // saveAppData.setOrg4(org4);
        editor.putString("org4",org4).commit();


        org5=edorg5.getText().toString().trim();
        //saveAppData.setOrg5(org5);
        editor.putString("org5",org5).commit();


        pos1=edpos1.getText().toString().trim();
        //saveAppData.setPos1(pos1);
        editor.putString("pos1",pos1).commit();

        pos2=edpos2.getText().toString().trim();
        //saveAppData.setPos2(pos2);
        editor.putString("pos2",pos2).commit();


        pos3=edpos3.getText().toString().trim();
        //saveAppData.setPos3(pos3);
        editor.putString("pos3",pos3).commit();


        pos4=edpos4.getText().toString().trim();
//        saveAppData.setPos4(pos4);
        editor.putString("pos4",pos4).commit();


        pos5=edpos5.getText().toString().trim();
        //      saveAppData.setPos5(pos5);
        editor.putString("pos5",pos5).commit();



        sl1=sal1.getText().toString().trim();
        //    saveAppData.setSal1(sl1);
        editor.putString("sal1",sl1).commit();


        sl2=sal2.getText().toString().trim();
        //  saveAppData.setSal2(sl2);
        editor.putString("sal2",sl2).commit();


        sl3=sal3.getText().toString().trim();
        //saveAppData.setSal3(sl3);
        editor.putString("sal3",sl3).commit();


        sl4=sal4.getText().toString().trim();
        //saveAppData.setSal4(sl4);
        editor.putString("sal4",sl4).commit();


        sl5=sal5.getText().toString().trim();
        //saveAppData.setSal5(sl5);
        editor.putString("sal5",sl5).commit();


        iy1=yi1.getText().toString().trim();
        //saveAppData.setDj1(iy1);
        editor.putString("iy1",iy1).commit();


        iy2=yi2.getText().toString().trim();
        //saveAppData.setDj2(iy2);
        editor.putString("iy2",iy2).commit();

        iy3=yi3.getText().toString().trim();
        //saveAppData.setDj3(iy3);
        editor.putString("iy3",iy3).commit();

        iy4=yi4.getText().toString().trim();
        //saveAppData.setDj4(iy4);
        editor.putString("iy4",iy4).commit();

        iy5=yi5.getText().toString().trim();
        //saveAppData.setDj5(iy5);
        editor.putString("iy5",iy5).commit();



        fy1=yf1.getText().toString().trim();
        //saveAppData.setDl1(fy1);
        editor.putString("fy1",fy1).commit();


        fy2=yf1.getText().toString().trim();
        // saveAppData.setDl2(fy2);
        editor.putString("fy2",fy2).commit();

        fy3=yf1.getText().toString().trim();
        //saveAppData.setDl3(fy3);
        editor.putString("fy3",fy3).commit();


        fy4=yf1.getText().toString().trim();
        //saveAppData.setDl4(fy4);
        editor.putString("fy4",fy4).commit();

        fy5=yf1.getText().toString().trim();
        // saveAppData.setDl5(fy5);
        editor.putString("fy5",fy5).commit();

     //   Toast.makeText(context, "Data "+pos5+"-"+pos4+"-"+pos3+"-"+pos2+"-"+pos1+"-"+org1+"-"+org2+"-"+org3+"-"+org4+"-"+org5+"-"+sl1+"-"+fy1+"-", Toast.LENGTH_SHORT).show();

    }


   /* class WorkA extends AsyncTask{


        String response;
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog=new ProgressDialog(context);
            dialog.setMessage("Uploading work exp data");
            dialog.show();


        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJSon();

            return null;

        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            dialog.dismiss();
            try
            {
                JSONArray jarr = new JSONArray(response);
                Toast.makeText(context, "Enter ........", Toast.LENGTH_SHORT).show();

                if(jarr.length()>0) {
                    Toast.makeText(context, "Json Length ........", Toast.LENGTH_SHORT).show();

                    JSONObject job = jarr.getJSONObject(0);

                }}
            catch(Exception e)
            {

                Toast.makeText(context, "catch block", Toast.LENGTH_SHORT).show();
            }




        }



        public String getJSon(){

            StringBuilder stringBuilder=new StringBuilder();
            String url="http://192.168.43.10/training/login_page.php";
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
                data= URLEncoder.encode("org1","UTF-8") + "=" +URLEncoder.encode(org1,"UTF-8")+
                        "&"+URLEncoder.encode("org2","UTF-8") + "=" +URLEncoder.encode(org2,"UTF-8")+
                        "&"+URLEncoder.encode("org3","UTF-8") + "=" +URLEncoder.encode(org3,"UTF-8")+
                        "&"+URLEncoder.encode("org4","UTF-8") + "=" +URLEncoder.encode(org4,"UTF-8")+
                        "&"+URLEncoder.encode("org5","UTF-8") + "=" +URLEncoder.encode(org5,"UTF-8")+
                        "&"+URLEncoder.encode("pos1","UTF-8") + "=" +URLEncoder.encode(pos1,"UTF-8")+
                        "&"+URLEncoder.encode("pos2","UTF-8") + "=" +URLEncoder.encode(pos2,"UTF-8")+
                        "&"+URLEncoder.encode("pos3","UTF-8") + "=" +URLEncoder.encode(pos3,"UTF-8")+
                        "&"+URLEncoder.encode("pos4","UTF-8") + "=" +URLEncoder.encode(pos4,"UTF-8")+
                        "&"+URLEncoder.encode("pos5","UTF-8") + "=" +URLEncoder.encode(pos5,"UTF-8")+
                        "&"+URLEncoder.encode("sal1","UTF-8") + "=" +URLEncoder.encode(sl1,"UTF-8")+
                        "&"+URLEncoder.encode("sal2","UTF-8") + "=" +URLEncoder.encode(sl2,"UTF-8")+
                        "&"+URLEncoder.encode("sal3","UTF-8") + "=" +URLEncoder.encode(sl3,"UTF-8")+
                        "&"+URLEncoder.encode("sal4","UTF-8") + "=" +URLEncoder.encode(sl4,"UTF-8")+
                        "&"+URLEncoder.encode("sal5","UTF-8") + "=" +URLEncoder.encode(sl5,"UTF-8")+
                        "&"+URLEncoder.encode("yi1","UTF-8") + "=" +URLEncoder.encode(iy1,"UTF-8")+
                        "&"+URLEncoder.encode("yi2","UTF-8") + "=" +URLEncoder.encode(iy2,"UTF-8")+
                        "&"+URLEncoder.encode("yi3","UTF-8") + "=" +URLEncoder.encode(iy3,"UTF-8")+
                        "&"+URLEncoder.encode("yi4","UTF-8") + "=" +URLEncoder.encode(iy4,"UTF-8")+
                        "&"+URLEncoder.encode("yi5","UTF-8") + "=" +URLEncoder.encode(iy5,"UTF-8")+
                        "&"+URLEncoder.encode("yf1","UTF-8") + "=" +URLEncoder.encode(fy1,"UTF-8")+
                        "&"+URLEncoder.encode("yf2","UTF-8") + "=" +URLEncoder.encode(fy2,"UTF-8")+
                        "&"+URLEncoder.encode("yf3","UTF-8") + "=" +URLEncoder.encode(fy3,"UTF-8")+
                        "&"+URLEncoder.encode("yf4","UTF-8") + "=" +URLEncoder.encode(fy4,"UTF-8")+
                        "&"+URLEncoder.encode("yf5","UTF-8") + "=" +URLEncoder.encode(fy5,"UTF-8");
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
    }*/


}
