package com.example.mymoneyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class chartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        BottomNavigationView bottomNavigationView=findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_mingxi:
                        Intent intent1=new Intent(chartActivity.this,DetailsActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_tubiao:
                        Intent intent2=new Intent(chartActivity.this,chartActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_wode:
                        Intent intent3=new Intent(chartActivity.this,MyInfoActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_jizhang:
                        Intent intent4=new Intent(chartActivity.this,AddActivity.class);
                        startActivity(intent4);
                        break;
                }
            }
        });
    }
}
