package com.example.predragmiljic.footballquiz.remote;

import com.example.predragmiljic.footballquiz.model.Candidate;
import com.example.predragmiljic.footballquiz.model.QuestionAnswers;
import com.example.predragmiljic.footballquiz.model.QuizQuestions;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @GET("questions")
    Call<List<QuestionAnswers>> getQuestions();

    @POST("candidates")
    Call<Candidate> postCandidate(@Body Candidate candidate);

    @POST("quizzes")
    Call<QuizQuestions> postQuiz(@Body QuizQuestions quizQuestions);

}
