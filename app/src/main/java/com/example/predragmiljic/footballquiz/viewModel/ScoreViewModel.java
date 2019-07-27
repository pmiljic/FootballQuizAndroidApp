package com.example.predragmiljic.footballquiz.viewModel;

import com.example.predragmiljic.footballquiz.model.Candidate;
import com.example.predragmiljic.footballquiz.model.Quiz;

import androidx.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {

    private Candidate candidate;
    private Quiz quiz;
    private int numberOfQuestions, tvMaxScoreVisible = 4;

    public String getQuizScore() {
        String score = quiz.getScore().toString() + "/" + numberOfQuestions;
        if (quiz.getScore() == numberOfQuestions) {
            tvMaxScoreVisible = 0;
        }
        return score;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getTvMaxScoreVisible() {
        return tvMaxScoreVisible;
    }
}
