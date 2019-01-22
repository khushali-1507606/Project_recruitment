package com.admin.nidhi_khushali;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import org.json.JSONObject;
import com.example.win_10.newapp.R;
import com.util.Util;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;
import java.util.TimeZone;

//import in.prsc.com.test_bin.util.Util;


public class Test_create__adv extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener,AdapterView.OnItemSelectedListener {

InputStream is;
byte[] inputData;
    Context context;
    EditText advNo, advDesc, advValidityDate, advDateCreated;
    Button btnAttachFile, btnCreateAdv;
    TextView txtDisplay, txtAttachment, txtAdvHeading;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10;
    Spinner spnrAdvStatus, spnrAdvNoOfDiv;
    LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8;

    Spinner spnrDiv1, spnrDiv2, spnrDiv3, spnrDiv4, spnrDiv5, spnrDiv6, spnrDiv7, spnrDiv8;
    TextView spnrPost1, spnrPost2, spnrPost3, spnrPost4, spnrPost5, spnrPost6, spnrPost7, spnrPost8;

    ArrayList<String> advStatusList, advNoDivList, listDivisions, list_Posts;

    String storeId = "", storeAdvNo = "", storeAdvDesc = "", storeAdvValidityDate = "", storeAdvDateCreated = "", storeTxtAttachment = "",
            storeSpnrAdvStatus = "", storeSpnrAdvNoOfDiv = "", storeDiv1 = "", storeDiv2 = "", storeDiv3 = "", storeDiv4 = "", storeDiv5 = "",
            storeDiv6 = "", storeDiv7 = "", storeDiv8 = "", storePost1 = "", storePost2 = "", storePost3 = "", storePost4 = "", storePost5 = "", storePost6 = "", storePost7 = "", storePost8 = "", mode = "";
    String c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, passId = "", show = "", show1[], show2[];
    StringBuilder builder;

    public static final int PICK_PDF_FILE = 1;

    int d1, d2, m1, m2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_create__adv);

        initViews();
        context = this;
        builder = new StringBuilder();


    }

    @Override
    protected void onStart() {
        super.onStart();
        //edit advertisement
        Intent rcv = getIntent();
        if (rcv.hasExtra("keyId")) {

            passId = rcv.getStringExtra("keyId");

            Toast.makeText(this, "Id: " + passId, Toast.LENGTH_SHORT).show();
            txtAdvHeading.setText(" Edit Advertisement");
            btnCreateAdv.setText("Update");
            updateAdds uAobj = new updateAdds();
            uAobj.execute();
        }

    }

    //initialisation function
    public void initViews() {

        show1 = new String[100];
        show2 = new String[100];

        //editTexts...
        advNo = findViewById(R.id.editTextAdvNo);
        advDesc = findViewById(R.id.editTextAdvDesc);
        advValidityDate = findViewById(R.id.editTextAdvValidityDate);
        advDateCreated = findViewById(R.id.editTextAdvDateCreated);

        //buttons...
        btnAttachFile = findViewById(R.id.buttonAdvAttachment);
        btnCreateAdv = findViewById(R.id.buttonAdvCreateAdv);

        //textViews..
        txtAttachment = findViewById(R.id.textViewAdvAttchmentName);
        txtAdvHeading = findViewById(R.id.textViewNewAdv);

        //spinners...
        spnrAdvStatus = findViewById(R.id.spinnerAdvStatus);
        spnrAdvNoOfDiv = findViewById(R.id.spinnerAdvNoOfDivisions);

        listDivisions = new ArrayList<>();
        list_Posts = new ArrayList<>();


        listDivisions.add("Please Select Division Applied For");
        listDivisions.add("ADVANCED GEOSPATIAL APPLICATION GROUP (AGAG)");
        listDivisions.add("LAND RESOURCE UTILITIES & INFRASTRUCTURE");
        listDivisions.add("GEOLOGY&WATER RESOURCES");
        listDivisions.add("GEOINFORMATICS");
        listDivisions.add("AGRO-ECOSYSTEM AND CROP MODELLING");
        listDivisions.add("FORESTRY AND LAND USE");
        listDivisions.add("NATURAL RESOURCES & ENVIRONMENT");


        ArrayAdapter<String> DivAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listDivisions);

        //listPosts=new ArrayList<>();
        //listPosts.add("Select Post");
        //listPosts.add("Programmer");
        //listPosts.add("Assistant Programmer");
        //listPosts.add("Web Application Development");
        //listPosts.add("IT Lab Attendant");
        //listPosts.add("Field_cum_Lab_Asstt");
        //listPosts.add("Research Associate");
        //listPosts.add("Research Fellow");
        //listPosts.add("Junior Research Fellow");
        //listPosts.add("Interns Student");
        // listPosts.add("GIS_CAD Trainee");


        spnrDiv1 = findViewById(R.id.spinnerAdvDiv1);
        spnrDiv1.setAdapter(DivAdapter);
        spnrDiv2 = findViewById(R.id.spinnerAdvDiv2);
        spnrDiv2.setAdapter(DivAdapter);
        spnrDiv3 = findViewById(R.id.spinnerAdvDiv3);
        spnrDiv3.setAdapter(DivAdapter);
        spnrDiv4 = findViewById(R.id.spinnerAdvDiv4);
        spnrDiv4.setAdapter(DivAdapter);
        spnrDiv5 = findViewById(R.id.spinnerAdvDiv5);
        spnrDiv5.setAdapter(DivAdapter);
        spnrDiv6 = findViewById(R.id.spinnerAdvDiv6);
        spnrDiv6.setAdapter(DivAdapter);
        spnrDiv7 = findViewById(R.id.spinnerAdvDiv7);
        spnrDiv7.setAdapter(DivAdapter);
        spnrDiv8 = findViewById(R.id.spinnerAdvDiv8);
        spnrDiv8.setAdapter(DivAdapter);

        spnrPost1 = findViewById(R.id.spinnerAdvPost1);
        //  spnrPost1.setAdapter(PostAdapter);
        spnrPost2 = findViewById(R.id.spinnerAdvPost2);
        //spnrPost2.setAdapter(PostAdapter);
        spnrPost3 = findViewById(R.id.spinnerAdvPost3);
        //spnrPost3.setAdapter(PostAdapter);
        spnrPost4 = findViewById(R.id.spinnerAdvPost4);
        //spnrPost4.setAdapter(PostAdapter);
        spnrPost5 = findViewById(R.id.spinnerAdvPost5);
        //spnrPost5.setAdapter(PostAdapter);
        spnrPost6 = findViewById(R.id.spinnerAdvPost6);
        //spnrPost6.setAdapter(PostAdapter);
        spnrPost7 = findViewById(R.id.spinnerAdvPost7);
        //spnrPost7.setAdapter(PostAdapter);
        spnrPost8 = findViewById(R.id.spinnerAdvPost8);
        //spnrPost8.setAdapter(PostAdapter);


       /* spnrPost2.setOnClickListener(this);
        spnrPost3.setOnClickListener(this);
        spnrPost4.setOnClickListener(this);
        spnrPost5.setOnClickListener(this);
        spnrPost6.setOnClickListener(this);
        spnrPost7.setOnClickListener(this);
        spnrPost8.setOnClickListener(this);*/


        ll1 = findViewById(R.id.Nod1);
        ll2 = findViewById(R.id.Nod2);
        ll3 = findViewById(R.id.Nod3);
        ll4 = findViewById(R.id.Nod4);
        ll5 = findViewById(R.id.Nod5);
        ll6 = findViewById(R.id.Nod6);
        ll7 = findViewById(R.id.Nod7);
        ll8 = findViewById(R.id.Nod8);

        ll1.setVisibility(View.GONE);
        ll2.setVisibility(View.GONE);
        ll3.setVisibility(View.GONE);
        ll4.setVisibility(View.GONE);
        ll5.setVisibility(View.GONE);
        ll6.setVisibility(View.GONE);
        ll7.setVisibility(View.GONE);
        ll8.setVisibility(View.GONE);

        //setting status spinner...
        advStatusList = new ArrayList<>();
        advStatusList.add("Select Status");
        advStatusList.add("Open");
        advStatusList.add("Close");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, advStatusList);
        spnrAdvStatus.setAdapter(statusAdapter);

        //setting no of divisions spinner
        advNoDivList = new ArrayList<>();
        advNoDivList.add("Select No. of Divisions");
        advNoDivList.add("1");
        advNoDivList.add("2");
        advNoDivList.add("3");
        advNoDivList.add("4");
        advNoDivList.add("5");
        advNoDivList.add("6");
        advNoDivList.add("7");
        ArrayAdapter<String> NoOfDivisionsAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, advNoDivList);
        spnrAdvNoOfDiv.setAdapter(NoOfDivisionsAdapter);
        spnrAdvNoOfDiv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {

                    case 1:
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.GONE);
                        ll3.setVisibility(View.GONE);
                        ll4.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll6.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        break;
                    case 2:
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.GONE);
                        ll4.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll6.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        break;
                    case 3:
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.VISIBLE);
                        ll4.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll6.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        break;
                    case 4:
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.VISIBLE);
                        ll4.setVisibility(View.VISIBLE);
                        ll5.setVisibility(View.GONE);
                        ll6.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        break;
                    case 5:
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.VISIBLE);
                        ll4.setVisibility(View.VISIBLE);
                        ll5.setVisibility(View.VISIBLE);
                        ll6.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        break;
                    case 6:
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.VISIBLE);
                        ll4.setVisibility(View.VISIBLE);
                        ll5.setVisibility(View.VISIBLE);
                        ll6.setVisibility(View.VISIBLE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        break;
                    case 7:
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.VISIBLE);
                        ll4.setVisibility(View.VISIBLE);
                        ll5.setVisibility(View.VISIBLE);
                        ll6.setVisibility(View.VISIBLE);
                        ll7.setVisibility(View.VISIBLE);
                        ll8.setVisibility(View.GONE);
                        break;
                    case 8:
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.VISIBLE);
                        ll4.setVisibility(View.VISIBLE);
                        ll5.setVisibility(View.VISIBLE);
                        ll6.setVisibility(View.VISIBLE);
                        ll7.setVisibility(View.VISIBLE);
                        ll8.setVisibility(View.VISIBLE);
                        break;
                    default:
                        //Toast.makeText(Test_create__adv.this, "Select Number Of Divisions", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Listeners...
        advValidityDate.setOnClickListener(this);
        advDateCreated.setOnClickListener(this);

        btnAttachFile.setOnClickListener(this);
        btnCreateAdv.setOnClickListener(this);

        spnrDiv1.setOnItemSelectedListener(this);
        spnrDiv2.setOnItemSelectedListener(this);
        spnrDiv3.setOnItemSelectedListener(this);
        spnrDiv4.setOnItemSelectedListener(this);
        spnrDiv5.setOnItemSelectedListener(this);
        spnrDiv6.setOnItemSelectedListener(this);
        spnrDiv7.setOnItemSelectedListener(this);
        spnrDiv8.setOnItemSelectedListener(this);

    }


    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.editTextAdvValidityDate) {
            advValidityDate.clearFocus();
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day_of_month) {
                    d2 = day_of_month;
                    m2 = month+1;
                    y2 = year;
                    StringBuilder strDate = new StringBuilder();
                    strDate.append(year + "-" + m2 + "-" + day_of_month);
                    advValidityDate.setText(strDate);

                }
            },
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        } else if (id == R.id.editTextAdvDateCreated) {

            advDateCreated.clearFocus();
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day_of_month) {
                    d1 = day_of_month;
                    m1 = month+1;
                    y1 = year;
                    StringBuilder strDate = new StringBuilder();
                    strDate.append(year + "-" + m1 + "-" + day_of_month);
                    advDateCreated.setText(strDate);
                }
            },
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        } else if (id == R.id.buttonAdvAttachment) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, PICK_PDF_FILE);
        } else if (id == R.id.buttonAdvCreateAdv) {

            if (btnCreateAdv.getText().toString().equals("Create")) {
                mode = "A";
            } else{
                mode = "M";}

                storedata();
            checkValidations();




        }
        else if (id == R.id.spinnerAdvPost1) {
            list_Posts.clear();
            showDialog("spnrPost1");
            //spnrPost1.setText(show);
            //show="";
        } else if (id == R.id.spinnerAdvPost2) {

            list_Posts.clear();
            showDialog("spnrPost2");
            //spnrPost2.setText(show);
            // show="";
        } else if (id == R.id.spinnerAdvPost3) {
            list_Posts.clear();
            showDialog("spnrPost3");
            //spnrPost3.setText(show);
            //show="";

        } else if (id == R.id.spinnerAdvPost4) {
            list_Posts.clear();
            showDialog("spnrPost4");
            //spnrPost4.setText(show);
            //show="";

        } else if (id == R.id.spinnerAdvPost5) {
            list_Posts.clear();
            showDialog("spnrPost5");
            //spnrPost5.setText(show);
            //show="";

        } else if (id == R.id.spinnerAdvPost6) {
            list_Posts.clear();
            showDialog("spnrPost6");
            //spnrPost6.setText(show);
            //show="";

        } else if (id == R.id.spinnerAdvPost7) {
            list_Posts.clear();
            showDialog("spnrPost7");
            //spnrPost7.setText(show);
            //show="";

        } else if (id == R.id.spinnerAdvPost8) {
            list_Posts.clear();
            showDialog("spnrPost8");
            //spnrPost8.setText(show);
            //show="";
        }

    }



        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){

            if (resultCode == RESULT_OK) {


                if (requestCode == PICK_PDF_FILE) {
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

                        txtAttachment.setText(displayName);
                    }
                }
            }

        }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    int id=compoundButton.getId();

    switch (id){

        case R.id.checkbox1:
            if (b){
                c1=cb1.getText().toString().trim();
                list_Posts.add(c1);

            }

            else {


                list_Posts.remove(c1);

            }
          //  builder.append(c1);
            String c= Arrays.toString(list_Posts.toArray()).replace("[","").replace("]","");
            txtDisplay.setText(c);

            break;

        case R.id.checkbox2:
            if (b){
                c2=cb2.getText().toString().trim();
list_Posts.add(c2);

            }

            else {

                list_Posts.remove(c2);
            }
         //   builder.append(c2);
            String c1= Arrays.toString(list_Posts.toArray()).replace("[","").replace("]","");
            txtDisplay.setText(c1);
            break;

        case R.id.checkbox3:
            if (b){
                c3=cb3.getText().toString().trim();
list_Posts.add(c3);

            }

            else {
            list_Posts.remove(c3);

            }
           // builder.append(c3);
            String c2= Arrays.toString(list_Posts.toArray()).replace("[","").replace("]","");
            txtDisplay.setText(c2);
            break;
        case R.id.checkbox4:
            if (b){
                c4=cb4.getText().toString().trim();
                list_Posts.add(c4);

            }

            else {
            list_Posts.remove(c4);
            }
            //builder.append(c4);
            String c3= Arrays.toString(list_Posts.toArray()).replace("[","").replace("]","");
            txtDisplay.setText(c3);
            break;
        case R.id.checkbox5:
            if (b){
                c5=cb5.getText().toString().trim();
list_Posts.add(c5);
            }

            else {
list_Posts.remove(c5);

            }
            //builder.append(c5);
            String c4= Arrays.toString(list_Posts.toArray()).replace("[","").replace("]","");
            txtDisplay.setText(c4);            break;
        case R.id.checkbox6:
            if (b){
                c6=cb6.getText().toString().trim();

list_Posts.add(c6);


            }

            else {

                list_Posts.remove(c6);
            }
          //  builder.append(c6);
            String c5= Arrays.toString(list_Posts.toArray()).replace("[","").replace("]","");
            txtDisplay.setText(c5);
            break;
        case R.id.checkbox7:
            if (b){
                c7=cb7.getText().toString().trim();
                list_Posts.add(c7);
            }

            else {

list_Posts.remove(c7);
            }
           // builder.append(c7);
            String c6= Arrays.toString(list_Posts.toArray()).replace("[","").replace("]","");
            txtDisplay.setText(c6);            break;
        case R.id.checkbox8:

            if (b){
                c8=cb8.getText().toString().trim();
            list_Posts.add(c8);
            }

            else {
            list_Posts.remove(c8);
            }
         //   builder.append(c8);
            String c7= Arrays.toString(list_Posts.toArray()).replace("[","").replace("]","");
            txtDisplay.setText(c7);            break;
        case R.id.checkbox9:
            if (b){
                c9=cb9.getText().toString().trim();
list_Posts.add(c9);
            }

            else {
list_Posts.remove(c9);
            }
           // builder.append(c9);
            String c8= Arrays.toString(list_Posts.toArray()).replace("[","").replace("]","");
            txtDisplay.setText(c8);            break;
        case R.id.checkbox10:
            if (b){
                c10=cb10.getText().toString().trim();
list_Posts.add(c10);
            }

            else {
                list_Posts.remove(c10);

            }
//            builder.append(c10);
            String c9= Arrays.toString(list_Posts.toArray()).replace("[","").replace("]","");
            txtDisplay.setText(c9);            break;





    }








    }

    public void showDialog(String msg){

        final Dialog dialog=new Dialog(context);

        dialog.setContentView(R.layout.dialog_post);

         txtDisplay=dialog.findViewById(R.id.textViewDisplay);
        cb1=dialog.findViewById(R.id.checkbox1);
         cb2=dialog.findViewById(R.id.checkbox2);
         cb3=dialog.findViewById(R.id.checkbox3);
         cb4=dialog.findViewById(R.id.checkbox4);
         cb5=dialog.findViewById(R.id.checkbox5);
         cb6=dialog.findViewById(R.id.checkbox6);
         cb7=dialog.findViewById(R.id.checkbox7);
         cb8=dialog.findViewById(R.id.checkbox8);
         cb9=dialog.findViewById(R.id.checkbox9);
         cb10=dialog.findViewById(R.id.checkbox10);
        if (msg.equals("spnrPost1")){

            if (show1[0]!=null) {

                show2 = show1[0].split(",");



              //  Toast.makeText(context, "array  "+show2.toString()+" Length "+show2.length, Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, "show "+Arrays.toString(show2), Toast.LENGTH_SHORT).show();
               /* Toast.makeText(context, "show "+show2[0], Toast.LENGTH_LONG).show();
                Toast.makeText(context, "show "+show2[1], Toast.LENGTH_LONG).show();
                Toast.makeText(context, "show "+show2[2], Toast.LENGTH_LONG).show();
                Toast.makeText(context, "show "+show2[3], Toast.LENGTH_LONG).show();
*/

                for (int i = 0; i < show2.length; i++) {



                    list_Posts.add(show2[i]);


                    if (show2[i].trim().equals(cb1.getText().toString().trim())){
                        cb1.setChecked(true);
                    }

                    if (show2[i].trim().equals(cb2.getText().toString().trim())){
                        cb2.setChecked(true);
                    }

                    if (show2[i].trim().equals(cb3.getText().toString().trim())){
                        cb3.setChecked(true);
                    }
                    if (show2[i].trim().equals(cb4.getText().toString().trim())){
                        cb4.setChecked(true);
                    }
                    if (show2[i].trim().equals(cb5.getText().toString().trim())){
                        cb5.setChecked(true);
                    }
                    if (show2[i].trim().equals(cb6.getText().toString().trim())){
                        cb6.setChecked(true);
                    }
                    if (show2[i].trim().equals(cb7.getText().toString().trim())){
                        cb7.setChecked(true);
                    }
                    if (show2[i].trim().equals(cb8.getText().toString().trim())){
                        cb8.setChecked(true);
                    }
                    if (show2[i].trim().equals(cb9.getText().toString().trim())){
                        cb9.setChecked(true);
                    }
                    if (show2[i].trim().equals(cb10.getText().toString().trim())){
                        cb10.setChecked(true);
                    }

                }


                for (int i=0;i<list_Posts.size();i++) {


                    if (list_Posts.get(i).equals(cb1.getText().toString().trim())) {
                        cb1.setChecked(true);
                    }

                     if (list_Posts.get(i).equals(cb2.getText().toString().trim())) {
                        cb2.setChecked(true);
                    }

                }

                               txtDisplay.setText(show1[0]);
            }




            //  Toast.makeText(context, "shw1 "+show1[0], Toast.LENGTH_SHORT).show();
            //show="";
        }
        else  if (msg.equals("spnrPost2")){
            txtDisplay.setText(show1[1]);
            check(show1[1]);
            //  spnrPost2.setText(show);
            //show="";

        }
        else if (msg.equals("spnrPost3")){
          //  show1[2]=txtDisplay.getText().toString().trim();
            txtDisplay.setText(show1[2]);
            check(show1[2]);

            //spnrPost4.setText(show);
            //show="";

        }
        else if (msg.equals("spnrPost4")){
            //show1[3]=txtDisplay.getText().toString().trim();
            txtDisplay.setText(show1[3]);
            check(show1[3]);

            //spnrPost3.setText(show);
            //show="";

        }
        else if (msg.equals("spnrPost5")){
            //show1[4]=txtDisplay.getText().toString().trim();
            txtDisplay.setText(show1[4]);
            check(show1[4]);

            //spnrPost5.setText(show);
            //show="";

        }
        else if (msg.equals("spnrPost6")){
            //show1[5]=txtDisplay.getText().toString().trim();
            txtDisplay.setText(show1[5]);
            check(show1[5]);

            //spnrPost6.setText(show);
            //show="";

        }
        else if (msg.equals("spnrPost7")){
            //show1[6]=txtDisplay.getText().toString().trim();
            txtDisplay.setText(show1[6]);
            check(show1[6]);

            //spnrPost7.setText(show);
            //show="";

        }  if (msg.equals("spnrPost8")){
            //show1[0]=txtDisplay.getText().toString().trim();
            txtDisplay.setText(show1[7]);
            check(show1[7]);
            //spnrPost8.setText(show);
            //show="";
        }
         Button btnDOne=dialog.findViewById(R.id.buttonDone);
         btnDOne.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                //show=txtDisplay.getText().toString().trim();
                if (msg.equals("spnrPost1")){
                    show1[0]=txtDisplay.getText().toString().trim();
                    spnrPost1.setText(show1[0]);
                    //show="";
                }
               else  if (msg.equals("spnrPost2")){
                    show1[1]=txtDisplay.getText().toString().trim();
                    spnrPost2.setText(show1[1]);
                   //  spnrPost2.setText(show);
                    //show="";

                }
                else if (msg.equals("spnrPost3")){
                    show1[2]=txtDisplay.getText().toString().trim();
                    spnrPost3.setText(show1[2]);
                    //spnrPost4.setText(show);
                    //show="";

                }
                 else if (msg.equals("spnrPost4")){
                    show1[3]=txtDisplay.getText().toString().trim();
                    spnrPost4.setText(show1[3]);
                     //spnrPost3.setText(show);
                    //show="";

                }
                else if (msg.equals("spnrPost5")){
                    show1[4]=txtDisplay.getText().toString().trim();
                    spnrPost5.setText(show1[4]);
                     //spnrPost5.setText(show);
                     //show="";

                 }
                 else if (msg.equals("spnrPost6")){
                    show1[5]=txtDisplay.getText().toString().trim();
                    spnrPost6.setText(show1[5]);
                     //spnrPost6.setText(show);
                     //show="";

                 }
                 else if (msg.equals("spnrPost7")){
                    show1[6]=txtDisplay.getText().toString().trim();
                    spnrPost7.setText(show1[6]);
                    //spnrPost7.setText(show);
                     //show="";

                 }  if (msg.equals("spnrPost8")){
                     show1[0]=txtDisplay.getText().toString().trim();
                     spnrPost8.setText(show1[7]);
                     //spnrPost8.setText(show);
                     //show="";
                 }
                 dialog.dismiss();


             }
         });


        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);
        cb3.setOnCheckedChangeListener(this);
        cb4.setOnCheckedChangeListener(this);
        cb5.setOnCheckedChangeListener(this);
        cb6.setOnCheckedChangeListener(this);
        cb7.setOnCheckedChangeListener(this);
        cb8.setOnCheckedChangeListener(this);
        cb9.setOnCheckedChangeListener(this);
        cb10.setOnCheckedChangeListener(this);

        dialog.show();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
Spinner spinner=(Spinner)adapterView;

switch (spinner.getId()){
    case R.id.spinnerAdvDiv1:
        if (i!=0) {
            spnrPost1.setHint("Select Post");
            spnrPost1.setOnClickListener(this);
        }
        break;
    case R.id.spinnerAdvDiv2:

        if (i!=0) {
            spnrPost2.setHint("Select Post");
            spnrPost2.setOnClickListener(this);
        }
        break;
    case R.id.spinnerAdvDiv3:
        if (i!=0) {
            spnrPost3.setHint("Select Post");
            spnrPost3.setOnClickListener(this);
        }
        break;
    case R.id.spinnerAdvDiv4:
        if (i!=0){
        spnrPost4.setHint("Select Post");
        spnrPost4.setOnClickListener(this);}
        break;
    case R.id.spinnerAdvDiv5:
        if (i!=0) {
            spnrPost5.setHint("Select Post");
            spnrPost5.setOnClickListener(this);
        }break;
    case R.id.spinnerAdvDiv6:

        if (i!=0) {
            spnrPost6.setHint("Select Post");
            spnrPost6.setOnClickListener(this);
        }break;
    case R.id.spinnerAdvDiv7:
        if (i!=0) {
            spnrPost7.setHint("Select Post");
            spnrPost7.setOnClickListener(this);
        }break;
    case R.id.spinnerAdvDiv8:
        if (i!=0) {
            spnrPost8.setHint("Select Post");
            spnrPost8.setOnClickListener(this);
        }break;

}

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void check(String element){


        if (element!=null) {

            show2 = element.split(",");



         //   Toast.makeText(context, "array  "+show2.toString()+" Length "+show2.length, Toast.LENGTH_SHORT).show();
            //Toast.makeText(context, "show "+Arrays.toString(show2), Toast.LENGTH_SHORT).show();
               /* Toast.makeText(context, "show "+show2[0], Toast.LENGTH_LONG).show();
                Toast.makeText(context, "show "+show2[1], Toast.LENGTH_LONG).show();
                Toast.makeText(context, "show "+show2[2], Toast.LENGTH_LONG).show();
                Toast.makeText(context, "show "+show2[3], Toast.LENGTH_LONG).show();
*/

            for (int i = 0; i < show2.length; i++) {



                list_Posts.add(show2[i]);


                if (show2[i].trim().equals(cb1.getText().toString().trim())){
                    cb1.setChecked(true);
                }

                if (show2[i].trim().equals(cb2.getText().toString().trim())){
                    cb2.setChecked(true);
                }

                if (show2[i].trim().equals(cb3.getText().toString().trim())){
                    cb3.setChecked(true);
                }
                if (show2[i].trim().equals(cb4.getText().toString().trim())){
                    cb4.setChecked(true);
                }
                if (show2[i].trim().equals(cb5.getText().toString().trim())){
                    cb5.setChecked(true);
                }
                if (show2[i].trim().equals(cb6.getText().toString().trim())){
                    cb6.setChecked(true);
                }
                if (show2[i].trim().equals(cb7.getText().toString().trim())){
                    cb7.setChecked(true);
                }
                if (show2[i].trim().equals(cb8.getText().toString().trim())){
                    cb8.setChecked(true);
                }

            }


            for (int i=0;i<list_Posts.size();i++) {


                if (list_Posts.get(i).equals(cb9.getText().toString().trim())) {
                    cb1.setChecked(true);
                }

                if (list_Posts.get(i).equals(cb10.getText().toString().trim())) {
                    cb2.setChecked(true);
                }

            }

            txtDisplay.setText(show1[0]);
        }

    }

    //to update the add...ReadAdvertisement
    class updateAdds extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {

            pd=new ProgressDialog(Test_create__adv.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pd.setTitle("Advertisement");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJSON();
            return null;
        }

        public String getJSON(){
            StringBuilder sb=new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url=Util.url_ReadAdvertisement;
                String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data=URLEncoder.encode("id","UTF-8")+ "="+ URLEncoder.encode(passId,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();


                //fetching response
                InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
                Scanner scanner=new Scanner(inputStreamReader);
                while(scanner.hasNext()){
                    sb.append(scanner.nextLine());
                }

                return sb.toString();

            }

            catch (Exception e) {
                Toast.makeText(Test_create__adv.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(Object o) {
           // Toast.makeText(Test_create__adv.this, "onPostExecute,response: "+response, Toast.LENGTH_SHORT).show();
            super.onPostExecute(o);
            pd.dismiss();
            try {
                JSONArray jsonArray=new JSONArray(response);
                int count=0;
                //Toast.makeText(Test_create__adv.this, "JSON length:"+jsonArray.length(), Toast.LENGTH_SHORT).show();
                if (jsonArray.length()>0){
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject job = jsonArray.getJSONObject(i);
                            String add_no = job.getString("Advertisement_No");
                            String desc = job.getString("Description");
                            String attachment = job.getString("Attach_file");
                            String start_date = job.getString("Starting_Date");
                            String end_date = job.getString("Ending_Date");
                            String[] arrSD=start_date.split("-");
                            d1=Integer.parseInt(arrSD[2]);
                            m1=Integer.parseInt(arrSD[1]);
                            y1=Integer.parseInt(arrSD[0]);

                            String[] arrED=end_date.split("-");
                            d2=Integer.parseInt(arrED[2]);
                            m2=Integer.parseInt(arrED[1]);
                            y2=Integer.parseInt(arrED[0]);

                            String status = job.getString("Status");

                            advNo.setText(add_no);
                            advDesc.setText(desc);
                            advDateCreated.setText(start_date);
                            advValidityDate.setText(end_date);
                            txtAttachment.setText(attachment);

                            if (status.equals("Open")) {
                                spnrAdvStatus.setSelection(1);
                            } else {
                                spnrAdvStatus.setSelection(2);
                            }


                            ArrayList<String> div=new ArrayList<>();
                            ArrayList<String> post=new ArrayList<>();

                            spnrAdvNoOfDiv.setSelection(7);
                            div.add(job.getString("Division1"));
                            div.add(job.getString("Division2"));
                            div.add(job.getString("Division3"));
                            div.add(job.getString("Division4"));
                            div.add(job.getString("Division5"));
                            div.add(job.getString("Division6"));
                            div.add(job.getString("Division7"));
                            int[] arrPos=new int[7];
                            for (int k=0;k<div.size();k++) {

                                if (!div.get(k).equals("")) {
                                    for (int j=0;j<listDivisions.size();j++) {
                                        if (listDivisions.get(j).equals(div.get(k))){
                                            spnrDiv1.setSelection(j);
                                            arrPos[k]=j;}
                                    }
                                }
                            }
//                            spnrAdvNoOfDiv.setSelection(count+1);
                            spnrDiv1.setSelection(arrPos[0]);
                            spnrDiv2.setSelection(arrPos[1]);
                            spnrDiv3.setSelection(arrPos[2]);
                            spnrDiv4.setSelection(arrPos[3]);
                            spnrDiv5.setSelection(arrPos[4]);
                            spnrDiv6.setSelection(arrPos[5]);
                            spnrDiv7.setSelection(arrPos[6]);

                            post.add(job.getString("Post1"));
                            post.add(job.getString("Post2"));
                            post.add(job.getString("Post3"));
                            post.add(job.getString("Post4"));
                            post.add(job.getString("Post5"));
                            post.add(job.getString("Post6"));
                            post.add(job.getString("Post7"));
                            spnrPost1.setText(post.get(0));
                            spnrPost2.setText(post.get(1));
                            spnrPost3.setText(post.get(2));
                            spnrPost4.setText(post.get(3));
                            spnrPost5.setText(post.get(4));
                            spnrPost6.setText(post.get(5));
                            spnrPost7.setText(post.get(6));

                            count=7;
                            if ((spnrPost1.getText().toString().equals(""))||(spnrPost1.getText().toString().equals("null"))){
                                //ll1.setVisibility(View.GONE);
                                count--;
                            }
                            if ((spnrPost2.getText().toString().equals(""))||(spnrPost2.getText().toString().equals("null"))){
                                //ll2.setVisibility(View.GONE);
                                count--;
                            }
                            if ((spnrPost3.getText().toString().equals(""))||(spnrPost3.getText().toString().equals("null"))){
                                //ll3.setVisibility(View.GONE);
                                count--;
                            }
                            if ((spnrPost4.getText().toString().equals(""))||(spnrPost4.getText().toString().equals("null"))){
                                //ll4.setVisibility(View.GONE);
                                count--;

                            }
                            if ((spnrPost5.getText().toString().equals(""))||(spnrPost5.getText().toString().equals("null"))){
                                //ll5.setVisibility(View.GONE);
                                count--;

                            }
                            if ((spnrPost6.getText().toString().equals(""))||(spnrPost6.getText().toString().equals("null"))){
                                //ll6.setVisibility(View.GONE);
                                count--;

                            }
                            if ((spnrPost7.getText().toString().equals(""))||(spnrPost7.getText().toString().equals("null"))){
                                //ll7.setVisibility(View.GONE);
                                count--;
                            }


//                            for (int j=0;j<div.size();j++){
//
//                                if ((div.get(j).equals(""))||(div.get(j).equals(null))||div.get(j).isEmpty()){
//                                    div.remove(j);
//                                    count--;
//                                }
//                            }
//                            Toast.makeText(context, "div.size()= "+div.size(), Toast.LENGTH_SHORT).show();
//                            spnrAdvNoOfDiv.setSelection(div.size());
                            //Toast.makeText(context, "count "+count, Toast.LENGTH_SHORT).show();
                            spnrAdvNoOfDiv.setSelection(count);




                            //ll7.setVisibility(View.GONE);


                            //Toast.makeText(context, "count: "+count, Toast.LENGTH_SHORT).show();

//                            list_Posts.clear();
//                            showDialog("spnrPost1");
//                            list_Posts.clear();
//                            showDialog("spnrPost2");
//                            list_Posts.clear();
//                            showDialog("spnrPost3");
//                            list_Posts.clear();
//                            showDialog("spnrPost4");
//                            list_Posts.clear();
//                            showDialog("spnrPost5");
//                            list_Posts.clear();
//                            showDialog("spnrPost6");
//                            list_Posts.clear();
//                            showDialog("spnrPost7");


                        }
                }
                else{
                    Toast.makeText(Test_create__adv.this, "Oops! Error occured...", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                Toast.makeText(Test_create__adv.this, "Oops!catch executed..."+e.getMessage(), Toast.LENGTH_SHORT).show();
                showalert("Adv",e.getMessage());
                e.printStackTrace();
            }

        }
    }

    //to save the add on server
    class uploadAdv extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {

            pd=new ProgressDialog(Test_create__adv.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pd.setTitle("Saving Advertisement");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJSON();
            return null;
        }

        public String getJSON(){
            StringBuilder sb=new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url=Util.url_Advertisement_Insertorupdate;
                String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data=URLEncoder.encode("id","UTF-8")+ "="+ URLEncoder.encode(passId,"UTF-8")
                        +"&"+URLEncoder.encode("advt","UTF-8")+ "="+ URLEncoder.encode(storeAdvNo,"UTF-8")
                        +"&"+URLEncoder.encode("desc","UTF-8")+ "="+ URLEncoder.encode(storeAdvDesc,"UTF-8")
                        +"&"+URLEncoder.encode("sdate","UTF-8")+ "="+ URLEncoder.encode(storeAdvDateCreated,"UTF-8")
                        +"&"+URLEncoder.encode("edate","UTF-8")+ "="+ URLEncoder.encode(storeAdvValidityDate,"UTF-8")
                        +"&"+URLEncoder.encode("attach","UTF-8")+ "="+ URLEncoder.encode(storeTxtAttachment,"UTF-8")
                        +"&"+URLEncoder.encode("d1","UTF-8")+ "="+ URLEncoder.encode(storeDiv1,"UTF-8")
                        +"&"+URLEncoder.encode("d2","UTF-8")+ "="+ URLEncoder.encode(storeDiv2,"UTF-8")
                        +"&"+URLEncoder.encode("d3","UTF-8")+ "="+ URLEncoder.encode(storeDiv3,"UTF-8")
                        +"&"+URLEncoder.encode("d4","UTF-8")+ "="+ URLEncoder.encode(storeDiv4,"UTF-8")
                        +"&"+URLEncoder.encode("d5","UTF-8")+ "="+ URLEncoder.encode(storeDiv5,"UTF-8")
                        +"&"+URLEncoder.encode("d6","UTF-8")+ "="+ URLEncoder.encode(storeDiv6,"UTF-8")
                        +"&"+URLEncoder.encode("d7","UTF-8")+ "="+ URLEncoder.encode(storeDiv7,"UTF-8")
                        +"&"+URLEncoder.encode("p1","UTF-8")+ "="+ URLEncoder.encode(storePost1,"UTF-8")
                        +"&"+URLEncoder.encode("p2","UTF-8")+ "="+ URLEncoder.encode(storePost2,"UTF-8")
                        +"&"+URLEncoder.encode("p3","UTF-8")+ "="+ URLEncoder.encode(storePost3,"UTF-8")
                        +"&"+URLEncoder.encode("p4","UTF-8")+ "="+ URLEncoder.encode(storePost4,"UTF-8")
                        +"&"+URLEncoder.encode("p5","UTF-8")+ "="+ URLEncoder.encode(storePost5,"UTF-8")
                        +"&"+URLEncoder.encode("p6","UTF-8")+ "="+ URLEncoder.encode(storePost6,"UTF-8")
                        +"&"+URLEncoder.encode("p7","UTF-8")+ "="+ URLEncoder.encode(storePost7,"UTF-8")
                        +"&"+URLEncoder.encode("status","UTF-8")+ "="+ URLEncoder.encode(storeSpnrAdvStatus,"UTF-8")
                        +"&"+URLEncoder.encode("mode","UTF-8")+ "="+ URLEncoder.encode(mode,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();


                //fetching response
                InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
                Scanner scanner=new Scanner(inputStreamReader);
                while(scanner.hasNext()){
                    sb.append(scanner.nextLine());
                }

                return sb.toString();

            }

            catch (Exception e) {
                Toast.makeText(Test_create__adv.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(Object o) {
            //Toast.makeText(Test_create__adv.this, "***"+storeAdvNo+"---"+storeAdvDesc, Toast.LENGTH_LONG).show();
            //Toast.makeText(Test_create__adv.this, "onPostExecute,response: "+response, Toast.LENGTH_SHORT).show();
            super.onPostExecute(o);
            pd.dismiss();
            try {
                JSONObject job=new JSONObject(response);
               // Toast.makeText(context, "response: "+response, Toast.LENGTH_SHORT).show();
                String res=job.getString("code");
                if (res.trim().equals("success")) {
                    showalert("Advertisements", "Done!");
                }
//                JSONArray jsonArray=new JSONArray(response);
//
//                //Toast.makeText(Test_create__adv.this, "JSON length:"+jsonArray.length(), Toast.LENGTH_SHORT).show();
//                if (jsonArray.length()>0){
//                    for(int i=0;i<jsonArray.length();i++) {
//                        JSONObject job = jsonArray.getJSONObject(i);
//                        String add_no = job.getString("Advertisement_No");
//
//                    }
//
                else if(res.equals("2 success")){
                    showalert("Advertisements", "This Advertisement No already exists!");
                }
                else{
                    showalert("Advertisements","Action Unsuccessful!");                }
            }
            catch (Exception e) {
                Toast.makeText(Test_create__adv.this, "Oops!catch executed..."+e.getMessage(), Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }

    public void showalert(String title,String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(Test_create__adv.this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNeutralButton("OK",null);
        AlertDialog showmsg=builder.create();
        showmsg.show();
    }

    public void checkValidations() {
        //Validations...
        if (!storeAdvNo.isEmpty()) {
            if (!storeAdvDesc.isEmpty()) {
                if (!storeAdvDateCreated.isEmpty()) {

                    if (!storeAdvValidityDate.isEmpty()) {
                        if (!(y2 < y1)) {
                            if (!((m2 < m1) && (y2 == y1))) {
                                if (!((y1 == y2) && (m1 == m2) && (d2 <= d1))) {

                                    if (!storeTxtAttachment.equals("choose file...")) {

                                        if (spnrAdvNoOfDiv.getSelectedItemPosition() == 0) {
                                            showalert("Advertisements", "Select No of Divisions");
                                            //Toast.makeText(this, "Select No of Divisions", Toast.LENGTH_SHORT).show();
                                            //spnrAdvNoOfDiv.requestFocus();
                                        }
                                        else{
                                            checkmoreValidations();
                                        }

                                    }
                                    else{
                                        showalert("Advertisements", "Attach a file");
                                        //txtAttachment.requestFocus();
                                    }

                                } else {
                                    showalert("Advertisements", "End Date should be greater than Start date!");
                                    //advValidityDate.requestFocus();
                                    //advValidityDate.requestFocus();
                                }
                            } else {
                                showalert("Advertisements", "End Date should be greater than Start date!");
                                // advValidityDate.requestFocus();
                            }
                        } else {
                            showalert("Advertisements", "End Date should be greater than Start date!");
                            //advValidityDate.setError("Start Date should be greater than End date!");
                            //advValidityDate.requestFocus();
                        }
                    } else {
                        showalert("Advertisements", "Select End Date!");//advValidityDate.requestFocus();
                    }
                } else {
                    showalert("Advertisements", "Select Start Date!");
                    //advDateCreated.requestFocus();
                }
            } else {
                advDesc.setError("Required field!");
                advDesc.requestFocus();
            }
        } else {
            advNo.setError("Required field!");
            advNo.requestFocus();
        }
    }

    public void checkmoreValidations(){


            if ((ll1.getVisibility() == View.VISIBLE) && (spnrDiv1.getSelectedItemPosition() == 0)) {
                showalert("Advertisements", "Select Division1 & Post1");
               // spnrDiv1.requestFocus();

            } else if ((ll2.getVisibility() == View.VISIBLE) && (spnrDiv2.getSelectedItemPosition() == 0)) {

                showalert("Advertisements", "Select Division2 & Post2");
                //spnrDiv2.requestFocus();
            } else if ((ll3.getVisibility() == View.VISIBLE) && (spnrDiv3.getSelectedItemPosition() == 0)) {
                showalert("Advertisements", "Select Division3 & Post3");
                //spnrDiv3.requestFocus();
            } else if ((ll4.getVisibility() == View.VISIBLE) && (spnrDiv4.getSelectedItemPosition() == 0)) {
                showalert("Advertisements", "Select Division4 & Post4");
                //spnrDiv4.requestFocus();
            } else if ((ll5.getVisibility() == View.VISIBLE) && (spnrDiv5.getSelectedItemPosition() == 0)) {
                showalert("Advertisements", "Select Division5 & Post5");
                //spnrDiv5.requestFocus();
            } else if ((ll6.getVisibility() == View.VISIBLE) && (spnrDiv6.getSelectedItemPosition() == 0)) {
                showalert("Advertisements", "Select Division6 & Post6");
                //spnrDiv6.requestFocus();
            } else if ((ll7.getVisibility() == View.VISIBLE) && (spnrDiv7.getSelectedItemPosition() == 0)) {
                showalert("Advertisements", "Select Division7 & Post7");
                //spnrDiv7.requestFocus();
            }

        else if (spnrAdvStatus.getSelectedItemPosition() == 0) {
            showalert("Advertisements", "Select Status");
            //Toast.makeText(this, "Select Status", Toast.LENGTH_SHORT).show();
            //spnrAdvStatus.requestFocus();
        }
        else{
            storedata();

            

            uploadAdv createObj = new uploadAdv();
            createObj.execute();

            BackTask b=new BackTask();
            b.execute();
            }
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
                InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(inputData), "/index_files/" + txtAttachment.getText().toString().trim());
                Log.d("i11", txtAttachment.getText().toString().trim() + "");
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


    public void storedata(){
        //store all data in variables
        storeId = "";
        storeAdvNo = advNo.getText().toString().trim();
        storeAdvDesc = advDesc.getText().toString().trim();
        storeAdvValidityDate = advValidityDate.getText().toString().trim();
        storeAdvDateCreated = advDateCreated.getText().toString().trim();
        if(!storeAdvDateCreated.isEmpty()){
        String[] arrSD=storeAdvDateCreated.split("-");
        d1=Integer.parseInt(arrSD[2]);
        m1=Integer.parseInt(arrSD[1]);
        y1=Integer.parseInt(arrSD[0]);}

        if (!storeAdvValidityDate.isEmpty()){
        String[] arrED=storeAdvValidityDate.split("-");
        d2=Integer.parseInt(arrED[2]);
        m2=Integer.parseInt(arrED[1]);
        y2=Integer.parseInt(arrED[0]);}

        storeTxtAttachment = txtAttachment.getText().toString().trim();
        storeSpnrAdvStatus = spnrAdvStatus.getSelectedItem().toString().trim();
        storeSpnrAdvNoOfDiv = spnrAdvNoOfDiv.getSelectedItem().toString().trim();
        storeDiv1 = spnrDiv1.getSelectedItem().toString().trim();
        storeDiv2 = spnrDiv2.getSelectedItem().toString().trim();
        storeDiv3 = spnrDiv3.getSelectedItem().toString().trim();
        storeDiv4 = spnrDiv4.getSelectedItem().toString().trim();
        storeDiv5 = spnrDiv5.getSelectedItem().toString().trim();
        storeDiv6 = spnrDiv6.getSelectedItem().toString().trim();
        storeDiv7 = spnrDiv7.getSelectedItem().toString().trim();
        storeDiv8 = spnrDiv8.getSelectedItem().toString().trim();

        storePost1 = spnrPost1.getText().toString().trim();
        storePost2 = spnrPost2.getText().toString().trim();
        storePost3 = spnrPost3.getText().toString().trim();
        storePost4 = spnrPost4.getText().toString().trim();
        storePost5 = spnrPost5.getText().toString().trim();
        storePost6 = spnrPost6.getText().toString().trim();
        storePost7 = spnrPost7.getText().toString().trim();
        storePost8 = spnrPost8.getText().toString().trim();
    }


    public byte[] getBytes(InputStream inputStream) throws IOException{

        ByteArrayOutputStream byteBuffer=new ByteArrayOutputStream();
        int bufferSize=1024;
        byte[] buffer =new byte[bufferSize];
        int len=0;
        while ((len=inputStream.read(buffer))!=-1){
            byteBuffer.write(buffer,0,len);

        }
    return byteBuffer.toByteArray();
    }



}


