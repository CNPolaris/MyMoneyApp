package com.example.mymoneyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.Data;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//这是关于账单信息的
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener,DatePicker.OnDateChangedListener {
    //日期选择器
    LinearLayout llDate=null;
    TextView tvDate=null;
    private int year,month,day;
    private StringBuffer date;
    private Context context;
    //账单信息的实现显示
    private TextView tvzhichu;
    private TextView tvshouru;
    private ScrollView scrollView;
    @Override
    protected void onStart() {
        super.onStart();updateBill();
    }

    @Override
    protected void onPause() {
        super.onPause();updateBill();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context=this;
        tvshouru=findViewById(R.id.showshouru);
        tvzhichu=findViewById(R.id.showzhichu);

        scrollView=findViewById(R.id.tv_data1);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("滑动");
                        break;
                }
                return false;
            }
        });
        //底部导航栏
        final BottomNavigationView bottomNavigationView=findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_mingxi:
                        Intent intent1=new Intent(DetailsActivity.this,DetailsActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_tubiao:
                        Intent intent2=new Intent(DetailsActivity.this,chartActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_wode:
                        Intent intent3=new Intent(DetailsActivity.this,MyInfoActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_jizhang:
                        Intent intent4=new Intent(DetailsActivity.this,AddActivity.class);
                        startActivity(intent4);
                        break;
                }
            }
        });
        //日期选择器
        date=new StringBuffer();
        initView();
        initDatetime();
        //数据库查询结果的显示
        inquire();
        updateBill();
    }
    /*
    *更新账单
    */
    public void updateBill(){
        StringBuffer z=new StringBuffer();
        StringBuffer s=new StringBuffer();
        tvzhichu.setText(z.append(DBUtil.monthzhichu));
        tvshouru.setText(s.append(DBUtil.monthshouru));
    }
    /*
    *查询结果的显示
    */
    private void inquire() {
        final String username=UserManage.getInstance().getUserInfo(this).getUsername();
        //final String date=tvDate.getText().toString();
        System.out.println(date);
        new Thread(){
            public void run(){
                DBUtil dbUtil=new DBUtil();
                try{
                    dbUtil.inquireData(username,tvDate.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        updateBill();
    }

    /*
    * 日期选择器的设置
    * */
    private  void initView(){
        llDate=findViewById(R.id.ll_date);
        tvDate=findViewById(R.id.tv_date);
        llDate.setOnClickListener(this);
    }
    private void initDatetime(){
        String newmonth = "";
        String newday="";
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
        if(month<10){
            newmonth="0"+String.valueOf(month);
        }else{newmonth=String.valueOf(month);}
        if(day<10){
            newday="0"+String.valueOf(day);
        }else {newday=String.valueOf(day);}
        tvDate.setText(date.append((year)).append("-").append(newmonth).append("-").append(newday));
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year=year;
        this.month=monthOfYear;
        this.day=dayOfMonth;
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_date:
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(date.length()>0){//如果还有时间，就清除上次记录的日期
                            date.delete(0,date.length());
                        }
                        String newmonth="";
                        if(month+1<10){
                            newmonth="0"+String.valueOf(month+1);
                        }else{newmonth=String.valueOf(month+1);}
                        String newday="";
                        if(day<10){
                            newday="0"+String.valueOf(day);
                        }else {newday=String.valueOf(day);}
                        tvDate.setText(date.append((year)).append("-").append(newmonth).append("-").append(newday));
                        //tvDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month+1)).append("-").append(day));
                        inquire();//每次更改时间后都会触发
                        updateBill();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inquire();//每次更改时间后都会触发
                        updateBill();
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog=builder.create();
                View dialogView=View.inflate(context,R.layout.dialog_date,null);
                final DatePicker datePicker=dialogView.findViewById(R.id.datePicker);
                dialog.setTitle("设置日期");
                dialog.setView(dialogView);
                dialog.show();
                //初始化日期监听事件
                datePicker.init(year,month,day,this);
                break;
        }
    }
}

