package com.admin.nidhi_khushali;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.util.Util;
import com.example.win_10.newapp.R;

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

//import in.prsc.com.test_bin.util.Util;

public class pastCandidates extends AppCompatActivity {

    ArrayList<Integer> listCandiDetailsId;
    ArrayList<String> listCandiDetailsAdvNum;
    ArrayList<String> listCandiDetailsFullName;
    ArrayList<String> listCandiDetailsDadName;
    ArrayList<String> listCandiDetailsEmail;
    ArrayList<String> listCandiDetailsMobNo;
    ArrayList<String> listCandiDetailsDivision;
    ArrayList<String> listCandiDetailsAppliedFor;

    ListView listViewpastcandi_details;

    ArrayAdapter pastcandi_detailsAdapter;

    EditText etxtSearch;
    Button btnSearchGo;
    String advt="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_candidates);
        initViews();

        //dynamic data fetch...
        getPastCandidates getPC_obj=new getPastCandidates();
        getPC_obj.execute();

        //static data fetch...
        //fetchAdvertisements();
    }

    public void initViews(){

        listCandiDetailsId=new ArrayList<>();
        listCandiDetailsAdvNum=new ArrayList<>();
        listCandiDetailsFullName=new ArrayList<>();
        listCandiDetailsDadName=new ArrayList<>();
        listCandiDetailsEmail=new ArrayList<>();
        listCandiDetailsMobNo=new ArrayList<>();
        listCandiDetailsDivision=new ArrayList<>();
        listCandiDetailsAppliedFor=new ArrayList<>();

        listViewpastcandi_details=(ListView) findViewById(R.id.listViewPastCandiDetails);
        etxtSearch=findViewById(R.id.edSeachCandi);
        btnSearchGo=findViewById(R.id.btnGoSearch);
        btnSearchGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advt=etxtSearch.getText().toString().trim();
                listCandiDetailsId.clear();
                listCandiDetailsAdvNum.clear();
                listCandiDetailsFullName.clear();
                listCandiDetailsDadName.clear();
                listCandiDetailsEmail.clear();
                listCandiDetailsMobNo.clear();
                listCandiDetailsDivision.clear();
                listCandiDetailsAppliedFor.clear();

                if(advt.equals("")){
                    getPastCandidates getPC_obj=new getPastCandidates();
                    getPC_obj.execute();
                }
                else {
                    getSelectedCandidates getSCobj = new getSelectedCandidates();
                    getSCobj.execute();
                }
            }
        });

    }

    public void fetchAdvertisements(){

        listCandiDetailsId.add(1);
        listCandiDetailsId.add(2);
        listCandiDetailsId.add(3);

        listCandiDetailsAdvNum.add("ABC123");
        listCandiDetailsAdvNum.add("DEF456");
        listCandiDetailsAdvNum.add("GHI789");

        listCandiDetailsFullName.add("Rohit Patel");
        listCandiDetailsFullName.add("Nidhi Shukla");
        listCandiDetailsFullName.add("Aparna Negi");

        listCandiDetailsDadName.add("Mr. KK Patel");
        listCandiDetailsDadName.add("Mr. Gautam Shukla");
        listCandiDetailsDadName.add("Mr. Vinay Negi");

        listCandiDetailsEmail.add("rohit@gmail.com");
        listCandiDetailsEmail.add("nidhi@gmail.com");
        listCandiDetailsEmail.add("aparna@gmail.com");

        listCandiDetailsMobNo.add("9877897878");
        listCandiDetailsMobNo.add("8796785676");
        listCandiDetailsMobNo.add("5678766788");

        listCandiDetailsDivision.add("GEOINFORMATICS");
        listCandiDetailsDivision.add("");
        listCandiDetailsDivision.add("GEOINFORMATICS");

        listCandiDetailsAppliedFor.add("Android");
        listCandiDetailsAppliedFor.add("Web Developer");
        listCandiDetailsAppliedFor.add("Web Designing");

        pastcandi_detailsAdapter=new pastCandidatesAdapter(getApplicationContext(),R.layout.list_candidate_details, listCandiDetailsId, listCandiDetailsAdvNum, listCandiDetailsFullName, listCandiDetailsDadName,listCandiDetailsEmail,listCandiDetailsMobNo,listCandiDetailsDivision,listCandiDetailsAppliedFor);
        listViewpastcandi_details.setAdapter(pastcandi_detailsAdapter);

    }

    class getPastCandidates extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {

            pd=new ProgressDialog(pastCandidates.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pd.setTitle("Fetching Past Candidates");
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
                String url=Util.url_GetPastCandidates;
                //String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                //fetching response
                InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
                Scanner scanner=new Scanner(inputStreamReader);
                while(scanner.hasNext()){
                    sb.append(scanner.nextLine());
                }

                return sb.toString();

            }

            catch (Exception e) {
                // Toast.makeText(pastCandidates.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(Object o) {
            //Toast.makeText(pastCandidates.this, "onPostExecute,response: "+response, Toast.LENGTH_SHORT).show();
            super.onPostExecute(o);
            pd.dismiss();
            try {
                JSONArray jsonArray=new JSONArray(response);

                if (jsonArray.length()>0){
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject job=jsonArray.getJSONObject(i);
                        int id=job.getInt("CandidateId");
                        String adv_no=job.getString("AdvertisementNo");
                        String full_name=job.getString("FullName");
                        String dad_name=job.getString("FatherName");
                        String email=job.getString("EmailId");
                        String mob_no=job.getString("ContactNo");
                        String div_applied=job.getString("DivisionNameAppliedIn");
                        String applied_for=job.getString("PostAppliedFor");


                        listCandiDetailsId.add(id);
                        listCandiDetailsAdvNum.add(adv_no);
                        listCandiDetailsFullName.add(full_name);
                        listCandiDetailsDadName.add(dad_name);
                        listCandiDetailsEmail.add(email);
                        listCandiDetailsMobNo.add(mob_no);
                        listCandiDetailsDivision.add(div_applied);
                        listCandiDetailsAppliedFor.add(applied_for);


                    }
                    pastcandi_detailsAdapter=new pastCandidatesAdapter(getApplicationContext(),R.layout.list_past_candidates,listCandiDetailsId, listCandiDetailsAdvNum,listCandiDetailsFullName,listCandiDetailsDadName,listCandiDetailsEmail,listCandiDetailsMobNo,listCandiDetailsDivision,listCandiDetailsAppliedFor);
                    listViewpastcandi_details.setAdapter(pastcandi_detailsAdapter);

                }
                else{
                    Toast.makeText(pastCandidates.this, "No Records Found...", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                Toast.makeText(pastCandidates.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }

    class getSelectedCandidates extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {

            pd=new ProgressDialog(pastCandidates.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            //pd.setTitle("Advertisements");
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
                String url=Util.url_GetSelectedCandidatesDetail;
                String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data=URLEncoder.encode("advt","UTF-8")+ "="+ URLEncoder.encode(advt,"UTF-8");
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
                Toast.makeText(pastCandidates.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(CandidateDetails.this, "JSON length:"+jsonArray.length(), Toast.LENGTH_SHORT).show();
                if (jsonArray.length()>0){
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject job=jsonArray.getJSONObject(i);
                        int id=job.getInt("CandidateId");
                        String adv_no=job.getString("AdvertisementNo");
                        String full_name=job.getString("FullName");
                        String dad_name=job.getString("FatherName");
                        String email=job.getString("EmailId");
                        String mob_no=job.getString("ContactNo");
                        String div_applied=job.getString("DivisionNameAppliedIn");
                        String applied_for=job.getString("PostAppliedFor");


                        listCandiDetailsId.add(id);
                        listCandiDetailsAdvNum.add(adv_no);
                        listCandiDetailsFullName.add(full_name);
                        listCandiDetailsDadName.add(dad_name);
                        listCandiDetailsEmail.add(email);
                        listCandiDetailsMobNo.add(mob_no);
                        listCandiDetailsDivision.add(div_applied);
                        listCandiDetailsAppliedFor.add(applied_for);


                    }
                    // Toast.makeText(CandidateDetails.this, "fetched "+listCandiDetailsId.size()+ " records", Toast.LENGTH_SHORT).show();
                    pastcandi_detailsAdapter=new pastCandidatesAdapter(getApplicationContext(),R.layout.list_past_candidates,listCandiDetailsId, listCandiDetailsAdvNum,listCandiDetailsFullName,listCandiDetailsDadName,listCandiDetailsEmail,listCandiDetailsMobNo,listCandiDetailsDivision,listCandiDetailsAppliedFor);
                    listViewpastcandi_details.setAdapter(pastcandi_detailsAdapter);

                }
                else{
                    showalert("No Records Found!");
                    //Toast.makeText(pastCandidates.this, "No Records Found...", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                Toast.makeText(pastCandidates.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }
    public void showalert(String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
       // builder.setTitle("Results");
        builder.setMessage(msg);
        builder.setNeutralButton("OK",null);
        AlertDialog showmsg=builder.create();
        showmsg.show();
    }

}
