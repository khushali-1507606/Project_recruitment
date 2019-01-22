package com.admin.nidhi_khushali;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win_10.newapp.AppFillForm;
import com.example.win_10.newapp.R;

import java.util.ArrayList;

public class pastCandidatesAdapter extends ArrayAdapter {

    Context context;
    ArrayList<Integer> listCandiDetailsId;
    ArrayList<String> listCandiDetailsAdvNum;
    ArrayList<String> listCandiDetailsFullName;
    ArrayList<String> listCandiDetailsDadName;
    ArrayList<String> listCandiDetailsEmail;
    ArrayList<String> listCandiDetailsMobNo;
    ArrayList<String> listCandiDetailsAppliedFor;
    ArrayList<String> listCandiDetailsDivision;


    public pastCandidatesAdapter(@NonNull Context context, int resource, ArrayList<Integer> listCandiDetailsId, ArrayList<String> listCandiDetailsAdvNum, ArrayList<String> listCandiDetailsFullName, ArrayList<String> listCandiDetailsDadName, ArrayList<String> listCandiDetailsEmail, ArrayList<String> listCandiDetailsMobNo, ArrayList<String> listCandiDetailsDivision, ArrayList<String> listCandiDetailsAppliedFor)
    {
        super(context, resource, listCandiDetailsId);
        this.context=context;
        this.listCandiDetailsId=listCandiDetailsId;
        this.listCandiDetailsAdvNum=listCandiDetailsAdvNum;
        this.listCandiDetailsFullName=listCandiDetailsFullName;
        this.listCandiDetailsDadName=listCandiDetailsDadName;
        this.listCandiDetailsEmail=listCandiDetailsEmail;
        this.listCandiDetailsMobNo=listCandiDetailsMobNo;
        this.listCandiDetailsDivision=listCandiDetailsDivision;
        this.listCandiDetailsAppliedFor=listCandiDetailsAppliedFor;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=null;

        view= LayoutInflater.from(context).inflate(R.layout.list_past_candidates,parent,false);
        TextView txt_CD_id=view.findViewById(R.id.textViewCandidDetailsID);
        TextView txt_CD_advno=view.findViewById(R.id.textViewCandidDetailsAdvNo);
        TextView txt_CD_fullName=view.findViewById(R.id.textViewCandidDetailsFullName);
        TextView txt_CD_DadName=view.findViewById(R.id.textViewCandidDetailsDadName);
        TextView txt_CD_email=view.findViewById(R.id.textViewCandidDetailsEmail);
        TextView txt_CD_mobNo=view.findViewById(R.id.textViewCandidDetailsMobNo);
        TextView txt_CD_division=view.findViewById(R.id.textViewCandidDetailsDivision);
        TextView txt_CD_AppliedFor=view.findViewById(R.id.textViewCandidDetailsAppliedFor);
        Button btn_CD_edit=view.findViewById(R.id.buttonCandidateDetailsEdit);
        Button btn_CD_details=view.findViewById(R.id.buttonCandidateDetailsDetails);


        txt_CD_id.setText(listCandiDetailsId.get(position).toString());
        txt_CD_advno.setText(listCandiDetailsAdvNum.get(position).toString());
        txt_CD_fullName.setText(listCandiDetailsFullName.get(position).toString());
        txt_CD_DadName.setText(listCandiDetailsDadName.get(position).toString());
        txt_CD_email.setText(listCandiDetailsEmail.get(position).toString());
        txt_CD_mobNo.setText(listCandiDetailsMobNo.get(position).toString());
        txt_CD_division.setText(listCandiDetailsDivision.get(position).toString());
        txt_CD_AppliedFor.setText(listCandiDetailsAppliedFor.get(position).toString());

        btn_CD_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),AppFillForm.class);
                intent.putExtra("candiId",listCandiDetailsId.get(position).toString());
                intent.putExtra("advt",listCandiDetailsAdvNum.get(position).toString());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);            }
        });

        btn_CD_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),AppFillForm.class);
                intent.putExtra("candiId",listCandiDetailsId.get(position).toString());
                intent.putExtra("advt",listCandiDetailsAdvNum.get(position).toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);             }
        });

        return view;
    }
}
