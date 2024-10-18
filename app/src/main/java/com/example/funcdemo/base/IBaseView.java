package com.example.funcdemo.base;

import android.content.Context;

/**
 * Created by Edward on 2022/5/19.
 */
public interface IBaseView {
    void clearDisplay();
    Context getAppContext();
    void showMsg(String msg);
    void hideLoadingMask();
    void addLoadingMask();
    void addLoadingMask(String message, boolean cancelButton);
    void updateLoadingMask(String message);
}
