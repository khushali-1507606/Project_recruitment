package com.example.win_10.newapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppHistoryAdapter extends ArrayAdapter<AppHisClass> {
    Context context;
    ArrayList<AppHisClass> list;
    int resource;
    public AppHistoryAdapter(@NonNull Context context, int resource, @NonNull ArrayList<AppHisClass> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        list=objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view=null;

       view= LayoutInflater.from(context).inflate(resource,parent,false);


        TextView txtId=(TextView)view.findViewById(R.id.textViewCA_Id);
        TextView txtAdvNo=(TextView)view.findViewById(R.id.textViewCA_AdvNo);
        TextView txtDiv=(TextView)view.findViewById(R.id.textViewCA_DivisionNameApplied);
        TextView txtAppFor=(TextView)view.findViewById(R.id.textViewCA_AppFor);
        TextView txtAppIn=(TextView)view.findViewById(R.id.textViewCA_AppDate);


        AppHisClass appHisClass=list.get(position);
        txtId.setText(String.valueOf(appHisClass.getCount()));
        txtAdvNo.setText(appHisClass.getAdvNo());
        txtDiv.setText(appHisClass.getDivisionAppliedFor());
        txtAppFor.setText(appHisClass.getAppliedFor());
        txtAppIn.setText(appHisClass.getAppliedDate());









        return view;
    }
}
