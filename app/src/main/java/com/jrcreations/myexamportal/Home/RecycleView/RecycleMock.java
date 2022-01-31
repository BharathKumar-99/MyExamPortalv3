package com.jrcreations.myexamportal.Home.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jrcreations.myexamportal.Home.SelectedItem.SelectedItem;
import com.jrcreations.myexamportal.Model.MockTestModel;
import com.jrcreations.myexamportal.R;

import java.util.List;

public class RecycleMock  extends RecyclerView.Adapter<RecycleMock.ViewHolder>{

    private Context context;
    private List<MockTestModel> list;

    public RecycleMock(Context context, List<MockTestModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RecycleMock.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.uibutton, parent, false);
        return new RecycleMock.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MockTestModel users = list.get(position);

        holder.start.setText("Start Quiz");
//        holder.start.setOnClickListener(v->{
//            Intent i = new Intent(context, Quiz.class);
//
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
//        });
        holder.name.setText(users.getName());
        holder.time.setText("Total Questions:"+users.getQuestions()+" \n" +
                "Total Time:"+users.getTime()+" \n" +
                "Total Marks:"+users.getTime());
        holder.start.setOnClickListener(v->{
            Intent i = new Intent(context, SelectedItem.class);
            i.putExtra("slelectedui",holder.name.getText().toString());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button start;
        ImageView logo;
        TextView name,questions,time,marks;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logo=itemView.findViewById(R.id.imglogo);
            start=itemView.findViewById(R.id.start);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);

        }
    }
}

