package com.example.funcdemo;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.funcdemo.activity.BatteryActivity;
import com.example.funcdemo.activity.DemoActivity;
import com.example.funcdemo.base.BaseActivity;

public class MainActivity extends BaseActivity implements  View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        findViewById(R.id.demo).setOnClickListener(this);
        findViewById(R.id.battery).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.demo:
                intent.setClass(this, DemoActivity.class);
                break;
            case R.id.battery:
                Log.i(TAG, "fdsadfd ");
                intent.setClass(this, BatteryActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }



}