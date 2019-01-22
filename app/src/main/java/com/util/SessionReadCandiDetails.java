package com.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionReadCandiDetails {

    SharedPreferences pref;

    public SessionReadCandiDetails(Context context){
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    String advNum, divName, post, fullName, dadName, email, gender, dob, address, country, state, district, mobNum, nationality, category, maritalStatus;
    String deg1, collegeUni1, YoP1, div1,
            deg2, collegeUni2, YoP2, div2,
            deg3, collegeUni3, YoP3,  div3,
            deg4, collegeUni4, YoP4,  div4,
            deg5, collegeUni5, YoP5,  div5;
    String org1, posHeld1, DoJ1, DoL1, salaryDrawn1,
            org2, posHeld2, DoJ2, DoL2, salaryDrawn2,
            org3, posHeld3, DoJ3, DoL3, salaryDrawn3,
            org4, posHeld4, DoJ4, DoL4, salaryDrawn4,
            org5, posHeld5, DoJ5, DoL5, salaryDrawn5;
    String opinion1, opinion2, ref1, ref2,
            pic, place, date ;

    String marks1,marks2,marks3,marks4,marks5;



    public SharedPreferences getPref() {
        return pref;
    }

    public void setPref(SharedPreferences pref) {
        this.pref = pref;
    }
//**********Login
   /* public String getVarLogin() {
        return pref.getString("varLogin","");    }

    public void setVarLogin(String varLogin) {
        pref.edit().putString("varLogin",varLogin).commit();    }

    public String getUserName() {
        return pref.getString("userName","");
    }

    public void setUserName(String userName) {
        pref.edit().putString("userName",userName).commit();    }

    public String getUserEmail() {
        return pref.getString("userEmail","");    }


    public void setUserEmail(String userEmail) {
        pref.edit().putString("userEmail",userEmail).commit();    }

    public String getUserPw() {
        return pref.getString("userPw","");    }


    public void setUserPw(String userPw) {
        pref.edit().putString("userPw",userPw).commit();    }

    public String getUserPhone() {
        return pref.getString("userPhone","");    }


    public void setUserPhone(String userPhone) {
        pref.edit().putString("userPhone",userPhone).commit();    }

   */

    public String getAdvNum() {
        return advNum;
    }

    public void setAdvNum(String advNum) {
        pref.edit().putString("advNum",advNum).commit();    }

    public String getDivName() {
        return divName;
    }

    public void setDivName(String divName) {
        pref.edit().putString("divName",divName).commit();    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        pref.edit().putString("post",post).commit();    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        pref.edit().putString("fullName",fullName).commit();    }

    public String getDadName() {
        return dadName;
    }

    public void setDadName(String dadName) {
        pref.edit().putString("dadName",dadName).commit();    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        pref.edit().putString("email",email).commit();    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        pref.edit().putString("gender",gender).commit();    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        pref.edit().putString("dob",dob).commit();    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        pref.edit().putString("address",address).commit();    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        pref.edit().putString("country",country).commit();    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        pref.edit().putString("state",state).commit();    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        pref.edit().putString("district",district).commit();    }

    public String getMobNum() {
        return mobNum;
    }

    public void setMobNum(String mobNum) {
        pref.edit().putString("mobNum",mobNum).commit();    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        pref.edit().putString("nationality",nationality).commit();    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        pref.edit().putString("category",category).commit();    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        pref.edit().putString("maritalStatus",maritalStatus).commit();    }

    //Act2_deg1

    public String getDeg1() {
        return pref.getString("deg1","");    }

    public void setDeg1(String deg1) {
        pref.edit().putString("deg1",deg1).commit();    }

    public String getCollegeUni1() {
        return pref.getString("collegeUni1","");    }

    public void setCollegeUni1(String collegeUni1) {
        pref.edit().putString("collegeUni1",collegeUni1).commit();    }

    public String getYoP1() {
        return pref.getString("yoP1","");    }

    public void setYoP1(String yoP1) {
        pref.edit().putString("yoP1",yoP1).commit();    }

    public String getMarks1() { return pref.getString("marks1","");    }

    public void setMarks1(String marks1) {
        pref.edit().putString("marks1",marks1).commit();    }

    public String getDiv1() {
        return pref.getString("div1","");    }

    public void setDiv1(String div1) {
        pref.edit().putString("div1",div1).commit();    }

    //deg2

    public String getDeg2() {
        return pref.getString("deg2","");    }

    public void setDeg2(String deg2) {
        pref.edit().putString("deg2",deg2).commit();    }

    public String getCollegeUni2() {
        return pref.getString("collegeUni2","");    }

    public void setCollegeUni2(String collegeUni2) {
        pref.edit().putString("collegeUni2",collegeUni2).commit();    }

    public String getYoP2() {
        return pref.getString("yoP2","");    }

    public void setYoP2(String yoP2) {
        pref.edit().putString("yoP2",yoP2).commit();    }

    public String getMarks2() {
        return pref.getString("marks2","");    }

    public void setMarks2(String marks2) {
        pref.edit().putString("marks2",marks2).commit();    }

    public String getDiv2() {
        return pref.getString("div2","");    }

    public void setDiv2(String div2) {
        pref.edit().putString("div2",div2).commit();    }

    //deg3

    public String getDeg3() {
        return pref.getString("deg1","");    }

    public void setDeg3(String deg3) {
        pref.edit().putString("deg3",deg3).commit();    }

    public String getCollegeUni3() {
        return pref.getString("collegeUni3","");    }

    public void setCollegeUni3(String collegeUni3) {
        pref.edit().putString("collegeUni3",collegeUni3).commit();    }

    public String getYoP3() {
        return pref.getString("yoP3","");    }

    public void setYoP3(String yoP3) {
        pref.edit().putString("yoP3",yoP3).commit();    }

    public String getMarks3() {
        return pref.getString("marks3","");    }

    public void setMarks3(String marks3) {
        pref.edit().putString("marks3",marks3).commit();    }

    public String getDiv3() {
        return pref.getString("div3","");    }

    public void setDiv3(String div3) {
        pref.edit().putString("div3",div3).commit();    }

    //deg4

    public String getDeg4() {
        return pref.getString("deg4","");    }

    public void setDeg4(String deg4) {
        pref.edit().putString("deg4",deg4).commit();    }

    public String getCollegeUni4() {
        return pref.getString("collegeUni4","");    }

    public void setCollegeUni4(String collegeUni4) {
        pref.edit().putString("collegeUni4",collegeUni4).commit();    }

    public String getYoP4() {
        return pref.getString("yoP4","");    }

    public void setYoP4(String yoP4) {
        pref.edit().putString("yoP4",yoP4).commit();    }

    public String getMarks4() {
        return pref.getString("marks4","");    }

    public void setMarks4(String marks4) {
        pref.edit().putString("marks4",marks4).commit();    }

    public String getDiv4() {
        return pref.getString("div4","");    }

    public void setDiv4(String div4) {
        pref.edit().putString("div4",div4).commit();    }

//deg5

    public String getDeg5() {
        return pref.getString("deg5","");    }

    public void setDeg5(String deg5) {
        pref.edit().putString("deg5",deg5).commit();    }

    public String getCollegeUni5() {
        return pref.getString("collegeUni5","");    }

    public void setCollegeUni5(String collegeUni5) {
        pref.edit().putString("collegeUni5",collegeUni5).commit();    }

    public String getYoP5() {
        return pref.getString("yoP5","");    }

    public void setYoP5(String yoP5) {
        pref.edit().putString("yoP5",yoP5).commit();    }

    public String getMarks5() {
        return pref.getString("marks5","");    }

    public void setMarks5(String marks5) {
        pref.edit().putString("marks5",marks5).commit();    }

    public String getDiv5() {
        return pref.getString("div5","");    }

    public void setDiv5(String div5) {
        pref.edit().putString("div5",div5).commit();    }

//Act3_org1

    public String getOrg1() {
        return pref.getString("org1","");    }

    public void setOrg1(String org1) {
        pref.edit().putString("org1",org1).commit();    }

    public String getPosHeld1() {
        return pref.getString("posHeld1","");    }

    public void setPosHeld1(String posHeld1) {
        pref.edit().putString("posHeld1",posHeld1).commit();    }

    public String getDoJ1() {
        return pref.getString("doJ1","");    }

    public void setDoJ1(String doJ1) {
        pref.edit().putString("doJ1",doJ1).commit();    }

    public String getDoL1() {
        return pref.getString("doL1","");    }

    public void setDoL1(String doL1) {
        pref.edit().putString("doL1",doL1).commit();    }

    public String getSalaryDrawn1() {
        return pref.getString("salaryDrawn1","");    }

    public void setSalaryDrawn1(String salaryDrawn1) {
        pref.edit().putString("salaryDrawn1",salaryDrawn1).commit();    }

    //org2
    public String getOrg2() {
        return pref.getString("org2","");    }

    public void setOrg2(String org2) {
        pref.edit().putString("org2",org2).commit();    }

    public String getPosHeld2() {
        return pref.getString("posHeld2","");    }

    public void setPosHeld2(String posHeld2) {
        pref.edit().putString("posHeld2",posHeld2).commit();    }

    public String getDoJ2() {
        return pref.getString("doJ2","");    }

    public void setDoJ2(String doJ2) {
        pref.edit().putString("doJ2",doJ2).commit();    }

    public String getDoL2() {
        return pref.getString("doL2","");    }

    public void setDoL2(String doL2) {
        pref.edit().putString("doL2",doL2).commit();    }

    public String getSalaryDrawn2() {
        return pref.getString("salaryDrawn2","");    }

    public void setSalaryDrawn2(String salaryDrawn2) {
        pref.edit().putString("salaryDrawn2",salaryDrawn2).commit();    }

    //org3

    public String getOrg3() {
        return pref.getString("org3","");    }

    public void setOrg3(String org3) {
        pref.edit().putString("org3",org3).commit();    }

    public String getPosHeld3() {
        return pref.getString("posHeld3","");    }

    public void setPosHeld3(String posHeld3) {
        pref.edit().putString("posHeld3",posHeld3).commit();    }

    public String getDoJ3() {
        return pref.getString("doJ3","");    }

    public void setDoJ3(String doJ3) {
        pref.edit().putString("doJ3",doJ3).commit();    }

    public String getDoL3() {
        return pref.getString("doL3","");    }

    public void setDoL3(String doL3) {
        pref.edit().putString("doL3",doL3).commit();    }

    public String getSalaryDrawn3() {
        return pref.getString("salaryDrawn3","");    }

    public void setSalaryDrawn3(String salaryDrawn3) {
        pref.edit().putString("salaryDrawn3",salaryDrawn3).commit();    }

    //org4

    public String getOrg4() {
        return pref.getString("org4","");    }

    public void setOrg4(String org4) {
        pref.edit().putString("org4",org4).commit();    }

    public String getPosHeld4() {
        return pref.getString("posHeld4","");    }

    public void setPosHeld4(String posHeld4) {
        pref.edit().putString("posHeld4",posHeld4).commit();    }

    public String getDoJ4() {
        return pref.getString("doJ4","");    }

    public void setDoJ4(String doJ4) {
        pref.edit().putString("doJ4",doJ4).commit();    }

    public String getDoL4() {
        return pref.getString("doL4","");    }

    public void setDoL4(String doL4) {
        pref.edit().putString("doL4",doL4).commit();    }

    public String getSalaryDrawn4() {
        return pref.getString("salaryDrawn4","");    }

    public void setSalaryDrawn4(String salaryDrawn4) {
        pref.edit().putString("salaryDrawn4",salaryDrawn4).commit();    }

    //org5

    public String getOrg5() {
        return pref.getString("org5","");    }

    public void setOrg5(String org5) {
        pref.edit().putString("org5",org5).commit();    }

    public String getPosHeld5() {
        return pref.getString("posHeld5","");    }

    public void setPosHeld5(String posHeld5) {
        pref.edit().putString("posHeld5",posHeld5).commit();    }

    public String getDoJ5() {
        return pref.getString("doJ5","");    }

    public void setDoJ5(String doJ5) {
        pref.edit().putString("doJ5",doJ5).commit();    }

    public String getDoL5() {
        return pref.getString("doL5","");    }

    public void setDoL5(String doL5) {
        pref.edit().putString("doL5",doL5).commit();    }

    public String getSalaryDrawn5() {
        return pref.getString("salaryDrawn5","");    }

    public void setSalaryDrawn5(String salaryDrawn5) {
        pref.edit().putString("salaryDrawn5",salaryDrawn5).commit();    }

    //Act4

    public String getOpinion1() {
        return pref.getString("opinion1","");    }

    public void setOpinion1(String opinion1) {
        pref.edit().putString("opinion1",opinion1).commit();    }

    public String getOpinion2() {
        return pref.getString("opinion2","");    }

    public void setOpinion2(String opinion2) {
        pref.edit().putString("opinion2",opinion2).commit();    }


    public String getRef1() {
        return pref.getString("ref1","");    }

    public void setRef1(String ref1) {
        pref.edit().putString("ref1",ref1).commit();    }


    public String getRef2() {
        return pref.getString("ref2","");    }

    public void setRef2(String ref2) {
        pref.edit().putString("ref2",ref2).commit();    }


    public String getPic() {
        return pref.getString("pic","");    }

    public void setPic(String pic) {
        pref.edit().putString("pic",pic).commit();    }


    public String getPlace() {
        return pref.getString("place","");    }

    public void setPlace(String place) {
        pref.edit().putString("place",place).commit();    }


    public String getDate() {
        return  pref.getString("date","");
    }

    public void setDate(String date) {
        pref.edit().putString("date",date).commit();    }

}
