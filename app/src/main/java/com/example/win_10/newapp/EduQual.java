package com.example.win_10.newapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.OpenableColumns;
import android.renderscript.Double3;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.util.SessionReadCandiDetails;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.Scanner;

public class EduQual extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button button;
    int num=100;
    SaveAppData saveAppData;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView txtViewUpload;
String ed;
TableRow tableRow;
Button btnAdd;
    EditText edD1,edD2,edD3,edD4,eddD5;//for degree name
    EditText edU1,edU2,edU3,edU4,edU5;//for college name
    EditText edM1,edM2,edM3,edM4,edM5;//for marks/cgpa
    Spinner spY1,spY2,spY3,spY4,spY5;//spinners for year of passing
    Spinner spD1,spD2,spD3,spD4,spD5;//spinners for division
    Context context;
    InputStream is;
    String  storeTxtAttachment = "";
    byte[] inputData;

    Button btnu,btnu1,btnu2,btnu3,btnu4;
    SessionReadCandiDetails sessionReadCandiObj;
    String D1,D2,d3,d4,d5,U1,U2,U3,U4,U5,M1,M2,M3,M4,M5,Y1,Y2,Y3,Y4,Y5,SD1,SD2,SD3,SD4,SD5;
    String candiId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_qual);
        context=this;
        saveAppData=new SaveAppData();

        sessionReadCandiObj=new SessionReadCandiDetails(this);

        edD1=(EditText)findViewById(R.id.editTextC);
        edD2=(EditText)findViewById(R.id.editTextC1);
        edD3=(EditText)findViewById(R.id.editTextC2);
        edD4=(EditText)findViewById(R.id.editTextC3);
        eddD5=(EditText)findViewById(R.id.editTextC4);
        btnu=(Button)findViewById(R.id.btnUpload10);
        btnu1=(Button)findViewById(R.id.btnUpload12);
        btnu2=(Button)findViewById(R.id.btnUploadBtech);
        btnu3=(Button)findViewById(R.id.btnUploadMtech);
        btnu4=(Button)findViewById(R.id.btnUploadPhd);

txtViewUpload=(TextView)findViewById(R.id.textViewUpload);

        tableRow=(TableRow)findViewById(R.id.table);
btnAdd=(Button)findViewById(R.id.add);
btnAdd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        tableRow.setVisibility(View.VISIBLE);

    }
});
        edU1=(EditText)findViewById(R.id.editTextU);
        edU2=(EditText)findViewById(R.id.editTextU1);
        edU3=(EditText)findViewById(R.id.editTextU2);
        edU4 =(EditText)findViewById(R.id.editTextU3);
        edU5=(EditText)findViewById(R.id.editTextU4);


        edM1=(EditText)findViewById(R.id.m1);
        edM2=(EditText)findViewById(R.id.m2);
        edM3 =(EditText)findViewById(R.id.m3);
        edM4=(EditText)findViewById(R.id.m4);
        edM5=(EditText)findViewById(R.id.m5);

      /*  edM1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(edM1);
            }
        });
        edM2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(edM2);
            }
        });  edM3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(edM3);
            }
        });  edM4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(edM4);
            }
        });  edM5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(edM5);
            }
        });*/
        edM1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!edM1.getText().toString().trim().equals("null")) {
                    Log.i("response ",edM1.getText().toString().trim());

                  /*  if (Float.parseFloat(edM1.getText().toString())> 100) {
                        Toast.makeText(context, "Value of it cannot be greater than " + num, Toast.LENGTH_SHORT).show();
                        edM1.setText(String.valueOf(0));

                    }*/
                   // Toast.makeText(context, "edm1 "+edM1.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edM2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ((!edM2.getText().toString().equals(""))||(!edM2.getText().toString().trim().equals("null")))
                {
                /*if (Float.parseFloat(edM2.getText().toString().trim())>100){
                    Toast.makeText(context, "Value of it cannot be greater than "+num, Toast.LENGTH_SHORT).show();
                    edM2.setText(String.valueOf(0));

                }*/
            }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edM3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ((!edM3.getText().toString().equals(""))||(!edM3.getText().toString().trim().equals("null"))) {
             /*   if (Float.parseFloat(edM3.getText().toString().trim())>100) {
                    Toast.makeText(context, "Value of it cannot be greater than " + num, Toast.LENGTH_SHORT).show();
                    edM3.setText(String.valueOf(0));
                }*/
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edM4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("response ",edM1.getText().toString().trim());
               // showDialog(edM4);
                if ((!edM4.getText().toString().equals(""))||(!edM4.getText().toString().trim().equals("null"))) {

                /*if (Float.parseFloat(edM4.getText().toString().trim())>100){
                    Toast.makeText(context, "Value of it cannot be greater than "+num, Toast.LENGTH_SHORT).show();
                    edM4.setText(String.valueOf(0));

                }*/
            }}

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edM5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if ((!edM5.getText().toString().equals(""))||(!edM5.getText().toString().trim().equals("null"))) {
              /*  if (Float.parseFloat(edM5.getText().toString().trim())>100||!edM1.getText().equals("")) {
                    Toast.makeText(context, "Value of it cannot be greater than " + num, Toast.LENGTH_SHORT).show();
                    edM5.setText(String.valueOf(0));
                }*/
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        spD1=(Spinner)findViewById(R.id.spd1);
        spD2=(Spinner)findViewById(R.id.spd2);
        spD3=(Spinner)findViewById(R.id.spd3);
        spD4=(Spinner)findViewById(R.id.spd4);
        spD5=(Spinner)findViewById(R.id.spd5);


        spY1=(Spinner)findViewById(R.id.spY1);
        spY2=(Spinner)findViewById(R.id.spY2);
        spY3=(Spinner)findViewById(R.id.spY3);
        spY4=(Spinner)findViewById(R.id.spY4);
        spY5=(Spinner)findViewById(R.id.spY5);

        spD1.setOnItemSelectedListener(this);
        spD2.setOnItemSelectedListener(this);
        spD3.setOnItemSelectedListener(this);
        spD4.setOnItemSelectedListener(this);
        spD5.setOnItemSelectedListener(this);
        spY1.setOnItemSelectedListener(this);
        spY2.setOnItemSelectedListener(this);
        spY3.setOnItemSelectedListener(this);
        spY4.setOnItemSelectedListener(this);
        spY5.setOnItemSelectedListener(this);



        button=(Button)findViewById(R.id.edu);

        sharedPreferences=getSharedPreferences("myFile",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        Intent rcv=getIntent();
        if (rcv.hasExtra("keyCandiId")){
            setDetails();
            txtViewUpload.setText("Download documents here");
            btnu.setText("Download document");
            btnu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        String url = "http://202.164.39.172:2345/test/Prsc_recruitment/images/PRSC_Report.pdf";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(url), "text/html");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnu1.setText("Download document");
            btnu1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        String url = "http://202.164.39.172:2345/test/Prsc_recruitment/images/PRSC_Report.pdf";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(url), "text/html");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnu2.setText("Download document");
            btnu2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        String url = "http://202.164.39.172:2345/test/Prsc_recruitment/images/PRSC_Report.pdf";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(url), "text/html");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnu3.setText("Download document");
            btnu3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        String url = "http://202.164.39.172:2345/test/Prsc_recruitment/images/PRSC_Report.pdf";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(url), "text/html");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnu4.setText("Download document");
            btnu4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        String url = "http://202.164.39.172:2345/test/Prsc_recruitment/images/PRSC_Report.pdf";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(url), "text/html");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnAdd.setVisibility(View.GONE);

        }
            else {setDetails();


        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getViews();
                Intent rcv=getIntent();
                if (rcv.hasExtra("keyCandiId")) {
                    candiId=rcv.getStringExtra("keyCandiId");
                    Intent intent=new Intent(EduQual.this,WorkExp.class);
                    intent.putExtra("keyCandiId",candiId);
                    startActivity(intent);
                }
                else {

                    if (TextUtils.isEmpty(D1))
                    {
                        edD1.setError("This field is required");
                        edD1.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edD1, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }
                    if (TextUtils.isEmpty(D2))
                    {
                        edD2.setError("This field is required");
                        edD2.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edD2, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }
                    if (TextUtils.isEmpty(d3))
                    {
                        edD3.setError("This field is required");
                        edD3.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edD3, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }
                    if (TextUtils.isEmpty(U1))
                    {
                        edU1.setError("This field is required");
                        edU1.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edU1, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }

                    if (TextUtils.isEmpty(U2))
                    {
                        edU2.setError("This field is required");
                        edU2.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edU2, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }    if (TextUtils.isEmpty(U3))
                    {
                        edU3.setError("This field is required");
                        edU3.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edU3, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }
                    if (TextUtils.isEmpty(M1))
                    {
                        edM1.setError("This field is required");
                        edM1.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edM1, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }
                    if (TextUtils.isEmpty(M2))
                    {
                        edM2.setError("This field is required");
                        edM2.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edM2, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }
                    if (TextUtils.isEmpty(M3))
                    {
                        edM3.setError("This field is required");
                        edM3.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edM3, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }

                    if (spY1.getSelectedItemPosition() == 0) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EduQual.this);

                        builder.setMessage("Please select the year of passing");

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                        return;
                    }
                    if (spY2.getSelectedItemPosition() == 0) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EduQual.this);

                        builder.setMessage("Please select the year of passing");

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                        return;
                    } if (spY3.getSelectedItemPosition() == 0) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EduQual.this);

                        builder.setMessage("Please select the year of passing");

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                        return;
                    }
                    if (spD1.getSelectedItemPosition() == 0) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EduQual.this);

                        builder.setMessage("Please select the division");

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                        return;
                    }
                    if (spD2.getSelectedItemPosition() == 0) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EduQual.this);

                        builder.setMessage("Please select the division");

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                        return;
                    }  if (spD3.getSelectedItemPosition() == 0) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EduQual.this);

                        builder.setMessage("Please select the division");

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                        return;
                    }
                    sessionReadCandiObj.setMarks1(edM1.getText().toString().trim());
                    sessionReadCandiObj.setMarks2(edM2.getText().toString().trim());
                    sessionReadCandiObj.setMarks3(edM3.getText().toString().trim());
                    sessionReadCandiObj.setMarks4(edM4.getText().toString().trim());
                    sessionReadCandiObj.setMarks5(edM5.getText().toString().trim());


                    if (!TextUtils.isEmpty(edM1.getText().toString().trim()))

                    {                    if (Float.parseFloat(edM1.getText().toString().trim())>100){
                        showDialog();

                    }}

                    if (!TextUtils.isEmpty(M2)){

                        if (Float.parseFloat(M2)>100){
                        showDialog();

                    }}

                    if (!TextUtils.isEmpty(M3)){

                        if (Float.parseFloat(M3)>100){
                        showDialog();

                    } }

                    if (!TextUtils.isEmpty(M4)){

                        if (Float.parseFloat(M4)>100) {
                            showDialog();
                        }
                    }
                    if (!TextUtils.isEmpty(M5)){
                        if (Float.parseFloat(M5)>100){
                        showDialog();

                    }}

                    Intent intent=new Intent(EduQual.this,WorkExp.class);
                    startActivity(intent);

                }
            }
        });

    }

    public void setDetails(){

        if (!sessionReadCandiObj.getDeg1().toString().trim().equals("null")) {
            edD1.setText(sessionReadCandiObj.getDeg1());
        }
        if (!sessionReadCandiObj.getDeg2().toString().trim().equals("null")) {
            edD2.setText(sessionReadCandiObj.getDeg2());
        }
        if (!sessionReadCandiObj.getDeg3().toString().trim().equals("null")) {
            edD3.setText(sessionReadCandiObj.getDeg3());

        }


        if (!sessionReadCandiObj.getDeg4().toString().trim().equals("null")) {
            edD4.setText(sessionReadCandiObj.getDeg4());

        }
        if (!sessionReadCandiObj.getDeg5().toString().trim().equals("null")) {
            eddD5.setText(sessionReadCandiObj.getDeg5());
        }
        if (!sessionReadCandiObj.getCollegeUni1().toString().trim().equals("null")) {

            edU1.setText(sessionReadCandiObj.getCollegeUni1());
        }

        if (!sessionReadCandiObj.getCollegeUni2().toString().trim().equals("null")) {

            edU2.setText(sessionReadCandiObj.getCollegeUni2());
          //  Toast.makeText(context, "U2 "+sessionReadCandiObj.getCollegeUni2(), Toast.LENGTH_SHORT).show();


        }
        if (!sessionReadCandiObj.getCollegeUni3().toString().trim().equals("null")) {


            edU3.setText(sessionReadCandiObj.getCollegeUni3());
        }
        if (!sessionReadCandiObj.getCollegeUni4().toString().trim().equals("null")) {

            edU4.setText(sessionReadCandiObj.getCollegeUni4());
        }
        if (!sessionReadCandiObj.getCollegeUni5().toString().trim().equals("null")) {
            edU5.setText(sessionReadCandiObj.getCollegeUni5());
        }



        String yop1=sessionReadCandiObj.getYoP1();
        for(int i=0;i<getResources().getStringArray(R.array.year_of_passing).length;i++){
            if(yop1.equals(getResources().getStringArray(R.array.year_of_passing)[i]))
                spY1.setSelection(i);

        }
        String yop2=sessionReadCandiObj.getYoP2();
        for(int i=0;i<getResources().getStringArray(R.array.year_of_passing).length;i++){
            if(yop2.equals(getResources().getStringArray(R.array.year_of_passing)[i]))
                spY2.setSelection(i);

        }
        String yop3=sessionReadCandiObj.getYoP3();
        for(int i=0;i<getResources().getStringArray(R.array.year_of_passing).length;i++){
            if(yop3.equals(getResources().getStringArray(R.array.year_of_passing)[i]))
                spY3.setSelection(i);

        }
        String yop4=sessionReadCandiObj.getYoP4();
        for(int i=0;i<getResources().getStringArray(R.array.year_of_passing).length;i++){
            if(yop4.equals(getResources().getStringArray(R.array.year_of_passing)[i]))
                spY4.setSelection(i);

        }
        String yop5=sessionReadCandiObj.getYoP5();
        for(int i=0;i<getResources().getStringArray(R.array.year_of_passing).length;i++){
            if(yop5.equals(getResources().getStringArray(R.array.year_of_passing)[i]))
                spY5.setSelection(i);

        }

if (!sessionReadCandiObj.getMarks1().toString().trim().equals("null")){
        edM1.setText(sessionReadCandiObj.getMarks1());


        }

        if (!sessionReadCandiObj.getMarks2().toString().trim().equals("null")){

            edM2.setText(sessionReadCandiObj.getMarks2().toString());
        }

        if (!sessionReadCandiObj.getMarks3().toString().trim().equals("null")) {

            edM3.setText(sessionReadCandiObj.getMarks3());
        }
        if (!sessionReadCandiObj.getMarks4().toString().trim().equals("null")) {
            edM4.setText(sessionReadCandiObj.getMarks4());

        }
        if (!sessionReadCandiObj.getMarks5().toString().trim().equals("null")) {
            edM5.setText(sessionReadCandiObj.getMarks5());
        }
        String div1=sessionReadCandiObj.getDiv1();
        for(int i=0;i<getResources().getStringArray(R.array.division).length;i++){
            if(div1.equals(getResources().getStringArray(R.array.division)[i]))
                spD1.setSelection(i);

        }
        String div2=sessionReadCandiObj.getDiv2();
        for(int i=0;i<getResources().getStringArray(R.array.division).length;i++){
            if(div2.equals(getResources().getStringArray(R.array.division)[i]))
                spD2.setSelection(i);

        }
        String div3=sessionReadCandiObj.getDiv3();
        for(int i=0;i<getResources().getStringArray(R.array.division).length;i++){
            if(div3.equals(getResources().getStringArray(R.array.division)[i]))
                spD3.setSelection(i);

        }
        String div4=sessionReadCandiObj.getDiv4();
        for(int i=0;i<getResources().getStringArray(R.array.division).length;i++){
            if(div4.equals(getResources().getStringArray(R.array.division)[i]))
                spD4.setSelection(i);

        }
        String div5=sessionReadCandiObj.getDiv5();
        for(int i=0;i<getResources().getStringArray(R.array.division).length;i++){
            if(div5.equals(getResources().getStringArray(R.array.division)[i]))
                spD5.setSelection(i);

        }


    }
    public void getViews(){
        D1=edD1.getText().toString().trim();
        //  saveAppData.setD1(D1);
        editor.putString("D1",D1).commit();
        D2=edD2.getText().toString().trim();
        editor.putString("D2",D2).commit();

        d3=edD3.getText().toString().trim();
        editor.putString("D3",d3).commit();

        d4=edD4.getText().toString().trim();
        editor.putString("D4",d4).commit();

        d5=eddD5.getText().toString().trim();
        editor.putString("D5",d5).commit();


        U1=edU1.getText().toString().trim();
        editor.putString("U1",U1).commit();

        U2=edU2.getText().toString().trim();
        editor.putString("U2",U2).commit();

        U3=edU3.getText().toString().trim();
        editor.putString("U3",U3).commit();

        U4=edU4.getText().toString().trim();
        editor.putString("U4",U4).commit();

        U5 =edU5.getText().toString().trim();
        editor.putString("U5",U5).commit();


        M1=edM1.getText().toString().trim();
        editor.putString("M1",M1).commit();

        M2=edM2.getText().toString().trim();
        editor.putString("M2",M2).commit();

        M3=edM3.getText().toString().trim();
        editor.putString("M3",M3).commit();

        M4=edM4.getText().toString().trim();
        editor.putString("M4",M4).commit();

        M5=edM5.getText().toString().trim();
        editor.putString("M5",M5).commit();

       // Toast.makeText(context, "Data "+U1+"-"+D1+"-"+M1+"-", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spinner=(Spinner)adapterView;
        switch (spinner.getId()){
            case R.id.spd1:

                SD1=spD1.getSelectedItem().toString();
                editor.putString("SD1",SD1).commit();
             //   Toast.makeText(context, "You selected "+SD1, Toast.LENGTH_SHORT).show();
                break;

            case R.id.spd2:

                SD2=spD2.getSelectedItem().toString();
                editor.putString("SD2",SD2).commit();

//                Toast.makeText(context, "You selected "+SD2, Toast.LENGTH_SHORT).show();


                break;


            case R.id.spd3:
                SD3=spD3.getSelectedItem().toString();
                editor.putString("SD3",SD3).commit();

  //              Toast.makeText(context, "You selected "+SD1, Toast.LENGTH_SHORT).show();


                break;

            case R.id.spd4:

                SD4=spD4.getSelectedItem().toString();
                editor.putString("SD4",SD4).commit();

                break;

            case R.id.spd5:
                SD5=spD5.getSelectedItem().toString();
                editor.putString("SD5",SD5).commit();

                break;

            case R.id.spY1:

                Y1=spY1.getSelectedItem().toString();
                editor.putString("SY1",Y1).commit();

                break;

            case  R.id.spY2:
                Y2=spY2.getSelectedItem().toString();
                editor.putString("SY2",Y2).commit();

                break;
            case  R.id.spY3:
                Y3=spY3.getSelectedItem().toString();
                editor.putString("SY3",Y3).commit();

                break;
            case  R.id.spY4:

                Y4=spY4.getSelectedItem().toString();
                editor.putString("SY4",Y4).commit();

                break;
            case  R.id.spY5:
                Y5=spY5.getSelectedItem().toString();

                editor.putString("SY5",Y5).commit();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {



    }


  /*  public void showDialog(EditText editText){
        Dialog dialog=new Dialog(this);

        dialog.setContentView(R.layout.select_cgpaorper);

        RadioButton rbCgpa=(RadioButton)dialog.findViewById(R.id.rbCGPA);
        RadioButton rbPer=(RadioButton)dialog.findViewById(R.id.rbPer);


rbCgpa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            num = 10;
            Toast.makeText(context, "VAlue is less than 10", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }*/

/*});

        rbPer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    num = 100;
                    Toast.makeText(context, "VAlue is less than 100", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

        });

dialog.show();*/

  //  }

public void showDialog(){

    AlertDialog.Builder builder=new AlertDialog.Builder(EduQual.this);
    builder.setMessage("Marks cannot be greater than 100");
    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    });

    AlertDialog dialog=builder.create();

    dialog.show();
    dialog.setCancelable(false);
    return;
}
public void uploadMarksheet(View view){
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("application/pdf");
    if (view.getId()==R.id.btnUpload10){}
    if (view.getId()==R.id.btnUpload12){}
    if (view.getId()==R.id.btnUploadBtech){}
    if (view.getId()==R.id.btnUploadMtech){}
    if (view.getId()==R.id.btnUploadPhd){}

    startActivityForResult(intent, 1002);



}

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){

        if (resultCode == RESULT_OK) {


            if (requestCode == 1002) {
                if (data != null) {

                    Uri uri = data.getData();
                    try {
                        is = getContentResolver().openInputStream(uri);

                    } catch (FileNotFoundException e) {
                        Toast.makeText(context, "catch " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    String uriString = uri.toString();
                    try {
                        is = getContentResolver().openInputStream(uri);
                        inputData = getBytes(is);
                        BackTask backTask=new BackTask();
                        backTask.execute();
                    }
                    catch (Exception e){
                        Toast.makeText(context, "catch "+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    //****STORE PATH IN GLOBAL VARIABLE****
                    storeTxtAttachment = path;
                    //*********

                    if (uriString.startsWith("content://")) {

                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }

                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }
ed=displayName;
                }
            }
        }

    }
    public byte[] getBytes(InputStream inputStream) throws IOException {

        ByteArrayOutputStream byteBuffer=new ByteArrayOutputStream();
        int bufferSize=1024;
        byte[] buffer =new byte[bufferSize];
        int len=0;
        while ((len=inputStream.read(buffer))!=-1){
            byteBuffer.write(buffer,0,len);

        }
        return byteBuffer.toByteArray();
    }
    private class BackTask extends AsyncTask<Void, Void, String> {
        TextView tv;

        protected void onPreExecute() {

        }

        protected String doInBackground(Void... params) {
            String text = "";
//we need to convert image bitmap into string
            try {
                // Create HttpClitent instance
                HttpClient httpClient = new DefaultHttpClient();//used as a wrapper to send data
                // Create HttpPost instance with specifying php file on server to handle file uploading
                HttpPost httpPostRequest = new HttpPost("http://202.164.39.172:2345/test/Prsc_recruitment/img_upd.php");

                // Read image file and convert it to byte array
                //    Bitmap bitmap=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/Rose.jpg");

                // Specify HttpClient parameters
                httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
                // Create InputStreamBody instance with specifying image data destination file name
                InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(inputData), "/index_files/" +ed.trim());
                Log.d("i11", ed.trim() + "");
                // Create MultipartEntityBuilder instance
                MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
                // Set multi-part mode
                multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                // Attach image data in InputStreamBody instance to MultipartEntityBuilder instance
                multipartEntity.addPart("img_file", inputStreamBody);
                // Set MultipartEntityBuilder instance to HttpPost
                httpPostRequest.setEntity(multipartEntity.build());

                // Start uploading the image and get the result from remote server

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                text = httpClient.execute(httpPostRequest, responseHandler);
                return text;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (result.contains("ok"))
                Toast.makeText(context, "Document uploaded successfully", Toast.LENGTH_SHORT).show();

            else
                Toast.makeText(context, "unsuccessful", Toast.LENGTH_SHORT).show();

        }
    }


}

