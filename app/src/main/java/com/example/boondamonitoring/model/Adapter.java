package com.example.boondamonitoring.model;

import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> titles;
    List<String> content;
    List<String> time;

    public Adapter(List<String> title, List<String> time, List<String> content) {
        this.titles = title;
        this.content = content;
        this.time = time;
    }

    @NonNull

    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.content.setText(content.get(position));
        holder.time.setText(time.get(position));
        holder.view.findViewById(R.id.expandableCardView).setVisibility(View.GONE);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (holder.view.findViewById(R.id.expandableCardView).getVisibility() == View.VISIBLE){
                    holder.view.findViewById(R.id.expandableCardView).setVisibility(View.GONE);

                }else{
                    TransitionManager.beginDelayedTransition((ViewGroup) holder.view.findViewById(R.id.cardViewItem), new AutoTransition());
                    holder.view.findViewById(R.id.expandableCardView).setVisibility(View.VISIBLE);

                }

                Toast.makeText(v.getContext(),"cliked"+holder.view.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content, time;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titles);
            content = itemView.findViewById(R.id.noted);
            time = itemView.findViewById(R.id.time);
            view = itemView;
        }
    }
}
