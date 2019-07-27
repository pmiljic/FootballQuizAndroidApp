package com.example.predragmiljic.footballquiz.viewModel;

import com.example.predragmiljic.footballquiz.model.Answer;
import com.example.predragmiljic.footballquiz.model.Candidate;
import com.example.predragmiljic.footballquiz.model.QuestionAnswers;
import com.example.predragmiljic.footballquiz.model.Quiz;

import com.example.predragmiljic.footballquiz.model.QuizQuestions;
import com.example.predragmiljic.footballquiz.remote.APIService;
import com.example.predragmiljic.footballquiz.remote.APIUtils;
import com.example.predragmiljic.footballquiz.remote.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionAnswersViewModel extends ViewModel {

    private LiveData<List<QuestionAnswers>> questionAnswers = new MutableLiveData<>();
    private int currentQuestionNumber, numberOfQuestions;
    private APIService apiService = APIUtils.getAPIService();
    private Candidate candidate;
    private Quiz quiz;

    public QuestionAnswersViewModel() {

    }

    public void setQuestionAnswers(ArrayList<QuestionAnswers> arrayListQuestionAnswers) {
        ((MutableLiveData<List<QuestionAnswers>>) questionAnswers).setValue(arrayListQuestionAnswers);
    }

    public LiveData<List<QuestionAnswers>> getQuestionAnswers() {
        numberOfQuestions = Objects.requireNonNull(questionAnswers.getValue()).size();

        return questionAnswers;
    }

    public void postQuiz(Result<Quiz> result) {
        quiz = new Quiz(candidate.getId());
        QuizQuestions quizQuestions = new QuizQuestions(quiz, questionAnswers.getValue());
        apiService.postQuiz(quizQuestions).enqueue(new Callback<QuizQuestions>() {
            @Override
            public void onResponse(@NonNull Call<QuizQuestions> call, @NonNull Response<QuizQuestions> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        quiz = response.body().getQuiz();
                        result.onSuccess(response.body().getQuiz());
                    }
                } else {
                    result.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<QuizQuestions> call, @NonNull Throwable t) {
                result.onFailure("Failed to connect to web server");
            }
        });
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public void setCurrentQuestionNumber(int currentQuestionNumber) {
        this.currentQuestionNumber = currentQuestionNumber;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public boolean isNotEmptyAnswer() {
        boolean answered = false;
        for (Answer a : Objects.requireNonNull(questionAnswers.getValue()).get(currentQuestionNumber).getAnswers()) {
            if (a.isSelected()) {
                answered = true;
                break;
            }
        }
        return answered;
    }

    public String getQuestionText() {
        return Objects.requireNonNull(questionAnswers.getValue()).get(currentQuestionNumber).getQuestion().getText();
    }

    public QuestionAnswers getCurrentQuestion() {
        return Objects.requireNonNull(questionAnswers.getValue()).get(currentQuestionNumber);
    }

    public Quiz getQuiz() {
        return quiz;
    }
}
