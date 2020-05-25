package com.example.mymoneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFamilyActivity extends AppCompatActivity {
    private Button jiaru,quxiao;
    private EditText codeedit,nameedit;
    private String code="",name="";
    private boolean flag=false;
    private String msg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family);

        jiaru=findViewById(R.id.jiaruFA);
        quxiao=findViewById(R.id.quxiaoFA);
        codeedit=findViewById(R.id.editCode);
        nameedit=findViewById(R.id.editFAname);


        quxiao.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent1 = new Intent(AddFamilyActivity.this, FamilyActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                return false;
            }
        });


        jiaru.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                code=codeedit.getText().toString();
                name=nameedit.getText().toString();
                if(code.equals("")||name.equals("")){
                    Toast.makeText(AddFamilyActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    addFamily();
                    if(flag==true){
                        Toast.makeText(AddFamilyActivity.this,msg,Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(AddFamilyActivity.this, FamilyActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                    }
                    else {
                        Toast.makeText(AddFamilyActivity.this,msg,Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }
    private void addFamily(){
        final String username=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                try{
                    if(DBUtil.checkFamily(username)==false){
                        msg="用户无法同时加入多个家庭";
                        flag=false;
                        return ;
                    }else{
                        flag=DBUtil.addFamily(name,username,code);
                        if(flag==false){
                            msg="用户加入家庭失败,请重新尝试";
                        }else {
                            msg="用户加入成功";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
