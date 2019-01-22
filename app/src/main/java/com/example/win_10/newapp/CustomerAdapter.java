package com.example.win_10.newapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends ArrayAdapter<Adv_show_list> {

    Context context;


    public CustomerAdapter(Context context, int resource, ArrayList<Adv_show_list> objects) {
        super(context, resource, objects);
    }
}
