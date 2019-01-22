package com.example.win_10.newapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Advertisement_class> {

    Context context;
    int resource;
    ArrayList<Advertisement_class> user;
    Advertisement_class aClass;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Advertisement_class> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        user=objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view=LayoutInflater.from(context).inflate(resource,parent,false);
        TextView txtId=(TextView)view.findViewById(R.id.textViewId);
        TextView txtAdvNo=(TextView)view.findViewById(R.id.textViewAdvNo_);
        TextView txtDesc=(TextView)view.findViewById(R.id.textViewDesc);
        Button button=(Button)view.findViewById(R.id.buttonApply);

        aClass=user.get(position);
        txtAdvNo.setText(aClass.getAdv_no());
        txtDesc.setText(aClass.getDescription());
        txtId.setText(aClass.getId());
        return view;
    }
}
