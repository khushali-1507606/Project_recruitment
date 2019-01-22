package com.admin.nidhi_khushali;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.win_10.newapp.R;

public class Instructions extends AppCompatActivity {

    TextView txtTimer;
    Button btnStart;

    int time=2;
    String t="",testId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        txtTimer=findViewById(R.id.instructionTimer);
        btnStart=findViewById(R.id.buttonStartTest);

        Intent rcv=getIntent();
         t=rcv.getStringExtra("time");
         testId=rcv.getStringExtra("testId");

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Instructions.this,Quiz_activity.class);
                intent.putExtra("testId",testId);
                intent.putExtra("time",t);
                startActivity(intent);
                finish();
            }
        });

        start_timer(time);
    }

    public void start_timer(int time){

        new CountDownTimer(time*60000, 1000) {

            @Override
            public void onTick(long l) {
                long hrs=l/(1000*60*60);
                long min=l/(1000*60)- (hrs*60);
                long sec=l/(1000)- (min*60);

                txtTimer.setText("Time: "+min+" : "+sec);
            }

            @Override
            public void onFinish() {
                txtTimer.setText("Finished");
                Intent intent=new Intent(Instructions.this,Quiz_activity.class);
                intent.putExtra("testId",testId);
                intent.putExtra("time",t);
                startActivity(intent);
                finish();
            }

        }.start();

    }
}
