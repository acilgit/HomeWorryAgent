package com.android.housingonitoringagent.homeworryagent.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.android.housingonitoringagent.homeworryagent.User;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2015/12/15 0015.
 */
public class MultiFileUploadUtil {
    private final String TAG = "MultiFileUploadUtil";
    private static MultiFileUploadUtil multiFileUploadUtil;
    private final int chunkSize = 64*1024;

    public void setListener(OnUploadProgressListener listener) {
        this.listener = listener;
    }
    OnUploadProgressListener listener;
    private static final int PERCENTUNIT_HUNDRED=100;
    private final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
    private final String PREFIX = "--";
    private final String LINEND = "\r\n";
    private final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
    private final int readTimeOut = 30 * 1000; // 读取超时
    private final int connectTimeout = 20 * 1000; // 超时时间
    private final String CHARSET = "utf-8"; // 设置编码
    /***
     * 上传成功
     */
    public static final int UPLOAD_SUCCESS_CODE = 1;
    /**
     * 文件不存在
     */
    public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
    /**
     * 服务器出错
     */
    public static final int UPLOAD_SERVER_ERROR_CODE = 3;



    public interface OnUploadProgressListener{
        void onUpLoad(int progress);
    }
    public static MultiFileUploadUtil getInstance() {
        if (null == multiFileUploadUtil) {
            multiFileUploadUtil = new MultiFileUploadUtil();
        }
        return multiFileUploadUtil;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void upload(String actionUrl, Map<String, String> params, LinkedList<File> files) {
        String result = null;
        try {
            URL uri = new URL(actionUrl);
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(readTimeOut); // 缓存的最长时间
            conn.setConnectTimeout(connectTimeout);
            conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST");
            conn.setChunkedStreamingMode(chunkSize);
//            conn.setRequestProperty("sign", Md5Utils.md5Encrypt());
            conn.setRequestProperty("sessionId", User.getSessionId());
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);


            // 首先组拼文本类型的参数
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINEND);
                sb.append("Content-Disposition: form-data; name=\""+entry.getKey()+ "\"" + LINEND);
                sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                sb.append(LINEND);
                sb.append(entry.getValue());
                sb.append(LINEND);
                sb.append("sessionId").append("=").append(User.getSessionId());
                sb.append(LINEND);
            }

            final DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(sb.toString().getBytes());

            InputStream in = null;
            // 发送文件数据
            if (files != null) {
                long totalLength=0;
                double progress=0;
                for (File file:files) {
                    totalLength+=file.length();
                }
                for (File file : files) {
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(PREFIX);
                    sb1.append(BOUNDARY);
                    sb1.append(LINEND);
                    sb1.append("Content-Disposition: form-data; name=\"" + "file" + "\"; filename=\""
                            + file.getName() + "\"" + LINEND);
                    sb1.append("Content-Type: image/png; charset=" + CHARSET + LINEND); // application/octet-stream
                    sb1.append(LINEND);
                    outStream.write(sb1.toString().getBytes());

                    InputStream is = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        progress+=len;
                        sendPercent(progress, totalLength);
                        outStream.write(buffer, 0, len);
                    }
                    is.close();
                    outStream.write(LINEND.getBytes());
                }
//                outStream.write(sb1.toString().getBytes());
            }
            // 请求结束标志
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
            outStream.write(end_data);
            outStream.flush();
            // 得到响应码
            int res = conn.getResponseCode();
            if (res == 200) {
                in = conn.getInputStream();
                int ch;
                StringBuilder sb2 = new StringBuilder();
                while ((ch = in.read()) != -1) {
                    sb2.append((char) ch);
                }
                result = sb2.toString();
                sendMessage(UPLOAD_SUCCESS_CODE, result);
                System.out.println("是否成功？" + result);
            } else {
                Log.e(TAG, "request error");
                sendMessage(UPLOAD_SERVER_ERROR_CODE, result);
                return;
            }
            outStream.close();
            conn.disconnect();

        } catch (MalformedURLException e) {
            sendMessage(UPLOAD_SERVER_ERROR_CODE, "上传失败：error=" + e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
            sendMessage(UPLOAD_SERVER_ERROR_CODE, "上传失败：error=" + e.getMessage());
            e.printStackTrace();
            return;
        }
    }

    private void sendPercent(double progress, long totalLength) {
        double percent=(progress*PERCENTUNIT_HUNDRED)/totalLength;
        if(listener!=null){
            listener.onUpLoad((int)percent);
        }
    }

    /**
     * 发送上传结果
     *
     * @param responseCode
     * @param responseMessage
     */
    private void sendMessage(int responseCode, String responseMessage) {
        onUploadProcessListener.onUploadDone(responseCode, responseMessage);
    }

    /**
     * 下面是一个自定义的回调函数，用到回调上传文件是否完成
     *
     */
    public interface OnUploadProcessListener {
        /**
         * 上传响应
         */
        void onUploadDone(int responseCode, String message);
    }

    private OnUploadProcessListener onUploadProcessListener;

    public void setOnUploadProcessListener(OnUploadProcessListener onUploadProcessListener) {
        this.onUploadProcessListener = onUploadProcessListener;
    }
}
