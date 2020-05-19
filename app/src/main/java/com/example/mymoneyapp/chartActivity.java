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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.DayData;
import data.GetData;
import data.ItemData;
import data.MonthData;

public class chartActivity extends AppCompatActivity {
    Context context;
    //图表一：月度收支折线图
    LineChart lineChart =null;
    //图表二：年度收支条形图
    BarChart barChart;
    //图表三：消费项目
    PieChart pieChart;
    List<GetData>dataList;
    final String [] zhichu=new String[]{"服饰","餐饮","购物","交通","居家","零食","礼物","旅行","美容","日用","通讯","数码","烟酒","医疗","学习","娱乐","运动","住房","水果","社交"};
    final String [] shouru=new String[]{"工资","理财","兼职","其他收入","礼金"};
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
        //消费项目控件绑定
        pieChart=findViewById(R.id.pieChart);
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
        itemData();
        //绘制月度收支折线图
        drawLine();
        //绘制年度收支条形图
        drawBar();
        //绘制消费项目饼状图
        drawPie();
    }
    /*
    * 更新获取数据*/
    private void updateData(){
        final String name=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                String date=MyInfoActivity.otheruse;
                DBUtil.updateChartData(name,date);
                dataList=new ArrayList<>(DBUtil.inquireData(name,date));
                //DBUtil.inquireMonthData(name,date);
            }
        }.start();
    }
    /*
    * 按照项目进行查询*/
    private void itemData(){
        final String name=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                DBUtil.inquireItem(zhichu);
            }
        }.start();
    }
    /*
    * 绘制消费项目图*/
    private void drawPie(){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        pieChart.setCenterText("消费项目");

        pieChart.setTransparentCircleColor(Color.BLACK);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // 触摸旋转
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        //设置数据
        ArrayList<PieEntry>entries=new ArrayList<PieEntry>();
        for(ItemData data:DBUtil.itemList){
            if(data.getPercentage()!=0){
                entries.add(new PieEntry(data.getPercentage(),data.getType()));
            }
        }

        // 获取pieCahrt图列
        Legend l = pieChart.getLegend();
        l.setEnabled(true);                    //是否启用图列（true：下面属性才有意义）
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setForm(Legend.LegendForm.DEFAULT); //设置图例的形状
        l.setFormSize(10);					  //设置图例的大小
        l.setFormToTextSpace(10f);			  //设置每个图例实体中标签和形状之间的间距
        l.setDrawInside(false);
        l.setWordWrapEnabled(true);			  //设置图列换行(注意使用影响性能,仅适用legend位于图表下面)
        l.setXEntrySpace(10f);				  //设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
        l.setYEntrySpace(8f);				  //设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）
        l.setYOffset(0f);					  //设置比例块Y轴偏移量
        l.setTextSize(14f);					  //设置图例标签文本的大小
        l.setTextColor(Color.parseColor("#000000"));//设置图例标签文本的颜色

        PieDataSet pieDataSet=new PieDataSet(entries,"消费项目");
        PieData pieData=new PieData(pieDataSet);
        pieData.setDrawValues(true);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
        pieData.setValueTextColor(Color.BLACK);  //设置所有DataSet内数据实体（百分比）的文本颜色
        pieData.setValueTextSize(12f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
        pieData.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式
        pieChart.setData(pieData);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        pieDataSet.setColors(colors);

        pieChart.invalidate();
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
