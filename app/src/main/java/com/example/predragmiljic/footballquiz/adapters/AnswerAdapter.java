package com.example.predragmiljic.footballquiz.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.example.predragmiljic.footballquiz.model.Answer;
import com.example.predragmiljic.footballquiz.model.QuestionAnswers;
import com.example.predragmiljic.footballquiz.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private QuestionAnswers questionAnswers;
    private int selectedItem = -1;

    public AnswerAdapter(QuestionAnswers questionAnswers) {
        this.questionAnswers = questionAnswers;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (questionAnswers.getQuestion().getIsMultipleChoice()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_list_item, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_button_list_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (questionAnswers.getQuestion().getIsMultipleChoice()) {
            holder.cbAnswer.setText(questionAnswers.getAnswers().get(position).getAnswer());
            holder.cbAnswer.setChecked(questionAnswers.getAnswers().get(position).isSelected());

            holder.cbAnswer.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (holder.cbAnswer.isChecked()) {
                    questionAnswers.getAnswers().get(position).setSelected(true);
                } else {
                    holder.cbAnswer.setChecked(false);
                    questionAnswers.getAnswers().get(position).setSelected(false);
                }
            });
        } else {
            holder.rbAnswer.setText(questionAnswers.getAnswers().get(position).getAnswer());
            holder.rbAnswer.setChecked(questionAnswers.getAnswers().get(position).isSelected());
            holder.rbAnswer.setOnClickListener(v -> {
                if (position != selectedItem) {
                    for (Answer answer : questionAnswers.getAnswers()) {
                        answer.setSelected(false);
                    }
                    holder.rbAnswer.setChecked(true);
                    questionAnswers.getAnswers().get(position).setSelected(true);
                    selectedItem = position;
                    notifyDataSetChanged();
                } else {
                    selectedItem = -1;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return questionAnswers.getAnswers().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RadioButton rbAnswer;
        CheckBox cbAnswer;

        ViewHolder(View view) {
            super(view);
            rbAnswer = view.findViewById(R.id.rbAnswer);
            cbAnswer = view.findViewById(R.id.cbAnswer);

        }
    }
}