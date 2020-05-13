package com.example.mymoneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class UpdateActivity extends AppCompatActivity {
    private LinearLayout llreturn=null;
    private EditText idText=null, leixingText=null, xiangmuText =null, jineText =null, beizhuText =null, riqiText =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        //
        idText =findViewById(R.id.editId);
        leixingText =findViewById(R.id.editLeixing);
        xiangmuText =findViewById(R.id.editxiangmu);
        jineText =findViewById(R.id.editJine);
        beizhuText =findViewById(R.id.editBeizhu);
        riqiText =findViewById(R.id.editDate);

        idText.setText(DetailsActivity.result.getId());leixingText.setText(DetailsActivity.result.getLeixing());xiangmuText.setText(DetailsActivity.result.getXiangmu());
        jineText.setText(""+DetailsActivity.result.getMoney());beizhuText.setText(DetailsActivity.result.getBeizhu());riqiText.setText(""+DetailsActivity.result.getDate());
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

    @Override
    protected void onStart() {
        super.onStart();

    }
}
