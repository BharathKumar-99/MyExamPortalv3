package com.jrcreations.myexamportal.Home.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jrcreations.myexamportal.Home.Quiz.Quiz;
import com.jrcreations.myexamportal.Model.MockTestModel;
import com.jrcreations.myexamportal.R;

import java.util.List;

public class QuizesRecycle extends RecyclerView.Adapter<QuizesRecycle.ViewHolder>{

    private Context context;
    private List<MockTestModel> list;

    public QuizesRecycle(Context context, List<MockTestModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public QuizesRecycle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.selectedui_block, parent, false);
        return new QuizesRecycle.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MockTestModel users = list.get(position);

        holder.start.setText("Start Quiz");
        holder.start.setOnClickListener(v->{
            Intent i = new Intent(context, Quiz.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });
        holder.name.setText(users.getName());
        holder.marks.setText("Total Marks"+String.valueOf(users.getMarks()));
        holder.time.setText("Total Time:"+String.valueOf(users.getTime()));
        holder.questions.setText("Questions:"+ users.getQuestions());

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button start;
        TextView name,questions,time,marks;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            start=itemView.findViewById(R.id.startquiz);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);
            marks=itemView.findViewById(R.id.marks);
            questions=itemView.findViewById(R.id.questions);
        }
    }
}
