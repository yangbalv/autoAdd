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
    String start;



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
        Long nowTime = System.currentTimeMillis();
        String signatureNonce = "appStart" + nowTime;
        Author author = new Author();
        String response = author.authorize(action, contentMd5, signatureNonce, jsonString);
        start = response;
        return response;
    }

}
