package com.admin.nidhi_khushali;

public class ShowQuestions  {
    String id;
    String advNo;
    String DivisionAppliedFor;

    String AppliedPost;

String Time;

    public ShowQuestions(String id, String advNo, String divisionAppliedFor, String appliedPost,String time) {
        this.id = id;
        this.advNo = advNo;
        DivisionAppliedFor = divisionAppliedFor;
        AppliedPost = appliedPost;
        Time=time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdvNo() {
        return advNo;
    }

    public void setAdvNo(String advNo) {
        this.advNo = advNo;
    }

    public String getDivisionAppliedFor() {
        return DivisionAppliedFor;
    }

    public void setDivisionAppliedFor(String divisionAppliedFor) {
        DivisionAppliedFor = divisionAppliedFor;
    }

    public String getAppliedPost() {
        return AppliedPost;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setAppliedPost(String appliedPost) {

        AppliedPost = appliedPost;
    }
}
