package com.example.funcdemo.utils;

import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TlsChecker {
    private static final String TAG = "TLSChecker";

    static final String OBSOLETE_PROTOCOL_SSLV3 = "SSLv3";
    static final String SUPPORTED_PROTOCOL_TLSV1 = "TLSv1";
    static final String SUPPORTED_PROTOCOL_TLSV1_1 = "TLSv1.1";
    static final String SUPPORTED_PROTOCOL_TLSV1_2 = "TLSv1.2";
    static final String SUPPORTED_PROTOCOL_TLSV1_3 = "TLSv1.3";
    private static final String[] SUPPORTED_PROTOCOLS = new String[] {
            SUPPORTED_PROTOCOL_TLSV1,
            SUPPORTED_PROTOCOL_TLSV1_1,
            SUPPORTED_PROTOCOL_TLSV1_2,
            SUPPORTED_PROTOCOL_TLSV1_3,
    };

    private static final String[] testlist = new String[] {
            SUPPORTED_PROTOCOL_TLSV1,
            SUPPORTED_PROTOCOL_TLSV1_1,

    };

    @RequiresApi(api = Build.VERSION_CODES.Q) // TLS 1.3 requires API 29 or later
    public static String checkHttpsConnection(String host, int port) {
        String result = "";
        try {
            // 创建 SSL Socket 工厂
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            // 创建 SSL Socket
            try (SSLSocket socket = (SSLSocket) factory.createSocket()) {
                // 打印支持的协议
                String[] supportedProtocols = socket.getSupportedProtocols();
                Log.i(TAG, "支持的协议 (Supported Protocols):");
                for (String protocol : supportedProtocols) {
                    Log.i(TAG, protocol);
                }

                // 打印当前启用的协议
//                String[] enabledProtocols = socket.getEnabledProtocols();
//                Log.i(TAG, "启用的协议 (Enabled Protocols):");
//                for (String protocol : enabledProtocols) {
//                    Log.i(TAG, protocol);
//                }

                // 连接服务器
                socket.connect(new InetSocketAddress(host, port), 5000);

                // 启动握手
                socket.startHandshake();

                // 获取协商的协议
                String protocol = socket.getSession().getProtocol();

                // 打印结果
                Log.i(TAG, "连接成功! 协商的协议版本为: " + protocol);
                result = "连接成功! 协商的协议版本为: " + protocol;
            }
        } catch (UnknownHostException e) {
            result = "未知主机: " + host;
            Log.e(TAG, "未知主机: " + host, e);
        } catch (IOException e) {
            result = "连接失败: 检查网络或服务器是否支持 HTTPS";
            Log.e(TAG, "连接失败: 检查网络或服务器是否支持 HTTPS", e);
        }

        return result;
    }

    // 测试明文 HTTP 接口
    public static String testPlainHttp(String host, int port) {
        StringBuilder result = new StringBuilder();

        try (Socket socket = new Socket()) {
            // 连接到服务器
            socket.connect(new InetSocketAddress(host, port), 5000);

            Log.i(TAG, "socket.connect success!");
            result = new StringBuilder("socket.connect success!");
            // 构建 HTTP 请求
            String httpRequest = "GET / HTTP/1.1\r\n" +
                    "Host: " + host + "\r\n" +
                    "Connection: close\r\n\r\n";

            // 发送请求
            OutputStream output = socket.getOutputStream();
            output.write(httpRequest.getBytes());
            output.flush();

            // 读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            Log.i(TAG, "HTTP 响应:");
            while ((line = reader.readLine()) != null) {
        //        Log.i(TAG, line);
            }

            result.append("\n明文 HTTP 连接成功");
            Log.i(TAG, "result:" + result.toString() + ";");
        } catch (IOException e) {
            Log.e(TAG, "明文 HTTP 连接失败: " + host + ":" + port, e);
            result = new StringBuilder("明文 HTTP 连接失败: " + host + ":" + port);
        }

        return result.toString();
    }

    public static void checkEnabledProtocols() {
        for (String protocol : testlist) {

            if (!Arrays.asList(SUPPORTED_PROTOCOLS).contains(protocol)) {
                Log.i("neil","protocol " + protocol + " is not supported");
            }


//            if (!protocol.equals(SUPPORTED_PROTOCOL_TLSV1)
//                    && !protocol.equals(SUPPORTED_PROTOCOL_TLSV1_1)
//                    && !protocol.equals(SUPPORTED_PROTOCOL_TLSV1_2)
//                    && !protocol.equals(SUPPORTED_PROTOCOL_TLSV1_3)
//                    && !protocol.equals(OBSOLETE_PROTOCOL_SSLV3)) {
//                throw new IllegalArgumentException("protocol " + protocol + " is not supported");
//            }
        }

    }
}
