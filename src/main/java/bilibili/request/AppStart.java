package bilibili.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Time;
import java.util.Calendar;

public class AppStart {
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

    public void AppStart() throws IOException {
        String head = "https://live-open.biliapi.com";
        String action = "/v2/app/start";
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
        String contentMd5 = "";
        String timestamp = timeInMillis.toString();
        String signatureMethod = "HMAC-SHA256";
        String signatureNonce = "";
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


    }
}
