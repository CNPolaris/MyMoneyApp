package com.example.mymoneyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.Data;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyInfoActivity extends AppCompatActivity {
    private ListView listView;
    final List<Map<String, Object>> line = new ArrayList<>();
    final int[]image=new int[]{R.drawable.jiating,R.drawable.clear,R.drawable.zhuxiao,R.drawable.tuichu};
    final String[]title=new String[]{"家庭管理","清除数据","账户注销 ","退出登录"};
    //账单信息展示
    private TextView tvcount,tvacount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        initMenu();//实例化布局
        //账单信息事件绑定
        tvcount=findViewById(R.id.showSum);
        tvacount=findViewById(R.id.showsummoney);
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
        //账户注销事件
        /*Button dropuser=findViewById(R.id.dropuserbutton);
        dropuser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                    dropowndata();

            }
        });*/
        //账户数据清除事件
        /*final Button clearuser=findViewById(R.id.cleardatabutton);
        clearuser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)  {
                clearowndata();
                return false;
            }
        });*/

        //退出登录
       /* Button tuichu=findViewById(R.id.tuichubutton);
        tuichu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)  {
                dropOut();
                return false;
            }
        });
    }*/


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

}