package com.example.mymoneyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private static String errMsg="";
    private static boolean check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button confimbutton=findViewById(R.id.ConfirmButton);
        Button registeredbutton=findViewById(R.id.RegisteredButton);

        /*登录模块*/
        confimbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUp();
                Toast.makeText(MainActivity.this,errMsg,Toast.LENGTH_SHORT).show();
                if(check==true){
                    Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        /*注册模块*/
        registeredbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*主界面点击注册后跳入注册页面*/
                Intent intent=new Intent(MainActivity.this,LoginInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
    /*登录方法*/
    public void loginUp(){
        /*取出*/
        TextView userName=findViewById(R.id.userText);
        TextView passWord=findViewById(R.id.passText);
        final String username=userName.getText().toString();
        final String password=passWord.getText().toString();
        System.out.println(username+" "+password);
        /*先判断输入是否为空*/
        if(username.equals("")||password.equals("")){
            Toast.makeText(MainActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
        }
        else{
            /*在数据库中检查是否有该用户*/
            System.out.println("开始操作");
            new Thread(){
                public void run(){
                    System.out.println("触发操作");
                    DBUtil dbUtil=new DBUtil();
                    try {
                        //System.out.println(dbUtil.loginCheck("bv","456"));
                        int result=dbUtil.loginCheck(username,password);
                        System.out.println(result);
                        //dbUtil.Insert();
                        //dbUtil.sle();
                        switch (result){
                            case 1:
                                String usernameErr="用户名错误";
                                errMsg=usernameErr;
                                check=false;
                                break;
                            case 2:
                                errMsg="登陆成功，欢迎使用";
                                check=true;
                                break;
                            case 3:
                                String passwordErr="密码错误";
                                errMsg=passwordErr;
                                check=false;
                                break;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    }
}
