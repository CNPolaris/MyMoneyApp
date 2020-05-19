package com.example.mymoneyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class chartActivity extends AppCompatActivity {
    Context context;
    //年月时间获取
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        context = this;
        //初始化tabhost
        TabHost tabHost = findViewById(R.id.charthost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("图表1", null).setContent(R.id.tab1));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("图表2", null).setContent(R.id.tab2));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("图表3", null).setContent(R.id.tab3));
        //底部导航栏
        final BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_mingxi:
                        Intent intent1 = new Intent(chartActivity.this, DetailsActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_tubiao:
                        Intent intent2 = new Intent(chartActivity.this, chartActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                        break;

                    case R.id.navigation_wode:
                        Intent intent3 = new Intent(chartActivity.this, MyInfoActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_jizhang:
                        Intent intent4 = new Intent(chartActivity.this, AddActivity.class);
                        startActivity(intent4);
                        break;
                }
            }
        });
        //更新数据
        updateData();
        //开始设置图表
        LineChart chart = findViewById(R.id.chart);
        //chart = new LineChart(context);
        LinearLayout tablay1 = findViewById(R.id.tab1);
        //tablay1.addView(chart);
        //设置图表数据
        List<Entry> entries = new ArrayList<>();//支出表
        for(DayData data:DBUtil.zhilist){
           System.out.println(data.getDay()+"  "+data.getMoney());
           entries.add(new Entry(data.getDay(),data.getMoney()));
        }
        List<Entry> entries2=new ArrayList<>();//收入表
        for(DayData data:DBUtil.shoulist){
            entries2.add(new Entry(data.getDay(),data.getMoney()));
        }
        LineDataSet dataSet = new LineDataSet(entries, "支出");
        LineDataSet dataSet2=new LineDataSet(entries2,"收入");

        dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
        dataSet.setLineWidth(1f);//线条宽度
        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelCount(15);

        LineData lineData = new LineData(dataSet);
        LineData lineData2=new LineData(dataSet2);

        chart.setData(lineData);
        //chart.setData(lineData2);
        chart.invalidate();
    }
    /*
    * 更新获取数据*/
    private void updateData(){
        final String name=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                String date=MyInfoActivity.otheruse;
                System.out.println(date);
                DBUtil.inquireDayData(name,date);
            }
        }.start();
    }
}
