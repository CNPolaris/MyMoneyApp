package com.example.mymoneyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TabHost;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class chartActivity extends AppCompatActivity {
    Context context;
    //图表一：月度收支折线图
    LineChart lineChart =null;
    //图表二：年度收支条形图
    BarChart barChart;
    //年月时间获取
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        context = this;
        //初始化tabhost
        TabHost tabHost = findViewById(R.id.charthost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("月度收支", null).setContent(R.id.tab1));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("年度收支", null).setContent(R.id.tab2));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("消费项目", null).setContent(R.id.tab3));
        //月度收支控件绑定
        lineChart = findViewById(R.id.chart);
        lineChart.setTouchEnabled(true);
        //年度收支条形图控件绑定
        barChart=findViewById(R.id.bar);
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
        //绘制月度收支折线图
        drawLine();
        //绘制年度收支饼形图
        drawBar();
    }
    /*
    * 更新获取数据*/
    private void updateData(){
        final String name=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                String date=MyInfoActivity.otheruse;
                System.out.println(date);
                DBUtil.updateChartData(name,date);
            }
        }.start();
    }
    /*
    *绘制年度收支条形图*/
    private void drawBar(){
        List<BarEntry>list1=new ArrayList<>();//收入
        List<BarEntry>list2=new ArrayList<>();//支出
        for(MonthData data:DBUtil.monthshoulist){//收入添加数据
            list1.add(new BarEntry(data.getMonth(),data.getMoney()));
        }
        for(MonthData data :DBUtil.monthZhilist){//支出添加数据
            list2.add(new BarEntry(data.getMonth(),data.getMoney()));
        }
        BarDataSet barDataSet1=new BarDataSet(list1,"收入");
        barDataSet1.setColor(Color.GREEN);//收入设置为绿色
        BarDataSet barDataSet2=new BarDataSet(list2,"支出");
        barDataSet2.setColor(Color.RED);//支出设置为红色

        BarData barData=new BarData(barDataSet1);//先把收入放进去
        barData.addDataSet(barDataSet2);//再把支出放进去
        //绘制条形图
        barChart.setData(barData);
        barData.setBarWidth(0.3f);//柱子的宽度
        //重点！   三个参数   分别代表   X轴起点     组与组之间的间隔      组内柱子的间隔
        barData.groupBars(1,0.6f,0);
        barChart.getXAxis().setCenterAxisLabels(true);   //设置柱子（柱子组）居中对齐X轴上的点
        barChart.getXAxis().setAxisMaximum(13);   //X轴最大数值
        barChart.getXAxis().setAxisMinimum(1);   //X轴最小数值
        //X轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        //barChart.getXAxis().setLabelCount(12,true);
        barChart.getDescription().setEnabled(false);    //右下角一串英文字母不显示
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);   //X轴的位置设置为下  默认为上
        barChart.getAxisRight().setEnabled(false);     //右侧Y轴不显示   默认为显示
        for(IDataSet ser:barChart.getData().getDataSets()){
            barDataSet1.setDrawValues(!barDataSet1.isDrawValuesEnabled());
            barDataSet2.setDrawValues(!barDataSet2.isDrawValuesEnabled());
        }
        XAxis xAxis=barChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setLabelCount(12);
        barChart.invalidate();
    }
    /*
    * 绘制图表1--折线图*/
    private void drawLine(){
        //设置图表数据
        //支出表
        List<Entry> entries = new ArrayList<>();
        for(DayData data:DBUtil.zhilist){
            System.out.println(data.getDay()+"  "+data.getMoney());
            entries.add(new Entry(data.getDay(),data.getMoney()));
        }
        //收入表
        List<Entry> entries2=new ArrayList<>();
        for(DayData data:DBUtil.shoulist){
            entries2.add(new Entry(data.getDay(),data.getMoney()));
        }
        //收支线对象
        LineDataSet dataSet1 = new LineDataSet(entries, "支出");
        dataSet1.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
        dataSet1.setLineWidth(1f);//线条宽度

        LineDataSet dataSet2=new LineDataSet(entries2,"收入");
        dataSet2.setColor(Color.parseColor("#FF3366"));
        dataSet2.setCircleColor(Color.parseColor("#330066"));//圆点颜色
        //图表属性设置
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setLabelCount(15);

        //保存LineDataSet集合
        ArrayList<ILineDataSet>dataSets=new ArrayList<>();
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);
        //创建LineData对象 属于LineChart折线图的数据集合
        LineData lineData=new LineData(dataSets);
        //添加到图表中
        lineChart.setData(lineData);
        //开始绘制折线图
        lineChart.invalidate();
    }
}
