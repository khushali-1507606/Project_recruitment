package com.admin.nidhi_khushali;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win_10.newapp.R;
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

public class Results_show extends AppCompatActivity {

    ArrayList<Integer> listCandiDetailsId;
    ArrayList<String> listCandiDetailsAdvNum;
    ArrayList<String> listCandiDetailsFullName;
    ArrayList<String> listCandiDetailsDadName;
    ArrayList<String> listCandiDetailsEmail;
    ArrayList<String> listCandiDetailsMobNo;
    ArrayList<String> listCandiDetailsDivision;
    ArrayList<String> listCandiDetailsAppliedFor;
    ArrayList<String> listCandiScore;
    ArrayList<String> listCandiResult;



    ListView listView_results;

    ArrayAdapter resultAdapter;

    String mode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_show);

        initViews();

        //fetchScores();

        getCandidateResult getRes=new getCandidateResult();
        getRes.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=getIntent();
        mode=intent.getStringExtra("mode");
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
        listCandiScore=new ArrayList<>();
        listCandiResult=new ArrayList<>();

        listView_results=(ListView) findViewById(R.id.listViewResults);

    }

    public void fetchScores(){

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

        listCandiScore.add("89");
        listCandiScore.add("60");
        listCandiScore.add("75");

        listCandiResult.add("89");
        listCandiResult.add("60");
        listCandiResult.add("75");

        resultAdapter=new candiScoresAdapter(getApplicationContext(),R.layout.list_showresults, listCandiDetailsId, listCandiDetailsAdvNum, listCandiDetailsFullName, listCandiDetailsDadName,listCandiDetailsEmail,listCandiDetailsMobNo,listCandiDetailsDivision,listCandiDetailsAppliedFor,listCandiScore,listCandiResult);
        listView_results.setAdapter(resultAdapter);

    }

    class getCandidateResult extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {

            pd=new ProgressDialog(Results_show.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pd.setTitle("Results");
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
                String url=Util.url_GetCandidatesResult;
                String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data=URLEncoder.encode("mode","UTF-8")+ "="+ URLEncoder.encode(mode,"UTF-8");
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
                Toast.makeText(Results_show.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
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
                        String candi_score=job.getString("TestMarks");
                        String result=job.getString("ResultStatus");


                        listCandiDetailsId.add(id);
                        listCandiDetailsAdvNum.add(adv_no);
                        listCandiDetailsFullName.add(full_name);
                        listCandiDetailsDadName.add(dad_name);
                        listCandiDetailsEmail.add(email);
                        listCandiDetailsMobNo.add(mob_no);
                        listCandiDetailsDivision.add(div_applied);
                        listCandiDetailsAppliedFor.add(applied_for);
                        listCandiScore.add(candi_score);
                        listCandiResult.add(result);

                    }
                    // Toast.makeText(CandidateDetails.this, "fetched "+listCandiDetailsId.size()+ " records", Toast.LENGTH_SHORT).show();
                    resultAdapter=new candiScoresAdapter(getApplicationContext(),R.layout.list_showresults,listCandiDetailsId, listCandiDetailsAdvNum,listCandiDetailsFullName,listCandiDetailsDadName,listCandiDetailsEmail,listCandiDetailsMobNo,listCandiDetailsDivision,listCandiDetailsAppliedFor,listCandiScore,listCandiResult);
                    listView_results.setAdapter(resultAdapter);

                }
                else{
                    showalert("No Records Found!");
                    //Toast.makeText(Results_show.this, "No Records Found!", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                Toast.makeText(Results_show.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }


    public class candiScoresAdapter extends ArrayAdapter {

        Context context;
        ArrayList<Integer> listCandiDetailsId;
        ArrayList<String> listCandiDetailsAdvNum;
        ArrayList<String> listCandiDetailsFullName;
        ArrayList<String> listCandiDetailsDadName;
        ArrayList<String> listCandiDetailsEmail;
        ArrayList<String> listCandiDetailsMobNo;
        ArrayList<String> listCandiDetailsDivision;
        ArrayList<String> listCandiDetailsAppliedFor;
        ArrayList<String> listTestScore;
        ArrayList<String> listResult;




        public candiScoresAdapter(@NonNull Context context, int resource, ArrayList<Integer> listCandiDetailsId, ArrayList<String> listCandiDetailsAdvNum, ArrayList<String> listCandiDetailsFullName, ArrayList<String> listCandiDetailsDadName, ArrayList<String> listCandiDetailsEmail, ArrayList<String> listCandiDetailsMobNo, ArrayList<String> listCandiDetailsDivision, ArrayList<String> listCandiDetailsAppliedFor, ArrayList<String> listTestScore, ArrayList<String> listResult)
        {
            super(context, resource, listCandiDetailsId);
            this.context=context;
            this.listCandiDetailsId=listCandiDetailsId;
            this.listCandiDetailsAdvNum=listCandiDetailsAdvNum;
            this.listCandiDetailsFullName=listCandiDetailsFullName;
            this.listCandiDetailsDadName=listCandiDetailsDadName;
            this.listCandiDetailsEmail=listCandiDetailsEmail;
            this.listCandiDetailsMobNo=listCandiDetailsMobNo;
            this.listCandiDetailsDivision=listCandiDetailsDivision;
            this.listCandiDetailsAppliedFor=listCandiDetailsAppliedFor;
            this.listTestScore=listTestScore;
            this.listResult=listResult;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view=null;

            view= LayoutInflater.from(context).inflate(R.layout.list_showresults,parent,false);
            TextView txt_CD_id=view.findViewById(R.id.textViewCandidDetailsID);
            TextView txt_CD_advno=view.findViewById(R.id.textViewCandidDetailsAdvNo);
            TextView txt_CD_fullName=view.findViewById(R.id.textViewCandidDetailsFullName);
            TextView txt_CD_DadName=view.findViewById(R.id.textViewCandidDetailsDadName);
            TextView txt_CD_email=view.findViewById(R.id.textViewCandidDetailsEmail);
            TextView txt_CD_mobNo=view.findViewById(R.id.textViewCandidDetailsMobNo);
            TextView txt_CD_division=view.findViewById(R.id.textViewCandidDetailsDivision);
            TextView txt_CD_AppliedFor=view.findViewById(R.id.textViewCandidDetailsAppliedFor);
            TextView txt_score=view.findViewById(R.id.textViewCandiScore);
            TextView txt_result=view.findViewById(R.id.textViewCandiResult);


            txt_CD_id.setText(listCandiDetailsId.get(position).toString());
            txt_CD_advno.setText(listCandiDetailsAdvNum.get(position).toString());
            txt_CD_fullName.setText(listCandiDetailsFullName.get(position).toString());
            txt_CD_DadName.setText(listCandiDetailsDadName.get(position).toString());
            txt_CD_email.setText(listCandiDetailsEmail.get(position).toString());
            txt_CD_mobNo.setText(listCandiDetailsMobNo.get(position).toString());
            txt_CD_division.setText(listCandiDetailsDivision.get(position).toString());
            txt_CD_AppliedFor.setText(listCandiDetailsAppliedFor.get(position).toString());
            txt_score.setText(listTestScore.get(position).toString());
            txt_result.setText(listResult.get(position).toString());


            return view;
        }

    }

    public void showalert(String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Results");
        builder.setMessage(msg);
        builder.setNeutralButton("OK",null);
        AlertDialog showmsg=builder.create();
        showmsg.show();
    }

}
