package com.example.win_10.newapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Adv_show_list extends AppCompatActivity {

    ArrayList<Advertisement_class> arr1;
    Advertisement_class advertisement_class1,advertisement_class2,advertisement_class3;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_);
        ListView listView=(ListView)findViewById(R.id.listView);

        arr1=new ArrayList<>();
        advertisement_class1=new Advertisement_class("1","Adv_123_245","post1");
        advertisement_class2=new Advertisement_class("2","Adv_123_246","post2");
        advertisement_class3=new Advertisement_class("3","Adv_123_247" ,"post3");

        arr1.add(advertisement_class1);
        arr1.add(advertisement_class2);
        arr1.add(advertisement_class3);
        adapter=new ListViewAdapter(this,R.layout.list_item2,arr1);
        listView.setAdapter(adapter);
    }
}
