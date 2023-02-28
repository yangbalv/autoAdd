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
import java.util.List;
import java.util.Properties;

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
        System.out.println(xml);
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

    public static void main(String[] args) throws Exception {
        String xlsPath = PropertiesUtil.properties.getProperty("xls-path");
        StepTest step = new StepTest();
        List<CompanyDetail> companyDetails = step.step1(xlsPath);
        for (CompanyDetail companyDetail : companyDetails) {
            CompanyResponseDto companyResponseDto = step.step2(companyDetail);
            System.out.println(companyResponseDto);
            step.step3(companyResponseDto);
        }
    }
}
