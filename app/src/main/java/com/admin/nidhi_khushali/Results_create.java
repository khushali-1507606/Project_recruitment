package com.admin.nidhi_khushali;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win_10.newapp.R;
import com.mail_sender.GMailSender;
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

public class Results_create extends AppCompatActivity {

    Button btnSave, btnReset;
    EditText etxtVacancies, etxtMsg;
    Spinner spAdv, spPost, spDivision;

    String adNo = "", div = "", post = "", vacancies = "", msg = "";
    ArrayList listAdvs, listDiv, listPosts;
    ArrayAdapter adapterAdv, adapterDivision, adapterPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_create);

        initViews();
        GetAdvertisements getAdv = new GetAdvertisements();
        getAdv.execute();
    }

    public void initViews() {
        spAdv = findViewById(R.id.spnrGetAdv);
        spDivision = findViewById(R.id.spnrDivision);
        spPost = findViewById(R.id.spnrPost);

        etxtVacancies = findViewById(R.id.edTextVacancies);
        etxtMsg = findViewById(R.id.edTextMessage);

        btnSave = findViewById(R.id.buttonSaveResult);
        btnReset = findViewById(R.id.buttonResetResult);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adNo = spAdv.getSelectedItem().toString();
                div = spDivision.getSelectedItem().toString();
                post = spPost.getSelectedItem().toString();
                vacancies = etxtVacancies.getText().toString();
                msg = etxtMsg.getText().toString();

                checkValidations();
//                CreateResult createObj = new CreateResult();
//                createObj.execute();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spPost.setSelection(0);
                spDivision.setSelection(0);
                spAdv.setSelection(0);
                etxtVacancies.setText("");
                etxtMsg.setText("");

            }
        });

        listAdvs = new ArrayList();
        listAdvs.add("Please Select Advertisement No");
//        listAdvs.add("Adv1");


        listDiv = new ArrayList();
        listDiv.add("Please Select Division");

        listPosts = new ArrayList();
        listPosts.add("Please Select Post");

        spAdv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adNo = spAdv.getSelectedItem().toString();
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);

                GetAllDivisions getDiv = new GetAllDivisions();
                getDiv.execute();
                if (spAdv.getSelectedItemPosition()!=0){
                    spAdv.clearFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                div = spDivision.getSelectedItem().toString();
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);

                GetAllPosts getPosts = new GetAllPosts();
                getPosts.execute();
                if (spDivision.getSelectedItemPosition()!=0){
                    spDivision.clearFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                post = spPost.getSelectedItem().toString();
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);

                if (spPost.getSelectedItemPosition()!=0){
                    spPost.clearFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    class GetAdvertisements extends AsyncTask {
        String response;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Results_create.this);
            //progressDialog.setMessage("Divisions...please wait");
            progressDialog.show();
            Log.d("pre", "prexecute");
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response = getJson();
            return null;
        }

        public String getJson() {
            StringBuilder stringBuilder = new StringBuilder();
            String url = Util.url_GetAdvertisements;
            //String data = "";
            try {
                URL url1 = new URL(url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                InputStreamReader isr = new InputStreamReader(httpURLConnection.getInputStream());
                Scanner scanner = new Scanner(isr);
                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.nextLine());

                }

                return stringBuilder.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
            try {
                if (response.equals("[]")) {
                    adapterAdv = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listAdvs);
                    spAdv.setAdapter(adapterAdv);
                } else {
                    JSONArray jarr = new JSONArray(response);

                    if (jarr.length() > 0) {
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject job = jarr.getJSONObject(i);
                            String adv = job.getString("Advertisement_No");
                            //getting values

                            listAdvs.add(adv);//adding in arraylist

                        }
                        adapterAdv = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listAdvs);
                        spAdv.setAdapter(adapterAdv);

                    } else {
                        listAdvs.add("Adv1");
                        listAdvs.add("adv4");
                        adapterAdv = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listAdvs);
                        spAdv.setAdapter(adapterAdv);
                        Toast.makeText(Results_create.this, "empty response...static data for testing", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {

                e.printStackTrace();
                listAdvs.add("Adv1");
                listAdvs.add("adv4");
                adapterAdv = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listAdvs);
                spAdv.setAdapter(adapterAdv);
                Toast.makeText(Results_create.this, "Static Adv for testing" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    class GetAllDivisions extends AsyncTask {
        String response;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Results_create.this);
            //progressDialog.setMessage("Divisions...please wait");
            progressDialog.show();
            Log.d("pre", "prexecute");
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response = getJson3();
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
            try {
                JSONArray jarr = new JSONArray(response);

                if (jarr.length() > 0) {
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject job = jarr.getJSONObject(i);
                        String district2 = job.getString("Division");
                        //getting values
                        Log.d("Division", district2);

                        listDiv.add(district2);//adding in arraylist

                    }
                    adapterDivision = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listDiv);
                    spDivision.setAdapter(adapterDivision);

                } else {


                   // Toast.makeText(Results_create.this, "Try again", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {

                e.printStackTrace();
                Toast.makeText(Results_create.this, "Catch block" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }


        public String getJson3() {
            StringBuilder stringBuilder = new StringBuilder();
            String url = Util.url_GetAllDivisions;
            String data = "";
            try {
                URL url1 = new URL(url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = null;
                OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = null;
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                data = URLEncoder.encode("advt_no", "UTF-8") + "=" +
                        URLEncoder.encode(adNo, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                InputStreamReader isr = new InputStreamReader(httpURLConnection.getInputStream());
                Scanner scanner = new Scanner(isr);
                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.nextLine());

                }

                return stringBuilder.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }

    }

    class GetAllPosts extends AsyncTask {

        ProgressDialog pd;
        String response;

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(Results_create.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            // pd.setTitle("Fetching data");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response = getJSON();
            return null;
        }

        public String getJSON() {
            StringBuilder sb = new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url = Util.url_GetAllPosts;
                String data = "";
                URL x = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                data = URLEncoder.encode("advt", "UTF-8") + "=" + URLEncoder.encode(adNo, "UTF-8")
                        + "&" + URLEncoder.encode("division", "UTF-8") + "=" + URLEncoder.encode(div, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                //fetching response
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                Scanner scanner = new Scanner(inputStreamReader);
                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }

                return sb.toString();

            } catch (Exception e) {
                //Toast.makeText(AppFillForm.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(Object o) {
            //Toast.makeText(CandidateDetails.this, "onPostExecute,response: "+response, Toast.LENGTH_SHORT).show();
            super.onPostExecute(o);
            pd.dismiss();
            try {
                JSONArray jsonArray = new JSONArray(response);
                //Toast.makeText(CandidateDetails.this, "JSON length:"+jsonArray.length(), Toast.LENGTH_SHORT).show();
                if (jsonArray.length() > 0) {

                    listPosts = new ArrayList<>();
                    listPosts.add("Select Post");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject job = jsonArray.getJSONObject(i);

                        String post = job.getString("DataItem");
                        listPosts.add(post);

                    }
                    adapterPost = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listPosts);
                    spPost.setAdapter(adapterPost);
                } else {
                    listPosts.clear();
                    listPosts.add("SelectPost");
                    listPosts.add("Programmer");
                    listPosts.add("Intern");
                    listPosts.add("Assistant Programmer");
                    adapterPost = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listPosts);
                    spPost.setAdapter(adapterPost);
                    //Toast.makeText(Results_create.this, "empty response...Static Posts for testing", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(Results_create.this, "Oops!catch executed..." + e.getMessage(), Toast.LENGTH_SHORT).show();
                listPosts.clear();
                listPosts.add("SelectPost");
                listPosts.add("Programmer");
                listPosts.add("Intern");
                listPosts.add("Assistant Programmer");
                adapterPost = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listPosts);
                spPost.setAdapter(adapterPost);
                e.printStackTrace();
            }

        }
    }

    class CreateResult extends AsyncTask {

        ProgressDialog pd;
        String response;

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(Results_create.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            // pd.setTitle("Fetching data");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response = getJSON();
            return null;
        }

        public String getJSON() {
            StringBuilder sb = new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url = Util.url_CreateResult;
                String data = "";
                URL x = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                data = URLEncoder.encode("advt", "UTF-8") + "=" + URLEncoder.encode(adNo, "UTF-8")
                        + "&" + URLEncoder.encode("division", "UTF-8") + "=" + URLEncoder.encode(div, "UTF-8")
                        + "&" + URLEncoder.encode("post", "UTF-8") + "=" + URLEncoder.encode(post, "UTF-8")
                        + "&" + URLEncoder.encode("selectNo", "UTF-8") + "=" + URLEncoder.encode(vacancies, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                //fetching response
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                Scanner scanner = new Scanner(inputStreamReader);
                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }

                return sb.toString();

            } catch (Exception e) {
                //Toast.makeText(AppFillForm.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(Object o) {
            //Toast.makeText(CandidateDetails.this, "onPostExecute,response: "+response, Toast.LENGTH_SHORT).show();
            super.onPostExecute(o);
            pd.dismiss();
            try {
                JSONObject job = new JSONObject(response);
                String res = job.getString("code");
                //Toast.makeText(CandidateDetails.this, "JSON length:"+jsonArray.length(), Toast.LENGTH_SHORT).show();
                if (res.equals("3")) {
                    String msg = "Result created successfully!";
                    showalert(msg);
                    GetCandidates getCobj=new GetCandidates();
                    getCobj.execute();

                } else {
                    String msg = "Error! ...Result not created";
                    showalert(msg);
                }
            } catch (Exception e) {
                Toast.makeText(Results_create.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }

    class GetCandidates extends AsyncTask {

        ProgressDialog pd;
        String response;

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(Results_create.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            // pd.setTitle("Fetching data");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response = getJSON();
            return null;
        }

        public String getJSON() {
            StringBuilder sb = new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url = Util.url_GetCandidates;
                String data = "";
                URL x = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                data = URLEncoder.encode("advt", "UTF-8") + "=" + URLEncoder.encode(adNo, "UTF-8")
                        + "&" + URLEncoder.encode("division", "UTF-8") + "=" + URLEncoder.encode(div, "UTF-8")
                        + "&" + URLEncoder.encode("post", "UTF-8") + "=" + URLEncoder.encode(post, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                //fetching response
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                Scanner scanner = new Scanner(inputStreamReader);
                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }

                return sb.toString();

            } catch (Exception e) {
                //Toast.makeText(AppFillForm.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(Object o) {
            //Toast.makeText(CandidateDetails.this, "onPostExecute,response: "+response, Toast.LENGTH_SHORT).show();
            super.onPostExecute(o);
            pd.dismiss();
            try {

                    JSONArray jsonArray=new JSONArray(response);

                    if(jsonArray.length()>0) {

                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject job = jsonArray.getJSONObject(i);

                            String email=job.getString("EmailId");
                            String fName=job.getString("FullName");
                            String resStatus=job.getString("ResultStatus");

                            String msgForSelected="You are shortlisted in the meritlist for the post "+post+" of division "+div+" .";
                            String msgForNonSeleced="You are  not shortlisted in the meritlist for the post "+post+" of division "+div+" .";
                            String msgToSend="";
                            if (resStatus.equals("Selected")) {
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        try {
                                            GMailSender sender = new GMailSender("kapoor.khushali07@gmail.com","kapoorrocks07"
                                            );
                                            sender.sendMail("Hello "+fName+" from PRSC e-Recruitments \n\n Result is Declared", msgForSelected,
                                                    "kapoor.khushali07@gmail.com", email);
                                        } catch (Exception e) {
                                            Log.e("SendMail", e.getMessage(), e);
                                        }
                                    }

                                }).start();
                            }
                            else {

                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        try {
                                            GMailSender sender = new GMailSender("kapoor.khushali07@gmail.com", "kapoorrocks07"
                                            );
                                            sender.sendMail("Hello " + fName + " from PRSC e-Recruitments \n\n Result is Declared", msgForNonSeleced,
                                                    "kapoor.khushali07@gmail.com", email);
                                        } catch (Exception e) {
                                            Log.e("SendMail", e.getMessage(), e);
                                        }
                                    }

                                }).start();

                            }
                        }
                    }

                    else{
                        showalert("0 candidates returned from GetCandidates");
                }

            } catch (Exception e) {
                Toast.makeText(Results_create.this, "Oops!catch executed..."+e.getMessage(), Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }

    public void checkValidations() {
        if (spAdv.getSelectedItemPosition()==0){
            showalert("Choose Advertisement No");
            spAdv.requestFocus();
        }
        else if(spDivision.getSelectedItemPosition()==0){
            showalert("Choose Division");
            spDivision.requestFocus();
        }
        else if(spPost.getSelectedItemPosition()==0){
            showalert("Choose Post");
            spPost.requestFocus();
        }
        else if (etxtVacancies.getText().toString().trim().equals("")) {
            etxtVacancies.setError("Required field");
            etxtVacancies.requestFocus();
        }
        else if (etxtMsg.getText().toString().trim().equals("")) {
            etxtMsg.setError("Required field");
            etxtMsg.requestFocus();
        }
        else {
            CreateResult createObj = new CreateResult();
            createObj.execute();
        }
    }


    public void showalert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Result");
        builder.setMessage(msg);
        builder.setNeutralButton("OK", null);
        AlertDialog showmsg = builder.create();
        showmsg.show();
    }
}

