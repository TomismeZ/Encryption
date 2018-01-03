package com.example.zdk.encryption;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zdk.encryption.activity.SetPasswordActivity;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button setPassword;
    private SharedPreferences sp = null;
    private boolean isSet = false;
    private String pass = null;
    private EditText checkPass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(); //初始化组件
        checkPass(); //检查密码

    }

    /**
     * 检查密码
     */
    private void checkPass() {
        try {
            sp = getSharedPreferences("pass", Context.MODE_PRIVATE);
            String passWay = sp.getString("passway", null);
            if (passWay.equals("digitalpass")) {
                isSet = sp.getBoolean("isSet", false);
                pass = sp.getString("password", null);
                if (isSet) {
                    LayoutInflater factory = LayoutInflater
                            .from(MainActivity.this);
                    final View textEntry = factory.inflate(
                            R.layout.confirm_pass, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("密码校验")
                            .setIcon(
                                    getResources().getDrawable(
                                            android.R.drawable.ic_lock_lock))
                            .setView(textEntry)
                            .setCancelable(false)
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            // TODO Auto-generated method stub
                                            checkPass = textEntry
                                                    .findViewById(R.id.check_pass);
                                            if (checkPass.getText().toString()
                                                    .trim().equals(pass)) {
                                                try {
                                                    Field field = dialog
                                                            .getClass()
                                                            .getSuperclass()
                                                            .getDeclaredField(
                                                                    "mShowing");
                                                    field.setAccessible(true);
                                                    field.set(dialog, true);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                dialog.dismiss();
                                            } else {
                                                try {
                                                    Field field = dialog
                                                            .getClass()
                                                            .getSuperclass()
                                                            .getDeclaredField(
                                                                    "mShowing");
                                                    field.setAccessible(true);
                                                    field.set(dialog, false);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                Toast.makeText(
                                                        MainActivity.this,
                                                        "密码错误，请重试！",
                                                        Toast.LENGTH_LONG)
                                                        .show();
                                                checkPass.setText("");

                                            }
                                        }
                                    });
                    builder.create().show();
                }
            }
        } catch (Exception e) {
            Log.e("MainActivity", e.toString());
        }
    }

    /**
     * 初始化组件
     */
    private void initView() {
        setPassword = findViewById(R.id.set_password);
        setPassword.setOnClickListener(this);  //注册点击事件监听器

    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_password:
                Intent intent = new Intent(this, SetPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
