package com.example.funcdemo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.Toast;

public class InputDialogUtil {

    public static void showTextInputDialog(Context context, String title, String hint,
                                           OnTextConfirmedListener listener) {
        final EditText input = new EditText(context);
        input.setHint(hint);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    String text = input.getText().toString().trim();
                    listener.onTextConfirmed(text);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public static void showHexColorInputDialog(Context context, String title, String hint,
                                               OnColorConfirmedListener listener) {
        final EditText input = new EditText(context);
        input.setHint(hint);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setFilters(new InputFilter[]{new HexColorInputFilter()});

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    String text = input.getText().toString().trim();

                    if (!text.matches("^#([0-9a-fA-F]{6}|[0-9a-fA-F]{8})$")) {
                        Toast.makeText(context, "请输入合法的颜色代码（#RRGGBB 或 #AARRGGBB）", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        int color = Color.parseColor(text);
                        listener.onColorConfirmed(color);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(context, "无法解析颜色代码", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private static class HexColorInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (c == '#' && dstart != 0) return "";
                if (!Character.toString(c).matches("[#0-9a-fA-F]")) return "";
            }
            return null;
        }
    }

    // 文本输入回调接口
    public interface OnTextConfirmedListener {
        void onTextConfirmed(String inputText);
    }

    // 颜色输入回调接口
    public interface OnColorConfirmedListener {
        void onColorConfirmed(int color);
    }
}
