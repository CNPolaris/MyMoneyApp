package com.example.mymoneyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    //这是支出页面的button
    private Button gongziBt=null;
    private Button jianzhiBt=null;
    private Button licaiBt=null;
    private Button qitashouruBt=null;
    private Button lijinBt=null;
    //收入页面上的button
    private Button fushiBt=null,canyinBt=null,gouwuBt=null,jiaotongBt=null,jujiaBt=null;
    private Button lingshiBt=null,liwuBt=null,lvxingBt=null,meirongBt=null,riyongBt=null;
    private Button tongxunBt=null,shumaBt=null,yanjiuBt=null,yiliaoBt=null,xuexiBt=null;
    private Button yuleBt=null,yundongBt=null,zhufangBt=null,shuiguoBt=null,shejiaoBt=null;
    //自定义数字键盘上的button
    private Button num0=null,num1=null,num2=null,num3=null,num4=null,num5=null,num6=null,num7=null,num8=null,num9=null,numdot=null;
    private Button numdel=null,numquxiao=null,numqueding=null;
    //当前所选中的项目
    public String nowItem="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        TabHost tabHost=findViewById(R.id.addtab);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("收入",getResources().getDrawable(R.drawable.ic_launcher_background)).setContent(R.id.tab1));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("支出",null).setContent(R.id.tab2));

        //zhicuhSpinner.setOnItemLongClickListener();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_mingxi:
                        Intent intent1 = new Intent(AddActivity.this, DetailsActivity.class);

                        startActivity(intent1);
                        break;
                    case R.id.navigation_tubiao:
                        Intent intent2 = new Intent(AddActivity.this, chartActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_wode:
                        Intent intent3 = new Intent(AddActivity.this, MyInfoActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_jizhang:
                        Intent intent4 = new Intent(AddActivity.this, AddActivity.class);
                        startActivity(intent4);
                        break;
                }
            }
        });
        //收入页面的button事件绑定
        gongziBt=findViewById(R.id.gongzi);
        jianzhiBt=findViewById(R.id.jianzhi);
        licaiBt=findViewById(R.id.licai);
        qitashouruBt=findViewById(R.id.qitashouru);
        lijinBt=findViewById(R.id.lijin);

        gongziBt.setOnClickListener(this);
        jianzhiBt.setOnClickListener(this);
        qitashouruBt.setOnClickListener(this);
        licaiBt.setOnClickListener(this);
        lijinBt.setOnClickListener(this);
        //支出页面的button事件绑定
        fushiBt=findViewById(R.id.fushi);
        canyinBt=findViewById(R.id.canyin);
        gouwuBt=findViewById(R.id.gouwu);
        jiaotongBt=findViewById(R.id.jiaotong);
        jujiaBt=findViewById(R.id.jujia);
        lingshiBt=findViewById(R.id.lingshi);
        liwuBt=findViewById(R.id.liwu);
        lvxingBt=findViewById(R.id.lvxing);
        meirongBt=findViewById(R.id.meirong);
        riyongBt=findViewById(R.id.riyong);
        tongxunBt=findViewById(R.id.tongxun);
        shumaBt=findViewById(R.id.shuma);
        yanjiuBt=findViewById(R.id.yanjiu);
        yiliaoBt=findViewById(R.id.yiliao);
        xuexiBt=findViewById(R.id.xuexi);
        yuleBt=findViewById(R.id.yule);
        yundongBt=findViewById(R.id.yundong);
        zhufangBt=findViewById(R.id.zhufang);
        shuiguoBt=findViewById(R.id.shuiguo);
        shejiaoBt=findViewById(R.id.shejiao);

        fushiBt.setOnClickListener(this);
        canyinBt.setOnClickListener(this);
        gouwuBt.setOnClickListener(this);
        jiaotongBt.setOnClickListener(this);
        jujiaBt.setOnClickListener(this);
        lingshiBt.setOnClickListener(this);
        liwuBt.setOnClickListener(this);
        lvxingBt.setOnClickListener(this);
        meirongBt.setOnClickListener(this);
        riyongBt.setOnClickListener(this);
        tongxunBt.setOnClickListener(this);
        shumaBt.setOnClickListener(this);
        yanjiuBt.setOnClickListener(this);
        yiliaoBt.setOnClickListener(this);
        xuexiBt.setOnClickListener(this);
        yuleBt.setOnClickListener(this);
        yundongBt.setOnClickListener(this);
        zhufangBt.setOnClickListener(this);
        shuiguoBt.setOnClickListener(this);
        shejiaoBt.setOnClickListener(this);
        //数字键盘上的button事件绑定
        num0=findViewById(R.id.num_0);
        num1=findViewById(R.id.num_1);
        num2=findViewById(R.id.num_2);
        num3=findViewById(R.id.num_3);
        num4=findViewById(R.id.num_4);
        num5=findViewById(R.id.num_5);
        num6=findViewById(R.id.num_6);
        num7=findViewById(R.id.num_7);
        num8=findViewById(R.id.num_8);
        num9=findViewById(R.id.num_9);
        numdel=findViewById(R.id.num_del);
        numquxiao=findViewById(R.id.num_quxiao);
        numqueding=findViewById(R.id.num_queding);

        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        numdel.setOnClickListener(this);
        numquxiao.setOnClickListener(this);
        numqueding.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            //收入事件
            case R.id.gongzi:
                //System.out.println("这是工资");
                add(gongziBt.getText().toString());
                System.out.println(gongziBt.getText());
                break;
            case R.id.licai:
                add(licaiBt.getText().toString());
                System.out.println("这是理财");
                break;
            case R.id.jianzhi:
                add(jianzhiBt.getText().toString());
                System.out.println("这是兼职");
                break;
            case R.id.qitashouru:
                add(qitashouruBt.getText().toString());
                System.out.println("这是其他收入");
            case R.id.lijin:
                add(lijinBt.getText().toString());
                break;
            //支出事件
            case R.id.fushi:
                add(fushiBt.getText().toString());
                break;
            case R.id.canyin:
                add(canyinBt.getText().toString());
                break;
            case R.id.gouwu:
                add(gouwuBt.getText().toString());
                break;
            case R.id.jiaotong:
                add(jiaotongBt.getText().toString());
                break;
            case R.id.jujia:
                add(jujiaBt.getText().toString());
                break;
            case R.id.lingshi:
                add(lingshiBt.getText().toString());
                break;
            case R.id.liwu:
                add(liwuBt.getText().toString());
                break;
            case R.id.lvxing:
                add(lvxingBt.getText().toString());
                break;
            case R.id.meirong:
                add(meirongBt.getText().toString());
                break;
            case R.id.riyong:
                add(riyongBt.getText().toString());
                break;
            case R.id.tongxun:
                add(tongxunBt.getText().toString());
                break;
            case R.id.shuma:
                add(shumaBt.getText().toString());
                break;
            case R.id.yanjiu:
                add(yanjiuBt.getText().toString());
                break;
            case R.id.yiliao:
                add(yiliaoBt.getText().toString());
                break;
            case R.id.xuexi:
                add(xuexiBt.getText().toString());
                break;
            case R.id.yule:
                add(yuleBt.getText().toString());
                break;
            case R.id.yundong:
                add(yundongBt.getText().toString());
                break;
            case R.id.zhufang:
                add(zhufangBt.getText().toString());
                break;
            case R.id.shuiguo:
                add(shuiguoBt.getText().toString());
                break;
            case R.id.shejiao:
                add(shejiaoBt.getText().toString());
                break;
            //数字键盘的事件重写
            case R.id.num_0:

                break;
            case R.id.num_1:

                break;
            case R.id.num_2:

                break;
            case R.id.num_3:

                break;
            case R.id.num_4:

                break;
            case R.id.num_5:

                break;
            case R.id.num_6:

                break;
            case R.id.num_7:

                break;
            case R.id.num_8:

                break;
            case R.id.num_9:

                break;
            case R.id.num_dot:

                break;
            case R.id.num_del:

                break;
            case R.id.num_queding:

                break;
            case R.id.num_quxiao:

                break;

        }
    }
    public void add(String msg){
        nowItem=msg;
        System.out.println(nowItem);
    }
}
