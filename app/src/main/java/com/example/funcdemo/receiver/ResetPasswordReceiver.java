package com.example.funcdemo.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ResetPasswordReceiver  extends BroadcastReceiver {
    private static final String TAG = "neil";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "BR action=" + action);

        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (dpm == null){
            Log.i(TAG, "BroadcastReceiver dpm = null");
            return;
        }

        ComponentName mDap = new ComponentName(context, CTDeviceAdminReceiver.class);
        boolean ret = false;

        if("com.example.funcdemo.RESETPW".equals(action)){
            Log.i(TAG, "BR RESETPW");
            ret = dpm.resetPassword("", 0);
            Log.i(TAG, "ret=" + ret);
        }
    }
}
