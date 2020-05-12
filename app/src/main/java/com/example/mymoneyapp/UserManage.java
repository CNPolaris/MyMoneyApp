package com.example.mymoneyapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.Data;

public class UserManage {
    private static UserManage instance;
    private UserManage(){

    }
    public static UserManage getInstance(){
        if(instance==null){
            instance=new UserManage();
        }
        return instance;
    }

    public void saveUserInfo(Context context,String username,String password){
        SharedPreferences sp =context.getSharedPreferences("Data", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("USERNAME",username);
        editor.putString("PASSWORD",password);
        editor.commit();
    }
    //保存财产
    public void saveUserproperty(Context context,float zhichu,float shouru){
        SharedPreferences sp =context.getSharedPreferences("Data", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor=sp.edit();
        editor.putFloat("ZHICHU",zhichu);
        editor.putFloat("SHOURU",shouru);
        editor.commit();
    }
    public Data getUserInfo(Context context){
        SharedPreferences sp=context.getSharedPreferences("Data",Context.MODE_PRIVATE);
        Data userInfo=new Data();
        userInfo.setUsername(sp.getString("USERNAME",""));
        userInfo.setPassword(sp.getString("PASSWORD",""));
        return userInfo;
    }
    public boolean hasUserInfo(Context context){
        Data userInfo=getUserInfo(context);
        if(userInfo!=null){
            if((!TextUtils.isEmpty(userInfo.getUsername()))&&(!TextUtils.isEmpty(userInfo.getPassword()))){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}
