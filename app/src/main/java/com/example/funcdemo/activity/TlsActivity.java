package com.example.funcdemo.activity;

import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.funcdemo.R;
import com.example.funcdemo.base.BaseActivity;
import com.example.funcdemo.utils.TlsChecker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TlsActivity extends BaseActivity
		implements  View.OnClickListener {

	private static final String TAG = "neil";

	@Override
	protected int getLayoutId() {
		return R.layout.activity_tls;
	}

	@Override
	public void initView() {
		findViewById(R.id.tls_connect).setOnClickListener(this);
		findViewById(R.id.tls_clear).setOnClickListener(this);
		mTextView = (TextView) findViewById(R.id.tls_result);
	}

    @Override
	public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tls_connect) {
			Log.i("neil", "show Loading");
			addLoadingMask("连接中，请稍候", false);
			ApplicationInfo info = getApplicationInfo();
			String packageName = info.packageName;
			ExecutorService executor = Executors.newFixedThreadPool(2);
			executor.execute(new Runnable() {
				@Override
				public void run() {
                    String resultTlsv12 = null;
					String resultTlsv11 = null;
					String resultTlsv13 = null;
					String resultPlain = null;
					TlsChecker.checkEnabledProtocols();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
						resultTlsv11 = TlsChecker.checkHttpsConnection("tls-v1-1.badssl.com", 1011);
						resultTlsv12 = TlsChecker.checkHttpsConnection("tls-v1-2.badssl.com", 1012);
						resultTlsv13 = TlsChecker.checkHttpsConnection("www.facebook.com", 443);
						resultPlain = TlsChecker.testPlainHttp("www.baidu.com", 80);
						Log.i(TAG, "shwo resultTlsv11: " + resultTlsv11 + ";");
						Log.i(TAG, "shwo resultTlsv12: " + resultTlsv12 + ";");
						Log.i(TAG, "shwo resultTlsv13: " + resultTlsv13 + ";");
						Log.i(TAG, "shwo resultPlain: " + resultPlain + ";");
						showMsg("result:" +
								"\ntls-v1-1.badssl.com:" + resultTlsv11 + ";" +
								"\ntls-v1-2.badssl.com:" + resultTlsv12 + ";" +
								"\nwww.facebook.com:" + resultTlsv13 + ";" +
								"\nwww.baidu.com:" + resultPlain + ";");
						return;
					}
					showMsg("result: 低于Q版本还未支持");
				}
			});
        } else if (id == R.id.tls_clear) {
            clearDisplay();
        }
	}
}
