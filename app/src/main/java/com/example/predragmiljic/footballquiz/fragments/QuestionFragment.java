package com.example.predragmiljic.footballquiz.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.predragmiljic.footballquiz.adapters.AnswerAdapter;
import com.example.predragmiljic.footballquiz.R;
import com.example.predragmiljic.footballquiz.model.Quiz;
import com.example.predragmiljic.footballquiz.remote.Result;
import com.example.predragmiljic.footballquiz.viewModel.QuestionAnswersViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

public class QuestionFragment extends Fragment {

    private TextView tvQuestionText, tvQuestionNumber;
    private Button btnNextQuestion;
    private AlertDialog loadingDialog;
    private RecyclerView rvAnswers;
    private QuestionAnswersViewModel viewModel;
    private ProgressBar progressBar;

    public QuestionFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        btnNextQuestion = view.findViewById(R.id.btnNextQuestion);
        tvQuestionNumber = view.findViewById(R.id.tvQuestionNumber);
        tvQuestionText = view.findViewById(R.id.tvQuestionText);
        rvAnswers = view.findViewById(R.id.rvAnswers);
        progressBar = view.findViewById(R.id.progressBar);

        loadingDialog = new SpotsDialog(getActivity(), R.style.loadingDialog);

        btnNextQuestion.setOnClickListener(v -> {
            if (viewModel.isNotEmptyAnswer()) {

                //if candidate has answered on all questions
                if (viewModel.getCurrentQuestionNumber() + 1 == viewModel.getNumberOfQuestions()) {
                    loadingDialog.show();
                    viewModel.postQuiz(new Result<Quiz>() {

                        @Override
                        public void onSuccess(Quiz result) {
                            loadingDialog.dismiss();
                            ScoreFragment scoreFragment = new ScoreFragment();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("candidate", viewModel.getCandidate());
                            bundle.putParcelable("quiz", viewModel.getQuiz());
                            bundle.putInt("number_of_questions", viewModel.getNumberOfQuestions());
                            scoreFragment.setArguments(bundle);
                            FragmentManager manager = getFragmentManager();
                            if (manager != null) {
                                manager.beginTransaction().replace(R.id.mainLayout, scoreFragment, scoreFragment.getTag()).commit();
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            loadingDialog.dismiss();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                        }
                    });

                } else if (viewModel.getCurrentQuestionNumber() + 1 < viewModel.getNumberOfQuestions()) {
                    viewModel.setCurrentQuestionNumber(viewModel.getCurrentQuestionNumber() + 1);
                    switchQuestion();

                    //if candidate is on his last question
                    if (viewModel.getCurrentQuestionNumber() + 1 == viewModel.getNumberOfQuestions()) {
                        btnNextQuestion.setText(R.string.finish);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(QuestionAnswersViewModel.class);
        if (viewModel.getCandidate() == null) {
            Bundle data = getArguments();
            if (data != null) {
                viewModel.setCandidate(data.getParcelable("candidate"));
                viewModel.setQuestionAnswers(data.getParcelableArrayList("questions"));
            }

        }
        loadingDialog.show();

        viewModel.getQuestionAnswers().observe(this, questionAnswers -> {
            switchQuestion();
            progressBar.setMax(questionAnswers.size());
            progressBar.setVisibility(View.VISIBLE);
            btnNextQuestion.setVisibility(View.VISIBLE);
            tvQuestionNumber.setVisibility(View.VISIBLE);
            tvQuestionText.setVisibility(View.VISIBLE);
            loadingDialog.dismiss();
        });
    }

    private void switchQuestion() {
        String questionNumber = "Question " + viewModel.getCurrentQuestionNumber() + 1;
        tvQuestionNumber.setText(questionNumber);
        tvQuestionText.setText(viewModel.getQuestionText());
        rvAnswers.setAdapter(new AnswerAdapter(viewModel.getCurrentQuestion()));
        rvAnswers.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar.setProgress(viewModel.getCurrentQuestionNumber() + 1);
    }
}