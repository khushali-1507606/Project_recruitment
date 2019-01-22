package com.example.win_10.newapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.nidhi_khushali.ExamHomeAct4;
import com.util.Session;

import java.util.ArrayList;

public class NewNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecModelClass recModelClass;
    RecyclerView recyclerView;
    ArrayList<RecModelClass> arrayList;
    RecyclerAdapter adapter;
    String name;
    String email;
    String password,phone;
    TextView txtname,txtEmail;
    Session sessionObj;

    TextView txtHeading;

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_navigation);

        sessionObj=new Session(this);
        isInternetOn();
        if(ContextCompat.checkSelfPermission(NewNavigation.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},RESULT_LOAD_IMAGE);
        }
        if (checkPermission()) {
            //main logic or main code

            // . write your main code to execute, It will execute if the permission is already given.

        } else {
            requestPermission();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        txtname=(TextView)header.findViewById(R.id.textViewName);
        txtEmail=(TextView)header.findViewById(R.id.textViewEmail);
        txtHeading=findViewById(R.id.welcome);
        txtHeading.setSelected(true);
        txtHeading.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        txtHeading.setSingleLine(true);
        /*Intent intent1=getIntent();
        Bundle bundle1=intent1.getBundleExtra("keyBundle");
         name=bundle1.getString("keyName");
         email=bundle1.getString("keyEmail");
         password=bundle1.getString("keyPass");
         phone=bundle1.getString("keyPhone");*/
        name=sessionObj.getName();
        email=sessionObj.getEmail();
        password=sessionObj.getPass();
        phone=sessionObj.getPhone();
        txtname.setText(name);
        txtEmail.setText(email);
        // txtname.setText("keshav");
        //txtEmail.setText("keshav@gmail.com");


        recModelClass=new RecModelClass(R.drawable.apphis,"Application ","History");
        RecModelClass recModelClass1=new RecModelClass(R.drawable.jb2,"Apply","Here");
        RecModelClass recModelClass2=new RecModelClass(R.drawable.iconexam,"Exam","");

        arrayList=new ArrayList<>();
        arrayList.add(recModelClass1);
        arrayList.add(recModelClass2);
        arrayList.add(recModelClass);

        RecyclerViewClickListener listener = (view, position) -> {
            // Toast.makeText(view.getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
            switch (position){

                case 0:

                    //Toast.makeText(this, "Advertisements", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NewNavigation.this,showAddsToUsers.class));
                    break;
                case 1:

                    //Toast.makeText(this, "Exam", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NewNavigation.this,ExamHomeAct4.class));
                    break;

                case 2:

                    //Toast.makeText(this, "Application History", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NewNavigation.this,ApplicationHistory.class));
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
                    AlertDialog.Builder builder=new AlertDialog.Builder(NewNavigation.this);
                    builder.setTitle("Alert!");
                    builder.setIcon(R.drawable.alert);
                    builder.setMessage("To continue allow this app access to External Storage");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(ContextCompat.checkSelfPermission(NewNavigation.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(NewNavigation.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},RESULT_LOAD_IMAGE);
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
        new AlertDialog.Builder(NewNavigation.this)
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
                    NewNavigation.this).create();

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account) {
            Intent intent=new Intent(NewNavigation.this,ChangeUSerPassword.class);
            Bundle bundle=new Bundle();
            bundle.putString("keyPass",password);
            bundle.putString("keyName",name);
            bundle.putString("keyEmail",email);
            bundle.putString("keyPhone",phone);

            intent.putExtra("keyBundle2",bundle);

            startActivity(intent);

        } else if (id == R.id.about) {

            startActivity(new Intent(NewNavigation.this,AboutUs.class));
            finish();
        }

        else if (id == R.id.logout) {
            //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            sessionObj.setUserLoginVar("false");
            startActivity(new Intent(NewNavigation.this,Login.class));
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
