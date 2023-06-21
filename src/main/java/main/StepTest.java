package main;

import mode.CompanyDetail;
import mode.CompanyResponseDto;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import util.PropertiesUtil;
import util.excel.BothExcel;
import util.http.HttpUtil;
import util.xml.XmlUtilOne;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class StepTest {
    public Properties properties = PropertiesUtil.properties;

    //读取表格获取所有公司的信息
    public List<CompanyDetail> step1(String path) throws Exception {
        List<CompanyDetail> allCompanyDetail = BothExcel.getAllCompanyDetail(path);
        return allCompanyDetail;
    }

    //造http请求获取公司其他信息
    public CompanyResponseDto step2(CompanyDetail companyDetail) throws IOException, DocumentException {
        String default_add_time = properties.getProperty("default_add_time");
        if (StringUtils.isBlank(companyDetail.getAddTime())) {
            companyDetail.setAddTime(default_add_time);
        }
        String url = properties.getProperty("Step2-url");
        HttpUtil httpUtil = new HttpUtil();
        String xml = httpUtil.getCompanyMessage2(url, companyDetail);
        XmlUtilOne xmlUtilOne = new XmlUtilOne();

        CompanyResponseDto companyResponseDto = xmlUtilOne.analysisCompanyResponse(xml);
        companyResponseDto.setAddTime(companyDetail.getAddTime());

        return companyResponseDto;
    }

    public boolean step3(CompanyResponseDto companyResponseDto) throws IOException, DocumentException {
        String url = properties.getProperty("Step3-url");
        HttpUtil httpUtil = new HttpUtil();
        String xml = httpUtil.addMessage(url, companyResponseDto);
        System.out.println(xml);
        return true;
    }

    //
    public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException {
        String path = "https://www.baidu.com/s?";
        String request = "";

        String decode = URLDecoder.decode(request, "UTF-8");
        decode = path + decode;
        URL url = new URL(decode);
        System.out.println(url.getQuery());


        System.out.println(decode);
        String xlsPath = PropertiesUtil.properties.getProperty("xls-path");
        StepTest step = new StepTest();
        List<CompanyDetail> companyDetails = null;
        try {
            companyDetails = step.step1(xlsPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (CompanyDetail companyDetail : companyDetails) {
            CompanyResponseDto companyResponseDto = null;
            try {
                companyResponseDto = step.step2(companyDetail);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }


//            System.out.println(companyResponseDto);


            try {
                step.step3(companyResponseDto);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("****----添加失败  " + companyResponseDto.toString());
            } catch (DocumentException e) {
                e.printStackTrace();
                System.out.println("****----添加失败  " + companyResponseDto.toString());
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//
            String format = "yyyy-MM-dd HH:mm:ss:SSS";
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            String message = simpleDateFormat.format(date);
            System.out.println("当前时间" + message);

        }
    }


//    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 100; i++) {
//            Random random = new Random();
//            random.setSeed(10000L);
//            int randomInt = random.nextInt(10000);
//            String format = "yyyy-MM-dd HH:mm:ss:SSS";
//            Date date = new Date();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
//            String message = simpleDateFormat.format(date);
//            System.out.println("第" + i + "次： 时间" + message);
//            int i1 = randomInt + 20000;
//            System.out.println(i1);
//            Thread.sleep(i1);
//        }
//    }
}
