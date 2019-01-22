package com.example.win_10.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.nidhi_khushali.AdminNewMain;
import com.util.Session;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    LinearLayout linearLayout;
    Session sessionLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionLogin=new Session(this);
        if(sessionLogin.getUserLoginVar().equals("true")){
            startActivity(new Intent(SplashActivity.this,NewNavigation.class));
        finish();
        }
        else if (sessionLogin.getAdminLoginVar().equals("true")){
            startActivity(new Intent(SplashActivity.this,AdminNewMain.class));
        finish();}
        else{
        setContentView(R.layout.activity_splash);
        imageView=(ImageView)findViewById(R.id.imageViewPRSC);
        textView=(TextView) findViewById(R.id.textViewPRSC);
        linearLayout=(LinearLayout)findViewById(R.id.relSplash);

        Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animtiom);
        imageView.startAnimation(animation1);
        textView.startAnimation(animation1);

        handler.sendEmptyMessageDelayed(101,4000);
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==101){
                Intent intent=new Intent(SplashActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        }
    };

}
