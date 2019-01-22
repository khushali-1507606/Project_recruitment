package com.example.win_10.newapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>
{
    private RecyclerViewClickListener mListener;

    Context context;
    ArrayList<RecModelClass> list;
    public RecyclerAdapter(Context context, ArrayList<RecModelClass> list,RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.list = list;
        mListener=recyclerViewClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new RecyclerViewHolder(view,mListener);

    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        RecModelClass recModelClass=list.get(position);
        holder.textView.setText(recModelClass.getText().toString().trim());
        holder.imageView.setBackgroundResource(recModelClass.getIcon());
        holder.textView1.setText(recModelClass.getText2().toString().trim());




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        TextView textView1;
        ImageView imageView;
        private RecyclerViewClickListener mListener;


        public RecyclerViewHolder(View itemView, RecyclerViewClickListener listener) {

            super(itemView);

            mListener = listener;
            itemView.setOnClickListener(this);
            textView=itemView.findViewById(R.id.textViewL);
            textView1=itemView.findViewById(R.id.textView2);
            imageView=itemView.findViewById(R.id.imageView);

        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }
}
