package com.admin.nidhi_khushali;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win_10.newapp.Login;
import com.example.win_10.newapp.R;
import com.example.win_10.newapp.RecModelClass;
import com.example.win_10.newapp.RecyclerAdapter;
import com.example.win_10.newapp.RecyclerViewClickListener;
import com.util.Session;

import java.util.ArrayList;

public class AdminNewMain extends AppCompatActivity {
    RecyclerView recyclerView;

    RecyclerAdapter adapter;
    RecModelClass recModelClass,recModelClass1,recModelClass2,recModelClass3;
    ArrayList<RecModelClass> arrayList;
    Session sessionLogin;

    TextView txtHeading;

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_main);

        isInternetOn();
        if(ContextCompat.checkSelfPermission(AdminNewMain.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},RESULT_LOAD_IMAGE);
        }
        if (checkPermission()) {
            //main logic or main code

            // . write your main code to execute, It will execute if the permission is already given.

        } else {
            requestPermission();
        }

        txtHeading=findViewById(R.id.welcome);
        txtHeading.setSelected(true);
        txtHeading.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        txtHeading.setSingleLine(true);

        sessionLogin=new Session(this);
        recyclerView=(RecyclerView)findViewById(R.id.recAdmin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recModelClass=new RecModelClass(R.drawable.imgadd,"Manage","Advertisements");
        recModelClass1=new RecModelClass(R.drawable.candidetails,"Candidate","Details");
        recModelClass2=new RecModelClass(R.drawable.score,"Candidate","Score");
        recModelClass3=new RecModelClass(R.drawable.results,"Results","");


        arrayList=new ArrayList<>();
        arrayList.add(recModelClass);
        arrayList.add(recModelClass1);
        arrayList.add(recModelClass2);
        arrayList.add(recModelClass3);


        RecyclerViewClickListener listener = (view, position) -> {
            // Toast.makeText(view.getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
            switch (position){

                case 0:

                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    String ar[]={"Add new Advertisments","Current Advertisments","Advertisment History"};

                    builder.setItems(ar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (i==0){

                                // Toast.makeText(AdminNewMain.this, "Create", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminNewMain.this,Test_create__adv.class));
                            }

                            else if (i==1){
                                //Toast.makeText(AdminNewMain.this, "View current advertisments", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminNewMain.this,currentAdvertisements.class));
                            }

                            else if (i==2){

                                //Toast.makeText(AdminNewMain.this, "View advertisments history", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminNewMain.this,allAdvertisements.class));
                            }
                        }
                    });

                    AlertDialog alertDialog=builder.create();

                    alertDialog.show();
                    break;

                case 1:

                    //Toast.makeText(this, "Candidate Details", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder1=new AlertDialog.Builder(this);
                    String ar1[]={"For Current Recruitments","For Past Recruitments"};

                    builder1.setItems(ar1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (i==0){

                                //Toast.makeText(AdminNewMain.this, "Create", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminNewMain.this,CandidateDetails.class));

                            }

                            else if (i==1){
                                //Toast.makeText(AdminNewMain.this, "View current advertisments", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminNewMain.this,pastCandidates.class));
                            }
                        }
                    });

                    AlertDialog alertDialog1=builder1.create();

                    alertDialog1.show();
                    break;


                case 2:

                    //Toast.makeText(this, "Candidate Scores", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder2=new AlertDialog.Builder(this);
                    String ar2[]={"Current Candidates' Score","Past Candidates' Score"};

                    builder2.setItems(ar2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (i==0){

                                //Toast.makeText(AdminNewMain.this, "Create", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(AdminNewMain.this,CandidateScore.class);
                                intent.putExtra("mode","C");
                                startActivity(intent);
                                //startActivity(new Intent(AdminNewMain.this,CandidateDetails.class));

                            }

                            else if (i==1){
                                //Toast.makeText(AdminNewMain.this, "View current advertisments", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(AdminNewMain.this,CandidateScore.class);
                                intent.putExtra("mode","P");
                                startActivity(intent);                            }
                        }
                    });

                    AlertDialog alertDialog2=builder2.create();

                    alertDialog2.show();
                    break;

                case 3:

                    AlertDialog.Builder builder3=new AlertDialog.Builder(this);
                    String ar3[]={"Create Result","Currently Declared Results","Past Results"};

                    builder3.setItems(ar3, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (i==0){

                                // Toast.makeText(AdminNewMain.this, "Create", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminNewMain.this,Results_create.class));
                            }

                            else if (i==1){
                                //Toast.makeText(AdminNewMain.this, "View current advertisments", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(AdminNewMain.this,Results_show.class);
                                intent.putExtra("mode","C");
                                startActivity(intent);
                            }

                            else if (i==2){

                                //Toast.makeText(AdminNewMain.this, "View advertisments history", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(AdminNewMain.this,Results_show.class);
                                intent.putExtra("mode","P");
                                startActivity(intent);                            }
                        }
                    });

                    AlertDialog alertDialog3=builder3.create();

                    alertDialog3.show();
                    break;


            }

        };
        adapter=new RecyclerAdapter(this,arrayList,listener);
        recyclerView.setAdapter(adapter);


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
                    Toast.makeText(this, "EXTERNAL STORAGE PERMISSIONS GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(AdminNewMain.this);
                    builder.setTitle("Alert!");
                    builder.setIcon(R.drawable.alert);
                    builder.setMessage("To continue allow this app access to External Storage");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(ContextCompat.checkSelfPermission(AdminNewMain.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(AdminNewMain.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},RESULT_LOAD_IMAGE);
                            }
                        }
                    });
                    android.app.AlertDialog showmsg=builder.create();
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
        new android.app.AlertDialog.Builder(AdminNewMain.this)
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

            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                    AdminNewMain.this).create();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                sessionLogin.setAdminLoginVar("false");
                // Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminNewMain.this, Login.class));
                finish();

        }



        return true;
    }
}
