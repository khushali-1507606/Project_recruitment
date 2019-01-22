package com.admin.nidhi_khushali;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.win_10.newapp.R;
import com.util.Util;


//import in.prsc.com.test_bin.util.Util;

public class currentAdvertisements extends AppCompatActivity {

    ArrayList<Integer> listAdvId;
    ArrayList<String> listAdvNum;
    ArrayList<String> listAdvDesc;
    ArrayList<String> listAdvAttachment;
    ArrayList<String> listAdvDateCreated;
    ArrayList<String> listAdvStatus;
    ArrayList<String> listAdvNoOfDiv;

    ListView listViewCurr_adv;

    ArrayAdapter advAdapter;

    String AddId="";
    class_current_adv CA_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_advertisements);

        initViews();

        getAdds gAobj = new getAdds();
        gAobj.execute();

        //fetchAdvertisements();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void initViews(){

        listAdvId=new ArrayList<>();
        listAdvNum=new ArrayList<>();
        listAdvDesc=new ArrayList<>();
        listAdvAttachment=new ArrayList<>();
        listAdvDateCreated=new ArrayList<>();
        listAdvStatus=new ArrayList<>();
        listAdvNoOfDiv=new ArrayList<>();

        listViewCurr_adv=(ListView) findViewById(R.id.listCurrentAdv);

        CA_obj=new class_current_adv();
    }

    public void fetchAdvertisements(){
        for(int i=0;i<3;i++) {

            listAdvId.add(CA_obj.arrId[i]);
            listAdvNum.add(CA_obj.arrAdv_no[i]);
            listAdvDesc.add(CA_obj.arrAdv_desc[i]);
            listAdvAttachment.add(CA_obj.arrAdv_Attchment[i]);
            listAdvDateCreated.add(CA_obj.arrAdv_date_created[i]);
            listAdvStatus.add(CA_obj.arrAdv_status[i]);

            listAdvNoOfDiv.add(CA_obj.arrAdv_noOfDiv[i]);

        }

        advAdapter=new advAdapter(getApplicationContext(),R.layout.list_current_adv,listAdvId,listAdvNum,listAdvDesc,listAdvAttachment,listAdvDateCreated,listAdvStatus);
        listViewCurr_adv.setAdapter(advAdapter);

    }

    class getAdds extends AsyncTask {

        ProgressDialog pd;
        String response;
        @Override
        protected void onPreExecute() {

            pd=new ProgressDialog(currentAdvertisements.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pd.setTitle("Fetching Advertisements");
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
                Toast.makeText(currentAdvertisements.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(Object o) {
            //Toast.makeText(currentAdvertisements.this, "onPostExecute,response: "+response, Toast.LENGTH_SHORT).show();
            super.onPostExecute(o);
            pd.dismiss();
            try {
                JSONArray jsonArray=new JSONArray(response);

                if (jsonArray.length()>0){
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject job=jsonArray.getJSONObject(i);
                        int id=job.getInt("Id");
                        String adv_no=job.getString("Advertisement_No");
                        String desc=job.getString("Description");
                        String attachment=job.getString("Attach_File");
                        JSONObject job_dateCreated=job.getJSONObject("Created_date");
                        String date_created=job_dateCreated.getString("date");
                        String status=job.getString("Status");


                        listAdvId.add(id);
                        listAdvNum.add(adv_no);
                        listAdvDesc.add(desc);
                        listAdvAttachment.add(attachment);
                        listAdvDateCreated.add(date_created);
                        listAdvStatus.add(status);

                    }
                    //  Toast.makeText(currentAdvertisements.this, "fetched "+listAdvNum.size()+ " records", Toast.LENGTH_SHORT).show();
                    advAdapter=new advAdapter(getApplicationContext(),R.layout.list_current_adv,listAdvId,listAdvNum,listAdvDesc,listAdvAttachment,listAdvDateCreated,listAdvStatus);
                    listViewCurr_adv.setAdapter(advAdapter);

                }
                else{
                    showalert("No Advertisements Found!");
                    //Toast.makeText(currentAdvertisements.this, "Oops! Error occured...", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                Toast.makeText(currentAdvertisements.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }


    class deleteAdd extends AsyncTask {

        ProgressDialog pd;
        String response;

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(currentAdvertisements.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pd.setTitle("Deleting");
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
                String url = Util.url_DeleteAdvertisement;
                String data = "";
                URL x = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(AddId, "UTF-8");
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
                Toast.makeText(currentAdvertisements.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            pd.dismiss();
            try {
                if (response.equals("[]")) ;
                Toast.makeText(currentAdvertisements.this, "moved to past advertisements successfully", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(currentAdvertisements.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }

    class closeAdd extends AsyncTask {

        ProgressDialog pd;
        String response;

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(currentAdvertisements.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pd.setTitle("Closing Advertisement");
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
                String url = Util.url_closeAdvertisement;
                String data = "";
                URL x = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) x.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream OS = null;

                OS = connection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(AddId, "UTF-8");
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
                Toast.makeText(currentAdvertisements.this, "getJSON catch executed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            pd.dismiss();
            try {
                if (response.equals("Success")) ;
                Toast.makeText(currentAdvertisements.this, "Advertisement Closed", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(currentAdvertisements.this, "Oops!catch executed...", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

        }
    }

    public void delete(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hide Advertisement");

        builder.setMessage("Are you sure you want to hide Advertisement with id= "+AddId+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteAdd delObj=new deleteAdd();
                delObj.execute();
                listAdvId.clear();
                listAdvNum.clear();
                listAdvDesc.clear();
                listAdvDateCreated.clear();
                listAdvAttachment.clear();
                listAdvStatus.clear();
                listAdvNoOfDiv.clear();

                getAdds getObj=new getAdds();
                getObj.execute();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog mes_box = builder.create();
        mes_box.show();
    }

    public void close(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Close Advertisement");

        builder.setMessage("Are you sure you want to close the Advertisement with id= "+AddId+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                closeAdd closeObj=new closeAdd();
                closeObj.execute();
                listAdvId.clear();
                listAdvNum.clear();
                listAdvDesc.clear();
                listAdvDateCreated.clear();
                listAdvAttachment.clear();
                listAdvStatus.clear();
                listAdvNoOfDiv.clear();

                getAdds getObj=new getAdds();
                getObj.execute();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog mes_box = builder.create();
        mes_box.show();
    }

    public void showalert(String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        //builder.setTitle("Results");
        builder.setMessage(msg);
        builder.setNeutralButton("OK",null);
        AlertDialog showmsg=builder.create();
        showmsg.show();
    }

    public class advAdapter extends ArrayAdapter {

        Context context;
        int resource;

        ArrayList<Integer> arrListAdvId;
        ArrayList<String> arrListAdvNum;
        ArrayList<String> arrListAdvDesc;
        ArrayList<String> arrListAdvAttachment;
        ArrayList<String> arrListAdvDateCreated;
        ArrayList<String> arrListAdvStatus;

        String addId;

        //ArrayList<String> statusListSpinner;


        public advAdapter(Context context, int resource, ArrayList<Integer> listAdvId, ArrayList<String> listAdvNum, ArrayList<String> listAdvDesc, ArrayList<String> listAdvAttachment, ArrayList<String> listAdvDateCreated, ArrayList<String> listAdvStatus) {
            super(context, resource, listAdvId);

            this.context=context;
            this.resource=resource;
            arrListAdvId=listAdvId;
            arrListAdvNum=listAdvNum;
            arrListAdvDesc=listAdvDesc;
            arrListAdvAttachment=listAdvAttachment;
            arrListAdvDateCreated=listAdvDateCreated;
            arrListAdvStatus=listAdvStatus;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;

            final int index=position;

            view= LayoutInflater.from(context).inflate(R.layout.list_current_adv,parent,false);
            TextView txt_id=view.findViewById(R.id.textViewCA_Id);
            TextView txt_advno=view.findViewById(R.id.textViewCA_AdvNo);
            TextView txt_desc=view.findViewById(R.id.textViewCA_Desc);
            final TextView txt_attachment=view.findViewById(R.id.textViewCA_Attachment);
            TextView txt_dateCreated=view.findViewById(R.id.textViewCA_DateCreated);
            TextView txt_status=view.findViewById(R.id.textViewCA_Status);
            // Spinner spAdv=view.findViewById(R.id.textViewCA_Status);
            Button btn_edit=view.findViewById(R.id.textViewCA_Edit);
            Button btn_delete=view.findViewById(R.id.textViewCA_Delete);
            Button btn_close=view.findViewById(R.id.textViewCA_Close);
            //statusListSpinner=new ArrayList<>();
       /* statusListSpinner.add("Open");
        statusListSpinner.add("Close");
        ArrayAdapter<String> advStatusAdapter=new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,statusListSpinner);
        spAdv.setAdapter(advStatusAdapter);*/

            txt_id.setText(arrListAdvId.get(position).toString());
            txt_advno.setText(arrListAdvNum.get(position).toString());
            txt_desc.setText(arrListAdvDesc.get(position).toString());
            txt_attachment.setText(arrListAdvAttachment.get(position).toString());
            txt_dateCreated.setText(arrListAdvDateCreated.get(position).toString());
            txt_status.setText(arrListAdvStatus.get(position).toString());

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(getContext(),Test_create__adv.class);
                    intent.putExtra("keyId",arrListAdvId.get(index).toString().trim());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getContext(), "Record at position "+index+" deleted", Toast.LENGTH_SHORT).show();
                 /*   addId=arrListAdvId.get(index).toString().trim();
                    Intent intent=new Intent(getContext(),currentAdvertisements.class);
                    intent.putExtra("keyDelId",addId);
                    context.startActivity(intent);*/
                    AddId=arrListAdvId.get(index).toString().trim();
                    delete();

                }
            });

            txt_attachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        String fileName=txt_attachment.getText().toString().trim();
                        String url = "http://202.164.39.172:2345/test/Prsc_recruitment/images/"+fileName;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(url), "text/html");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(), "File not found", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddId=arrListAdvId.get(index).toString().trim();
                    close();
                }
            });

            return view;
        }

    }

}
