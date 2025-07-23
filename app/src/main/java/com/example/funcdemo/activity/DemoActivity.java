package com.example.funcdemo.activity;
import android.util.Log;

import android.view.View;
import android.widget.TextView;

import com.example.funcdemo.R;
import com.example.funcdemo.base.BaseActivity;
import com.example.funcdemo.utils.AssetUtil;
import com.example.funcdemo.utils.InputDialogUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoActivity extends BaseActivity
		implements  View.OnClickListener {

	private static final String TAG = "neil";

	@Override
	protected int getLayoutId() {
		return R.layout.activity_demo;
	}

	@Override
	public void initView() {
		findViewById(R.id.tm_btn1).setOnClickListener(this);
		findViewById(R.id.clear).setOnClickListener(this);
		findViewById(R.id.demo_input_dialog).setOnClickListener(this);
		mTextView = (TextView) findViewById(R.id.result);
	}

    @Override
	public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tm_btn1) {
			Log.i("neil", "show Loading 1233213");
			addLoadingMask("拷贝中，请稍后", false);
			ExecutorService executor = Executors.newFixedThreadPool(2);
			executor.execute(new Runnable() {
				@Override
				public void run() {
					AssetUtil.copyFilefromAssets(getAppContext(), "test.zip");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Log.i(TAG, "shwo ok11");
					showMsg("ok");

				}
			});
		} else if (id == R.id.demo_input_dialog) {
			InputDialogUtil.showTextInputDialog(this, "Input", "Enter something", inputText -> {
				showMsg("You entered: " + inputText);
			});
        } else if (id == R.id.clear) {
            clearDisplay();
        }
	}
}
