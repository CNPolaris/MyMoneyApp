package com.example.mymoneyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
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
                TextView userName=findViewById(R.id.userText);
                TextView passWord=findViewById(R.id.password);
                String username=userName.getText().toString();
                String password=passWord.getText().toString();
                /*先判断输入是否为空*/
                if(username.equals("")||password.equals("")){
                    Toast.makeText(MainActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                /*在数据库中检查是否有该用户*/
                System.out.println("开始操作");
                new Thread(){
                    public void run(){
                        System.out.println("触发操作");
                        DBUtil dbUtil=new DBUtil();
                        try {
                            dbUtil.Insert();
                            dbUtil.sle();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                System.out.println("结束");
                //DBUtil.getSQLConnection();
                //System.out.println(dbUtil.getSQLConnection());
                /*如果有跳入主界面1-日常记账信息*/
                Toast.makeText(MainActivity.this,"登录成功，欢迎使用",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
                startActivity(intent);
            }
        });
        /*注册模块*/
        registeredbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*主界面点击注册后跳入注册页面*/
                Intent intent=new Intent(MainActivity.this,LoginInActivity.class);
                startActivity(intent);
            }
        });
    }

}
