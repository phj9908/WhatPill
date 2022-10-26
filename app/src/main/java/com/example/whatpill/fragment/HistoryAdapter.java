package com.example.whatpill.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatpill.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private ArrayList<UserHistory> historyArrayList;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView pillName,illnessName;
        public ImageView delIv;

        public MyViewHolder(@NonNull View view) {
            super(view);
            pillName = view.findViewById(R.id.pilltv);
            illnessName = view.findViewById(R.id.illtv);
            delIv = view.findViewById(R.id.delIv);

            // 리사이클뷰 객체 삭제
            delIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    historyArrayList.remove(getAdapterPosition());

                    notifyItemRemoved(getAdapterPosition());
                    notifyItemChanged(getAdapterPosition(),historyArrayList.size());
                }
            });
        }


    }

    public HistoryAdapter(ArrayList<UserHistory> history){
        this.historyArrayList = history;
    }

    // 뷰홀더 생성 메서드
    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler,parent,false);

        return new MyViewHolder(view);
    }

    // 뷰홀더 재활용 메서드
   @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {

        holder.pillName.setText(historyArrayList.get(position).getPillName());
        holder.illnessName.setText(historyArrayList.get(position).getIllnessName());

//        holder.pillName.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//
//                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.)
//            }
//        });
    }




    @Override
    public int getItemCount() {
        return historyArrayList.size();
    }



}
