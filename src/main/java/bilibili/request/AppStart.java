package bilibili.request;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.http.HttpUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class AppStart {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //    Request Header
//    Accept	String	是	接受的返回结果的类型。目前只支持JSON类型，取值：application/json。
//    Content-Type	String	是	当前请求体（Request Body）的数据类型。目前只支持JSON类型，取值：application/json。
//    x-bili-content-md5	String	是	请求体的编码值，根据请求体计算所得。算法说明：将请求体内容当作字符串进行MD5编码。
//    x-bili-timestamp	String	是	unix时间戳，单位是秒。请求时间戳不能超过当前时间10分钟，否则请求会被丢弃。
//    x-bili-signature-method	String	是	签名方式。取值：HMAC-SHA256
//    x-bili-signature-nonce	String	是	签名唯一随机数。用于防止网络重放攻击，建议您每一次请求都使用不同的随机数。
//    x-bili-accesskeyid	String	是	AccessKeyId
//    x-bili-signature-version	String	是	1.0
//    Authorization	String	是	请求签名（注意生成的签名是小写的）。关于请求签名的计算方法，请参见签名机制
    public static void main(String[] args) throws IOException {
        AppStart appStart = new AppStart();
        appStart.AppStart();
    }

    public void authorize(String action, String contentMd5, String signatureNonce) throws IOException {
        String head = "https://live-open.biliapi.com";
//        String action = "/v2/app/start";
        String path = head + action;
        String accessKeySecret = "iZiqOAXaTQAuABux65aBrMIm7IvZSk";

        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
//        使用缓存标识
        httpURLConnection.setUseCaches(false);

        Calendar canlender = Calendar.getInstance();
        Long timeInMillis = canlender.getTimeInMillis();
//        String contentMd5 = "";
        String timestamp = timeInMillis.toString();
        String signatureMethod = "HMAC-SHA256";
//        String signatureNonce = "";
        String accessKeyId = "YblHh6OvUc0L29WOqJboMv3l";
        String signatureVersion = "1.0";

        String needSign = "x-bili-accesskeyid:" + accessKeyId + "\n" +
                "x-bili-content-md5:" + contentMd5 + "\n" +
                "x-bili-signature-method:" + signatureMethod + "\n" +
                "x-bili-signature-nonce:" + signatureNonce + "\n" +
                "x-bili-signature-version:" + signatureVersion + "\n" +
                "x-bili-timestamp:" + timestamp;
        String Authorization = HMACSHA256.sha256_HMAC(needSign, accessKeySecret);


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
//        outputStream.write((message).getBytes());
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
        System.out.println(sb);
        logger.info(sb.toString());

        logger.info(" end the postRequest of url: {}", path);
    }

    public void AppStart() throws IOException {
//        code	是	string	[主播身份码]
//        app_id	是	integer(13位长度的数值，注意不要用普通int，会溢出的)	项目ID
        String action = "/v2/app/start";
        Map<String, Object> params = new HashMap<>();
        params.put("code", "3461578856860568");
        params.put("app_id", 3463268406164140l);
        String jsonString = JSONObject.toJSONString(params);
        String contentMd5 = md5(jsonString);
        String signatureNonce = "aa";
        authorize(action, contentMd5, signatureNonce);
    }


    public static String md5(String data) {
        byte[] dataBytes = data.getBytes();
        return md5(dataBytes);
    }

    /**
     * md5
     */
    public static String md5(byte[] data) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = null;
        try {
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 使用指定的字节更新摘要
        mdInst.update(data);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
}
