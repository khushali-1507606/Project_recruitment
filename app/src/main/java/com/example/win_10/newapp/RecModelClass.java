package com.example.win_10.newapp;

public class RecModelClass {
    int icon;
    String text;
    String text2;

    public RecModelClass(){                }

    public RecModelClass(int icon, String text, String text2) {
        this.icon = icon;
        this.text = text;
        this.text2 = text2;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}