package com.example.mymoneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import data.FamilyData;

public class FamilyDetailActivity extends AppCompatActivity {
    private ListView listView;
    private List<FamilyData>dataList=new ArrayList<FamilyData>();
    private Context context;
    public static FamilyData result;
    private Button shuaxin,fanhui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_detail);
        context=this;
        listView=findViewById(R.id.familySet);
        shuaxin=findViewById(R.id.shuaxinFA);
        fanhui=findViewById(R.id.fanhuiFA);

        LayoutInflater layoutInflater = LayoutInflater.from(FamilyDetailActivity.this);
        shuaxin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                initquire();
                initBill();
                return false;
            }
        });
       fanhui.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               Intent intent4 = new Intent(FamilyDetailActivity.this,FamilyActivity.class);
               intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent4);
               return false;
           }
       });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    /*
    * 更新账单*/
    private void initBill(){
        ArrayAdapter<FamilyData>adapter=new ArrayAdapter<FamilyData>(
                FamilyDetailActivity.this,
                android.R.layout.simple_list_item_1,
                dataList);
        listView.setAdapter(adapter);
    }
    /*
    * 查询家庭账单*/
    private void initquire(){
        final String username=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                DBUtil dbUtil=new DBUtil();
                try{
                    dataList=dbUtil.getFamilyData(username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        initBill();
    }
}
