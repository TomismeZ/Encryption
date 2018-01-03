package com.example.zdk.encryption.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zdk.encryption.R;
import com.example.zdk.encryption.view.LockPatternUtils;
import com.example.zdk.encryption.view.LockPatternView;

import java.util.List;

public class GraphicPassSetActivity extends AppCompatActivity {
    private static boolean isSet = false;
    private Button cancel;
    private Button next;
    private LockPatternView lockPatternView;
    private LockPatternUtils lockPatternUtils;
    private SharedPreferences preferences;
    public static String pass="";
    private int result;
    private SharedPreferences sharedPreferences = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_pass_set);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        sharedPreferences = getSharedPreferences("image", MODE_PRIVATE);
        lockPatternView = findViewById(R.id.lock);
        lockPatternUtils = new LockPatternUtils(this);
        cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new CancelListener());
        next=findViewById(R.id.next);
        next.setOnClickListener(new NextListener());
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
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("lock_pwd", lockPatternUtils.patternToString(pattern));
                editor.commit();
                isSet=true;
            }
        });
    }

    /**
     * 取消时的监听
     */
    class CancelListener implements View.OnClickListener {

        public void onClick(View v) {
            // TODO Auto-generated method stub
            setPass();
            finish();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }

    /**
     * 下一步时的监听
     */
    class NextListener implements View.OnClickListener {
        public void onClick(View v) {
            if (isSet) {
                Intent intent=new Intent();
                intent.setClass(GraphicPassSetActivity.this, ConfirmPassActivity.class);
                startActivity(intent);
                GraphicPassSetActivity.this.finish();
//                overridePendingTransition(R.anim.push_below_in,R.anim.push_below_out);
                isSet=false;
            }
            else {
                Toast.makeText(GraphicPassSetActivity.this, "请设置密码", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void setPass(){
        SharedPreferences.Editor editor = preferences.edit();
        if (pass!=null&&!pass.equals("")) {
            editor.putString("lock_pwd", pass);
            editor.commit();
        }else {
            editor.putString("lock_pwd", null);
        }
    }
}
