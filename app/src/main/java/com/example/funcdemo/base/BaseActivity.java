package com.example.funcdemo.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.funcdemo.utils.LoadingUtil;

/**
 * Created by edward on 2022/5/19.
 */
public abstract class BaseActivity extends Activity implements IBaseView {

    private LoadingUtil loading; // 遮罩层效果
    protected TextView mTextView;

    protected final static String TAG = "neil";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        // 初始化UI元素
        initView();
    }

    @Override
    public Context getAppContext()  {
        return getApplicationContext();
    }

    @SuppressLint("HandlerLeak")
    protected Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    hideLoadingMask();
                    mTextView.append((String) msg.obj + "\n");
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 在界面上显示消息
     * @param msg
     */
    @Override
    public void showMsg(String msg) {
        Message message = Message.obtain();
        message.what = 0;
        message.obj = msg;
        mHandler.sendMessage(message);
    }

    /**
     * 加载遮罩层（无参数）
     */
    @Override
    public void addLoadingMask() {
        loading = new LoadingUtil(this);
        loading.show();
    }

    /**
     * 加载遮罩层（有参数）
     *
     * @param message 显示文字信息
     * @param cancelButton 显示Cancel按钮（true：显示 false：隐藏）
     */
    @Override
    public void addLoadingMask(String message, boolean cancelButton) {
        if (loading != null) {
            loading = null;
        }
        loading = new LoadingUtil(this, message);
        loading.setCancelButton(cancelButton);
        loading.show();
    }

    /**
     * 更新遮罩层提示信息
     *
     * @param message 显示文字信息
     */
    @Override
    public void updateLoadingMask(String message) {
        if (loading != null && loading.isShowing()) {
            loading.tvMessage.setText(message);
        }
    }

    /**
     * 关闭遮罩层
     */
    @Override
    public void hideLoadingMask() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
            loading = null;
        }
    }

    protected void createToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 清空界面显示的文本
     */
    @Override
    public void clearDisplay() {
        if (mTextView != null) {
            mTextView.setText("");
        }
    }

    /**
     * 获取布局id
     * @return layoutId
     */
    protected abstract int getLayoutId();

    /**
     * 初始化UI元素
     */
    protected abstract void initView();

}
