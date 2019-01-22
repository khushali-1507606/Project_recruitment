package com.admin.nidhi_khushali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.win_10.newapp.Login;
import com.example.win_10.newapp.R;

public class Admin_main extends AppCompatActivity implements View.OnClickListener{

    ImageView img1,img2,img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main2);
        img1=(ImageView)findViewById(R.id.imageViewCreate);
        img2=(ImageView)findViewById(R.id.imageViewCandiDetails);
        img3=(ImageView)findViewById(R.id.imageViewLogout);


        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        int id=view.getId();
        switch (id){

            case R.id.imageViewCreate:
                startActivity(new Intent(Admin_main.this,Test_create__adv.class));


                break;

            case R.id.imageViewCandiDetails:
                startActivity(new Intent(Admin_main.this,Quiz_activity.class));


                break;

            case R.id.imageViewLogout:
                startActivity(new Intent(Admin_main.this,Login.class));
                finish();

                break;






        }
    }
}