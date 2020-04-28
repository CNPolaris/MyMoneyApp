package com.example.mymoneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);
        /*为返回登录菜单添加监听事件*/
        ImageView returnMain=findViewById(R.id.returnView1);
        returnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*返回主菜单*/
                Intent intent=new Intent(LoginInActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        /*注册*/
        Button loginin=findViewById(R.id.loginbutton);
        loginin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*先判断输入是否合法*/
                TextView user=findViewById(R.id.loginname);
                TextView pass =findViewById(R.id.passText);
                final String username=user.getText().toString();
                final String password=pass.getText().toString();
                if(username.equals("")||password.equals("")){
                    Toast.makeText(LoginInActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                /*再判断用户名是否有重复*/
                /*如果符合创建要求，就用id创建新的数据库，实现用户之间的独立*/
                else{
                    new Thread(){
                        public void run(){
                            try {
                                boolean yesno=DBUtil.longinInCheck(username,password);
                                if(yesno==true){
                                    Looper.prepare();
                                    Toast.makeText(LoginInActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    /*合法就写入数据库，提示成功后，跳转到登录页面*/
                                    Intent intent=new Intent(LoginInActivity.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    Looper.loop();
                                }
                                else if(yesno==false){
                                    Looper.prepare();
                                    Toast.makeText(LoginInActivity.this,"注册失败,用户名重复",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        }.start();
                    }
            }
        });
        Button clearButton=findViewById(R.id.clebutton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView user=findViewById(R.id.loginname);
                TextView pass =findViewById(R.id.passText);
                user.setText(null);
                pass.setText(null);
            }
        });
    }
}
