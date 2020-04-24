package com.example.mymoneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class LoginInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);
        /*为返回上一级菜单添加监听事件*/
        ImageView returnMain=findViewById(R.id.returnView1);
        returnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*返回主菜单*/
                Intent intent=new Intent(LoginInActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        /*注册*/
        Button loginin=findViewById(R.id.loginbutton);
        loginin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*先判断输入是否合法*/

                /*再判断用户名是否有重复*/


                /*如果符合创建要求，就用id创建新的数据库，实现用户之间的独立*/

                /*合法就写入数据库，提示成功后，跳转到登录页面*/
                Intent intent=new Intent(LoginInActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
