package com.example.funcdemo.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.funcdemo.R;
import com.example.funcdemo.base.BaseActivity;
import com.example.funcdemo.utils.AssetUtil;

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
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("登录");

			// 自定义对话框布局
			View customView = LayoutInflater.from(this).inflate(R.layout.input_dialog_demo, null);
			builder.setView(customView);

			// 获取用户名和密码输入框
			EditText usernameInput = customView.findViewById(R.id.input1_text);

			// 添加“登录”按钮
			builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String username = usernameInput.getText().toString();

					// 处理用户输入的用户名和密码，比如进行验证
					if (username.isEmpty()) {
						showMsg("input name or password can not be empty");
					} else {
						showMsg("username=" + username + ";");
					}
				}
			});

			// 添加“取消”按钮
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});

			AlertDialog dialog = builder.create();
			dialog.show();
        } else if (id == R.id.clear) {
            clearDisplay();
        }
	}
}
