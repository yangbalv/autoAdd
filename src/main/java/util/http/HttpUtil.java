package util.http;

import com.alibaba.fastjson.JSON;
import mode.CompanyDetail;
import mode.CompanyResponseDto;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.PropertiesUtil;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public String getHTML(String path, Map<String, Object> requestArgs) throws IOException {
        String uri = makeGetUrl(requestArgs, true);
        return getHTML(path + "?" + uri);
    }

    public String getHTML(String url) throws IOException {
        logger.info("start do getPost, and the url is : {}", url);
        if (StringUtils.isBlank(url)) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL toUrl = new URL(url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) toUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
            bufferedReader.close();

        } catch (MalformedURLException e) {
            logger.error("MalformedURLException during getPost ", e);
        } catch (IOException e) {
            logger.error("IOException during getPost ", e);
            throw e;
        }
        return stringBuffer.toString();


    }

    public String doPost(String toUrl, Map<String, Object> requestArgs) throws IOException {
        return doPost(toUrl, JSON.toJSONString(requestArgs));
    }

    /**
     * @param toUrl
     * @param message json格式的字符串
     * @return
     * @throws IOException
     */
    public String doPost(String toUrl, String message) throws IOException {
        logger.info("start do postRequest, and the url is : {}", toUrl);
        StringBuffer stringBuffer = new StringBuffer();

        URL url = new URL(toUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);

        httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        httpURLConnection.connect();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
        bufferedWriter.write(message);
        bufferedWriter.close();
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            logger.info("bed request for: {},responseCode is: {}, and the request is: {}", toUrl, responseCode, message);
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\n");

        }
        logger.info(" end the postRequest of url: {}", toUrl);
        return stringBuffer.toString();


    }

    /**
     * 构造get的请求参数形式如 a=1&b=2
     *
     * @param message
     * @param removeLast 设置为false不会除去最后的&
     * @return
     */
    public static String makeGetUrl(Map<String, Object> message, boolean removeLast) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> entry : message.entrySet()) {
            Object value = null;
            try {
                value = URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                value = entry.getValue();
            }
            stringBuffer.append(entry.getKey() + "=" + value + "&");
        }
        if (removeLast) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return new String(stringBuffer);
    }

//    public static void main(String[] args) throws IOException {
//        String url = "http://localhost:8090/zty/test/hello";
//        HttpUtil httpUtil = new HttpUtil();
//        CompanyDetail companyDetail = new CompanyDetail();
//        companyDetail.setCompanyName("石油大亨公司");
//        String html = httpUtil.getCompanyMessage(url, companyDetail);
//        System.out.println(html);
//    }


    public String getCompanyMessage(String toUrl, CompanyDetail companyDetail) throws IOException {
        logger.info("start do postRequest, and the url is : {}", toUrl);
        Properties properties = PropertiesUtil.getProperties();

        StringBuffer stringBuffer = new StringBuffer();

        URL url = new URL(toUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
//        使用缓存标识
        httpURLConnection.setUseCaches(false);


//        Referer就未经过处理的数据UrlEncoding之后的请求头/请求体
        String Referer = "qxxkdm=" + properties.getProperty("qxxkdm") +
                "&KHSH=" + "" +
                "&KHSH=" + "" +
                "&KHMC=" + companyDetail.getCompanyName() +
                "&KHMC_PYT=" + "";
        //        message是也是请求数据写在body中，是Referer经过URLEncoder编码之后的数据
        String message = "qxxkdm=" + URLEncoder.encode(properties.getProperty("qxxkdm")) +
                "&KHSH=" + URLEncoder.encode("") +
                "&KHSH=" + URLEncoder.encode("") +
                "&KHMC=" + companyDetail.getCompanyName() +
                "&KHMC_PYT=" + URLEncoder.encode("");

        String cookie;
        String JSESSIONID = properties.getProperty("JSESSIONID");
        String HmVltName = properties.getProperty("Hm-vlt_name");
        String HmVltValue = properties.getProperty("Hm-vlt_value");
        cookie = HmVltName + "=" + HmVltValue + ";JSESSIONID=" + JSESSIONID;

        httpURLConnection.setRequestProperty("Accept", properties.getProperty("Accept"));
        httpURLConnection.setRequestProperty("Accept-Language", properties.getProperty("Accept-Language"));
        httpURLConnection.setRequestProperty("Content-Type", properties.getProperty("Content-Type"));
        httpURLConnection.setRequestProperty("Accept-Encoding", properties.getProperty("Accept-Encoding"));
        httpURLConnection.setRequestProperty("User-Agent", properties.getProperty("User-Agent"));
//        Referer放置到请求头中
        httpURLConnection.setRequestProperty("Referer", Referer);
        httpURLConnection.setRequestProperty("Cookie", cookie);


        httpURLConnection.connect();


//        message数据写入body中
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write((message).getBytes());
        outputStream.flush();
        outputStream.close();

        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            logger.info("bed request for: {},responseCode is: {}, and the request is: {}", toUrl, responseCode, Referer);
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\n");

        }
        logger.info(" end the postRequest of url: {}", toUrl);
        return stringBuffer.toString();


    }


    public String getCompanyMessage2(String toUrl, CompanyDetail companyDetail) throws IOException {
        logger.info("start do postRequest, and the url is : {}", toUrl);
        Properties properties = PropertiesUtil.getProperties();

        URL url = new URL(toUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
//        使用缓存标识
        httpURLConnection.setUseCaches(false);


        String message = "qxxkdm=" + URLEncoder.encode(properties.getProperty("qxxkdm")) +
                "&KHSH=" + URLEncoder.encode("") +
                "&KHSH=" + URLEncoder.encode("") +
                "&KHMC=" + URLEncoder.encode(companyDetail.getCompanyName()) +
                "&KHMC_PYT=" + URLEncoder.encode("");

        String cookie;
        String JSESSIONID = properties.getProperty("JSESSIONID");
        cookie = "JSESSIONID=" + JSESSIONID;

        httpURLConnection.setRequestProperty("Accept", properties.getProperty("Accept"));
        httpURLConnection.setRequestProperty("Accept-Language", properties.getProperty("Accept-Language"));
        httpURLConnection.setRequestProperty("Content-Type", properties.getProperty("Content-Type"));
        httpURLConnection.setRequestProperty("Accept-Encoding", properties.getProperty("Accept-Encoding"));
        httpURLConnection.setRequestProperty("User-Agent", properties.getProperty("User-Agent"));
//        Referer放置到请求头中
//        httpURLConnection.setRequestProperty("Referer", Referer);
        httpURLConnection.setRequestProperty("Cookie", cookie);


        httpURLConnection.connect();


//        message数据写入body中
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write((message).getBytes());
        outputStream.flush();
        outputStream.close();

        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            logger.info("bed request for: {},responseCode is: {}, and the request is: ", toUrl, responseCode);
        }

        InputStream stream = new GZIPInputStream(httpURLConnection.getInputStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));

        StringBuffer sb = new StringBuffer();

        String line = "";

        while ((line = reader.readLine()) != null) {

            sb.append(line);

        }

        System.out.println(sb.toString());


        logger.info(" end the postRequest of url: {}", toUrl);
        return sb.toString();


    }


    public String addMessage(String toUrl, CompanyResponseDto companyResponseDto) throws IOException {
        logger.info("start do addMessage, and the url is : {}", toUrl);
        Properties properties = PropertiesUtil.getProperties();


        URL url = new URL(toUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
//        使用缓存标识
        httpURLConnection.setUseCaches(false);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("KHSH", companyResponseDto.getRECORD().getKHSH());
        objectMap.put("KHBH", companyResponseDto.getRECORD().getKHBH());
        objectMap.put("PYT", properties.getProperty("PYT"));
        objectMap.put("editTage", properties.getProperty("editTage"));
        objectMap.put("KHMC", companyResponseDto.getRECORD().getKHMC());
        objectMap.put("DZ", companyResponseDto.getRECORD().getTXDZ());
        objectMap.put("LXRMC", companyResponseDto.getRECORD().getLXR());
        objectMap.put("GDDH", companyResponseDto.getRECORD().getGDDH());
        objectMap.put("YDDH", companyResponseDto.getRECORD().getYDDH());
        objectMap.put("DH3", companyResponseDto.getRECORD().getDH3());
//        静态页面取值
        objectMap.put("sszzjg_mc", properties.getProperty("sszzjg_mc"));
        objectMap.put("sszzjg_dm", properties.getProperty("sszzjg_dm"));
        objectMap.put("FWRY_ZZJG_DM", properties.getProperty("sszzjg_dm"));
        objectMap.put("FWRY_ZZJG_MC", properties.getProperty("sszzjg_mc"));
//        回访人
        objectMap.put("JBR_MC", properties.getProperty("JBR_MC"));
        objectMap.put("JBR_DM", properties.getProperty("JBR_DM"));
        objectMap.put("FWRY_MC", properties.getProperty("FWRY_MC"));
        objectMap.put("FWRY_DM", properties.getProperty("FWRY_DM"));
//        配置值
        objectMap.put("ZZDJH", properties.getProperty("ZZDJH"));
        objectMap.put("ZZDJH_TEMP", properties.getProperty("ZZDJH_TEMP"));
        objectMap.put("ZZDJH_SAME", properties.getProperty("ZZDJH_SAME"));

        objectMap.put("hf_bz", properties.getProperty("hf_bz"));
//税控盘
        objectMap.put("FWCP_DM", properties.getProperty("FWCP_DM"));
        objectMap.put("OTHER", properties.getProperty("OTHER"));
//        CPYXQK=正常
//        CLBF=收费
        objectMap.put("CPYXQK", properties.getProperty("CPYXQK"));
        objectMap.put("CLBF", properties.getProperty("CLBF"));

//        0 很满意
        objectMap.put("FWPJ", properties.getProperty("FWPJ"));
//
        objectMap.put("FWZTPJYY", properties.getProperty("FWZTPJYY"));
        objectMap.put("FWZTPJ", properties.getProperty("FWZTPJ"));

        objectMap.put("LRR_DM", properties.getProperty("LRR_DM"));
        //金磊1
        objectMap.put("LRR_MC", properties.getProperty("LRR_MC"));
//        公司产品总体评价
        objectMap.put("CPPJ", properties.getProperty("CPPJ"));
        objectMap.put("CPZTPJYY", properties.getProperty("CPZTPJYY"));
        objectMap.put("CPZTPJ", properties.getProperty("CPZTPJ"));

        objectMap.put("HFKSSJ", companyResponseDto.getAddTime());
        objectMap.put("qxxkdm", properties.getProperty("qxxkdm"));


        String message = makeGetUrl(objectMap, true);

        String cookie;
        String JSESSIONID = properties.getProperty("JSESSIONID");
        cookie = "JSESSIONID=" + JSESSIONID;

        httpURLConnection.setRequestProperty("Accept", properties.getProperty("Accept"));
        httpURLConnection.setRequestProperty("Accept-Language", properties.getProperty("Accept-Language"));
        httpURLConnection.setRequestProperty("Content-Type", properties.getProperty("Content-Type"));
        httpURLConnection.setRequestProperty("Accept-Encoding", properties.getProperty("Accept-Encoding"));
        httpURLConnection.setRequestProperty("User-Agent", properties.getProperty("User-Agent"));
//        Referer放置到请求头中
//        httpURLConnection.setRequestProperty("Referer", Referer);
        httpURLConnection.setRequestProperty("Cookie", cookie);


        httpURLConnection.connect();


//        message数据写入body中
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write((message).getBytes());
        outputStream.flush();
        outputStream.close();

        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            logger.info("bed request for: {},responseCode is: {}, and the request ", toUrl, responseCode);
        }

        InputStream stream = new GZIPInputStream(httpURLConnection.getInputStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));

        StringBuffer sb = new StringBuffer();

        String line = "";

        while ((line = reader.readLine()) != null) {

            sb.append(line);

        }

        System.out.println(sb.toString());

        logger.info(" end the postRequest of url: {}", toUrl);
        return sb.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        objectMap.put("PYT", );
//        objectMap.put("editTage", N);
//        objectMap.put("DZ", 青村镇奉永路399号);
//        objectMap.put("LXRMC", 阳永桥);
//        objectMap.put("FWRY_ZZJG_DM", 1911071636050001);
//        objectMap.put("FWRY_ZZJG_MC", 光明办事处);
//        objectMap.put("JBR_MC", 张淑芬);
//        objectMap.put("JBR_DM", 2106181057340001);
//        objectMap.put("FWRY_MC", 张淑芬);
//        objectMap.put("FWRY_DM", 2106181057340001);
//        objectMap.put("ZZDJH", 1);
//        objectMap.put("ZZDJH_TEMP", );
//        objectMap.put("ZZDJH_SAME", );
//        objectMap.put("hf_bz", N);
//        objectMap.put("FWCP_DM", 2);
//        objectMap.put("OTHER", );
//        objectMap.put("CPYXQK", 正常);
//        objectMap.put("CLBF", 收费);
//        objectMap.put("FWPJ", 0);
//        objectMap.put("FWZTPJYY", 无);
//        objectMap.put("FWZTPJ", 0);
//        objectMap.put("LRR_DM", 1909231647080001);
//        objectMap.put("LRR_MC", 金磊1);
//        objectMap.put("CPPJ", 0);
//        objectMap.put("CPZTPJYY", 无);
//        objectMap.put("CPZTPJ", 0);
//        objectMap.put("HFKSSJ", 2022-12-14);
//        objectMap.put("qxxkdm", 11191);


//        KHSH=91310120703100112H
//                &KHBH=xj1505271033151075
//                &PYT=
//            &editTage=N
//                &KHMC=%E4%B8%8A%E6%B5%B7%E6%8D%B7%E4%BB%81%E6%9D%B0%E6%A8%A1%E5%85%B7%E5%A1%91%E8%83%B6%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8
//                &DZ=%E5%A5%89%E8%B4%A4%E5%8C%BA%E6%B0%91%E4%B9%90%E8%B7%AF328%E5%BC%847%E5%8F%B7
//                &LXRMC=%E9%A1%BE%E6%98%8A
//                &GDDH=13916012769
//                &YDDH=13916012769
//                &DH3=
//            &sszzjg_mc=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84
//                &sszzjg_dm=1911071636050001
//                &FWRY_ZZJG_DM=1911071636050001
//                &FWRY_ZZJG_MC=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84
//                &JBR_MC=%E5%BC%A0%E6%B7%91%E8%8A%AC
//                &JBR_DM=2106181057340001
//                &FWRY_MC=%E5%BC%A0%E6%B7%91%E8%8A%AC
//                &FWRY_DM=2106181057340001
//                &ZZDJH=1
//                &ZZDJH_TEMP=
//            &ZZDJH_SAME=
//            &hf_bz=N
//                &FWCP_DM=2
//                &OTHER=
//            &CPYXQK=%E6%AD%A3%E5%B8%B8
//                &CLBF=%E6%94%B6%E8%B4%B9
//                &FWPJ=0
//                &FWZTPJYY=%E6%97%A0
//                &FWZTPJ=0
//                &LRR_DM=1909231647080001
//                &LRR_MC=%E9%87%91%E7%A3%8A1
//                &CPPJ=0
//                &CPZTPJYY=%E6%97%A0
//                &CPZTPJ=0
//                &HFKSSJ=2023-02-17
//                &qxxkdm=11191
        String message = "%E5%A5%89%E8%B4%A4%E5%8C%BA%E6%B0%91%E4%B9%90%E8%B7%AF328%E5%BC%847%E5%8F%B7";
        System.out.println(URLDecoder.decode(message, "UTF-8"));
    }
//    生成的
//    KHBH=xj1702101129100001
//    &GDDH=15000265235
//    &FWRY_ZZJG_DM=1911071636050001
//    &JBR_DM=2106181057340001
//    &CPYXQK=%E6%AD%A3%E5%B8%B8
//    &LXRMC=%E9%98%B3%E6%B0%B8%E6%A1%A5
//    &qxxkdm=11191
//    &LRR_DM=1909231647080001
//    &FWRY_DM=2106181057340001
//    &CPZTPJ=0
//    &editTage=N
//    &FWRY_MC=%E5%BC%A0%E6%B7%91%E8%8A%AC
//    &FWZTPJYY=%E6%97%A0
//    &ZZDJH_TEMP=
//    &KHMC=%E4%B8%8A%E6%B5%B7%E6%B2%BF%E9%B9%A4%E6%B6%B2%E5%8E%8B%E5%99%A8%E6%9D%90%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8
//    &hf_bz=N
//    &KHSH=91310120MA1HLQRR2B
//    &FWRY_ZZJG_MC=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84
//    &JBR_MC=%E5%BC%A0%E6%B7%91%E8%8A%AC
//    &FWCP_DM=2
//    &DZ=%E9%9D%92%E6%9D%91%E9%95%87%E5%A5%89%E6%B0%B8%E8%B7%AF399%E5%8F%B7
//    &ZZDJH=1
//    &FWZTPJ=0
//    &YDDH=15000265235
//    &HFKSSJ=2010-09-10
//    &PYT=
//    &sszzjg_mc=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84
//    &FWPJ=0
//    &DH3=
//    &OTHER=
//    &CLBF=%E6%94%B6%E8%B4%B9
//    &LRR_MC=%E9%87%91%E7%A3%8A1
//    &CPPJ=0
//    &sszzjg_dm=1911071636050001
//    &CPZTPJYY=%E6%97%A0
//    &ZZDJH_SAME=


//    copy下来的
//    KHSH=91310120MA1HLQRR2B
//            &KHBH=xj1702101129100001
//            &PYT=
//            &editTage=N
//                &KHMC=%E4%B8%8A%E6%B5%B7%E6%B2%BF%E9%B9%A4%E6%B6%B2%E5%8E%8B%E5%99%A8%E6%9D%90%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8
//            &DZ=%E9%9D%92%E6%9D%91%E9%95%87%E5%A5%89%E6%B0%B8%E8%B7%AF399%E5%8F%B7
//                &LXRMC=%E9%98%B3%E6%B0%B8%E6%A1%A5
//                &GDDH=15000265235
//            &YDDH=15000265235
//            &DH3=
//            &sszzjg_mc=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84
//            &sszzjg_dm=1911071636050001
//            &FWRY_ZZJG_DM=1911071636050001
//            &FWRY_ZZJG_MC=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84
//            &JBR_MC=%E5%BC%A0%E6%B7%91%E8%8A%AC
//            &JBR_DM=2106181057340001
//            &FWRY_MC=%E5%BC%A0%E6%B7%91%E8%8A%AC
//            &FWRY_DM=2106181057340001
//            &ZZDJH=1
//            &ZZDJH_TEMP=
//            &ZZDJH_SAME=
//            &hf_bz=N
//                &FWCP_DM=2
//            &OTHER=
//            &CPYXQK=%E6%AD%A3%E5%B8%B8
//                &CLBF=%E6%94%B6%E8%B4%B9
//                &FWPJ=0
//            &FWZTPJYY=%E6%97%A0
//                &FWZTPJ=0
//            &LRR_DM=1909231647080001
//            &LRR_MC=%E9%87%91%E7%A3%8A1
//            &CPPJ=0
//            &CPZTPJYY=%E6%97%A0
//                &CPZTPJ=0
//            &HFKSSJ=2022-12-14
//            &qxxkdm=11191


}
