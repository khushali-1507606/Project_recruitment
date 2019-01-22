package com.example.win_10.newapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.nidhi_khushali.AdminNewMain;
import com.admin.nidhi_khushali.Test_create__adv;
import com.mail_sender.GMailSender;
import com.util.Session;
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
import java.util.Scanner;

public class Login extends AppCompatActivity {

    EditText edUSer,edPass;
    TextView textView;
    Button btnSbmit;
    Context context;
    private AwesomeValidation awesomeValidation;

    LinearLayout linearLayout,linearLayoutPassword;
    String user4,pass4;
    TextView txtRegister,skip,txtHeading;
    String response;


    ImageView eye;
    Session session;
    String email,getEmail;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    int count=0;

    String type="";
    Session sessionLogin;

    TextView txtGoD;
    LinearLayout llGoDirectly;
    Button btnGoAdmin, btnGoUser;
    String emailAct;//variable used while activating account
    String CodeAct;//variable used while activating account


    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnGoAdmin=findViewById(R.id.buttonGoDtoAdmin);
        btnGoUser=findViewById(R.id.buttonGoDtoUser);
        llGoDirectly=findViewById(R.id.llGoD);
        txtGoD=findViewById(R.id.textViewGoD);

        btnGoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,NewNavigation.class));
                finish();
            }
        });

        btnGoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,AdminNewMain.class));
                finish();
            }
        });
        llGoDirectly.setVisibility(View.GONE);
        txtGoD.setVisibility(View.GONE);

        sessionLogin=new Session(this);

        edUSer=(EditText)findViewById(R.id.editTextUser);
        edPass=(EditText)findViewById(R.id.editTextPassword);
        txtHeading=(TextView)findViewById(R.id.textViewHeading);

        txtHeading.setTranslationX(-1000f);
        txtHeading.animate().translationX(50f).setDuration(2000);
        context=this;
        btnSbmit=(Button)findViewById(R.id.buttonLogin);
        txtRegister=(TextView)findViewById(R.id.Register);
        session=new Session(context);
        skip=(TextView)findViewById(R.id.btnskip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this,LatestAdvs.class));

            }
        });
        linearLayout=(LinearLayout)findViewById(R.id.layout_login);
        linearLayoutPassword=(LinearLayout)findViewById(R.id.layout_password);
        linearLayout.setVisibility(View.GONE);
        spinner=(Spinner)findViewById(R.id.spinnerStart);
        adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item);
        adapter.add("---Login through---");
        adapter.add("Email");
        adapter.add("Mobile");
        spinner.setAdapter(adapter);
        context=this;
        textView=(TextView) findViewById(R.id.textViewForgotPassword);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){

                    case 1:

                        type="E";
                       // awesomeValidation.addValidation(this, R.id.editTextUsername, Patterns.EMAIL_ADDRESS, R.string.emailerror);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayoutPassword.setVisibility(View.VISIBLE);
textView.setVisibility(View.VISIBLE);
                        edUSer.setHint("Enter email");
                        edUSer.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        edUSer.requestFocus();

                        break;


                    case 2:
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayoutPassword.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                       // awesomeValidation.addValidation(this, R.id.editTextPhone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);


                        edUSer.setHint("Enter mobile");
                        edUSer.setInputType(InputType.TYPE_CLASS_NUMBER);
                        type="M";
                        edUSer.requestFocus();
                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        isInternetOn();
        if(ContextCompat.checkSelfPermission(Login.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},RESULT_LOAD_IMAGE);
        }
        if (checkPermission()) {
            //main logic or main code

            // . write your main code to execute, It will execute if the permission is already given.

        } else {
            requestPermission();
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Forgot password", Toast.LENGTH_SHORT).show();
                Dialog dialog=new Dialog(Login.this);
                dialog.setContentView(R.layout.forgot);
                EditText editText=(EditText) dialog.findViewById(R.id.editTextEnter);
                Button btn=(Button) dialog.findViewById(R.id.buttonSend);


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
            getEmail=editText.getText().toString().trim();
            if (getEmail.isEmpty()){
                AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                builder.setMessage("Please enter email address");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
            if (!isValidEmail(getEmail)){
                AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                builder.setMessage("Please enter valid email address");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                    ForgotPassword forgotPassword=new ForgotPassword();
                    forgotPassword.execute();
                    }
                });
                dialog.show();

                //Toast.makeText(context, "Forgot Password", Toast.LENGTH_SHORT).show();

            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));

            }
        });

        btnSbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user4=edUSer.getText().toString().trim();
                pass4=edPass.getText().toString().trim();

                if (user4.isEmpty()){

                    if (pass4.isEmpty()){
AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
if (type.equals("E")) {
    builder.setMessage("Please enter email address and password");
}
else {
    builder.setMessage("Please enter mobile and password");

}
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

                    else {
                        AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                        if (type.equals("E")) {
                            builder.setMessage("Please enter email address");
                        }
                        else {
                            builder.setMessage("Please enter mobile");

                        }
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });

                        AlertDialog dialog=builder.create();
                        dialog.show();
                        dialog.setCancelable(false);
return;
                    }}
                    if(pass4.isEmpty()){

                        AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);

                            builder.setMessage("Please enter password");


                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                    if (type.equals("E")){

                        if (!isValidEmail(user4)){
                            AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                            builder.setMessage("Please enter valid email address");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                    }

                    else {

                    if (user4.length()!=10){
                        AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                        builder.setMessage("Please enter valid mobile no address");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

                    }




               /* if (user4.equals("keshav@gmail.com")&&pass4.equals("keshav")){

                    startActivity(new Intent(Login.this,NewNavigation.class));

                }

               else if (user4.equals("admin@gmail.com")&&pass4.equals("admin")){
                    startActivity(new Intent(Login.this,AdminNewMain.class));


                }
               else {
                    Toast.makeText(context, "Enter valid username and password", Toast.LENGTH_SHORT).show();
                }*/
                LogU logU=new LogU();
                logU.execute();


            }
        });

      /*  eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count%2!=0)
                    edPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    edPass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });*/

    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case RESULT_LOAD_IMAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                    Toast.makeText(context, "EXTERNAL STORAGE PERMISSIONS GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                    builder.setTitle("Alert!");
                    builder.setIcon(R.drawable.alert);
                    builder.setMessage("To continue allow this app access to External Storage");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(ContextCompat.checkSelfPermission(Login.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(Login.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},RESULT_LOAD_IMAGE);
                            }
                        }
                    });
                    AlertDialog showmsg=builder.create();
                    showmsg.show();
                    //Toast.makeText(getApplicationContext(), "Please provide access to the gallery", Toast.LENGTH_LONG).show();
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;

            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    //Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("To continue please grant Camera Permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;

        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Login.this)
                .setMessage(message)
                .setIcon(R.drawable.alert)
                .setPositiveButton("OK", okListener)
                // .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            // Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            AlertDialog alertDialog = new AlertDialog.Builder(
                    Login.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Alert");

            // Setting Dialog Message
            alertDialog.setMessage("To continue please turn on internet connection");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.alert);

            // Setting OK Button
            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getApplicationContext(), "Okay", Toast.LENGTH_SHORT).show();
                    isInternetOn();
                }
            });
            // Showing Alert Message
            alertDialog.show();


            return false;
        }
        return false;
    }



class LogU extends AsyncTask{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Checking credentials.....please wait");
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            response=getJson3();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.i("Post","testing..."+user4.length());
            progressDialog.dismiss();

            //Toast.makeText(context, "Data"+user4+" - "+pass4, Toast.LENGTH_SHORT).show();
            try {
                JSONArray jarr = new JSONArray(response);
                //  Toast.makeText(context, "Enter ........", Toast.LENGTH_SHORT).show();
if (jarr.length()==0){

    AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
    if (type.equals("E")) {
        builder.setMessage("Invalid email address  or password");
    }
    else {
        builder.setMessage("Please enter mobile and password");

    }
    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();

        }
    });

    AlertDialog dialog=builder.create();
    dialog.show();
    dialog.setCancelable(false);
}
                if (jarr.length() > 0) {
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject job = jarr.getJSONObject(i);
                        String user = job.getString("EmailId");
                        session.setId(job.getString("UserId"));
                        session.setEmail(user);
                        String pass = job.getString("Password");
                        session.setPass(pass);

                        String phone = job.getString("MobileNo");
                        session.setPhone(phone);

                        String role = job.getString("User_Role");

                        String name = job.getString("FullName");
                        session.setName(name);

                        //Toast.makeText(context, "values "+user+"-"+pass+"-"+phone+"-"+role+"-"+name, Toast.LENGTH_SHORT).show();

                        if (role.equals("USERS")) {

                            sessionLogin.setUserLoginVar("true");
                            sessionLogin.setAdminLoginVar("false");

                            // Toast.makeText(context, "verified", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, NewNavigation.class);
                          /*  Bundle bundle = new Bundle();
                            bundle.putString("keyPass", pass);
                            bundle.putString("keyName", name);
                            bundle.putString("keyEmail", user);
                            bundle.putString("keyPhone", phone);

                            intent.putExtra("keyBundle", bundle);*/
                            startActivity(intent);
                            finish();


                        } else if (role.equals("Admin")) {

                            sessionLogin.setAdminLoginVar("true");
                            sessionLogin.setUserLoginVar("false");

                            //Toast.makeText(context, "Else if block", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, AdminNewMain.class);
                            startActivity(intent);
                            finish();


                        } else {


                            AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                            if (type.equals("E")){
                            builder.setMessage("Invalid email address or password");}
                            else {
                                builder.setMessage("Invalid mobile or password");
                            }
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();

                                }
                            });

                            AlertDialog dialog=builder.create();
                            dialog.show();
                            dialog.setCancelable(false);
                        }


                    }

                }
            }
            catch(Exception e)
            {

                AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                builder.setMessage(e.toString());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });


                AlertDialog dialog=builder.create();
                dialog.show();
                dialog.setCancelable(false);


                txtGoD.setVisibility(View.VISIBLE);
                llGoDirectly.setVisibility(View.VISIBLE);
            }

        }

        public String getJson3(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.login;
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
                if(type.equals("E")) {
                    data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(user4, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass4, "UTF-8") + "&" + URLEncoder.encode("mode", "UTF-8") + "=" + URLEncoder.encode("E", "UTF-8") + "&" + URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode("null", "UTF-8");
                }
                else {
                    data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode("null", "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass4, "UTF-8") + "&" + URLEncoder.encode("mode", "UTF-8") + "=" + URLEncoder.encode("M", "UTF-8") + "&" + URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode(user4, "UTF-8");



                }
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



    class ForgotPassword extends AsyncTask{
        String response;
        ProgressDialog progressDialog;
             @Override
            protected Object doInBackground(Object[] objects) {
                response=getjson2();
                return null;
        }

        public String getjson2(){
            StringBuilder stringBuilder=new StringBuilder();
            String url=Util.checkEmailExist;
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
                data= URLEncoder.encode("email","UTF-8") + "=" +URLEncoder.encode(getEmail,"UTF-8");
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




        @Override
        protected void onPreExecute() {
          progressDialog=new ProgressDialog(context);
         progressDialog.setMessage("Sending mail ...");
         progressDialog.show();


        }


        @Override
        protected void onPostExecute(Object o){
            progressDialog.dismiss();
            try
            {
                JSONArray jarr = new JSONArray(response);
                if (jarr.length()==0){
                    AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                    builder.setMessage("Please enter valid email address");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                if(jarr.length()>0) {
                    JSONObject job = jarr.getJSONObject(0);
                    String pass = job.getString("pass");
                    String ret = job.getString("return");

                    if (ret.equals("1")){

                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    GMailSender sender = new GMailSender("kapoor.khushali07@gmail.com","kapoorrocks07"
                                    );
                                    sender.sendMail("Prsc Recruitments", "Your password is "+pass,
                                            "kapoor.khushali07@gmail.com", getEmail);
                                } catch (Exception e) {
                                    Log.e("SendMail", e.getMessage(), e);
                                }
                            }

                        }).start();
                        AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                        builder.setMessage("Password sent");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

                    else if (ret.equals("2")){
                        AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                        builder.setMessage("Not valid email");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

                }
                else {
                    Toast.makeText(context," not valid user",Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e)
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                builder.setMessage(e.toString());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

            super.onPostExecute(o);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);

        
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            
            
            case R.id.prsc:
                //Toast.makeText(context, "Prsc  website", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this,PrscWebsite.class));
        }        
        
        return super.onOptionsItemSelected(item);
    }

    class ActivateAccount extends AsyncTask{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Verifying the account");
            progressDialog.show();
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

            Toast.makeText(context, "response "+response ,Toast.LENGTH_SHORT).show();
            try {
                JSONObject jarr = new JSONObject(response);
                //  Toast.makeText(context, "Enter ........", Toast.LENGTH_SHORT).show();
if (jarr.length()==0){
    AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
    builder.setMessage("Invalid email address or password");
    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                if (jarr.length() > 0) {
                    String code = jarr.getString("code");

                    if (code.equals(" ")){
                        AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                        builder.setMessage("Account Activated successfully");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });


                        AlertDialog dialog=builder.create();
                        dialog.show();
                        dialog.setCancelable(false);
                        return;
                       // Toast.makeText(context, "Account Activated successfully ", Toast.LENGTH_SHORT).show();
                    }
                    else if (code.equals("3")){
                        AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                        builder.setMessage("Account activated already");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });


                        AlertDialog dialog=builder.create();
                        dialog.show();
                        dialog.setCancelable(false);
                        return;
                        //Toast.makeText(context, "Account activated already", Toast.LENGTH_SHORT).show();

                    }
                    else if (code.equals("4")){

                        AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                        builder.setMessage("Invalid email id or code");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });


                        AlertDialog dialog=builder.create();
                        dialog.show();
                        dialog.setCancelable(false);
                        return;
                       // Toast.makeText(context, "Invalid email id or code ", Toast.LENGTH_SHORT).show();

                    }



                }


            }
            catch(Exception e)
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                builder.setMessage(e.toString());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });


                AlertDialog dialog=builder.create();
                dialog.show();
                dialog.setCancelable(false);
               // Toast.makeText(context, "catch block"+e.toString(), Toast.LENGTH_SHORT).show();
            }

        }

        public String getJson3(){
            StringBuilder stringBuilder=new StringBuilder();
            String url= Util.activateAccount;
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
                data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(emailAct, "UTF-8") + "&"
                        + URLEncoder.encode("actCode", "UTF-8") + "=" + URLEncoder.encode(CodeAct, "UTF-8");





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
                AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                builder.setMessage("Invalid email address or password");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                AlertDialog dialog=builder.create();

                dialog.show();
                dialog.setCancelable(false);
                e.printStackTrace();

            }


            return stringBuilder.toString();

        }

    }

    public void activateLogin(View view){


        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_activate_login);
        EditText edEmail=dialog.findViewById(R.id.editTextemail);
        EditText edCode=dialog.findViewById(R.id.editTextcode);
        Button btnSbmit=dialog.findViewById(R.id.buttonActivate);
        btnSbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailAct=edEmail.getText().toString().trim();
                CodeAct=edCode.getText().toString().trim();

                if (emailAct.isEmpty()){
                    AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                    builder.setMessage("Please enter  email address");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                if (CodeAct.isEmpty()){
                    AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                    builder.setMessage("Please enter activation code");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                if (!isValidEmail(emailAct)){
                    AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                    builder.setMessage("Please enter valid email address");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

                ActivateAccount ac=new ActivateAccount();
                ac.execute();




            }
        });
        dialog.show();
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
