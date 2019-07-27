package com.example.predragmiljic.footballquiz.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.predragmiljic.footballquiz.R;
import com.example.predragmiljic.footballquiz.viewModel.ScoreViewModel;

public class ScoreFragment extends Fragment {

    private TextView tvScore, tvMaxScore;

    public ScoreFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        tvScore = view.findViewById(R.id.tvScore);
        tvMaxScore = view.findViewById(R.id.tvMaxScore);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ScoreViewModel viewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);
        Bundle data = getArguments();

        if (viewModel.getCandidate() == null || viewModel.getQuiz() == null) {
            if (data != null) {
                viewModel.setCandidate(data.getParcelable("candidate"));
                viewModel.setQuiz(data.getParcelable("quiz"));
                viewModel.setNumberOfQuestions(data.getInt("number_of_questions"));
            }
        }
        tvScore.setText(viewModel.getQuizScore());
        tvMaxScore.setVisibility(viewModel.getTvMaxScoreVisible());
    }
}
