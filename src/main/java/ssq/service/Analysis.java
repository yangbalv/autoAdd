package ssq.service;

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
            analysis.step2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void step1() throws IOException {
        HttpUtil httpUtil = new HttpUtil();
        String html = httpUtil.doPost("http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice?name=ssq&issueCount=&issueStart=&issueEnd=&dayStart=&dayEnd=&pageNo=2&pageSize=30&week=&systemType=PC", "");
        System.out.println(html.replaceAll(" ", ""));
    }

    public void step2() throws IOException {
        String html = "http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice?name=ssq";
//        logger.info("start do postRequest, and the url is : {}", toUrl);


        URL url = new URL(html);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
//        httpURLConnection.setDoInput(true);
//        使用缓存标识
//        httpURLConnection.setUseCaches(false);

        String cookie;


        httpURLConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        httpURLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
        httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,et;q=0.8");

        httpURLConnection.setRequestProperty("Cache-Control", "max-age=0");
        httpURLConnection.setRequestProperty("Connection", "keep-alive");
//        httpURLConnection.setRequestProperty("Cookie", "HMF_CI=277bc847e20c6111bc6892431c696a8b2c3d611b3ffdaa30d633425d0fc221618c58a7f8ce8fc640e8df62e03e342640a895fe8d9bdaa87251407578b9961084f9");
        httpURLConnection.setRequestProperty("Host", "www.cwl.gov.cn");
        httpURLConnection.setRequestProperty("Upgrade-Insecure-Requests", "1");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        System.out.println(httpURLConnection.getRequestMethod());
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.connect();



//        message数据写入body中
//        OutputStream outputStream = httpURLConnection.getOutputStream();
//        outputStream.write();
//        outputStream.flush();
//        outputStream.close();
        System.out.println(httpURLConnection.getRequestMethod());
        System.out.println(httpURLConnection.getResponseMessage());
        System.out.println(httpURLConnection.getRequestProperty("Set-Cookie"));
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        System.out.println(headerFields.get("Set-Cookie"));

        int responseCode = httpURLConnection.getResponseCode();
        System.out.println(responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            logger.info("bed request for: {},responseCode is: {}", html, responseCode);
        }

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\n");

        }
        logger.info(" end the postRequest of url: {}", html);
        System.out.println(stringBuffer);
    }
}
