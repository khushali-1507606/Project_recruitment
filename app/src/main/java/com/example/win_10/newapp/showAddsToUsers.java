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


public class showAddsToUsers extends AppCompatActivity {

    ArrayList<Integer> listShowAddsId;
    ArrayList<String> listShowAddsNum;
    ArrayList<String> listShowAddsDesc;
    ArrayList<String> listShowAddsAttachedFile;
    ArrayList<String> listShowAddsStartDate;
    ArrayList<String> listShowAddsEndDate;

    ArrayAdapter showadds_adapter;

    ListView listView_showadds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_adds_to_users);

        initViews();

        // showAdds();

        showAddsAsyncTask showObj=new showAddsAsyncTask();
        showObj.execute();
    }

    public void initViews(){
        listShowAddsId=new ArrayList<>();
        listShowAddsNum=new ArrayList<>();
        listShowAddsDesc=new ArrayList<>();
        listShowAddsAttachedFile=new ArrayList<>();
        listShowAddsStartDate=new ArrayList<>();
        listShowAddsEndDate=new ArrayList<>();

       /*static data

        listShowAddsId.add(1);
        listShowAddsId.add(2);

        listShowAddsNum.add("123abc");
        listShowAddsNum.add("345xyz");

        listShowAddsDesc.add("xxxxxxxx");
        listShowAddsDesc.add("yyyyyy");

        listShowAddsAttachedFile.add(Util.url_pdf);
        listShowAddsAttachedFile.add("abc");

        listShowAddsStartDate.add("20/9/18");
        listShowAddsStartDate.add("2/10/18");

        listShowAddsEndDate.add("29/10/18");
        listShowAddsEndDate.add("5/12/18");*/


        listView_showadds=findViewById(R.id.listViewShowAddsToUsers);


    }

    public void showAdds(){

        showadds_adapter=new showAddsToUsersAdapter(this,R.layout.list_show_adds,listShowAddsId,listShowAddsNum,listShowAddsDesc,listShowAddsAttachedFile,listShowAddsStartDate,listShowAddsEndDate);
        listView_showadds.setAdapter(showadds_adapter);

    }

    class showAddsAsyncTask extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {

            pd=new ProgressDialog(showAddsToUsers.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
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

        public  String getJSON(){
            StringBuilder sb=new StringBuilder();
            try {
                //String url="http://192.168.42.16/AdvertisementsFolder/getAllAdds.php";
                String url= Util.url_GetOpenAdvertisement;
                //String data="";
                URL x=new URL(url);
                HttpURLConnection connection=(HttpURLConnection)x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

              /*    OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data=URLEncoder.encode("question","UTF-8")+ "="+ URLEncoder.encode(que,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();*/

                //fetching response
                InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
                Scanner scanner=new Scanner(inputStreamReader);
                while(scanner.hasNext()){
                    sb.append(scanner.nextLine());
                }

                return sb.toString();

            }

            catch (Exception e) {
                Toast.makeText(showAddsToUsers.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
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

                        listShowAddsId.add(id);
                        listShowAddsNum.add(adv_no);
                        listShowAddsDesc.add(desc);
                        listShowAddsAttachedFile.add(attachment);
                        listShowAddsStartDate.add(date_created);
                        listShowAddsEndDate.add(end_date);

                    }
                    //Toast.makeText(showAddsToUsers.this, "fetched "+listShowAddsId.size()+ " records", Toast.LENGTH_SHORT).show();
                    showadds_adapter=new showAddsToUsersAdapter(getApplicationContext(),R.layout.list_show_adds,listShowAddsId,listShowAddsNum,listShowAddsDesc,listShowAddsAttachedFile,listShowAddsStartDate,listShowAddsEndDate);
                    listView_showadds.setAdapter(showadds_adapter);

                }
                else{
                    Toast.makeText(showAddsToUsers.this, "Oops! Error occured...", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                Toast.makeText(showAddsToUsers.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }
}
