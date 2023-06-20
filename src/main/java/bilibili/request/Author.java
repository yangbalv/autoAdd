package bilibili.request;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

public class Author {
    String head = "https://live-open.biliapi.com";
    String accessKeySecret = "iZiqOAXaTQAuABux65aBrMIm7IvZSk";
    String signatureMethod = "HMAC-SHA256";
    String accessKeyId = "YblHh6OvUc0L29WOqJboMv3l";
    String signatureVersion = "1.0";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String authorize(String action, Map<String, Object> params, String signatureNonce) throws IOException {
        String jsonString = JSONObject.toJSONString(params);
        System.out.println("jsonString is:" + jsonString);
        String contentMd5 = MD5Utils.md5(jsonString);
//        System.out.println("contentMd5 is:" + contentMd5);
        return authorize(action, contentMd5, signatureNonce, jsonString);
    }

    public String authorize(String action, String contentMd5, String signatureNonce, String jsonString) throws IOException {

//        String action = "/v2/app/start";
        String path = head + action;


        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
//        使用缓存标识
        httpURLConnection.setUseCaches(false);

        Calendar canlender = Calendar.getInstance();
        Long timeInMillis = canlender.getTimeInMillis();
        String timestamp = timeInMillis.toString();
//        System.out.println("timestamp is: " + timestamp);


        String needSign = "x-bili-accesskeyid:" + accessKeyId + "\n" +
                "x-bili-content-md5:" + contentMd5 + "\n" +
                "x-bili-signature-method:" + signatureMethod + "\n" +
                "x-bili-signature-nonce:" + signatureNonce + "\n" +
                "x-bili-signature-version:" + signatureVersion + "\n" +
                "x-bili-timestamp:" + timestamp;
        String Authorization = HMACSHA256.sha256_HMAC(needSign, accessKeySecret);
//        System.out.println("Authorization is:" + Authorization);


        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("x-bili-content-md5", contentMd5);
        httpURLConnection.setRequestProperty("x-bili-timestamp", timestamp);
        httpURLConnection.setRequestProperty("x-bili-signature-method", signatureMethod);
        httpURLConnection.setRequestProperty("x-bili-signature-nonce", signatureNonce);
        httpURLConnection.setRequestProperty("x-bili-accesskeyid", accessKeyId);
        httpURLConnection.setRequestProperty("x-bili-signature-version", signatureVersion);
        httpURLConnection.setRequestProperty("Authorization", Authorization);

        httpURLConnection.connect();


//        message数据写入body中
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write((jsonString).getBytes());
        outputStream.flush();
        outputStream.close();

        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            logger.info("bed request for: {},responseCode is: {}, and the request ", path, responseCode);
        }


        InputStream stream = httpURLConnection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));

        StringBuffer sb = new StringBuffer();

        String line = "";

        while ((line = reader.readLine()) != null) {

            sb.append(line);

        }
        logger.info(" end the postRequest of url: {}", path);
        return sb.toString();
    }

}
