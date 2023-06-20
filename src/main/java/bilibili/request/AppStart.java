package bilibili.request;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppStart {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    String action = "/v2/app/start";
    String code = "CONPD7EKD0JK8";
    long app_id = 3463268406164140L;
    String signatureNonce;

    String start;

    public AppStart() {
    }

    public AppStart(String signatureNonce) {
        this.signatureNonce = signatureNonce;
    }


    public static void main(String[] args) throws IOException {
        String signatureNonce = "start11";
        AppStart appStart = new AppStart(signatureNonce);
        String start = appStart.appStart();
        System.out.println("start is: " + start);


        Thread thread = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                super.run();
                while (true) {

                    HeartBeat heartBeat = new HeartBeat(start, signatureNonce);
                    try {
                        String heartbeat = heartBeat.heartbeat();
                        System.out.println("heartbeat is:" + heartbeat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String format = "yyyy-MM-dd HH:mm:ss";
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                    String message = simpleDateFormat.format(date);
                    System.out.println("当前时间" + message);
                    sleep(20 * 1000);

                }
            }
        };
        thread.start();
        System.out.println("线程已经开启");
    }

    @Override
    protected void finalize() throws Throwable {
        AppEnd appEnd = new AppEnd(start, signatureNonce);
        String append = appEnd.append();
        System.out.println("append is: " + append);
    }

    public String appStart() throws IOException {
//        code	是	string	[主播身份码]
//        app_id	是	integer(13位长度的数值，注意不要用普通int，会溢出的)	项目ID

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("app_id", app_id);
        String jsonString = JSONObject.toJSONString(params);
        System.out.println("jsonString is:" + jsonString);
        String contentMd5 = MD5Utils.md5(jsonString);
        System.out.println("contentMd5 is:" + contentMd5);

        Author author = new Author();
        String response = author.authorize(action, contentMd5, signatureNonce, jsonString);
        start = response;
        return response;
    }

}
