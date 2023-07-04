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
            analysis.step1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void step1() throws IOException {
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
        httpURLConnection.setRequestProperty("Cookie", "HMF_CI=277bc847e20c6111bc6892431c696a8bdd774ae81275b0ca2d6d7acbf92f1f63fde8fc58af15fd93bd72b6fe02610d7227953f7cd0bdde07a1e632023f3f565757; Expires=Thu, 03-Aug-23 07:22:59 GMT; Path=/");
        httpURLConnection.setRequestProperty("Host", "www.cwl.gov.cn");
        httpURLConnection.setRequestProperty("Upgrade-Insecure-Requests", "1");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        System.out.println(httpURLConnection.getRequestMethod());
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.connect();


        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        for (Map.Entry<String, List<String>> stringListEntry : headerFields.entrySet()) {
            System.out.println("key is: " + stringListEntry.getKey() + "value is: " + stringListEntry.getValue());
        }


        InputStream stream = new GZIPInputStream(httpURLConnection.getInputStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));

        StringBuffer sb = new StringBuffer();

        String line = "";

        while ((line = reader.readLine()) != null) {

            sb.append(line);

        }
        System.out.println(sb);
    }
}
