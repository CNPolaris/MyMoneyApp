package com.example.mymoneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FamilyActivity extends AppCompatActivity {
    private ListView famliyView;
    private TextView returnMyinfo;

    private boolean flag,flag2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        //初始化功能列表
        initFamliyView();
        //返回个人信息
        returnMyinfo=findViewById(R.id.returnMy);
        returnMyinfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent1 = new Intent(FamilyActivity.this, MyInfoActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                return false;
            }
        });
        /*
        * 为功能列表添加点击事件*/
        famliyView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://创建家庭
                        Intent intent0 = new Intent(FamilyActivity.this, CreateFamilyActivity.class);
                        intent0.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent0);
                        break;
                    case 1://加入家庭
                        Intent intent1 = new Intent(FamilyActivity.this, AddFamilyActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        break;
                    case 2://开启同步
                        openSync();
                        if (flag==true){
                            Toast.makeText(FamilyActivity.this,"开启同步成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(FamilyActivity.this,"开启同步失败",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3://结束同步
                        closeSync();
                        if (flag2==true){
                            Toast.makeText(FamilyActivity.this,"关闭同步成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(FamilyActivity.this,"关闭同步失败",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 4://家庭账单
                        Intent intent4 = new Intent(FamilyActivity.this, FamilyDetailActivity.class);
                        //intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent4);
                        break;
                    case 5://退出家庭
                        dropFamily();
                        break;
                    case 6://删除家庭

                        break;
                }
            }
        });
    }
    //初始化功能列表
    private void initFamliyView(){
        int[] image=new int[]{R.drawable.chuangjian,R.drawable.jiaru,R.drawable.tongbu,R.drawable.quxiao,R.drawable.jiatingzhangdan,R.drawable.tuichujiating,R.drawable.shanchujiating};
        String []title=new String[]{"创建家庭","加入家庭","开启同步","结束同步","家庭账单","退出家庭","删除家庭"};
        List<Map<String,Object>> line=new ArrayList<>();
        for(int i=0;i<=6;i++){
            Map<String,Object>temp=new LinkedHashMap<>();
            temp.put("image",image[i]);
            temp.put("name",title[i]);
            line.add(temp);
        }
        famliyView=findViewById(R.id.familySet);
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,line,R.layout.data_item,new String[]{"image","name"},new int[]{R.id.image,R.id.name});
        famliyView.setAdapter(simpleAdapter);
    }
    //退出家庭
    private void dropFamily(){

    }
    //开启同步
    private void openSync(){
        final String username=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                try{
                    flag=DBUtil.openSycn(username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //结束同步
    private void closeSync(){
        final String username=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                try{
                    flag2=DBUtil.closeSync(username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }
}
