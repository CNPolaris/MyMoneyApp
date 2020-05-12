package com.example.mymoneyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    public String shouruString="收入";
    public String zhichuString="支出";
    private int zhiORshou=0;//标记收入或支出
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
    //自定义数字键盘上的button等控件
    private Button num0=null,num1=null,num2=null,num3=null,num4=null,num5=null,num6=null,num7=null,num8=null,num9=null,numdot=null;
    private Button numdel=null,numquxiao=null,numqueding=null;
    private Button numjian=null,numjia=null;
    private LinearLayout beizhu,numlay;
    private TextView beizhuText=null;
    //当前所选中的项目
    public String nowItem="";//项目名
    public TextView moneyTv=null;//当前金额的显示
    //数字键盘
    protected String num="0";//整数部分
    protected String dotNum=".00";//小数部分
    protected boolean isDot;//判断是否为小数
    protected final int MAX_NUM = 9999999;    //最大整数
    protected final int DOT_NUM = 2;          //小数部分最大位数
    protected int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        TabHost tabHost=findViewById(R.id.addtab);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("收入",getResources().getDrawable(R.drawable.ic_launcher_background)).setContent(R.id.tab1));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("支出",null).setContent(R.id.tab2));

        //zhicuhSpinner.setOnItemLongClickListener();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_mingxi:
                        Intent intent1 = new Intent(AddActivity.this, DetailsActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
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
                        intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
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
        moneyTv=findViewById(R.id.showmoney);
        numjian=findViewById(R.id.num_jian);
        numjia=findViewById(R.id.num_jia);
        beizhu=findViewById(R.id.beizhulayout);
        numlay=findViewById(R.id.numLayout);
        beizhuText=findViewById(R.id.beizhu);

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
        numjian.setOnClickListener(this);
        numjia.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            //收入事件
            case R.id.gongzi:
                //System.out.println("这是工资");
                getItemName_shouru(gongziBt.getText().toString());
                System.out.println(gongziBt.getText());
                break;
            case R.id.licai:
                getItemName_shouru(licaiBt.getText().toString());
                System.out.println("这是理财");
                break;
            case R.id.jianzhi:
                getItemName_shouru(jianzhiBt.getText().toString());
                System.out.println("这是兼职");
                break;
            case R.id.qitashouru:
                getItemName_shouru(qitashouruBt.getText().toString());
                System.out.println("这是其他收入");
            case R.id.lijin:
                getItemName_shouru(lijinBt.getText().toString());
                break;
            //支出事件
            case R.id.fushi:
                getItemName_zhichu(fushiBt.getText().toString());
                break;
            case R.id.canyin:
                getItemName_zhichu(canyinBt.getText().toString());
                break;
            case R.id.gouwu:
                getItemName_zhichu(gouwuBt.getText().toString());
                break;
            case R.id.jiaotong:
                getItemName_zhichu(jiaotongBt.getText().toString());
                break;
            case R.id.jujia:
                getItemName_zhichu(jujiaBt.getText().toString());
                break;
            case R.id.lingshi:
                getItemName_zhichu(lingshiBt.getText().toString());
                break;
            case R.id.liwu:
                getItemName_zhichu(liwuBt.getText().toString());
                break;
            case R.id.lvxing:
                getItemName_zhichu(lvxingBt.getText().toString());
                break;
            case R.id.meirong:
                getItemName_zhichu(meirongBt.getText().toString());
                break;
            case R.id.riyong:
                getItemName_zhichu(riyongBt.getText().toString());
                break;
            case R.id.tongxun:
                getItemName_zhichu(tongxunBt.getText().toString());
                break;
            case R.id.shuma:
                getItemName_zhichu(shumaBt.getText().toString());
                break;
            case R.id.yanjiu:
                getItemName_zhichu(yanjiuBt.getText().toString());
                break;
            case R.id.yiliao:
                getItemName_zhichu(yiliaoBt.getText().toString());
                break;
            case R.id.xuexi:
                getItemName_zhichu(xuexiBt.getText().toString());
                break;
            case R.id.yule:
                getItemName_zhichu(yuleBt.getText().toString());
                break;
            case R.id.yundong:
                getItemName_zhichu(yundongBt.getText().toString());
                break;
            case R.id.zhufang:
                getItemName_zhichu(zhufangBt.getText().toString());
                break;
            case R.id.shuiguo:
                getItemName_zhichu(shuiguoBt.getText().toString());
                break;
            case R.id.shejiao:
                getItemName_zhichu(shejiaoBt.getText().toString());
                break;
            //数字键盘的事件重写
            case R.id.num_0:
                calcMoney(0);
                break;
            case R.id.num_1:
                calcMoney(1);
                break;
            case R.id.num_2:
                calcMoney(2);
                break;
            case R.id.num_3:
                calcMoney(3);
                break;
            case R.id.num_4:
                calcMoney(4);
                break;
            case R.id.num_5:
                calcMoney(5);
                break;
            case R.id.num_6:
                calcMoney(6);
                break;
            case R.id.num_7:
                calcMoney(7);
                break;
            case R.id.num_8:
                calcMoney(8);
                break;
            case R.id.num_9:
                calcMoney(9);
                break;
            case R.id.num_dot://判断小数
                if (dotNum.equals(".00")) {
                    isDot = true;
                    dotNum = ".";
                }
                moneyTv.setText(num + dotNum);
                break;
            case R.id.num_del://数字删除
                doDelete();
                break;
            case R.id.num_jian://减法
                Subtraction();
                break;
            case R.id.num_jia://加法
                addition(num);
                break;
            case R.id.num_queding://确定
                doCommit();
                break;
            case R.id.num_quxiao://取消
                doClear();
                break;

        }
    }
    //获取当前进行的项目
    protected void getItemName_zhichu(String msg){
        nowItem=msg;
        zhiORshou=-1;
        //显示数字键盘还有备注区
        setVi();
        System.out.println(nowItem);
    }
    protected void getItemName_shouru(String msg){
        nowItem=msg;
        zhiORshou=0;
        //显示数字键盘还有备注区
        setVi();
        System.out.println(nowItem);
    }
    //计算金额
    protected void calcMoney(int money){
        if (num.equals("0") && money == 0)
            return;
        if (isDot) {
            if (count < DOT_NUM) {
                count++;
                dotNum += money;
                moneyTv.setText(num + dotNum);
            }
        } else if (Integer.parseInt(num) < MAX_NUM) {
            if (num.equals("0"))
                num = "";
            num += money;
            moneyTv.setText(num + dotNum);
        }
    }
    //删除上次输入
    public void doDelete() {
        if (isDot) {
            if (count > 0) {
                dotNum = dotNum.substring(0, dotNum.length() - 1);
                count--;
            }
            if (count == 0) {
                isDot = false;
                dotNum = ".00";
            }
            moneyTv.setText(num + dotNum);
        } else {
            if (num.length() > 0)
                num = num.substring(0, num.length() - 1);
            if (num.length() == 0)
                num = "0";
            moneyTv.setText(num + dotNum);
        }
    }
    //设置数字键盘可见
    protected void setVi(){
        beizhu.setVisibility(View.VISIBLE);
        numlay.setVisibility(View.VISIBLE);
    }
    //恢复数字键盘不可见
    protected void setNotVi(){
        beizhu.setVisibility(View.GONE);
        numlay.setVisibility(View.GONE);
    }
    //清空/取消
    protected void doClear(){
        num = "0";
        count = 0;
        dotNum = ".00";
        isDot = false;
        moneyTv.setText("0.00");
        beizhuText.setText("");
        setNotVi();
    }
    //向数据库中插入数据
    protected void insertData(final String username, final String zhi,final String type, final float amount, final String trade){
        new Thread(){
            public void run(){
                try{
                    DBUtil dbUtil=new DBUtil();
                    dbUtil.InsertData(username,zhi,type,amount,trade);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    protected void doCommit(){
        if(zhiORshou==0){//执行收入操作
            doCommitshouru();
        }
        else if(zhiORshou==-1){//执行支出操作
            doCommitzhichu();
        }
    }
    //确认提交-->执行向数据库插入支出的操作
    protected void doCommitzhichu(){
        String username=UserManage.getInstance().getUserInfo(this).getUsername();
        String beizhuString=beizhuText.getText().toString();
        insertData(username,zhichuString,nowItem,0-Float.valueOf(num),beizhuString);
        doClear();
        setNotVi();
    }
    //确认提交-->执行向数据库插入收入的操作
    protected void doCommitshouru(){
        String username=UserManage.getInstance().getUserInfo(this).getUsername();
        String beizhuString=beizhuText.getText().toString();
        insertData(username,zhichuString,nowItem,Float.valueOf(num),beizhuString);
        doClear();
        setNotVi();
    }
    //加法
    protected void addition(String money){
        int tempMoney=Integer.valueOf(money);
    }
    //减法
    protected void Subtraction(){

    }
}
