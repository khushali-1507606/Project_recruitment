package com.example.win_10.newapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.util.Session;
import com.util.SessionReadCandiDetails;
import com.util.Util;

import org.apache.http.util.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppFillForm extends AppCompatActivity implements View.OnClickListener ,CompoundButton.OnCheckedChangeListener{

    SaveAppData saveAppData;
    ProgressDialog progressDialog;

    Session session;

    SharedPreferences.Editor editor;
    ArrayAdapter<String> adapterCountry,adapterState,adapterDistrict,adapterCategory,adapterMarital,adapterDivision;
    DatePickerDialog.OnDateSetListener dateSetListener;
    ArrayList<String> list_district, id, list_states, districts_id, list_posts, list_divisions, listDivisions,listPosts;
    SharedPreferences sharedPreferences;
    Calendar myCalendar;
    Button btnNext;
    TextView edAdNo;
    EditText edname,edDob,edEmail,edAddress,edFather,edContact,edNat;
    Spinner spCountry,spState,spCategory,spMarital,spDistrict,spPost,spDivision;
    RadioButton rbMale,rbFemale;
    int id2;
    String fileName="myFile",state_id1,advNum, adNo,DivNo,Post,fName,FatName,Email,Gender,DOB,Address,Country,State,District,Contact,Nationality,Category,Marital;
    Context context;
    SessionReadCandiDetails sessionReadObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_fill_form);
        myCalendar=Calendar.getInstance();
        context=this;
        session=new Session(this);
        list_states=new ArrayList<>();

        list_district=new ArrayList<>();


        list_divisions=new ArrayList<>();

        id=new ArrayList<>();
        districts_id=new ArrayList<>();
        saveAppData=new SaveAppData();
        btnNext=(Button)findViewById(R.id.btnNext);
        edname=(EditText)findViewById(R.id.edYName);
        spDivision=(Spinner) findViewById(R.id.spnrDivision);
        spPost=(Spinner) findViewById(R.id.spnrPost);
        edEmail=(EditText)findViewById(R.id.edEmail);

        edDob=(EditText)findViewById(R.id.edDob);
        edAdNo=(TextView)findViewById(R.id.TextViewAdno);
        edAddress=(EditText)findViewById(R.id.edAddress);
        edFather=(EditText)findViewById(R.id.edYname);
        edContact=(EditText)findViewById(R.id.edContact);
        edNat=(EditText)findViewById(R.id.edNationality);

        rbMale=(RadioButton)findViewById(R.id.rbM);
        rbFemale=(RadioButton)findViewById(R.id.rbF);
        rbMale.setOnCheckedChangeListener(this);
        rbFemale.setOnCheckedChangeListener(this);
        spCountry=(Spinner)findViewById(R.id.spCountry);
        spState=(Spinner)findViewById(R.id.spState);
        spCategory=(Spinner)findViewById(R.id.spCategory);
        spMarital=(Spinner)findViewById(R.id.spMarital);
        spDistrict=(Spinner)findViewById(R.id.spDistrict);
        sharedPreferences=getSharedPreferences(fileName,MODE_PRIVATE);
        editor=sharedPreferences.edit();


        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (i!=0){
                    Country=spCountry.getSelectedItem().toString();
                    if (Country.equals("India")){

                        States statesObj=new States();
                        statesObj.execute();
Intent rcv=getIntent();
                        if (rcv.hasExtra("keyAddId")){
                            CheckCandiDetail2 checkCandiDetail=new CheckCandiDetail2();
                            checkCandiDetail.execute();
                        }

                        if(rcv.hasExtra("candiId")){
                            readCandidateDetails2 readCandidateDetails2=new readCandidateDetails2();
                            readCandidateDetails2.execute();

                    }

                    }
                    editor.putString("Country",Country);
                    editor.commit();
                    //Toast.makeText(context, "You selected "+Country, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                //   ((TextView)adapterView.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.district_size));

                if (i!=0){
                    State=spState.getSelectedItem().toString();
                    state_id1=id.get(i);
                    id2=Integer.parseInt(state_id1)-1;

                    //Toast.makeText(context, "You selected "+State+"-id-"+id2, Toast.LENGTH_SHORT).show();
                    Districts districtsObj=new Districts();
                    districtsObj.execute();
                    Intent rcv=getIntent();
                    if (rcv.hasExtra("keyAddId")){
                        CheckCandiDetail3 checkCandiDetail2=new CheckCandiDetail3();
                        checkCandiDetail2.execute();
                    }

                    if(rcv.hasExtra("candiId")){
                        readCandidateDetails3 readCandidateDetails3=new readCandidateDetails3();
                        readCandidateDetails3.execute();

                    }


                    editor.putString("State",State);
                    editor.commit();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
District=spDistrict.getSelectedItem().toString().trim();
                if (i!=0){
                    // Toast.makeText(context, "You selected "+spDistrict.getSelectedItem(), Toast.LENGTH_SHORT).show();
                    editor.putString("District",District);
                    editor.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    Category=spCategory.getSelectedItem().toString();
                    editor.putString("Category",Category);
                    editor.commit();
                  //  Toast.makeText(context, "You selected "+Category, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spMarital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    Marital=spMarital.getSelectedItem().toString();
                    editor.putString("Marital",Marital);
                    editor.commit();
                  //  Toast.makeText(context, "You selected "+Marital, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        adapterCountry=new ArrayAdapter<String>(AppFillForm.this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.country));
        spCountry.setAdapter(adapterCountry);

        adapterCategory=new ArrayAdapter<String>(AppFillForm.this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.category));
        spCategory.setAdapter(adapterCategory);

        adapterMarital=new ArrayAdapter<String>(AppFillForm.this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.marital_status));
        spMarital.setAdapter(adapterMarital);


        districtSelect();
        btnNext.setOnClickListener(this);
        edDob.setOnClickListener(this);
        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                myCalendar.set(Calendar.YEAR,i);
                myCalendar.set(Calendar.MONTH,i1);
                myCalendar.set(Calendar.DAY_OF_MONTH,i2);
                updateLabel();//to set the date to a specified format


            }
        };


        sessionReadObj=new SessionReadCandiDetails(this);


        //*********STATIC divisions
    listDivisions=new ArrayList<>();
/*//        listDivisions.add("Select Division Applied For");
//        listDivisions.add("ADVANCED GEOSPATIAL APPLICATION GROUP (AGAG)");
//        listDivisions.add("LAND RESOURCE UTILITIES & INFRASTRUCTURE");
//        listDivisions.add("GEOLOGY&WATER RESOURCES");
//        listDivisions.add("GEOINFORMATICS");
//        listDivisions.add("AGRO-ECOSYSTEM AND CROP MODELLING");
//        listDivisions.add("FORESTRY AND LAND USE");
//        listDivisions.add("NATURAL RESOURCES & ENVIRONMENT");
//*/



       // ArrayAdapter<String> DivAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,listDivisions);

        //spDivision.setAdapter(DivAdapter);

        spDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextSize(15);
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                DivNo=spDivision.getSelectedItem().toString().trim();

                if (i!=0){
                getAllPosts getPostObj=new getAllPosts();
                getPostObj.execute();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listPosts=new ArrayList<>();
//        listPosts.add("Select Post");
//        listPosts.add("Programmer");
//        listPosts.add("Assistant Programmer");
//        listPosts.add("Web Application Development");
//        listPosts.add("IT Lab Attendant");
//        listPosts.add("Field_cum_Lab_Asstt");
//        listPosts.add("Research Associate");
//        listPosts.add("Research Fellow");
//        listPosts.add("Junior Research Fellow");
//        listPosts.add("Interns Student");
//        listPosts.add("GIS_CAD Trainee");
//
//        ArrayAdapter<String> PostAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,listPosts);
//        spPost.setAdapter(PostAdapter);
        //*******************

        spPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextSize(15);
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                Post=spPost.getSelectedItem().toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AppFillForm.this, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Intent rcv=getIntent();
        if (rcv.hasExtra("keyAddId")){
            String advId=rcv.getStringExtra("keyAddId");
            advNum=rcv.getStringExtra("keyAddNum");
            edAdNo.setText(advNum);
            Divisions divisions=new Divisions();
            divisions.execute();
            CheckCandiDetail1 checkCandiDetail1=new CheckCandiDetail1();
            checkCandiDetail1.execute();
        }

        if(rcv.hasExtra("candiId")){

            candiId=rcv.getStringExtra("candiId");
            advNum=rcv.getStringExtra("advt");
            edAdNo.setText(advNum);
            Divisions divisions=new Divisions();
            divisions.execute();

            //Toast.makeText(AppFillForm.this, "Id="+candiId, Toast.LENGTH_LONG).show();
            readCandidateDetails candiDetailsObj=new readCandidateDetails();
            candiDetailsObj.execute();}
    }

    String candiId="";

    @Override
    protected void onStart() {
        super.onStart();



    }

    public void districtSelect(){


    }

    @Override
    public void onClick(View view) {

        Intent rcv=getIntent();
        if(rcv.hasExtra("candiId")){
            adNo = edAdNo.getText().toString().trim();

            fName = edname.getText().toString().trim();
            FatName = edFather.getText().toString().trim();
            Email = edEmail.getText().toString().trim();
            DOB = edDob.getText().toString().trim();
            Address = edAddress.getText().toString().trim();
            Contact = edContact.getText().toString().trim();

            Nationality = edNat.getText().toString().trim();
            //Toast.makeText(context, "Data" + adNo + "-" + DivNo + "-" + Post + "-" + fName + "-" + FatName + "-" + Email + "-" + Country, Toast.LENGTH_SHORT).show();
            //saveAppData.adno=adNo;

            editor.putString("adNo", adNo);
            editor.putString("DivNo", DivNo);
            editor.putString("Post", Post);
            editor.putString("fname", fName);
            editor.putString("FatName", FatName);
            editor.putString("Email", Email);
            editor.putString("Dob", DOB);
            editor.putString("Address", Address);
            editor.putString("Contact", Contact);
            editor.putString("Nationality", Nationality);

            editor.commit();

            candiId=rcv.getStringExtra("candiId");
            Intent intent=new Intent(AppFillForm.this,EduQual.class);
            intent.putExtra("keyCandiId",candiId);
            startActivity(intent);
        }

        else {
            adNo = edAdNo.getText().toString().trim();

            fName = edname.getText().toString().trim();
            FatName = edFather.getText().toString().trim();
            Email = edEmail.getText().toString().trim();
            DOB = edDob.getText().toString().trim();
            Address = edAddress.getText().toString().trim();
            Contact = edContact.getText().toString().trim();

            Nationality = edNat.getText().toString().trim();
            //Toast.makeText(context, "Data" + adNo + "-" + DivNo + "-" + Post + "-" + fName + "-" + FatName + "-" + Email + "-" + Country, Toast.LENGTH_SHORT).show();
            //saveAppData.adno=adNo;

            editor.putString("adNo", adNo);
            editor.putString("DivNo", DivNo);
            editor.putString("Post", Post);
            editor.putString("fname", fName);
            editor.putString("FatName", FatName);
            editor.putString("Email", Email);
            editor.putString("Dob", DOB);
            editor.putString("Address", Address);
            editor.putString("Contact", Contact);
            editor.putString("Nationality", Nationality);

            editor.commit();

            if (TextUtils.isEmpty(fName)) {
                edname.setError("This field is required");
                edname.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edname, InputMethodManager.SHOW_IMPLICIT);
                return;
            }

            if (TextUtils.isEmpty(FatName)) {
                edFather.setError("This field is required");
                edFather.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edFather, InputMethodManager.SHOW_IMPLICIT);

                return;
            }

            if (TextUtils.isEmpty(Nationality)) {
                edNat.setError("This field is required");
                edNat.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edNat, InputMethodManager.SHOW_IMPLICIT);

                return;
            }
            if (TextUtils.isEmpty(Email)) {
                edEmail.setError("This field is required");
                edEmail.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edEmail, InputMethodManager.SHOW_IMPLICIT);

                return;
            }
            if (TextUtils.isEmpty(DOB)) {

                Toast.makeText(this, "Please fill this field", Toast.LENGTH_SHORT).show();
                edDob.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edDob, InputMethodManager.SHOW_IMPLICIT);

            }
            if (TextUtils.isEmpty(Address)) {
                edAddress.setError("This field is required");
                edAddress.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edAddress, InputMethodManager.SHOW_IMPLICIT);
                return;

            }

            if (TextUtils.isEmpty(Contact)) {
                edContact.setError("This field is required");
                edContact.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edContact, InputMethodManager.SHOW_IMPLICIT);
                return;
            }
            if (!emailValidator(Email)) {

                edEmail.setError("Enter a valid email address");
                edEmail.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edEmail, InputMethodManager.SHOW_IMPLICIT);
                return;
            }

            if (spMarital.getSelectedItemPosition() == 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Please fill your marital status");

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
            if (spCountry.getSelectedItemPosition() == 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Please select your country");
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
            if (spState.getSelectedItemPosition() == 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Please select your state");
                AlertDialog dialog = builder.create();
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                dialog.show();

                return;
            }
            if (spCategory.getSelectedItemPosition() == 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Please select your category");
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

            if (spPost.getSelectedItemPosition()==0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Please select post");
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
            if (spDivision.getSelectedItemPosition()==0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please select division");
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

            if (spDistrict.getSelectedItemPosition()==0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Please select district");
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


            if (edContact.getText().toString().trim().length()!=10){


                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Invalid mobile no.");
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


            startActivity(new Intent(AppFillForm.this, EduQual.class));

        }

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edDob.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean emailValidator(String email){
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9]+)*@[_A-Za-z0-9]+(\\.[_A-Za-z0-9]+)*(\\.[_A-Za-z]{2,})$";

        pattern=Pattern.compile(EMAIL_PATTERN);
        matcher=pattern.matcher(email);
        return matcher.matches();



    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (rbMale.isChecked()){
            Gender="Male";
            editor.putString("gender",Gender);
            editor.commit();
           // Toast.makeText(context, " "+Gender, Toast.LENGTH_SHORT).show();
        }
        else if (rbFemale.isChecked()){
            Gender="Female";
            editor.putString("gender",Gender);
            editor.commit();
            //Toast.makeText(context, " "+Gender, Toast.LENGTH_SHORT).show();



        }

    }

    class getAllPosts extends AsyncTask {

        String response;
        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJSON();
            return null;
        }

        public  String getJSON(){
            StringBuilder sb=new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url=Util.url_GetAllPosts;
                String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data=URLEncoder.encode("advt","UTF-8")+ "="+ URLEncoder.encode(advNum,"UTF-8")+"&"+URLEncoder.encode("division","UTF-8")+ "="+ URLEncoder.encode(spDivision.getSelectedItem().toString().trim(),"UTF-8");
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
                //Toast.makeText(AppFillForm.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(Object o) {
            //Toast.makeText(CandidateDetails.this, "onPostExecute,response: "+response, Toast.LENGTH_SHORT).show();
            super.onPostExecute(o);
            try {
                JSONArray jsonArray=new JSONArray(response);
                //Toast.makeText(CandidateDetails.this, "JSON length:"+jsonArray.length(), Toast.LENGTH_SHORT).show();
                if (jsonArray.length()>0){

                    list_posts=new ArrayList<>();
                    list_posts.add("Select Post");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject job=jsonArray.getJSONObject(i);

                        String post=job.getString("DataItem");
                        if (!post.equals("null")) {
                            list_posts.add(post);
                        }
                    }
                    ArrayAdapter<String> PostAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,list_posts);
                    spPost.setAdapter(PostAdapter);
                }
                else{
                    Toast.makeText(AppFillForm.this, "Oops! Error occured...", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                Toast.makeText(AppFillForm.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }


    class readCandidateDetails extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {
          //  Toast.makeText(AppFillForm.this, "Id="+candiId, Toast.LENGTH_LONG).show();

            pd=new ProgressDialog(AppFillForm.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            // pd.setTitle("Fetching data");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Please wait...");
            pd.show();
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJSON();
            return null;
        }
        public  String getJSON(){
            StringBuilder sb=new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url=Util.url_ReadCandidateDetails;
                String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data=URLEncoder.encode("candidate_id","UTF-8")+ "="+ URLEncoder.encode(candiId,"UTF-8");
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
                // Toast.makeText(AppFillForm.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Toast.makeText(AppFillForm.this, "response: "+response, Toast.LENGTH_SHORT).show();
            pd.dismiss();
            try {
                JSONArray jsonArray=new JSONArray(response);
                if (jsonArray.length()>0){

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject job=jsonArray.getJSONObject(i);

//                        String advNo=job.getString("AdvertisementNo");
//                        edAdNo.setText(advNo);

                        String div=job.getString("DivisionNameAppliedIn");
                        for(int k=0;k<list_divisions.size();k++){
                            if(list_divisions.get(k).trim().equals(div))
                                spDivision.setSelection(k);
                        }



                        String fullname=job.getString("FullName");
                        edname.setText(fullname);

                        String dadName=job.getString("FatherName");
                        edFather.setText(dadName);

                        String email=job.getString("EmailId");
                        edEmail.setText(email);

                        String gender=job.getString("Gender");
                        if (gender.equals("M")){
                            rbMale.setChecked(true);
                        }
                        else{
                            rbFemale.setChecked(true);
                        }

                        String dob=job.getString("DateofBirth");
                        edDob.setText(dob);

                        String address=job.getString("Address");
                        edAddress.setText(address);

                        /*String country=job.getString("Country");
                        for(int k=0;k<getResources().getStringArray(R.array.country).length;k++){
                            if(getResources().getStringArray(R.array.country)[k].equals(country))
                                spCountry.setSelection(k);

                            if (country.equals("India")){

                                States statesObj=new States();
                                statesObj.execute();
                            }
                            editor.putString("Country",country);
                            editor.commit();
                        }*/
                        String country = job.getString("Country");
                        spCountry.setSelection(1);



                        String mobNum=job.getString("ContactNo");
                        edContact.setText(mobNum);

                        String nationality=job.getString("Nationality");
                        edNat.setText(nationality);

                        String category=job.getString("Category");
                        for(int k=0;k<getResources().getStringArray(R.array.category).length;k++){
                            if(getResources().getStringArray(R.array.category)[k].equals(category))
                                spCategory.setSelection(k);
                        }

                        String maritalStatus=job.getString("MartialStatus");
                        for(int k=0;k<getResources().getStringArray(R.array.marital_status).length;k++){
                            if(getResources().getStringArray(R.array.marital_status)[k].equals(maritalStatus))
                                spMarital.setSelection(k);
                        }

                        //Act2
                        String degree1=job.getString("CertificateR1");
                        sessionReadObj.setDeg1(degree1);
                        String degree2=job.getString("CertificateR2");
                        sessionReadObj.setDeg2(degree2);
                        String degree3=job.getString("CertificateR3");
                        sessionReadObj.setDeg3(degree3);
                        String degree4=job.getString("CertificateR4");
                        sessionReadObj.setDeg4(degree4);
                        String degree5=job.getString("CertificateR5");
                        sessionReadObj.setDeg5(degree5);

                        String colUnivName1=job.getString("CollegeUnivR1");
                        sessionReadObj.setCollegeUni1(colUnivName1);
                        String colUnivName2=job.getString("CollegeUnivR2");
                        sessionReadObj.setCollegeUni2(colUnivName2);
                        String colUnivName3=job.getString("CollegeUnivR3");
                        sessionReadObj.setCollegeUni3(colUnivName3);
                        String colUnivName4=job.getString("CollegeUnivR4");
                        sessionReadObj.setCollegeUni4(colUnivName4);
                        String colUnivName5=job.getString("CollegeUnivR5");
                        sessionReadObj.setCollegeUni5(colUnivName5);

                        String YoP1=job.getString("YearofPassingR1");
                        sessionReadObj.setYoP1(YoP1);
                        String YoP2=job.getString("YearofPassingR2");
                        sessionReadObj.setYoP2(YoP2);
                        String YoP3=job.getString("YearofPassingR3");
                        sessionReadObj.setYoP3(YoP3);
                        String YoP4=job.getString("YearofPassingR4");
                        sessionReadObj.setYoP4(YoP4);
                        String YoP5=job.getString("YearofPassingR5");
                        sessionReadObj.setYoP5(YoP5);

                        String marks1=job.getString("PercentMarksorCGPAR1");
                        sessionReadObj.setMarks1(marks1);
                        String marks2=job.getString("PercentMarksorCGPAR2");
                        sessionReadObj.setMarks2(marks2);
                        String marks3=job.getString("PercentMarksorCGPAR3");
                        sessionReadObj.setMarks3(marks3);
                        String marks4=job.getString("PercentMarksorCGPAR4");
                        sessionReadObj.setMarks4(marks4);
                        String marks5=job.getString("PercentMarksorCGPAR5");
                        sessionReadObj.setMarks5(marks5);

                        String division1=job.getString("DivisionR1");
                        sessionReadObj.setDiv1(division1);
                        String division2=job.getString("DivisionR2");
                        sessionReadObj.setDiv2(division2);
                        String division3=job.getString("DivisionR3");
                        sessionReadObj.setDiv3(division3);
                        String division4=job.getString("DivisionR4");
                        sessionReadObj.setDiv4(division4);
                        String division5=job.getString("DivisionR5");
                        sessionReadObj.setDiv5(division5);

                        //Act3

                        sessionReadObj.setOrg1(job.getString("OrganizationR1"));
                        sessionReadObj.setOrg2(job.getString("OrganizationR2"));
                        sessionReadObj.setOrg3(job.getString("OrganizationR3"));
                        sessionReadObj.setOrg4(job.getString("OrganizationR4"));
                        sessionReadObj.setOrg5(job.getString("OrganizationR5"));

                        sessionReadObj.setPosHeld1(job.getString("PositionHeldR1"));
                        sessionReadObj.setPosHeld2(job.getString("PositionHeldR2"));
                        sessionReadObj.setPosHeld3(job.getString("PositionHeldR3"));
                        sessionReadObj.setPosHeld4(job.getString("PositionHeldR4"));
                        sessionReadObj.setPosHeld5(job.getString("PositionHeldR5"));

                        sessionReadObj.setDoJ1(job.getString("DataofJoiningR1"));
                        sessionReadObj.setDoJ2(job.getString("DataofJoiningR2"));
                        sessionReadObj.setDoJ3(job.getString("DataofJoiningR3"));
                        sessionReadObj.setDoJ4(job.getString("DataofJoiningR4"));
                        sessionReadObj.setDoJ5(job.getString("DataofJoiningR5"));

                        sessionReadObj.setDoL1(job.getString("DateofLeavingR1"));
                        sessionReadObj.setDoL2(job.getString("DateofLeavingR2"));
                        sessionReadObj.setDoL3(job.getString("DateofLeavingR3"));
                        sessionReadObj.setDoL4(job.getString("DateofLeavingR4"));
                        sessionReadObj.setDoL5(job.getString("DateofLeavingR5"));

                        sessionReadObj.setSalaryDrawn1(job.getString("SalaryDrawnR1"));
                        sessionReadObj.setSalaryDrawn2(job.getString("SalaryDrawnR2"));
                        sessionReadObj.setSalaryDrawn3(job.getString("SalaryDrawnR3"));
                        sessionReadObj.setSalaryDrawn4(job.getString("SalaryDrawnR4"));
                        sessionReadObj.setSalaryDrawn5(job.getString("SalaryDrawnR5"));

                        //Act4
                        sessionReadObj.setOpinion1(job.getString("Opinion"));
                        sessionReadObj.setRef1(job.getString("ReferenceOneDetails"));
                        sessionReadObj.setRef2(job.getString("ReferenceSecondDetails"));
                        sessionReadObj.setPlace(job.getString("PlaceofDeclaration"));
                        sessionReadObj.setDate(job.getString("DateofDeclaration"));


                    }

                }
                else{
                    Toast.makeText(AppFillForm.this, "Oops! Error occured...", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                //Toast.makeText(AppFillForm.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }


    class States extends AsyncTask{
        String response;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJson3();
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try
            {
                list_states.add("Select states");
                JSONArray jarr = new JSONArray(response);

                if(jarr.length()>0) {
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject job = jarr.getJSONObject(i);
                        String state = job.getString("StateName");
                        String state_id=job.getString("StateId");

                        //getting values
                        Log.d("State", state);
                        list_states.add(state);//adding in arraylist
                        id.add(state_id);
                    }
                    adapterState=new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,list_states);
                    spState.setAdapter(adapterState);

                }

                else {


                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
                }


            }

            catch(Exception e)
            {

                e.printStackTrace();
                Toast.makeText(context, "Catch block"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }



        public String getJson3(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.states;
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
                data= URLEncoder.encode("country_id","UTF-8") + "=" +
                        URLEncoder.encode("1","UTF-8");
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
    class Districts extends AsyncTask{
        String response;


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJson3();
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            list_district.clear();
            try
            {
                list_district.add("Select district");
                JSONArray jarr = new JSONArray(response);

                if(jarr.length()>0) {
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject job = jarr.getJSONObject(i);
                        String district2 = job.getString("DistrictName");
                        String district_id=job.getString("DistrictId");
                        //getting values
                        Log.d("District", district2);

                        list_district.add(district2);//adding in arraylist
                        districts_id.add(district_id);

                    }
                    adapterDistrict=new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,list_district);
                    spDistrict.setAdapter(adapterDistrict);

                }

                else {


                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
                }


            }

            catch(Exception e)
            {

                e.printStackTrace();
                Toast.makeText(context, "Catch block"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }



        public String getJson3(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.district;
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
                data= URLEncoder.encode("state_id","UTF-8") + "=" +
                        URLEncoder.encode(String.valueOf(id2),"UTF-8");
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
    class Divisions extends AsyncTask
    {
        String response;

        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Loading Data..please wait");
            progressDialog.show();
            Log.d("pre","prexecute");
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
            try
            {

                list_divisions.clear();
               // Toast.makeText(context, "response "+response, Toast.LENGTH_SHORT).show();
                JSONArray jarr = new JSONArray(response);
                    list_divisions.add("Please select Division Applied for");
                if(jarr.length()>0) {
                    for (int i = 0; i < jarr.length()-1; i++) {
                        JSONObject job = jarr.getJSONObject(i);
                        String district2 = job.getString("Division");
                        //getting values
                        Log.d("Division", district2);

                        list_divisions.add(district2);//adding in arraylist

                    }
                    adapterDivision=new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,list_divisions);
                    spDivision.setAdapter(adapterDivision);

                }

                else {


                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
                }


            }

            catch(Exception e)
            {

                e.printStackTrace();
                Toast.makeText(context, "Catch block"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }



        public String getJson3(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.url_GetAllDivisions;
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
                data= URLEncoder.encode("advt_no","UTF-8") + "=" +
                        URLEncoder.encode(edAdNo.getText().toString().trim(),"UTF-8");
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
    class CheckCandiDetail1 extends AsyncTask
    {
        String response;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJson3();
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try
            {

                JSONArray jarr = new JSONArray(response);

                if (jarr.length()==0){

                   // Toast.makeText(context, "No previous form is filled by the user", Toast.LENGTH_SHORT).show();
                    edname.setText(session.getName());
                    edEmail.setText(session.getEmail());
                    edContact.setText(session.getPhone());

                    sessionReadObj.setDeg1("");
                    sessionReadObj.setDeg2("");
                    sessionReadObj.setDeg3("");
                    sessionReadObj.setDeg4("");
                    sessionReadObj.setDeg5("");

                    sessionReadObj.setCollegeUni1("");
                    sessionReadObj.setCollegeUni2("");
                    sessionReadObj.setCollegeUni3("");
                    sessionReadObj.setCollegeUni4("");
                    sessionReadObj.setCollegeUni5("");

                    sessionReadObj.setYoP1("");
                    sessionReadObj.setYoP2("");
                    sessionReadObj.setYoP3("");
                    sessionReadObj.setYoP4("");
                    sessionReadObj.setYoP5("");

                    sessionReadObj.setMarks1("");
                    sessionReadObj.setMarks2("");
                    sessionReadObj.setMarks3("");
                    sessionReadObj.setMarks4("");
                    sessionReadObj.setMarks5("");



                    sessionReadObj.setDiv1("");
                    sessionReadObj.setDiv2("");
                    sessionReadObj.setDiv3("");
                    sessionReadObj.setDiv4("");
                    sessionReadObj.setDiv5("");

                    //Act3

                    sessionReadObj.setOrg1("");
                    sessionReadObj.setOrg2("");
                    sessionReadObj.setOrg3("");
                    sessionReadObj.setOrg4("");
                    sessionReadObj.setOrg5("");

                    sessionReadObj.setPosHeld1("");
                    sessionReadObj.setPosHeld2("");
                    sessionReadObj.setPosHeld3("");
                    sessionReadObj.setPosHeld4("");
                    sessionReadObj.setPosHeld5("");

                    sessionReadObj.setDoJ1("");
                    sessionReadObj.setDoJ2("");
                    sessionReadObj.setDoJ3("");
                    sessionReadObj.setDoJ4("");
                    sessionReadObj.setDoJ5("");

                    sessionReadObj.setDoL1("");
                    sessionReadObj.setDoL2("");
                    sessionReadObj.setDoL3("");
                    sessionReadObj.setDoL4("");
                    sessionReadObj.setDoL5("");

                    sessionReadObj.setSalaryDrawn1("");
                    sessionReadObj.setSalaryDrawn2("");
                    sessionReadObj.setSalaryDrawn3("");
                    sessionReadObj.setSalaryDrawn4("");
                    sessionReadObj.setSalaryDrawn5("");


                }

                if(jarr.length()>0) {
                        JSONObject job = jarr.getJSONObject(0);
                        String name = job.getString("FullName");

                        edname.setText(name);
                        String fname = job.getString("FatherName");
                        edFather.setText(fname);
                        String emailId = job.getString("EmailId");

                        edEmail.setText(emailId);

                        String gender = job.getString("Gender");
                        if (gender.equals("M")){

                            rbMale.setChecked(true);

                        }
                        else {

                            rbFemale.setChecked(true);
                        }


                        String dob = job.getString("DateofBirth");
                        edDob.setText(dob);

                        String address = job.getString("Address");
                        edAddress.setText(address);

                        String country = job.getString("Country");
                        spCountry.setSelection(1);


                        String mobile = job.getString("ContactNo");
                    edContact.setText(mobile);

                        String nationality = job.getString("Nationality");
                        edNat.setText(nationality);

                        String category = job.getString("Category");

                    for(int k=0;k<getResources().getStringArray(R.array.category).length;k++){
                        if(getResources().getStringArray(R.array.category)[k].equals(category))
                            spCategory.setSelection(k);
                    }

                    String maritalStatus=job.getString("MartialStatus");
                    for(int k=0;k<getResources().getStringArray(R.array.marital_status).length;k++){
                        if(getResources().getStringArray(R.array.marital_status)[k].equals(maritalStatus))
                            spMarital.setSelection(k);
                    }



                    //Act2
                    String degree1=job.getString("CertificateR1");
                    sessionReadObj.setDeg1(degree1);
                    String degree2=job.getString("CertificateR2");
                    sessionReadObj.setDeg2(degree2);
                    String degree3=job.getString("CertificateR3");
                    sessionReadObj.setDeg3(degree3);
                    String degree4=job.getString("CertificateR4");
                    sessionReadObj.setDeg4(degree4);
                    String degree5=job.getString("CertificateR5");
                    sessionReadObj.setDeg5(degree5);

                    String colUnivName1=job.getString("CollegeUnivR1");
                    sessionReadObj.setCollegeUni1(colUnivName1);
                    String colUnivName2=job.getString("CollegeUnivR2");
                    sessionReadObj.setCollegeUni2(colUnivName2);
                    String colUnivName3=job.getString("CollegeUnivR3");
                    sessionReadObj.setCollegeUni3(colUnivName3);
                    String colUnivName4=job.getString("CollegeUnivR4");
                    sessionReadObj.setCollegeUni4(colUnivName4);
                    String colUnivName5=job.getString("CollegeUnivR5");
                    sessionReadObj.setCollegeUni5(colUnivName5);

                    String YoP1=job.getString("YearofPassingR1");
                    sessionReadObj.setYoP1(YoP1);
                    String YoP2=job.getString("YearofPassingR2");
                    sessionReadObj.setYoP2(YoP2);
                    String YoP3=job.getString("YearofPassingR3");
                    sessionReadObj.setYoP3(YoP3);
                    String YoP4=job.getString("YearofPassingR4");
                    sessionReadObj.setYoP4(YoP4);
                    String YoP5=job.getString("YearofPassingR5");
                    sessionReadObj.setYoP5(YoP5);

                    String marks1=job.getString("PercentMarksorCGPAR1");
                        sessionReadObj.setMarks1(marks1);



                        String marks2=job.getString("PercentMarksorCGPAR2");
                        sessionReadObj.setMarks2(marks2);


                        String marks3=job.getString("PercentMarksorCGPAR3");

                        sessionReadObj.setMarks3(marks3);


                    String marks4=job.getString("PercentMarksorCGPAR4");

                        sessionReadObj.setMarks4(marks4);


                    String marks5=job.getString("PercentMarksorCGPAR5");

                        sessionReadObj.setMarks5(marks5);

                    String division1=job.getString("DivisionR1");
                    sessionReadObj.setDiv1(division1);
                    String division2=job.getString("DivisionR2");
                    sessionReadObj.setDiv2(division2);
                    String division3=job.getString("DivisionR3");
                    sessionReadObj.setDiv3(division3);
                    String division4=job.getString("DivisionR4");
                    sessionReadObj.setDiv4(division4);
                    String division5=job.getString("DivisionR5");
                    sessionReadObj.setDiv5(division5);

                    //Act3

                    sessionReadObj.setOrg1(job.getString("OrganizationR1"));
                    sessionReadObj.setOrg2(job.getString("OrganizationR2"));
                    sessionReadObj.setOrg3(job.getString("OrganizationR3"));
                    sessionReadObj.setOrg4(job.getString("OrganizationR4"));
                    sessionReadObj.setOrg5(job.getString("OrganizationR5"));

                    sessionReadObj.setPosHeld1(job.getString("PositionHeldR1"));
                    sessionReadObj.setPosHeld2(job.getString("PositionHeldR2"));
                    sessionReadObj.setPosHeld3(job.getString("PositionHeldR3"));
                    sessionReadObj.setPosHeld4(job.getString("PositionHeldR4"));
                    sessionReadObj.setPosHeld5(job.getString("PositionHeldR5"));

                    sessionReadObj.setDoJ1(job.getString("DataofJoiningR1"));
                    sessionReadObj.setDoJ2(job.getString("DataofJoiningR2"));
                    sessionReadObj.setDoJ3(job.getString("DataofJoiningR3"));
                    sessionReadObj.setDoJ4(job.getString("DataofJoiningR4"));
                    sessionReadObj.setDoJ5(job.getString("DataofJoiningR5"));

                    sessionReadObj.setDoL1(job.getString("DateofLeavingR1"));
                    sessionReadObj.setDoL2(job.getString("DateofLeavingR2"));
                    sessionReadObj.setDoL3(job.getString("DateofLeavingR3"));
                    sessionReadObj.setDoL4(job.getString("DateofLeavingR4"));
                    sessionReadObj.setDoL5(job.getString("DateofLeavingR5"));

                    sessionReadObj.setSalaryDrawn1(job.getString("SalaryDrawnR1"));
                    sessionReadObj.setSalaryDrawn2(job.getString("SalaryDrawnR2"));
                    sessionReadObj.setSalaryDrawn3(job.getString("SalaryDrawnR3"));
                    sessionReadObj.setSalaryDrawn4(job.getString("SalaryDrawnR4"));
                    sessionReadObj.setSalaryDrawn5(job.getString("SalaryDrawnR5"));

/*

                    String certficateR1 = job.getString("CertificateR1");
                    String clgR1 = job.getString("CollegeUnivR1");
                    String yop = job.getString("YearofPassingR1");
                    String cgpa1 = job.getString("PercentMarksorCGPAR1");
                    String divisionR1 = job.getString("DivisionR1");

                    String certficateR2 = job.getString("CertificateR2");
                    String clgR2 = job.getString("CollegeUnivR2");
                    String yop2 = job.getString("YearofPassingR2");
                    String cgpa2 = job.getString("PercentMarksorCGPAR2");
                    String divisionR2 = job.getString("DivisionR2");

                    String certficateR3 = job.getString("CertificateR3");
                    String clgR3 = job.getString("CollegeUnivR3");
                    String yop3 = job.getString("YearofPassingR3");
                    String cgpa3 = job.getString("PercentMarksorCGPAR3");
                    String divisionR3 = job.getString("DivisionR3");



                    String certficateR4 = job.getString("CertificateR4");
                    String clgR4 = job.getString("CollegeUnivR4");
                    String yop4 = job.getString("YearofPassingR4");
                    String cgpa4 = job.getString("PercentMarksorCGPAR4");
                    String divisionR4 = job.getString("DivisionR4");


                    String certficateR5 = job.getString("CertificateR5");
                    String clgR5 = job.getString("CollegeUnivR5");
                    String yop5= job.getString("YearofPassingR5");
                    String cgpa5 = job.getString("PercentMarksorCGPAR5");
                    String divisionR5 = job.getString("DivisionR5");


                    //getting values
*/





                }

                else {


                  //  Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
                }


            }

            catch(Exception e)
            {

                e.printStackTrace();
                Toast.makeText(context, "Catch block"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }



        public String getJson3(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.CheckCandidateDetail;
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
                data= URLEncoder.encode("advt","UTF-8") + "=" +
                        URLEncoder.encode(edAdNo.getText().toString().trim(),"UTF-8") + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(session.getEmail(), "UTF-8");
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
    class CheckCandiDetail2 extends AsyncTask
    {
        String response;

        @Override
        protected void onPreExecute() {
           // Toast.makeText(context, "while setting state", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJson3();
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try
            {

                JSONArray jarr = new JSONArray(response);

                if (jarr.length()==0){

                    Toast.makeText(context, "No previous form is filled by the user", Toast.LENGTH_SHORT).show();

                }

                if(jarr.length()>0) {
                    JSONObject job = jarr.getJSONObject(0);



                    String state = job.getString("States");
                  //  Toast.makeText(context, "Before state matched "+list_states.size(), Toast.LENGTH_SHORT).show();

                    for(int k=0;k<list_states.size();k++){
                        //Toast.makeText(AppFillForm.this, "st "+list_states, Toast.LENGTH_SHORT).show();
                        if(list_states.get(k).trim().equals(state.trim())){

                            spState.setSelection(k);}
                    }







                }

                else {


                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
                }


            }

            catch(Exception e)
            {

                e.printStackTrace();
                Toast.makeText(context, "Catch block"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }



        public String getJson3(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.CheckCandidateDetail;
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
                data= URLEncoder.encode("advt","UTF-8") + "=" +
                        URLEncoder.encode(edAdNo.getText().toString().trim(),"UTF-8") + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(edEmail.getText().toString().trim(), "UTF-8");
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
    class CheckCandiDetail3 extends AsyncTask
    {
        String response;

        @Override
        protected void onPreExecute() {
          //  Toast.makeText(context, "while setting district", Toast.LENGTH_SHORT).show();
            //Log.d("pre","prexecute");
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJson3();
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try
            {

                JSONArray jarr = new JSONArray(response);

                if (jarr.length()==0){

                    Toast.makeText(context, "No previous form is filled by the user", Toast.LENGTH_SHORT).show();

                }

                if(jarr.length()>0) {
                    JSONObject job = jarr.getJSONObject(0);



                    String district = job.getString("Districts");
                    for(int k=0;k<list_district.size();k++){
                        if(list_district.get(k).trim().equals(district.trim()))
                            spDistrict.setSelection(k);
                    }






                }

                else {


                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
                }


            }

            catch(Exception e)
            {

                e.printStackTrace();
                Toast.makeText(context, "Catch block"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }



        public String getJson3(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.CheckCandidateDetail;
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
                data= URLEncoder.encode("advt","UTF-8") + "=" +
                        URLEncoder.encode(edAdNo.getText().toString().trim(),"UTF-8") + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(edEmail.getText().toString().trim(), "UTF-8");
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

    class readCandidateDetails2 extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {
           // Toast.makeText(AppFillForm.this, "Id="+candiId, Toast.LENGTH_LONG).show();


        }
        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJSON();
            return null;
        }
        public  String getJSON(){
            StringBuilder sb=new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url=Util.url_ReadCandidateDetails;
                String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data=URLEncoder.encode("candidate_id","UTF-8")+ "="+ URLEncoder.encode(candiId,"UTF-8");
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
                // Toast.makeText(AppFillForm.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Toast.makeText(AppFillForm.this, "response: "+response, Toast.LENGTH_SHORT).show();
          //  pd.dismiss();
            try {
                JSONArray jsonArray=new JSONArray(response);
                if (jsonArray.length()>0){

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject job=jsonArray.getJSONObject(i);
                        String post=job.getString("PostAppliedFor");
                        for(int k=0;k<list_posts.size();k++){
                            if(list_posts.get(k).trim().equals(post))
                                spPost.setSelection(k);
                        }

                        String jobState=job.getString("States");
                        for(int k=0;k<list_states.size();k++){
                            //Toast.makeText(AppFillForm.this, "st "+list_states, Toast.LENGTH_SHORT).show();
                            if(list_states.get(k).equals(jobState))
                                spState.setSelection(k);
                        }

                    }

                }
                else{
                    Toast.makeText(AppFillForm.this, "Oops! Error occured...", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                //Toast.makeText(AppFillForm.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }

    class readCandidateDetails3 extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {
          //  Toast.makeText(AppFillForm.this, "Id="+candiId, Toast.LENGTH_LONG).show();

        }
        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJSON();
            return null;
        }
        public  String getJSON(){
            StringBuilder sb=new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url=Util.url_ReadCandidateDetails;
                String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data=URLEncoder.encode("candidate_id","UTF-8")+ "="+ URLEncoder.encode(candiId,"UTF-8");
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
                // Toast.makeText(AppFillForm.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Toast.makeText(AppFillForm.this, "response: "+response, Toast.LENGTH_SHORT).show();
            try {
                JSONArray jsonArray=new JSONArray(response);
                if (jsonArray.length()>0){

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject job=jsonArray.getJSONObject(i);


                        String dis=job.getString("Districts");
                        for(int k=0;k<list_district.size();k++){
                            if(list_district.get(k).equals(dis))
                                spDistrict.setSelection(k);
                        }





                    }

                }
                else{
                    Toast.makeText(AppFillForm.this, "Oops! Error occured...", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                //Toast.makeText(AppFillForm.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }


}
