package ssq.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PropertiesUtil;
import util.http.HttpUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

public class Analysis {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        try {
            String path = "http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice?name=ssq";
            String cookie = analysis.step11(path);
//            System.out.println(cookie);
            String message = analysis.step12(path, cookie);
//            System.out.println(message);
            analysis.step2(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //第一步获取往期数据
//    第一步（1）进行预请求并拿到cookie
    public String step11(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.connect();
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        for (Map.Entry<String, List<String>> stringListEntry : headerFields.entrySet()) {
            System.out.println("key is: " + stringListEntry.getKey() + " value is: " + stringListEntry.getValue());
        }

        return httpURLConnection.getHeaderField("Set-Cookie");
    }

    //    第一步（2）将cookie塞到请求中进行数据获取
    public String step12(String path, String cookie) throws IOException {

        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestProperty("Cookie", cookie);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        for (Map.Entry<String, List<String>> stringListEntry : headerFields.entrySet()) {
            System.out.println("key is: " + stringListEntry.getKey() + " value is: " + stringListEntry.getValue());
        }
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\n");
        }
        return stringBuffer.toString();
    }

    //    第二部分析数据
    public void step2(String message) throws IOException {
        JSONObject messageJson = (JSONObject) JSONObject.parse(message);
        JSONArray resultArrays = (JSONArray) messageJson.get("result");
        for (Object resultArray : resultArrays) {
            String reds = (String) ((JSONObject) resultArray).get("red");
            System.out.println(reds);
            String blues = (String) ((JSONObject) resultArray).get("blue");
            System.out.println(blues);
        }

    }

}
