package com.example.win_10.newapp;

public class Advertisement_class {


    String id;
    String adv_no;
    String Description;


    public Advertisement_class(){


    }

    public Advertisement_class(String id, String adv_no, String description) {
        this.id = id;
        this.adv_no = adv_no;
        Description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdv_no() {
        return adv_no;
    }

    public void setAdv_no(String adv_no) {
        this.adv_no = adv_no;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
