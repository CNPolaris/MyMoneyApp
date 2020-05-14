package com.example.mymoneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class UpdateActivity extends AppCompatActivity {
    private LinearLayout llreturn=null;
    private TextView idText=null;
    private EditText leixingText=null, xiangmuText =null, jineText =null, beizhuText =null, riqiText =null;
    private Button upbaocun=null,upshanchu=null;
    //存储修改后的数据
    private String newid,newleixing,newxiangmu,newbeizhu;
    private String newdate;
    private float newjine;
    private boolean jieguo1,jieguo2;
    private ImageView imageView;//显示头像
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
        imageView=findViewById(R.id.showicon);

        //显示数据-->将选中的信息显示在界面上
        if(ShowMonthData.yes==0){
            idText.setText(DetailsActivity.result.getId());leixingText.setText(DetailsActivity.result.getLeixing());xiangmuText.setText(DetailsActivity.result.getXiangmu());
            jineText.setText(""+DetailsActivity.result.getMoney());beizhuText.setText(DetailsActivity.result.getBeizhu());riqiText.setText(""+DetailsActivity.result.getDate());
            setIcon();
        }
        else if(ShowMonthData.yes==1){
            idText.setText(ShowMonthData.monthresult.getId());leixingText.setText(ShowMonthData.monthresult.getLeixing());xiangmuText.setText(ShowMonthData.monthresult.getXiangmu());
            jineText.setText(""+ShowMonthData.monthresult.getMoney());beizhuText.setText(ShowMonthData.monthresult.getBeizhu());riqiText.setText(""+ShowMonthData.monthresult.getDate());
            setIcon();
        }
        //保存按键-->执行数据更新
        upbaocun=findViewById(R.id.upquebutton);
        upbaocun.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                update();
                if (jieguo1 ==true){
                    Toast.makeText(UpdateActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    if(ShowMonthData.yes==0){
                        Intent intent1=new Intent(UpdateActivity.this,DetailsActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                    }else {
                        Intent intent1=new Intent(UpdateActivity.this,MyInfoActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                    }

                }else {
                    Toast.makeText(UpdateActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
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
                if(jieguo2 ==true){
                    if(ShowMonthData.yes==0){
                        Toast.makeText(UpdateActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        Intent intent1=new Intent(UpdateActivity.this,DetailsActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                    }else {
                        Toast.makeText(UpdateActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        Intent intent1=new Intent(UpdateActivity.this,ShowMonthData.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                    }
                }else{
                    Toast.makeText(UpdateActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        //返回按键-->返回账单界面
        llreturn=findViewById(R.id.upreturn);
        llreturn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(ShowMonthData.yes==0){
                    Intent intent1=new Intent(UpdateActivity.this,DetailsActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    Toast.makeText(UpdateActivity.this,"取消修改",Toast.LENGTH_SHORT).show();
                    startActivity(intent1);
                }
                else {
                    Intent intent1=new Intent(UpdateActivity.this,ShowMonthData.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    Toast.makeText(UpdateActivity.this,"取消修改",Toast.LENGTH_SHORT).show();
                    startActivity(intent1);
                    return false;
                }
                return false;
            }
        });
    }
    /*
    *设置类别头像
    */
    private void setIcon(){
        switch (xiangmuText.getText().toString()){
            case "服饰":
                imageView.setImageResource(R.drawable.fushi);
                break;
            case "餐饮":
                imageView.setImageResource(R.drawable.canyin);
                break;
            case "购物":
                imageView.setImageResource(R.drawable.gouwu);
                break;
            case "交通":
                imageView.setImageResource(R.drawable.jiaotong);
                break;
            case "居家":
                imageView.setImageResource(R.drawable.jujia);
                break;
            case "零食":
                imageView.setImageResource(R.drawable.lingshi);
                break;
            case "礼物":
                imageView.setImageResource(R.drawable.liwu);
                break;
            case "旅行":
                imageView.setImageResource(R.drawable.lvxing);
                break;
            case "美容":
                imageView.setImageResource(R.drawable.meirong);
                break;
            case "日用品":
                imageView.setImageResource(R.drawable.riyong);
                break;
            case "通讯":
                imageView.setImageResource(R.drawable.tongxun);
                break;
            case "数码":
                imageView.setImageResource(R.drawable.shuma);
                break;
            case "烟酒":
                imageView.setImageResource(R.drawable.yanjiu);
                break;
            case "医疗":
                imageView.setImageResource(R.drawable.yiliao);
                break;
            case "学习":
                imageView.setImageResource(R.drawable.xuexi);
                break;
            case "娱乐":
                imageView.setImageResource(R.drawable.yule);
                break;
            case "运动":
                imageView.setImageResource(R.drawable.yundong);
                break;
            case "住房":
                imageView.setImageResource(R.drawable.zhufang);
                break;
            case "水果":
                imageView.setImageResource(R.drawable.shuiguo);
                break;
            case "社交":
                imageView.setImageResource(R.drawable.shejiao);
                break;
            case "工资":
                imageView.setImageResource(R.drawable.gongzi);
                break;
            case "理财":
                imageView.setImageResource(R.drawable.licai);
                break;
            case "兼职":
                imageView.setImageResource(R.drawable.jianzhi);
                break;
            case "其他收入":
                imageView.setImageResource(R.drawable.qitashouru);
                break;
            case "礼金":
                imageView.setImageResource(R.drawable.lijin);
                break;
        }
    }
    /*
    *更新方法
    */
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
                    jieguo1 =DBUtil.dataUpdate(name,newid,newleixing,newxiangmu,newjine,newbeizhu,newdate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /*
    *删除方法
    */
    private void del(){
        newid=idText.getText().toString();
        final String name=UserManage.getInstance().getUserInfo(this).getUsername();
        new Thread(){
            public void run(){
                try{
                    jieguo2 =DBUtil.deleteLine(name,newid);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
