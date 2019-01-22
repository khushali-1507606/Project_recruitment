package com.example.win_10.newapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.admin.nidhi_khushali.AdminNewMain;
import com.util.SessionReadCandiDetails;
import com.util.Util;

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

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class
LastAppFill extends AppCompatActivity {
    public static final int PICK_IMAGE_GALARY = 1;
    Bitmap bitmap;
    String candiId = "";

    String GetImageNameEditText = "photo_upload_gallery";
    Context context;
    SaveAppData saveAppData;
    String fileName = "myFile";
    ImageView imageView;
    Button btnUpload;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btnSave, btnSavP, btnBack;
    String mode;
    EditText edOp1, edOp2, edRef1, edRef2, edPlace, edDate;

    CheckBox checkDeclaration;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener dateSetListener;

    SessionReadCandiDetails sessionReadObj;
    String op1, op2, ref1, ref2, place, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_app_fill);
        saveAppData = new SaveAppData();
myCalendar=Calendar.getInstance();
        sessionReadObj = new SessionReadCandiDetails(this);
        initViews();
        btnSave = (Button) findViewById(R.id.save);
        imageView = (ImageView) findViewById(R.id.imageView);
        context = this;
        btnSavP = (Button) findViewById(R.id.uploadPhoto);
        btnUpload = (Button) findViewById(R.id.upload);
        Intent rcv = getIntent();
        if (rcv.hasExtra("abc")) {
            candiId = rcv.getStringExtra("abc");
           // Toast.makeText(context, "candiId last"+candiId, Toast.LENGTH_SHORT).show();
            btnSave.setText("Update");
            mode="M";
        }
        else {
mode="A";
        }
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(LastAppFill.this, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel();//to set the date to a specified format


            }
        };

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                BackTask bt = new BackTask();
                bt.execute();


            }
        });

        btnBack = (Button) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LastAppFill.this, WorkExp.class));

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                op1 = edOp1.getText().toString().trim();
                ref1 = edRef1.getText().toString().trim();
                ref2 = edRef2.getText().toString().trim();
                place = edPlace.getText().toString().trim();
                date = edDate.getText().toString().trim();
               // Toast.makeText(context, "Data "+op1+"-"+ref1+"-"+ref2+"-"+place+"-"+date, Toast.LENGTH_SHORT).show();
                sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
                if (!checkDeclaration.isChecked()){
                    AlertDialog.Builder builder=new AlertDialog.Builder(LastAppFill.this);
                    builder.setMessage("You have to agree to our declaration to move forward");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                    return;
                }

            WorkA e = new WorkA();

                e.execute();
                Log.i("data", "data " + "-" + saveAppData.getCate() + "-" + saveAppData.getPos1() + "-" + saveAppData.getOrg1() + "-" + saveAppData.getMarital() + "-" + saveAppData.getM1() + "-" + saveAppData.getDl1() + "-" + saveAppData.getDj1() + "-" + saveAppData.getYp1() + "-" + saveAppData.getDeg2() + "-" + saveAppData.getUni1() + "-" + saveAppData.getSal4() + "-" + op1 + "--" + op2 + "--" + ref1 + "--" + ref2 + "--" + place + "--" + date);

//                Toast.makeText(LastAppFill.this, "Application Form Filled" + sharedPreferences.getString("adNo", "AdnoDefaualt") + "-" + sharedPreferences.getString("Post", "PostDefault") + "-" + sharedPreferences.getString("DivNo", "=") + "-" + sharedPreferences.getString("fname", "Fnamedefault") + "-" + sharedPreferences.getString("FatName", "FatNameDefault") + "-" + sharedPreferences.getString("Country", "CountryDefault") + "-" + sharedPreferences.getString("Country", "CountryDefault") + "-" + sharedPreferences.getString("Country", "CountryDefault") + "-" + sharedPreferences.getString("Country", "CountryDefault") + "-" + sharedPreferences.getString("D1", "=") + "-" + sharedPreferences.getString("U1", "=") + "-" + sharedPreferences.getString("M1", "=") + "-" + sharedPreferences.getString("SD1", "=") + "-" + sharedPreferences.getString("SY1", "=") + "-" + sharedPreferences.getString("org1", "=") + "-" + sharedPreferences.getString("pos1", "=") + "-" + sharedPreferences.getString("sal1", "=") + "-" + sharedPreferences.getString("iy1", "=") + "-" + sharedPreferences.getString("fy1", "=") + "-" + sharedPreferences.getString("iy2", "="), Toast.LENGTH_SHORT).show();



            }
        });

        btnSavP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), PICK_IMAGE_GALARY);
            }
        });


    }

    public void initViews() {
        edOp1 = (EditText) findViewById(R.id.editText1);
        edRef1 = (EditText) findViewById(R.id.edRef1);
        edRef2 = (EditText) findViewById(R.id.edRef2);
        edPlace = (EditText) findViewById(R.id.edPlace);
        edDate = (EditText) findViewById(R.id.eddate);
        checkDeclaration = findViewById(R.id.cbDec);

    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    public void setDetails() {

        Toast.makeText(LastAppFill.this, "last=" + sessionReadObj.getOpinion1(), Toast.LENGTH_SHORT).show();
        edOp1.setText(sessionReadObj.getOpinion1());
        edRef1.setText(sessionReadObj.getRef1());
        edRef2.setText(sessionReadObj.getRef2());
        edPlace.setText(sessionReadObj.getPlace());
        edDate.setText(sessionReadObj.getDate());
        checkDeclaration.setChecked(true);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "inside onActivityResult if block", Toast.LENGTH_SHORT).show();

        try {
            if (requestCode == PICK_IMAGE_GALARY && resultCode == RESULT_OK && null != data) {
                Toast.makeText(this, "inside if block", Toast.LENGTH_SHORT).show();
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    Log.d("b1", bitmap + "");
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(this, "You haven't selected any image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "exception", Toast.LENGTH_SHORT).show();


        }
    }


    private class BackTask extends AsyncTask<Void, Void, String> {
        TextView tv;

        protected void onPreExecute() {
            tv = (TextView) findViewById(R.id.txtview);
            tv.setText("Uploading the image. Please wait...");
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
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bo);// we compress the image into png format and then passed and set the quality to 100% and 3rd parameter is output stream
                byte[] data = bo.toByteArray();
                // Specify HttpClient parameters
                httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
                // Create InputStreamBody instance with specifying image data destination file name
                InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), "/index_files/" + GetImageNameEditText + ".jpg");
                Log.d("i11", GetImageNameEditText + "");
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
                tv.setText("The image was uploaded successfully.");
            else
                tv.setText("Failed to upload the image");
        }
    }


    class WorkA extends AsyncTask {


        String response;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Uploading work exp data");
            dialog.show();


        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response = getJSon();

            return null;

        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
           // Toast.makeText(context, "respons " + response, Toast.LENGTH_SHORT).show();

            dialog.dismiss();
            try {
                JSONObject jarr = new JSONObject(response);
               // Toast.makeText(context, "Enter ........", Toast.LENGTH_SHORT).show();

                if (jarr.length() > 0) {

                    String i = jarr.getString("code");

                    if (i.equals("1")) {
                        if (btnSave.getText().toString().trim().equals("Update")) {
                            Toast.makeText(context, "Form successfully updated", Toast.LENGTH_SHORT).show();
                            Intent intent2=new Intent(LastAppFill.this,AdminNewMain.class);
                            startActivity(intent2);
                            finish();
                        }
                        else                          {   Toast.makeText(context, "Form successfully filled", Toast.LENGTH_SHORT).show();

                        Intent intent2=new Intent(LastAppFill.this,NewNavigation.class);
                        startActivity(intent2);
                        finish();
                    }}


                }
            } catch (Exception e) {

                Toast.makeText(context, "catch block " + e.toString(), Toast.LENGTH_SHORT).show();
            }


        }


        public String getJSon() {

            StringBuilder stringBuilder = new StringBuilder();
            String url = Util.candi_insert_update;
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
                data = URLEncoder.encode("org_r1", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("org_r1", "="), "UTF-8") +
                        "&" + URLEncoder.encode("org_r2", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("org2", "="), "UTF-8") +
                        "&" + URLEncoder.encode("org_r3", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("org3", "="), "UTF-8") +
                        "&" + URLEncoder.encode("org_r4", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("org4", "="), "UTF-8") +
                        "&" + URLEncoder.encode("org_r5", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("org5", "="), "UTF-8") +
                        "&" + URLEncoder.encode("pos_r1", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("pos1", "="), "UTF-8") +
                        "&" + URLEncoder.encode("pos_r2", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("pos2", "="), "UTF-8") +
                        "&" + URLEncoder.encode("pos_r3", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("pos3", "="), "UTF-8") +
                        "&" + URLEncoder.encode("pos_r4", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("pos4", "="), "UTF-8") +
                        "&" + URLEncoder.encode("pos_r5", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("pos5", "="), "UTF-8") +
                        "&" + URLEncoder.encode("sal_d_r1", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("sal1", "="), "UTF-8") +
                        "&" + URLEncoder.encode("sal_d_r2", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("sal2", "="), "UTF-8") +
                        "&" + URLEncoder.encode("sal_d_r3", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("sal3", "="), "UTF-8") +
                        "&" + URLEncoder.encode("sal_d_r4", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("sal4", "="), "UTF-8") +
                        "&" + URLEncoder.encode("sal_d_r5", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("sal5", "="), "UTF-8") +
                        "&" + URLEncoder.encode("d_o_j_r1", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("iy1", "="), "UTF-8") +
                        "&" + URLEncoder.encode("d_o_j_r2", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("iy2", "="), "UTF-8") +
                        "&" + URLEncoder.encode("d_o_j_r3", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("iy3", "="), "UTF-8") +
                        "&" + URLEncoder.encode("d_o_j_r4", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("iy4", "="), "UTF-8") +
                        "&" + URLEncoder.encode("d_o_j_r5", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("iy5", "="), "UTF-8") +
                        "&" + URLEncoder.encode("d_o_l_r1", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("fy1", "="), "UTF-8") +
                        "&" + URLEncoder.encode("d_o_l_r2", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("fy2", "="), "UTF-8") +
                        "&" + URLEncoder.encode("d_o_l_r3", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("fy3", "="), "UTF-8") +
                        "&" + URLEncoder.encode("d_o_l_r4", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("fy4", "="), "UTF-8") +
                        "&" + URLEncoder.encode("d_o_l_r5", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("fy5", "="), "UTF-8") +
                        "&" + URLEncoder.encode("advt_no", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("adNo", "="), "UTF-8") +
                        "&" + URLEncoder.encode("DivNo", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("DivNo", "="), "UTF-8") +
                        "&" + URLEncoder.encode("post_app", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("Post", "="), "UTF-8") +
                        "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("fname", "="), "UTF-8") +
                        "&" + URLEncoder.encode("f_name", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("FatName", "="), "UTF-8") +
                        "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("Email", "="), "UTF-8") +
                        "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("gender", "="), "UTF-8") +
                        "&" + URLEncoder.encode("d_o_b", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("Dob", "="), "UTF-8") +
                        "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("Address", "="), "UTF-8") +
                        "&" + URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("Country", "="), "UTF-8") +
                        "&" + URLEncoder.encode("states", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("State", "="), "UTF-8") +
                        "&" + URLEncoder.encode("districts", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("District", "="), "UTF-8") +
                        "&" + URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("Contact", "="), "UTF-8") +
                        "&" + URLEncoder.encode("nationality", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("Nationality", "="), "UTF-8") +
                        "&" + URLEncoder.encode("category", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("Category", "="), "UTF-8") +
                        "&" + URLEncoder.encode("m_status", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("Marital", "="), "UTF-8") +
                        "&" + URLEncoder.encode("cert_r1", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("D1", "="), "UTF-8") +
                        "&" + URLEncoder.encode("cert_r2", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("D2", "="), "UTF-8") +
                        "&" + URLEncoder.encode("cert_r3", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("D3", "="), "UTF-8") +
                        "&" + URLEncoder.encode("cert_r4", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("D4", "="), "UTF-8") +
                        "&" + URLEncoder.encode("cert_r5", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("D5", "="), "UTF-8") +
                        "&" + URLEncoder.encode("coll_r1", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("U1", "="), "UTF-8") +
                        "&" + URLEncoder.encode("coll_r2", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("U2", "="), "UTF-8") +
                        "&" + URLEncoder.encode("coll_r3", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("U3", "="), "UTF-8") +
                        "&" + URLEncoder.encode("coll_r4", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("U4", "="), "UTF-8") +
                        "&" + URLEncoder.encode("coll_r5", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("U5", "="), "UTF-8") +
                        "&" + URLEncoder.encode("per_marks_r1", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("M1", "="), "UTF-8") +
                        "&" + URLEncoder.encode("per_marks_r2", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("M2", "="), "UTF-8") +
                        "&" + URLEncoder.encode("per_marks_r3", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("M3", "="), "UTF-8") +
                        "&" + URLEncoder.encode("per_marks_r4", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("M4", "="), "UTF-8") +
                        "&" + URLEncoder.encode("per_marks_r5", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("M5", "="), "UTF-8") +
                        "&" + URLEncoder.encode("y_o_p_r1", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("SY1", "="), "UTF-8") +
                        "&" + URLEncoder.encode("y_o_p_r2", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("SY2", "="), "UTF-8") +
                        "&" + URLEncoder.encode("y_o_p_r3", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("SY3", "="), "UTF-8") +
                        "&" + URLEncoder.encode("y_o_p_r4", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("SY4", "="), "UTF-8") +
                        "&" + URLEncoder.encode("y_o_p_r5", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("SY5", "="), "UTF-8") +
                        "&" + URLEncoder.encode("div_r1", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("SD1", "="), "UTF-8") +
                        "&" + URLEncoder.encode("div_r2", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("SD2", "="), "UTF-8") +
                        "&" + URLEncoder.encode("div_r3", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("SD3", "="), "UTF-8") +
                        "&" + URLEncoder.encode("div_r4", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("SD4", "="), "UTF-8") +
                        "&" + URLEncoder.encode("div_r5", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("SD5", "="), "UTF-8") +
                        "&" + URLEncoder.encode("opinion", "UTF-8") + "=" + URLEncoder.encode(op1, "UTF-8") +
                        "&" + URLEncoder.encode("ref_1", "UTF-8") + "=" + URLEncoder.encode(ref1, "UTF-8") +
                        "&" + URLEncoder.encode("ref_2", "UTF-8") + "=" + URLEncoder.encode(ref2, "UTF-8") +
                        "&" + URLEncoder.encode("p_o_dec", "UTF-8") + "=" + URLEncoder.encode(place, "UTF-8") +
                        "&" + URLEncoder.encode("d_o_dec", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") +
                        "&" + URLEncoder.encode("sp_chr", "UTF-8") + "=" + URLEncoder.encode("757", "UTF-8") +
                        "&" + URLEncoder.encode("div_name_app", "UTF-8") + "=" + URLEncoder.encode(sharedPreferences.getString("DivNo", "="), "UTF-8") +
                        "&" + URLEncoder.encode("mode", "UTF-8") + "=" + URLEncoder.encode(mode, "UTF-8")+
                        "&" + URLEncoder.encode("candId", "UTF-8") + "=" + URLEncoder.encode(candiId, "UTF-8")
                ;

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


    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edDate.setText(sdf.format(myCalendar.getTime()));
    }
}