package com.example.funcdemo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.widget.EditText;

public class InputDialogUtil {

    public static void showInputDialog(Context context, String title, String hint,
                                       int inputType,
                                       OnInputConfirmedListener listener) {
        final EditText input = new EditText(context);
        input.setHint(hint);
        input.setInputType(inputType);

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    String text = input.getText().toString();
                    listener.onInputConfirmed(text);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // 定义一个输入确认的回调接口
    public interface OnInputConfirmedListener {
        void onInputConfirmed(String inputText);
    }
}
