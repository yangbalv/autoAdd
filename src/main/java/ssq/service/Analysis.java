package ssq.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ssq.data.HistoryData;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Analysis {
//    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private HistoryData historyData = new HistoryData();

    public static void main(String[] args) {
        System.out.println("开始获取历史双色球中奖数据。");
        Analysis analysis = new Analysis();
        try {
            String path = "http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice?name=ssq";
            System.out.println("第一步获取往期数据开始。");
            String cookie = analysis.step11(path);
            String message = analysis.step12(path, cookie);
            System.out.println("第一步获取往期数据成功。");
            System.out.println("第二步处理往期数据并进行存储开始。");
            analysis.step2(message);
            System.out.println("第二步处理往期数据并进行存储成功。");
            System.out.println("第三步打印分析结果开始。");
            System.out.println(analysis.historyData.toString());
            System.out.println("第三步打印分析结果结束。");
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
//        for (Map.Entry<String, List<String>> stringListEntry : headerFields.entrySet()) {
//            System.out.println("key is: " + stringListEntry.getKey() + " value is: " + stringListEntry.getValue());
//        }

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
//        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
//        for (Map.Entry<String, List<String>> stringListEntry : headerFields.entrySet()) {
//            System.out.println("key is: " + stringListEntry.getKey() + " value is: " + stringListEntry.getValue());
//        }
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\n");
        }
        return stringBuffer.toString();
    }

    //    第二部分析响应数据并进行统计
    public void step2(String message) throws IOException {
        JSONObject messageJson = (JSONObject) JSONObject.parse(message);
        JSONArray resultArrays = (JSONArray) messageJson.get("result");
        for (Object resultArray : resultArrays) {
            String reds = (String) ((JSONObject) resultArray).get("red");
            String[] redSplit = reds.split(",");
            for (String red : redSplit) {
                historyData.getRedNumbers()[Integer.parseInt(red) - 1] = historyData.getRedNumbers()[Integer.parseInt(red) - 1] + 1;
            }
            String blues = (String) ((JSONObject) resultArray).get("blue");
            historyData.getBlueNumbers()[Integer.parseInt(blues) - 1] = historyData.getBlueNumbers()[Integer.parseInt(blues) - 1] + 1;
        }

    }

}
