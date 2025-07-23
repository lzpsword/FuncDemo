package com.example.funcdemo;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.funcdemo.activity.BatteryActivity;
import com.example.funcdemo.activity.DemoActivity;
import com.example.funcdemo.activity.DpmsActivity;
import com.example.funcdemo.activity.LightHalActivity;
import com.example.funcdemo.activity.TlsActivity;
import com.example.funcdemo.base.BaseActivity;

public class MainActivity extends BaseActivity implements  View.OnClickListener {
    private ComponentName mDeviceAdminReceiver;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        findViewById(R.id.demo).setOnClickListener(this);
        findViewById(R.id.battery).setOnClickListener(this);
        findViewById(R.id.dpms).setOnClickListener(this);
        findViewById(R.id.tls).setOnClickListener(this);
        findViewById(R.id.light_hal).setOnClickListener(this);
    }


    @Override
    public void onClick(@NonNull View view) {
        Intent intent = new Intent();
        int id = view.getId();
        if (id == R.id.demo) {
            intent.setClass(this, DemoActivity.class);
        } else if (id == R.id.battery) {
            Log.i(TAG, "fdsadfd ");
            intent.setClass(this, BatteryActivity.class);
        } else if (id == R.id.dpms) {
            Log.i(TAG, "dpms");
            intent.setClass(this, DpmsActivity.class);
        } else if (id == R.id.tls) {
            Log.i(TAG, "tls");
            intent.setClass(this, TlsActivity.class);
        } else if (id == R.id.light_hal) {
            Log.i(TAG, "light_hal");
            intent.setClass(this, LightHalActivity.class);
        }
        startActivity(intent);
    }

    public void activeDeviceAdmin(){
        Log.i(TAG, "activeDeviceAdmin");
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminReceiver);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "用于设备和应用安全管理");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.startActivity(intent);
    }

}