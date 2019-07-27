package com.example.predragmiljic.footballquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quiz implements Parcelable {

    @SerializedName("candidate")
    @Expose
    private Integer candidate;
    @SerializedName("score")
    @Expose
    private Integer score;

    public Quiz(int candidate) {
        this.candidate=candidate;
    }

    public Integer getCandidate() {
        return candidate;
    }

    public Integer getScore() {
        return score;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { //to deserialize object

        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    private Quiz(Parcel in) {
        this.candidate = in.readInt();
        this.score = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { //dest - The Parcel in which the object should be written, object can be serialized now
        dest.writeInt(this.candidate);
        dest.writeInt(this.score);
    }
}