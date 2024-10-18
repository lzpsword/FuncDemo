package com.example.funcdemo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.funcdemo.R;

/**
 * 加载中Dialog
 */
public class LoadingUtil extends AlertDialog implements View.OnClickListener {
	protected String message = "加载中...";
	public TextView tvMessage;
	protected boolean cancelButton = false;
	
	public LoadingUtil(Context context) {
		super(context);
		this.setCancelable(false);
	}

	public LoadingUtil(Context context, String message) {
		super(context);
		this.message = message;
		this.setCancelable(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_loading);

		tvMessage = (TextView) findViewById(R.id.loading_message);
		tvMessage.setText(this.message);

		Button button = (Button) findViewById(R.id.loading_button);
		button.setOnClickListener(this);
		if (!isCancelButton()) {
			button.setVisibility(View.GONE);
		}
	}

	public boolean isCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(boolean cancelButton) {
		this.cancelButton = cancelButton;
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}
}
