package com.example.win_10.newapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.win_10.newapp.R;
import android.widget.Toast;

import java.util.ArrayList;

public class latestAdvAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Integer> listAdvId;
    ArrayList<String> listAdvNum;
    ArrayList<String> listAdvDesc;
    ArrayList<String> listAdvAttachedFile;
    ArrayList<String> listAdvStartDate;
    ArrayList<String> listAdvEndDate;

    public latestAdvAdapter(Context context, int resource, ArrayList<Integer> listAdvId, ArrayList<String> listAdvNum, ArrayList<String> listAdvDesc, ArrayList<String> listAdvAttachedFile, ArrayList<String> listAdvStartDate, ArrayList<String> listAdvEndDate) {
        super(context, resource, listAdvId);
        this.context = context;
        this.resource=resource;
        this.listAdvId = listAdvId;
        this.listAdvNum = listAdvNum;
        this.listAdvDesc = listAdvDesc;
        this.listAdvAttachedFile = listAdvAttachedFile;
        this.listAdvStartDate = listAdvStartDate;
        this.listAdvEndDate = listAdvEndDate;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        view = LayoutInflater.from(context).inflate(R.layout.list_latest_adv, parent, false);
        TextView txt_Lt_Id = view.findViewById(R.id.textViewLtA_Id);
        TextView txt_Lt_advNo = view.findViewById(R.id.textViewLtA_AdvNo);
        TextView txt_Lt_desc = view.findViewById(R.id.textViewLtA_Desc);
        final TextView txt_Lt_attachedFile = view.findViewById(R.id.textViewLtA_AttachedFile);
        TextView txt_Lt_startDate = view.findViewById(R.id.textViewLtA_StartDate);
        TextView txt_Lt_endDate = view.findViewById(R.id.textViewLtA_EndDate);


        txt_Lt_Id.setText(listAdvId.get(position).toString());
        txt_Lt_advNo.setText(listAdvNum.get(position).toString());
        txt_Lt_desc.setText(listAdvDesc.get(position).toString());
        txt_Lt_attachedFile.setText(listAdvAttachedFile.get(position).toString());
        txt_Lt_startDate.setText(listAdvStartDate.get(position).toString());
        txt_Lt_endDate.setText(listAdvEndDate.get(position).toString());

        txt_Lt_attachedFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String fileName= txt_Lt_attachedFile.getText().toString().trim();
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

        return view;
    }
}
