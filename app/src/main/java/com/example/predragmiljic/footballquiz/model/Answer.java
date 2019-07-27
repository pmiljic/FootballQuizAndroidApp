package com.example.predragmiljic.footballquiz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("is_selected")
    @Expose
    private boolean isSelected;

    public Answer(int id, String answer, boolean isSelected) {
        this.id=id;
        this.answer=answer;
        this.isSelected=isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAnswer() {
        return answer;
    }

}
