package com.example.quizapp.Model;

public class ProgramingModel {

    int pics;
    String text;

    public ProgramingModel(int pics, String text) {
        this.pics = pics;
        this.text = text;
    }

    public int getPics() {
        return pics;
    }

    public void setPics(int pics) {
        this.pics = pics;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
