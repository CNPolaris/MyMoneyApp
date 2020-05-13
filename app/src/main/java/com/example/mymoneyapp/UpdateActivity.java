package com.example.mymoneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class UpdateActivity extends AppCompatActivity {
    private LinearLayout llreturn=null;
    private TextView idText=null;
    private EditText leixingText=null, xiangmuText =null, jineText =null, beizhuText =null, riqiText =null;
    private Button upbaocun=null,upshanchu=null;
    //存储修改后的数据
    private String newid,newleixing,newxiangmu,newbeizhu;
    private String newdate;
    private float newjine;
    private boolean jieguo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        //显示数据的控件关联
        idText =findViewById(R.id.editId);
        leixingText =findViewById(R.id.editLeixing);
        xiangmuText =findViewById(R.id.editxiangmu);
        jineText =findViewById(R.id.editJine);
        beizhuText =findViewById(R.id.editBeizhu);
        riqiText =findViewById(R.id.editDate);
        //显示数据
        idText.setText(DetailsActivity.result.getId());leixingText.setText(DetailsActivity.result.getLeixing());xiangmuText.setText(DetailsActivity.result.getXiangmu());
        jineText.setText(""+DetailsActivity.result.getMoney());beizhuText.setText(DetailsActivity.result.getBeizhu());riqiText.setText(""+DetailsActivity.result.getDate());
        //保存按键-->执行数据更新
        upbaocun=findViewById(R.id.upquebutton);
        upbaocun.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                update();
                if (jieguo==true){
                    Toast.makeText(UpdateActivity.this,"修改成功",Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });
        //删除按键-->执行数据删除
        upshanchu=findViewById(R.id.updelbutton);
        upshanchu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                del();
                return false;
            }
        });
        //返回按键
        llreturn=findViewById(R.id.upreturn);
        llreturn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent1=new Intent(UpdateActivity.this,DetailsActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                return false;
            }
        });
    }
    private void update(){
        newid=idText.getText().toString();
        newbeizhu=beizhuText.getText().toString();
        newleixing=leixingText.getText().toString();
        newxiangmu=xiangmuText.getText().toString();
        newdate=riqiText.getText().toString();
        newjine=Float.valueOf(jineText.getText().toString());
        final String name=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                try{
                    jieguo=DBUtil.dataUpdate(name,newid,newleixing,newxiangmu,newjine,newbeizhu,newdate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void del(){

    }
}
