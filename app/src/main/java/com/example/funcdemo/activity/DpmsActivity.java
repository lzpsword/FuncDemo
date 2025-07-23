package com.example.funcdemo.activity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.funcdemo.R;
import com.example.funcdemo.base.BaseActivity;
import com.example.funcdemo.receiver.CTDeviceAdminReceiver;
import com.example.funcdemo.utils.InputDialogUtil;

public class DpmsActivity extends BaseActivity
		implements  View.OnClickListener {

	private static final String TAG = "neil";

	private static final String mResetToken = "12345678901234567890123456789012";
	private Context mContext = null;

	private DevicePolicyManager mDPMS = null;

	private ComponentName mDeviceAdminReceiver;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_dpms;
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

	@Override
	public void initView() {
		findViewById(R.id.dpms_btn1).setOnClickListener(this);
		findViewById(R.id.dpms_active_admin).setOnClickListener(this);
		findViewById(R.id.dpms_clear).setOnClickListener(this);
		findViewById(R.id.dpms_reset_password).setOnClickListener(this);
		findViewById(R.id.dpms_set_reset_token).setOnClickListener(this);
		findViewById(R.id.dpms_reset_pw_with_token).setOnClickListener(this);
		mTextView = (TextView) findViewById(R.id.dpms_result);

		mContext = getApplicationContext();

		mDeviceAdminReceiver = new ComponentName(mContext, CTDeviceAdminReceiver.class);
		Log.i(TAG, "mDAR=" + mDeviceAdminReceiver);

		mDPMS = (DevicePolicyManager) getApplicationContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
	}

	@Override
	public void onClick(View view) {
		boolean ret = false;
        int id = view.getId();
        if (id == R.id.dpms_btn1) {
            boolean isAdminActive = mDPMS.isAdminActive(mDeviceAdminReceiver);
            boolean isDeviceOwner = mDPMS.isDeviceOwnerApp("com.example.funcdemo");
            showMsg("deviceOwner=" + isDeviceOwner + ";adminActive=" + isAdminActive);
        } else if (id == R.id.dpms_active_admin) {
            activeDeviceAdmin();
        } else if (id == R.id.dpms_reset_password) {
            InputDialogUtil.showTextInputDialog(this, "重置密码", "密码", inputText -> {
                if (inputText.isEmpty()) {
                    showMsg("reset password to null");
                    Log.i(TAG, "reset password to null");
                } else {
                    showMsg("reset password to [" + inputText + "]");
                    Log.i(TAG, "reset password to [" + inputText + "]");
                }

				boolean tmp = mDPMS.resetPassword(inputText, 0);
				Log.i(TAG, "reset password ret=" + tmp);
				showMsg("reset password ret=" + tmp);
            });
        } else if (id == R.id.dpms_set_reset_token) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "set reset token 32bit");
                ret = mDPMS.setResetPasswordToken(mDeviceAdminReceiver, mResetToken.getBytes());
                showMsg("Set reset token ret=" + ret);
                Log.i(TAG, "set reset token 32bit end");
            }
        } else if (id == R.id.dpms_reset_pw_with_token) {
			InputDialogUtil.showTextInputDialog(this, "重置密码", "密码", inputText -> {
				if (inputText.isEmpty()) {
					showMsg("reset password to null");
					Log.i(TAG, "reset password to null");
				} else {
					showMsg("reset password to [" + inputText + "]");
					Log.i(TAG, "reset password to [" + inputText + "]");
				}

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					boolean tmp = mDPMS.resetPasswordWithToken(mDeviceAdminReceiver, inputText, mResetToken.getBytes(), 0);
					Log.i(TAG, "resetPasswordWithToken password=[" + inputText + "]; ret=" + tmp);
					showMsg("resetPasswordWithToken password=[" + inputText + "]; ret=" + tmp);
				}
			});

        } else if (id == R.id.dpms_clear) {
            clearDisplay();
        }
	}
}
