package com.example.mymoneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import data.GetData;

public class ShowMonthData extends AppCompatActivity {
    private LinearLayout monthreturn,shua;
    private ListView monthshow;
    public static GetData monthresult;
    public static int yes=0;
    private static List<GetData>monthList=new ArrayList<GetData>();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_month_data);
        yes=0;
        //月份账单信息
        monthshow=findViewById(R.id.monthshow);
        shua=findViewById(R.id.monthshuaxin);
        LayoutInflater layoutInflater = LayoutInflater.from(ShowMonthData.this);
        View layout=layoutInflater.inflate(R.layout.activity_update,null);
        //返回个人信息界面
        monthreturn=findViewById(R.id.returnMy);
        monthreturn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent1=new Intent(ShowMonthData.this,MyInfoActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                return false;
            }
        });
        inquire();
        updateBill();
        monthshow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1=new Intent(ShowMonthData.this,UpdateActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                ArrayAdapter<GetData> adapter = new ArrayAdapter<GetData>(
                        ShowMonthData.this,   // Context上下文
                        android.R.layout.simple_list_item_1,  // 子项布局id
                        monthList);
                yes=1;
                monthresult=adapter.getItem(position);
                startActivity(intent1);
            }
        });
        shua.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                inquire();
                updateBill();
                return false;
            }
        });
    }
    /*
     *更新账单
     */
    private void updateBill(){
        ArrayAdapter<GetData> adapter = new ArrayAdapter<GetData>(
                ShowMonthData.this,   // Context上下文
                android.R.layout.simple_list_item_1,  // 子项布局id
                monthList);                                // 数据
        monthshow.setAdapter(adapter);
    }
    /*
     *查询结果的显示
     */
    private void inquire() {
        final String username=UserManage.getInstance().getUserInfo(this).getUsername();
        //final String date=tvDate.getText().toString();
        new Thread(){
            public void run(){
                DBUtil dbUtil=new DBUtil();
                try{
                    monthList=dbUtil.inquireData(username,MyInfoActivity.tvmonth.getText().toString());
                    //System.out.println(monthList.get);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        updateBill();
    }
}
