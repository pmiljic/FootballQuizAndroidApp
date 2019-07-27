package com.example.predragmiljic.footballquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Candidate implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;

    public Candidate(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { //to deserialize object

        public Candidate createFromParcel(Parcel in) {
            return new Candidate(in);
        }

        public Candidate[] newArray(int size) {
            return new Candidate[size];
        }
    };

    private Candidate(Parcel in) {
        this.id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { //dest - The Parcel in which the object should be written, object can be serialized now
        dest.writeInt(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
    }
}
