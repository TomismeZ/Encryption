package com.example.zdk.encryption.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zdk.encryption.MainActivity;
import com.example.zdk.encryption.R;
import com.example.zdk.encryption.view.LockPatternUtils;
import com.example.zdk.encryption.view.LockPatternView;

import java.util.List;

public class ConfirmPassActivity extends AppCompatActivity {
    private Button last;
    private Button ok;
    private static boolean isSet = false;
    private LockPatternView lockPatternView;
    private LockPatternUtils lockPatternUtils;
    private SharedPreferences preferences;
    private String pass="";
    private SharedPreferences sharedPreferences = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pass);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        sharedPreferences = getSharedPreferences("image", MODE_PRIVATE);
        lockPatternView = findViewById(R.id.lock_confirm);
        last=findViewById(R.id.last);
        last.setOnClickListener(new LastListener());
        ok=findViewById(R.id.ok);
        ok.setOnClickListener(new OkListener());
        lockPatternUtils = new LockPatternUtils(this);
        preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
        pass=preferences.getString("lock_pwd", "");
        lockPatternView.setOnPatternListener(new LockPatternView.OnPatternListener() {
            @Override
            public void onPatternStart() {

            }

            @Override
            public void onPatternCleared() {

            }

            @Override
            public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

            }

            @Override
            public void onPatternDetected(List<LockPatternView.Cell> pattern) {
                if (pass.trim().equals(lockPatternUtils.patternToString(pattern))) {
                    isSet=true;
                }
                else {
//                    Toast.makeText(ConfirmPassActivity.this, "密码不一致，设置失败！", Toast.LENGTH_LONG).show();
                    isSet=false;
                }
            }
        });
    }

    /**
     * 上一步
     */
    class LastListener implements View.OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent=new Intent();
            intent.setClass(ConfirmPassActivity.this, GraphicPassSetActivity.class);
            startActivity(intent);
            setPass();
            ConfirmPassActivity.this.finish();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }

    /**
     * 确定
     */
    class OkListener implements View.OnClickListener {
        public void onClick(View v) {
            if (isSet) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("passway", "graphicpass");
                editor.putBoolean("isSet", true);
                editor.commit();
                Intent intent=new Intent(ConfirmPassActivity.this,MainActivity.class);
                startActivity(intent);
                ConfirmPassActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                isSet=false;
                Toast.makeText(ConfirmPassActivity.this, "密码设置成功", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(ConfirmPassActivity.this, "密码不一致，设置失败！", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 设置密码
     */
    public void setPass(){
        SharedPreferences.Editor editor = preferences.edit();
        if (GraphicPassSetActivity.pass!=null&&!GraphicPassSetActivity.pass.equals("")) {
            editor.putString("lock_pwd", GraphicPassSetActivity.pass);
            editor.commit();
        }else {
            editor.putString("lock_pwd", null);
        }
    }
}
