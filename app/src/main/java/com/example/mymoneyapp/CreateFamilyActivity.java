package com.example.mymoneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateFamilyActivity extends AppCompatActivity {
    //布局控件
    private TextView returnFa;
    private Button creatButton,clearButton;
    private EditText familyName,familyCode;
    //存储家庭名和邀请码
    private String familyname,familycode;
    //判断是否创建成功
    private boolean flag=false;
    private String msg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_family);
        //为布局控件添加事件
        returnFa=findViewById(R.id.returnFA);
        creatButton=findViewById(R.id.yesCreate);
        clearButton=findViewById(R.id.clearEdit);
        familyCode=findViewById(R.id.editFamliyCode);
        familyName=findViewById(R.id.editFamilyName);

        //返回家庭管理页面
        returnFa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent1 = new Intent(CreateFamilyActivity.this, FamilyActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                return false;
            }
        });
        //文本框清除
        clearButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                familyCode.setText("");
                familyName.setText("");
                return false;
            }
        });
        //创建家庭
        creatButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                familyname=familyName.getText().toString();
                familycode=familyCode.getText().toString();
                if(familycode.equals("")||familyname.equals("")){
                    Toast.makeText(CreateFamilyActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    createFamily();
                    if(flag==false){
                        Toast.makeText(CreateFamilyActivity.this,msg,Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(CreateFamilyActivity.this,msg,Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(CreateFamilyActivity.this, FamilyActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                    }
                }
                return false;
            }
        });
    }
    //创建家庭
    private void createFamily(){
        final String own=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                try{
                    if(DBUtil.checkFamily(own)==false){
                        flag=false;
                        msg="用户已经是一个家庭的成员";
                        return ;
                    }
                    DBUtil.createFamily(familyname,own,familycode);
                    msg="创建家庭成功";
                    flag=true;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg="家庭创建失败,请检查用户名";
                    flag=false;
                }
            }
        }.start();
    }
}
