package com.example.funcdemo.activity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.funcdemo.R;
import com.example.funcdemo.base.BaseActivity;
import com.example.funcdemo.receiver.CTDeviceAdminReceiver;

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
		switch (view.getId()) {
			case R.id.dpms_btn1:
				boolean isAdminActive = mDPMS.isAdminActive(mDeviceAdminReceiver);
				boolean isDeviceOwner = mDPMS.isDeviceOwnerApp("com.example.funcdemo");
				showMsg("deviceOwner=" + isDeviceOwner + ";adminActive=" + isAdminActive);
//				Log.i("neil", "show Loading 1233213");
//				addLoadingMask("拷贝中，请稍后", false);
//				ExecutorService executor = Executors.newFixedThreadPool(2);
//				executor.execute(new Runnable() {
//					@Override
//					public void run() {
//						AssetUtil.copyFilefromAssets(getAppContext(), "test.zip");
//						try {
//							Thread.sleep(3000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						Log.i(TAG, "shwo ok11");
//						showMsg("ok");
//
//					}
//				});
				break;
			case R.id.dpms_active_admin:
				activeDeviceAdmin();
				break;
			case R.id.dpms_reset_password:
				Log.i(TAG, "reset password to null");
				mDPMS.resetPassword("", 0);
				Log.i(TAG, "reset password end");
				break;
			case R.id.dpms_set_reset_token:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					Log.i(TAG, "set reset token 32bit");
					ret = mDPMS.setResetPasswordToken(mDeviceAdminReceiver, mResetToken.getBytes());
					showMsg("Set reset token ret=" + ret);
					Log.i(TAG, "set reset token 32bit end");
				}
				break;
			case R.id.dpms_reset_pw_with_token:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					ret = mDPMS.resetPasswordWithToken(mDeviceAdminReceiver, "4321", mResetToken.getBytes(), 0);
					Log.i(TAG, "resetPasswordWithToken password=4321 ret=" + ret);
					showMsg("resetPasswordWithToken password=4321 ret=" + ret);
				}
				break;
			case R.id.dpms_clear:
				clearDisplay();
				break;
			default:
				break;
		}
	}
}
