package com.example.zdk.encryption.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zdk.encryption.MainActivity;
import com.example.zdk.encryption.R;

/**
 * 闪屏页的作用
 * 1.欢迎界面
 * 2.初始化工作
 */
public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferences sp=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        },2000);
    }

    /**
     * 根据条件来跳转到哪一个界面
     */
    private void startMainActivity() {
        sp=getSharedPreferences("pass", Context.MODE_PRIVATE);
        String passWay=sp.getString("passway", null);
        Intent intent = null;
        if (passWay!=null) {
            if (passWay.equals("graphicpass")) {
                intent =new Intent(WelcomeActivity.this,CheckPassActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
            else {
                intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        }
        else {
            intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            finish();
        }
    }

    private Handler handler=new Handler();
}
