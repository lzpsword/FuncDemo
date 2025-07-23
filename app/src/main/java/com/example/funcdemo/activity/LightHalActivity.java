package com.example.funcdemo.activity;

import android.view.View;
import android.widget.TextView;

import com.example.funcdemo.R;
import com.example.funcdemo.base.BaseActivity;
import com.example.funcdemo.utils.InputDialogUtil;

public class LightHalActivity extends BaseActivity
		implements  View.OnClickListener {

	private static final String TAG = "neil";

	private int mColor = 0xFF0000;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_light_hal;
	}

	@Override
	public void initView() {
		findViewById(R.id.light_color_btn).setOnClickListener(this);
		findViewById(R.id.light_rgb0_btn).setOnClickListener(this);
		findViewById(R.id.light_rgb1_btn).setOnClickListener(this);
		findViewById(R.id.light_rgb2_btn).setOnClickListener(this);
		findViewById(R.id.light_rgb3_btn).setOnClickListener(this);
		findViewById(R.id.light_clear).setOnClickListener(this);
		mTextView = (TextView) findViewById(R.id.light_result);
		mColor = 0xFF0000;
		showMsg("default Light color: #" + Integer.toHexString(mColor));
	}

    @Override
	public void onClick(View view) {
        int id = view.getId();
      	if (id == R.id.light_color_btn) {
			InputDialogUtil.showHexColorInputDialog(this, "InputColor", "Enter RGB color (e.g., #FF0000)", color -> {
				mColor = color & 0xFFFFFF;
				showMsg("Selected Light color: #" + Integer.toHexString(mColor));

			});
		} else if (id == R.id.light_rgb0_btn) {
			showMsg("TOGGLE RGB0" + " color: #" + Integer.toHexString(mColor));
		} else if (id == R.id.light_rgb1_btn) {
			showMsg("TOGGLE RGB1" + " color: #" + Integer.toHexString(mColor));
		} else if (id == R.id.light_rgb2_btn) {
			showMsg("TOGGLE RGB2" + " color: #" + Integer.toHexString(mColor));
		} else if (id == R.id.light_rgb3_btn) {
			showMsg("TOGGLE RGB3" + " color: #" + Integer.toHexString(mColor));
        } else if (id == R.id.light_clear) {
            clearDisplay();
        }
	}
}
