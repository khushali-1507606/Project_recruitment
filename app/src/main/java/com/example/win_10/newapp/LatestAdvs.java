package com.example.win_10.newapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

//import in.prsc.com.test_bin.util.Util;

public class LatestAdvs extends AppCompatActivity {

    ArrayList<Integer> listAdvId;
    ArrayList<String> listAdvNum;
    ArrayList<String> listAdvDesc;
    ArrayList<String> listAdvAttachedFile;
    ArrayList<String> listAdvStartDate;
    ArrayList<String> listAdvEndDate;

    ListView listViewLatest_adv;

    ArrayAdapter latest_AdvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_advs);

        initViews();

        //fetchLatestAdv();

        showAddsAsyncTask ltadds=new showAddsAsyncTask();
        ltadds.execute();
    }

    public void initViews(){

        listAdvId=new ArrayList<>();
        listAdvNum=new ArrayList<>();
        listAdvDesc=new ArrayList<>();
        listAdvAttachedFile=new ArrayList<>();
        listAdvStartDate=new ArrayList<>();
        listAdvEndDate=new ArrayList<>();

        listAdvId.add(1);
        listAdvId.add(2);

        listAdvNum.add("123ABC");
        listAdvNum.add("234DFG");

        listAdvDesc.add("xxxxx");
        listAdvDesc.add("yyyyyyyyyy");

        listAdvAttachedFile.add("adch1");
        listAdvAttachedFile.add("adch2");

        listAdvStartDate.add("22/8/18");
        listAdvStartDate.add("1/10/18");

        listAdvEndDate.add("30/9/18");
        listAdvEndDate.add("10/12/18");


        listViewLatest_adv=(ListView) findViewById(R.id.listLatestAdv);

    }

    public void fetchLatestAdv(){

        latest_AdvAdapter=new latestAdvAdapter(getApplicationContext(),R.layout.list_latest_adv,listAdvId,listAdvNum,listAdvDesc,listAdvAttachedFile,listAdvStartDate,listAdvEndDate );
        listViewLatest_adv.setAdapter(latest_AdvAdapter);
    }

    class showAddsAsyncTask extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {

            pd=new ProgressDialog(LatestAdvs.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pd.setTitle("Advertisements");
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
                String url=Util.url_GetOpenAdvertisement;
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
               // Toast.makeText(LatestAdvs.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(Object o) {
            //Toast.makeText(showAddsToUsers.this, "onPostExecute,response: "+response, Toast.LENGTH_SHORT).show();
            super.onPostExecute(o);
            pd.dismiss();
            try {
                JSONArray jsonArray=new JSONArray(response);

                // Toast.makeText(showAddsToUsers.this, "JSON length:"+jsonArray.length(), Toast.LENGTH_SHORT).show();
                if (jsonArray.length()>0){
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject job=jsonArray.getJSONObject(i);
                        int id=job.getInt("Id");
                        String adv_no=job.getString("Advertisement_No");
                        String desc=job.getString("Description");
                        String attachment=job.getString("Attach_File");
                        String date_created=job.getString("Starting_Date");
                        String end_date=job.getString("Ending_Date");

                        listAdvId.add(id);
                        listAdvNum.add(adv_no);
                        listAdvDesc.add(desc);
                        listAdvAttachedFile.add(attachment);
                        listAdvStartDate.add(date_created);
                        listAdvEndDate.add(end_date);

                    }
                    //Toast.makeText(showAddsToUsers.this, "fetched "+listShowAddsId.size()+ " records", Toast.LENGTH_SHORT).show();
                    latest_AdvAdapter=new latestAdvAdapter(getApplicationContext(),R.layout.list_latest_adv,listAdvId,listAdvNum,
                            listAdvDesc,listAdvAttachedFile,listAdvStartDate,listAdvEndDate);
                    listViewLatest_adv.setAdapter(latest_AdvAdapter);

                }
                else{
                    Toast.makeText(LatestAdvs.this, "No Records Found...", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                //Toast.makeText(LatestAdvs.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }
}
