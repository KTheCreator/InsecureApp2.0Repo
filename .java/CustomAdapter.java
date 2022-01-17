package com.example.insecureapp20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/*Code inspired from : https://abhiandroid.com/programming/json */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    ArrayList<String> dates;
    ArrayList<String> weights;
    ArrayList<String> bodyFats;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> dates, ArrayList<String> weights, ArrayList<String> bodyFats){
        this.context = context;
        this.dates = dates;
        this.weights = weights;
        this.bodyFats = bodyFats;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position){
        holder.date.setText(dates.get(holder.getAdapterPosition()));
        holder.weight.setText(weights.get(holder.getAdapterPosition()));
        holder.bodyFat.setText(bodyFats.get(holder.getAdapterPosition()));
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(context,dates.get(holder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount(){
        return dates.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date, weight, bodyFat;
        public MyViewHolder(View itemView){
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.Date);
            weight = (TextView) itemView.findViewById(R.id.Weight);
            bodyFat = (TextView) itemView.findViewById(R.id.BodyFat);


        }
    }

}
