package com.example.funcdemo.activity;

import android.app.Activity;
import android.os.BatteryManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.funcdemo.R;
import com.example.funcdemo.base.BaseActivity;

public class BatteryActivity extends BaseActivity
		implements  View.OnClickListener {
	@Override
	protected int getLayoutId() {
		return R.layout.activity_battery;
	}

	@Override
	public void initView() {
		findViewById(R.id.battery_clear).setOnClickListener(this);
		findViewById(R.id.battery_info).setOnClickListener(this);
		mTextView = (TextView) findViewById(R.id.battery_result);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.battery_info:
				Log.i(TAG, "show Loading 1233213");
				BatteryManager manager = (BatteryManager) this.getSystemService(Activity.BATTERY_SERVICE);
				long current_now = manager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);

				showMsg("current_now=" + current_now + ";");

				break;
			case R.id.battery_clear:
				clearDisplay();
				break;
			default:
				break;
		}
	}

}
