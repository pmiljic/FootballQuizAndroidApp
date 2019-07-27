package com.example.predragmiljic.footballquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswers implements Parcelable {

    @SerializedName("question")
    @Expose
    private Question question;
    @SerializedName("answers")
    @Expose
    private List<Answer> answers;

    public Question getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    private QuestionAnswers(Parcel in) {
        question = (Question) in.readValue(Question.class.getClassLoader());
        if (in.readByte() == 0x01) {
            answers = new ArrayList<>();
            in.readList(answers, Answer.class.getClassLoader());
        } else {
            answers = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(question);
        if (answers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(answers);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<QuestionAnswers> CREATOR = new Parcelable.Creator<QuestionAnswers>() {
        @Override
        public QuestionAnswers createFromParcel(Parcel in) {
            return new QuestionAnswers(in);
        }

        @Override
        public QuestionAnswers[] newArray(int size) {
            return new QuestionAnswers[size];
        }
    };
}

