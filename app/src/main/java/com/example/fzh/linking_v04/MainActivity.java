package com.example.fzh.linking_v04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏系统状态栏
        getSupportActionBar().hide();//隐藏系统标题栏

        setContentView(R.layout.activity_main);
    }

    protected void Start(View v) {
        Intent it = new Intent(this, MainGame.class);//启动主游戏Activity
        startActivity(it);
    }

}
