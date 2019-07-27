package com.example.predragmiljic.footballquiz.model;

import java.util.List;

public class QuizQuestions {

    private Quiz quiz;
    private List<QuestionAnswers> questionAnswers;

    public QuizQuestions(Quiz quiz, List<QuestionAnswers> questionAnswers) {
        this.quiz = quiz;
        this.questionAnswers=questionAnswers;
    }

    public Quiz getQuiz() {
        return quiz;
    }
}
