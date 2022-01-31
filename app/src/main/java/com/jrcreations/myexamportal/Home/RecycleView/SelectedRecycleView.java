package com.jrcreations.myexamportal.Home.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jrcreations.myexamportal.Home.Quiz.Quiz;
import com.jrcreations.myexamportal.Home.SelectedItemNext.SeletedListUi;
import com.jrcreations.myexamportal.Model.MockTestModel;
import com.jrcreations.myexamportal.R;

import java.util.List;

public class SelectedRecycleView extends RecyclerView.Adapter<SelectedRecycleView.ViewHolder>{
   Context context;
   List<MockTestModel> mockTestModels;

    public SelectedRecycleView(Context context, List<MockTestModel> mockTestModels) {
        this.context=context;
        this.mockTestModels=mockTestModels;
    }

    @NonNull
    @Override
    public SelectedRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.selectedui, parent, false);
        return new SelectedRecycleView.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedRecycleView.ViewHolder holder, int position) {
        MockTestModel users = mockTestModels.get(position);

        holder.name.setText(users.getName());
        holder.question.setText(String.valueOf(users.getQuestions()));
        holder.marks.setText(String.valueOf(users.getMarks()));
        holder.time.setText(String.valueOf(users.getTime()));
        holder.card.setOnClickListener(v->{
            Intent i = new Intent(context, SeletedListUi.class);
            i.putExtra("keys",holder.name.getText().toString());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return mockTestModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,question,marks,time;

        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.examname);
            question=itemView.findViewById(R.id.questionssel);
            marks=itemView.findViewById(R.id.marks);
            time=itemView.findViewById(R.id.timetotal);
            card=itemView.findViewById(R.id.cardsel);
        }
    }
}
