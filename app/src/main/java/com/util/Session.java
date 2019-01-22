package com.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    SharedPreferences pref;

    public Session(Context context){
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUserLoginVar(String userLoginVar){
        pref.edit().putString("login",userLoginVar).commit();
    }

    public String getUserLoginVar(){
        String loginVar =pref.getString("login","");
        return loginVar;
    }

    public void setAdminLoginVar(String adminLoginVar){
        pref.edit().putString("Adminlogin",adminLoginVar).commit();
    }

    public String getAdminLoginVar(){
        String loginVar =pref.getString("Adminlogin","");
        return loginVar;
    }

    public void setName(String name){
        pref.edit().putString("name",name).commit();
    }

    public String getName(){
        String name =pref.getString("name","");
        return name;
    }

    public void setEmail(String name){
        pref.edit().putString("email",name).commit();
    }

    public String getEmail(){
        String name = pref.getString("email","");
        return name;
    }

    public void setPass(String name){
        pref.edit().putString("pass",name).commit();
    }

    public String getPass(){
        String name = pref.getString("pass","");
        return name;
    }
    public void setPhone(String name){
        pref.edit().putString("mobile",name).commit();
    }

    public String getPhone(){
        String name = pref.getString("mobile","");
        return name;
    }

    public void setId(String id){
        pref.edit().putString("id",id).commit();
    }

    public String getId(){
        String name = pref.getString("id","");
        return name;
    }
    public void setOtp(String id){
        pref.edit().putString("otp",id).commit();
    }

    public String getOtp(){
        String name = pref.getString("otp","");
        return name;
    }
}
