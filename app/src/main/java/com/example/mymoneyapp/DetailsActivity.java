package com.example.mymoneyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.BreakIterator;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context=this;
        //底部导航栏
        final BottomNavigationView bottomNavigationView=findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_mingxi:
                        Intent intent1=new Intent(DetailsActivity.this,DetailsActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_tubiao:
                        Intent intent2=new Intent(DetailsActivity.this,chartActivity.class);
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
        tvDate.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month)).append("月").append(day).append("日"));

        //数据库查询结果的显示
    }
    /*
    *查询结果的显示
    */


    /*
    * 日期选择器的设置
    * */
    private  void initView(){
        llDate=findViewById(R.id.ll_date);
        tvDate=findViewById(R.id.tv_date);
        llDate.setOnClickListener((View.OnClickListener) this);
    }
    private void initDatetime(){
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
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
                        tvDate.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month+1)).append("月").append(day).append("日"));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                datePicker.init(year,month-1,day,this);
                break;
        }
    }
}

