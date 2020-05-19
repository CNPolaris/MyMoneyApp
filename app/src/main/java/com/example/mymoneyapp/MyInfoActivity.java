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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import data.Data;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyInfoActivity extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener{
    private ListView listView;
    final List<Map<String, Object>> line = new ArrayList<>();
    final int[]image=new int[]{R.drawable.jiating,R.drawable.clear,R.drawable.zhuxiao,R.drawable.tuichu};
    final String[]title=new String[]{"家庭管理","清除数据","账户注销 ","退出登录"};
    private TextView tvname;
    //账单信息展示
    private TextView tvcount,tvacount;
    //月份账单信息展示
    private LinearLayout tvmonthshow;
    private TextView tvmonthshou,tvmonthzhi,tvmonthbalance;
    public static TextView tvmonth;
    public static String otheruse;
    public static StringBuffer date;
    private Context context;
    private int year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        initMenu();//实例化布局
        context=this;
        //用户名展示
        tvname=findViewById(R.id.showuser);
        tvname.setText(UserManage.getInstance().getUserInfo(this).getUsername());
        //账单信息事件绑定
        tvmonthshow=findViewById(R.id.monthshowline);
        tvcount=findViewById(R.id.showSum);
        tvacount=findViewById(R.id.showsummoney);
        tvmonthshou=findViewById(R.id.showmonthshou);
        tvmonthzhi=findViewById(R.id.showmonthzhi);
        tvmonthbalance=findViewById(R.id.showmonthban);
        //底部导航栏
        final BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_mingxi:
                        Intent intent1 = new Intent(MyInfoActivity.this, DetailsActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_tubiao:
                        Intent intent2 = new Intent(MyInfoActivity.this, chartActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_wode:
                        Intent intent3 = new Intent(MyInfoActivity.this, MyInfoActivity.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_jizhang:
                        Intent intent4 = new Intent(MyInfoActivity.this, AddActivity.class);
                        startActivity(intent4);
                        break;
                }
            }
        });
        //设置菜单的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                switch (position){
                    case 0://家庭管理
                        break;
                    case 1://清除数据
                        clearowndata();
                        break;
                    case 2://账户注销
                        dropowndata();
                        Toast.makeText(MyInfoActivity.this,"账户注销成功",Toast.LENGTH_SHORT).show();
                        UserManage.getInstance().saveUserInfo(MyInfoActivity.this,"","");
                        Intent intent=new Intent(MyInfoActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case 3://退出
                        dropOut();
                        break;
                }

            }
        });
        //显示账户信息
        initInfo();
        //显示月账单总收入、总支出、结余
        initmonth();
        //显示日期
        date=new StringBuffer();
        initView();
        initDatetime();
        //月份账单显示添加点击事件
        tvmonthshow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent1=new Intent(MyInfoActivity.this,ShowMonthData.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                return false;
            }
        });
    }
    /*
    * 显示月账单总收入、总支出、结余
    * */
    private void initmonth(){
        final String name=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                DBUtil.inquireMonthData(name,tvmonth.getText().toString());
                tvmonthshou.setText(""+DBUtil.monthshouru);
                tvmonthzhi.setText(""+DBUtil.monthzhichu);
                tvmonthbalance.setText(""+(DBUtil.monthzhichu+DBUtil.monthshouru));
            }
        }.start();
    }
    private void initView(){
        tvmonth=findViewById(R.id.showMonth);
        tvmonth.setOnClickListener(this);
    }
    public void initDatetime(){
        String newmonth = "";
        //String newday="";
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        if(month<10){
            newmonth="0"+String.valueOf(month);
        }else{newmonth=String.valueOf(month);}
        tvmonth.setText(date.append((year)).append("-").append(newmonth));
        otheruse=tvmonth.getText().toString();
    }
    /*
    * 显示账单信息
    */
    private void initInfo(){
        final String name=UserManage.getInstance().getUserInfo(this).getUsername();
        final int[] count = {0};
        final float[] balance = {0};
        new Thread(){
            public void run(){
                try{
                    DBUtil dbUtil=new DBUtil();
                    count[0] =dbUtil.getcount(name);
                    balance[0] =dbUtil.getBalance(name);
                    tvcount.setText(""+count[0]);
                    tvacount.setText(""+balance[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /*
    * 实例化菜单
    */
    private void initMenu(){
        for(int i=0;i<4;i++){
            Map<String,Object>temp=new LinkedHashMap<>();
            temp.put("image",image[i]);
            temp.put("name",title[i]);
            line.add(temp);
        }
        listView=findViewById(R.id.usermanger);
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,line,R.layout.data_item,new String[]{"image","name"},new int[]{R.id.image,R.id.name});
        listView.setAdapter(simpleAdapter);
}


        //账户注销方法
        public void dropowndata () {
            final String username = UserManage.getInstance().getUserInfo(this).getUsername();
            final String password = UserManage.getInstance().getUserInfo(this).getPassword();
            new Thread() {
                public void run() {
                    try {
                        DBUtil.logout(username, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        //账户数据清除方法
        public static void clearowndata () {
            final String username = Data.getUsername();
            new Thread() {
                public void run() {
                    try {
                        DBUtil.ClearData(username);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        //用户退出登录操作
        public void dropOut () {
            UserManage.getInstance().saveUserInfo(MyInfoActivity.this, "", "");
            Intent intent = new Intent(MyInfoActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showMonth:
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
                        tvmonth.setText(date.append((year)).append("-").append(newmonth));
                        //tvDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month+1)).append("-").append(day));
                        //inquire();//每次更改时间后都会触发
                        //updateBill();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //inquire();//每次更改时间后都会触发
                        //updateBill();
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
                //inquire();//每次更改时间后都会触发
                //updateBill();
                break;
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year=year;
        this.month=monthOfYear;
        this.day=dayOfMonth;
    }
}