package com.example.mymoneyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.Data;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        //底部导航栏
        BottomNavigationView bottomNavigationView=findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_mingxi:
                        Intent intent1=new Intent(MyInfoActivity.this,DetailsActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_tubiao:
                        Intent intent2=new Intent(MyInfoActivity.this,chartActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_wode:
                        Intent intent3=new Intent(MyInfoActivity.this,MyInfoActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_jizhang:
                        Intent intent4=new Intent(MyInfoActivity.this,AddActivity.class);
                        startActivity(intent4);
                        break;
                }
            }
        });
        //账户注销事件
        Button dropuser=findViewById(R.id.dropuserbutton);
        dropuser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                    dropowndata();
                    Toast.makeText(MyInfoActivity.this,"账户注销成功",Toast.LENGTH_SHORT).show();
                    UserManage.getInstance().saveUserInfo(MyInfoActivity.this,"","");
                    Intent intent=new Intent(MyInfoActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return false;
            }
        });
        //账户数据清除事件
        final Button clearuser=findViewById(R.id.cleardatabutton);
        clearuser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)  {
                clearowndata();
                return false;
            }
        });


        //退出登录
        Button tuichu=findViewById(R.id.tuichubutton);
        tuichu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)  {
                dropOut();
                return false;
            }
        });
    }


    //账户注销方法
    public void dropowndata(){
        final String username=UserManage.getInstance().getUserInfo(this).getUsername();
        final String password=UserManage.getInstance().getUserInfo(this).getPassword();
        new Thread(){
            public void run(){
                try{
                    DBUtil.logout(username, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //账户数据清除方法
    public static void clearowndata(){
        final String username=Data.getUsername();
        new Thread(){
            public void run(){
                try{
                    DBUtil.ClearData(username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //用户退出登录操作
    public void dropOut(){
        UserManage.getInstance().saveUserInfo(MyInfoActivity.this,"","");
        Intent intent=new Intent(MyInfoActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
