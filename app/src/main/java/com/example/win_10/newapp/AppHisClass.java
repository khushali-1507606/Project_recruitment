package com.example.win_10.newapp;

public class AppHisClass {
    int count;
    String advNo;
    String DivisionAppliedFor;

    String AppliedFor;
    String AppliedDate;




    public AppHisClass(int count, String advNo, String divisionAppliedFor, String appliedFor, String appliedDate) {
        this.count = count;
        this.advNo = advNo;
        DivisionAppliedFor = divisionAppliedFor;
        AppliedFor = appliedFor;
        AppliedDate = appliedDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public String getAppliedFor() {
        return AppliedFor;
    }

    public void setAppliedFor(String appliedFor) {
        AppliedFor = appliedFor;
    }

    public String getAppliedDate() {
        return AppliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        AppliedDate = appliedDate;
    }
}
