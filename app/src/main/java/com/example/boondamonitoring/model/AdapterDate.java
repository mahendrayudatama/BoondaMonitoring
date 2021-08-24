package com.example.boondamonitoring.model;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boondamonitoring.R;

import java.util.List;

public class AdapterDate extends RecyclerView.Adapter<AdapterDate.ViewHolder>{

    List<String> day;
    List<String> date;

    public AdapterDate(List<String> day, List<String> date) {
        this.day = day;
        this.date = date;
    }
    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_date, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDate.ViewHolder holder, int position) {
        holder.day.setText(day.get(position));
        holder.date.setText(date.get(position));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
//                if (holder.view.findViewById(R.id.cardViewDate).getBackground().toString() == "@drawable/viewcard"){
//                    holder.view.findViewById(R.id.cardViewDate).setBackground(Drawable.createFromPath("@drawable/viewcardnotfocused"));
//
//                }else{
//                    TransitionManager.beginDelayedTransition((ViewGroup) holder.view.findViewById(R.id.cardViewItem), new AutoTransition());
//                    holder.view.findViewById(R.id.expandableCardView).setVisibility(View.VISIBLE);
//
//                }

            }
        });
    }


    @Override
    public int getItemCount() {
        Log.d("----------------", String.valueOf(day.size()));
        return day.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView day, date;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
            view = itemView;
        }
    }
}
