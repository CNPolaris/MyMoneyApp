package com.example.mymoneyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;

import com.Data;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.*;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    //这是支出页面的button
    private Button gongziBt=null;
    private Button jianzhiBt=null;
    private Button licaiBt=null;
    private Button qitashouruBt=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        TabHost tabHost=findViewById(R.id.addtab);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("收入",getResources().getDrawable(R.drawable.ic_launcher_background)).setContent(R.id.tab1));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("支出",null).setContent(R.id.tab2));

        //zhicuhSpinner.setOnItemLongClickListener();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_mingxi:
                        Intent intent1 = new Intent(AddActivity.this, DetailsActivity.class);

                        startActivity(intent1);
                        break;
                    case R.id.navigation_tubiao:
                        Intent intent2 = new Intent(AddActivity.this, chartActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_wode:
                        Intent intent3 = new Intent(AddActivity.this, MyInfoActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_jizhang:
                        Intent intent4 = new Intent(AddActivity.this, AddActivity.class);
                        startActivity(intent4);
                        break;
                }
            }
        });
        //为他们重写点击事件
        gongziBt=findViewById(R.id.gongzi);
        jianzhiBt=findViewById(R.id.jianzhi);
        licaiBt=findViewById(R.id.licai);
        qitashouruBt=findViewById(R.id.qitashouru);
        gongziBt.setOnClickListener(this);
        jianzhiBt.setOnClickListener(this);
        qitashouruBt.setOnClickListener(this);
        licaiBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.gongzi:
                //System.out.println("这是工资");
                add((String) gongziBt.getText());
                System.out.println(gongziBt.getText());
                break;
            case R.id.licai:
                System.out.println("这是理财");
                break;
            case R.id.jianzhi:
                System.out.println("这是兼职");
                break;
            case R.id.qitashouru:
                System.out.println("这是其他收入");
                break;
        }
    }
    public void add(String msg){
    }
}
