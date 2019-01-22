package com.admin.nidhi_khushali;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.win_10.newapp.R;

import java.util.ArrayList;

public class advAdapter extends ArrayAdapter {

    Context context;
    int resource;

    class_current_adv obj=new class_current_adv();

    ArrayList<Integer> arrListAdvId;
    ArrayList<String> arrListAdvNum;
    ArrayList<String> arrListAdvDesc;
    ArrayList<String> arrListAdvAttachment;
    ArrayList<String> arrListAdvDateCreated;
    ArrayList<String> arrListAdvStatus;

    String addId;

    //ArrayList<String> statusListSpinner;


    public advAdapter(Context context, int resource, ArrayList<Integer> listAdvId, ArrayList<String> listAdvNum, ArrayList<String> listAdvDesc, ArrayList<String> listAdvAttachment, ArrayList<String> listAdvDateCreated, ArrayList<String> listAdvStatus) {
        super(context, resource, listAdvId);

        this.context=context;
        this.resource=resource;
        arrListAdvId=listAdvId;
        arrListAdvNum=listAdvNum;
        arrListAdvDesc=listAdvDesc;
        arrListAdvAttachment=listAdvAttachment;
        arrListAdvDateCreated=listAdvDateCreated;
        arrListAdvStatus=listAdvStatus;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;

        final int index=position;

        view= LayoutInflater.from(context).inflate(R.layout.list_current_adv,parent,false);
        TextView txt_id=view.findViewById(R.id.textViewCA_Id);
        TextView txt_advno=view.findViewById(R.id.textViewCA_AdvNo);
        TextView txt_desc=view.findViewById(R.id.textViewCA_Desc);
        final TextView txt_attachment=view.findViewById(R.id.textViewCA_Attachment);
        TextView txt_dateCreated=view.findViewById(R.id.textViewCA_DateCreated);
        TextView txt_status=view.findViewById(R.id.textViewCA_Status);
       // Spinner spAdv=view.findViewById(R.id.textViewCA_Status);
        Button btn_edit=view.findViewById(R.id.textViewCA_Edit);
        Button btn_delete=view.findViewById(R.id.textViewCA_Delete);
        //statusListSpinner=new ArrayList<>();
       /* statusListSpinner.add("Open");
        statusListSpinner.add("Close");
        ArrayAdapter<String> advStatusAdapter=new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,statusListSpinner);
        spAdv.setAdapter(advStatusAdapter);*/

        txt_id.setText(arrListAdvId.get(position).toString());
        txt_advno.setText(arrListAdvNum.get(position).toString());
        txt_desc.setText(arrListAdvDesc.get(position).toString());
        txt_attachment.setText(arrListAdvAttachment.get(position).toString());
        txt_dateCreated.setText(arrListAdvDateCreated.get(position).toString());
        txt_status.setText(arrListAdvStatus.get(position).toString());

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(),Test_create__adv.class);
                intent.putExtra("keyId",arrListAdvId.get(index).toString().trim());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Record at position "+index+" deleted", Toast.LENGTH_SHORT).show();
                addId=arrListAdvId.get(index).toString().trim();
                Intent intent=new Intent(getContext(),currentAdvertisements.class);
                intent.putExtra("keyDelId",addId);
                context.startActivity(intent);

            }
        });

        txt_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String url = txt_attachment.getText().toString().trim();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "text/html");
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
