package com.example.zdk.encryption.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zdk.encryption.MainActivity;
import com.example.zdk.encryption.R;
import com.example.zdk.encryption.view.LockPatternUtils;
import com.example.zdk.encryption.view.LockPatternView;

import java.util.List;

public class CheckPassActivity extends AppCompatActivity {
    private LockPatternView lockPatternView = null;
    private LockPatternUtils lockPatternUtils = null;
    private SharedPreferences sharedPreferences = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_pass);
        sharedPreferences = getSharedPreferences("image", MODE_PRIVATE);
        lockPatternView =  findViewById(R.id.lock_check);
        lockPatternUtils = new LockPatternUtils(this);
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
                SharedPreferences preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
                String pass=preferences.getString("lock_pwd", "");
                if (pass.trim().equals(lockPatternUtils.patternToString(pattern))) {
                    Intent intent=new Intent(CheckPassActivity.this,MainActivity.class);
                    startActivity(intent);
                    CheckPassActivity.this.finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                else {
                    Toast.makeText(CheckPassActivity.this, "密码错误，请重试！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
