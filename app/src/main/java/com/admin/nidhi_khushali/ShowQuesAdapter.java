package com.admin.nidhi_khushali;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.win_10.newapp.R;

import java.util.ArrayList;

public class ShowQuesAdapter extends ArrayAdapter<ShowQuestions> {

    Context context;

    ArrayList<ShowQuestions> list;
    int resource;
    public ShowQuesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ShowQuestions> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        list=objects;
    }

    int srNo=0;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=null;

        view= LayoutInflater.from(context).inflate(resource,parent,false);

        srNo++;
        TextView txtId=(TextView)view.findViewById(R.id.textViewQ_Id);
        TextView txtAdvNo=(TextView)view.findViewById(R.id.textViewQ_AdvNo);
        TextView txtDiv=(TextView)view.findViewById(R.id.textViewQDivision);
        TextView txtAppFor=(TextView)view.findViewById(R.id.textViewQAppliedPost);
        TextView time=(TextView)view.findViewById(R.id.textViewQTime);

        Button txtAppIn=(Button) view.findViewById(R.id.buttonStartTest);



        ShowQuestions examHomeAct4=list.get(position);

        txtId.setText(String.valueOf(srNo));
        txtAdvNo.setText(examHomeAct4.getAdvNo());
        txtDiv.setText(examHomeAct4.getDivisionAppliedFor());
        txtAppFor.setText(examHomeAct4.getAppliedPost());
        time.setText(examHomeAct4.getTime());
        txtAppIn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent=new Intent(getContext(),Instructions.class);
        intent.putExtra("testId",examHomeAct4.getId());
        intent.putExtra("time",examHomeAct4.getTime());
        context.startActivity(intent);
        //Toast.makeText(getContext(), "Test Id "+examHomeAct4.getId(), Toast.LENGTH_SHORT).show();

    }
});
        return view;
    }
}
