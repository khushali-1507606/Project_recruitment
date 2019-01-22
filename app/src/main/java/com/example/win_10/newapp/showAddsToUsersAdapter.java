package com.example.win_10.newapp;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class showAddsToUsersAdapter extends ArrayAdapter {

    Context context;
    int resource;

    ArrayList<Integer> listAddId;
    ArrayList<String> listAddNum;
    ArrayList<String> listAddDesc;
    ArrayList<String> listAddFile;
    ArrayList<String> listStartDate;
    ArrayList<String> listEndDate;


    public showAddsToUsersAdapter(@NonNull Context context, int resource, ArrayList listAddId, ArrayList listAddNum, ArrayList listAddDesc, ArrayList listAddFile, ArrayList listStartDate, ArrayList listEndDate) {
        super(context, resource, listAddId);

        this.context=context;
        this.resource=resource;
        this.listAddId=listAddId;
        this.listAddNum=listAddNum;
        this.listAddDesc=listAddDesc;
        this.listAddFile=listAddFile;
        this.listStartDate=listStartDate;
        this.listEndDate=listEndDate;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=null;

        view=LayoutInflater.from(context).inflate(R.layout.list_show_adds,parent,false);
        final int index;
        index=position;

        TextView txtAddId=view.findViewById(R.id.textViewShowAddsId);
        TextView txtAddNum=view.findViewById(R.id.textViewShowAddsAdvNo);
        TextView txtAddDesc=view.findViewById(R.id.textViewShowAddsDesc);
        TextView txtAddFile=view.findViewById(R.id.textViewShowAddsAttachedFile);
        TextView txtSdate=view.findViewById(R.id.textViewShowAddsStartDate);
        TextView txtEdate=view.findViewById(R.id.textViewShowAddsEndDate);
        Button btnApply=view.findViewById(R.id.buttonShowAdds_Apply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=index;

                String id=listAddId.get(pos).toString().trim();
                String addNum=listAddNum.get(pos);
                String addDesc=listAddDesc.get(pos);

                Intent intent=new Intent(getContext(),AppFillForm.class);
                intent.putExtra("keyAddId",id);
                intent.putExtra("keyAddNum",addNum);
                intent.putExtra("keyAddDesc",addDesc);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        txtAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String fileName=listAddFile.get(index);;
                    String url = "http://202.164.39.172:2345/test/Prsc_recruitment/images/"+fileName;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "text/html");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "File not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        if(position==0){
//            txtAddId.setText("ID");
//            txtAddNum.setText("Adv. Number");
//            txtAddDesc.setText("Description");
//            txtAddFile.setText("Attached File");
//            txtSdate.setText("Start Date");
//            txtEdate.setText("End Date");
//            btnApply.setVisibility(View.GONE);

        txtAddId.setText(listAddId.get(position).toString().trim());
        txtAddNum.setText(listAddNum.get(position).toString().trim());
        txtAddDesc.setText(listAddDesc.get(position).toString().trim());
        txtAddFile.setText(listAddFile.get(position).toString().trim());
        txtSdate.setText(listStartDate.get(position).toString().trim());
        txtEdate.setText(listEndDate.get(position).toString().trim());


        return view;
    }
}
