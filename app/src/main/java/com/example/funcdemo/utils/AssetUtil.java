package com.example.funcdemo.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Edward on 2022/6/9.
 */
public class AssetUtil {
    /**
     * 拷贝Asset下的文件到context.getFilesDir()目录下
     */
    public static void copyFilefromAssets(Context context, String modelName) {
        InputStream is = null;
        OutputStream os = null;
        try {
            File outputDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            File modelFile = new File(outputDir, modelName);
            modelFile.delete();
            is = context.getAssets().open(modelName);
            os = new FileOutputStream(modelFile);
            byte[] buffer = new byte[1024];
            int length = is.read(buffer);
            while (length > 0) {
                os.write(buffer, 0, length);
                length = is.read(buffer);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
