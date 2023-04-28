package main;

import mode.CompanyDetail;
import mode.CompanyResponseDto;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import util.PropertiesUtil;
import util.excel.BothExcel;
import util.http.HttpUtil;
import util.xml.XmlUtilOne;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class StepTest2 {
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

    public boolean step32(CompanyResponseDto companyResponseDto, String LRR_DM, String LRR_MC, String JBR_MC, String JBR_DM, String FWRY_MC, String FWRY_DM, String sszzjg_dm, String sszzjg_mc) throws IOException, DocumentException {
        String url = properties.getProperty("Step3-url");
        HttpUtil httpUtil = new HttpUtil();
        String xml = httpUtil.addMessage2(url, companyResponseDto, LRR_DM, LRR_MC, JBR_MC, JBR_DM, FWRY_MC, FWRY_DM, sszzjg_dm, sszzjg_mc);
        System.out.println(xml);
        if (xml.contains("添加成功")) {
            return true;
        } else {
            return false;
        }
    }

    //
    public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException {
        String requestcrj = "KHSH=91310120MA1HP99Y0A&KHBH=xj1807271013060001&PYT=&editTage=N&KHMC=%E4%B8%8A%E6%B5%B7%E6%9B%99%E6%98%9F%E5%95%86%E5%8A%A1%E6%9C%8D%E5%8A%A1%E4%BA%8B%E5%8A%A1%E6%89%80&DZ=%E5%A5%89%E8%B4%A4%E5%8C%BA%E5%B2%9A%E4%B8%B0%E8%B7%AF1150%E5%8F%B7&LXRMC=%E8%8D%A3%E8%BE%89&GDDH=13817034072&YDDH=13817034072&DH3=&sszzjg_mc=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84&sszzjg_dm=1911071636050001&FWRY_ZZJG_DM=1911071636050001&FWRY_ZZJG_MC=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84&JBR_MC=%E9%99%88%E6%B1%9D%E9%9D%99&JBR_DM=2209291023040001&FWRY_MC=%E9%99%88%E6%B1%9D%E9%9D%99&FWRY_DM=2209291023040001&ZZDJH=1&ZZDJH_TEMP=&ZZDJH_SAME=&hf_bz=N&FWCP_DM=2&OTHER=&CPYXQK=%E6%AD%A3%E5%B8%B8&CLBF=%E6%94%B6%E8%B4%B9&FWPJ=0&FWZTPJYY=%E6%97%A0&FWZTPJ=0&LRR_DM=1909231647080001&LRR_MC=%E9%87%91%E7%A3%8A1&CPPJ=0&CPZTPJYY=%E6%97%A0&CPZTPJ=0&HFKSSJ=2023-03-29&qxxkdm=11191";
        String requestwkn = "KHSH=913101145601225160&KHBH=xj1504291613301032&PYT=&editTage=N&KHMC=%E9%A9%AD%E5%AD%9A%E6%99%BA%E8%83%BD%E7%A7%91%E6%8A%80%EF%BC%88%E4%B8%8A%E6%B5%B7%EF%BC%89%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&DZ=%E4%B8%8A%E6%B5%B7%E5%B8%82%E5%A5%89%E8%B4%A4%E5%8C%BA%E9%9D%92%E6%9D%91%E9%95%87%E6%B2%BF%E9%92%B1%E5%85%AC%E8%B7%AF5157%E5%8F%B7&LXRMC=%E8%A4%9A%E9%9B%AA%E5%BE%AE&GDDH=37595166&YDDH=18019753625&DH3=&sszzjg_mc=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84&sszzjg_dm=1911071636050001&FWRY_ZZJG_DM=1911071636050001&FWRY_ZZJG_MC=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84&JBR_MC=%E7%8E%8B%E5%85%8B%E5%A8%9C&JBR_DM=2003270825510001&FWRY_MC=%E7%8E%8B%E5%85%8B%E5%A8%9C&FWRY_DM=2003270825510001&ZZDJH=1&ZZDJH_TEMP=&ZZDJH_SAME=&hf_bz=N&FWCP_DM=2&OTHER=&CPYXQK=%E6%AD%A3%E5%B8%B8&CLBF=%E6%94%B6%E8%B4%B9&FWPJ=0&FWZTPJYY=%E6%97%A0&FWZTPJ=0&LRR_DM=1909231647080001&LRR_MC=%E9%87%91%E7%A3%8A1&CPPJ=0&CPZTPJYY=%E6%97%A0&CPZTPJ=0&HFKSSJ=2023-03-22&qxxkdm=11191";
        String requestlyc = "KHSH=91310120MA1HPTN65M&KHBH=xj1904221526410001&PYT=&editTage=N&KHMC=%E4%B8%8A%E6%B5%B7%E8%8B%8F%E6%9C%AC%E7%A7%91%E6%8A%80%E5%8F%91%E5%B1%95%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&DZ=%E4%B8%8A%E6%B5%B7%E5%B8%82%E5%A5%89%E8%B4%A4%E5%8C%BA%E5%A4%A7%E5%8F%B6%E5%85%AC%E8%B7%AF5995%E5%8F%B75%E5%B9%A22615%E5%AE%A4&LXRMC=%E9%82%B5%E8%BF%9E%E4%B9%90&GDDH=15802132175&YDDH=15802132175&DH3=&sszzjg_mc=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84&sszzjg_dm=1911071636050001&FWRY_ZZJG_DM=1911071636050001&FWRY_ZZJG_MC=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84&JBR_MC=%E5%88%98%E7%BF%BC%E7%95%85&JBR_DM=2210241054090001&FWRY_MC=%E5%88%98%E7%BF%BC%E7%95%85&FWRY_DM=2210241054090001&ZZDJH=1&ZZDJH_TEMP=&ZZDJH_SAME=&hf_bz=N&FWCP_DM=2&OTHER=&CPYXQK=%E6%AD%A3%E5%B8%B8&CLBF=%E6%94%B6%E8%B4%B9&FWPJ=0&FWZTPJYY=%E6%97%A0&FWZTPJ=0&LRR_DM=1909231647080001&LRR_MC=%E9%87%91%E7%A3%8A1&CPPJ=0&CPZTPJYY=%E6%97%A0&CPZTPJ=0&HFKSSJ=2023-03-29&qxxkdm=11191";
        String requestgxq = "KHSH=91310120MA1HNA592K&KHBH=xj1803081534280005&PYT=&editTage=N&KHMC=%E4%B8%8A%E6%B5%B7%E9%9D%92%E6%AC%A3%E6%B1%BD%E8%BD%A6%E6%9C%8D%E5%8A%A1%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&DZ=%E6%9D%BE%E6%B1%9F%E5%8C%BA%E4%B9%9D%E6%9D%9C%E8%B7%AF1000%E5%BC%84403%E5%8F%B71101&LXRMC=%E5%BC%A0%E6%B8%85%E5%8D%8E&GDDH=13671783692&YDDH=15026466955&DH3=&sszzjg_mc=%E5%8D%97%E6%A1%A5%E5%8A%9E%E4%BA%8B%E5%A4%84&sszzjg_dm=1911071636070001&FWRY_ZZJG_DM=1911071636070001&FWRY_ZZJG_MC=%E5%8D%97%E6%A1%A5%E5%8A%9E%E4%BA%8B%E5%A4%84&JBR_MC=%E9%A1%BE%E6%99%93%E9%9D%92&JBR_DM=2105261013420001&FWRY_MC=%E9%A1%BE%E6%99%93%E9%9D%92&FWRY_DM=2105261013420001&ZZDJH=1&ZZDJH_TEMP=&ZZDJH_SAME=&hf_bz=N&FWCP_DM=2&OTHER=&CPYXQK=%E6%AD%A3%E5%B8%B8&CLBF=%E6%94%B6%E8%B4%B9&FWPJ=0&FWZTPJYY=%E6%97%A0&FWZTPJ=0&LRR_DM=2105261013420001&LRR_MC=%E9%A1%BE%E6%99%93%E9%9D%92&CPPJ=0&CPZTPJYY=%E6%97%A0&CPZTPJ=0&HFKSSJ=2023-03-15&qxxkdm=11191";
        String requestzsf = "KHSH=91310120MA1HRDBW9U&KHBH=xj1905131616140001&PYT=&editTage=N&KHMC=%E4%B8%8A%E6%B5%B7%E8%88%9C%E4%BF%A1%E5%BB%BA%E7%AD%91%E8%A3%85%E9%A5%B0%E8%AE%BE%E8%AE%A1%E5%B7%A5%E7%A8%8B%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&DZ=%E7%9F%B3%E9%BE%99%E8%B7%AF345%E5%BC%843%E5%8F%B7%E5%8F%A3%EF%BC%88%E6%9D%B0%E5%88%9D%E6%99%BA%E5%9B%AD%EF%BC%899%E5%B9%A2502%E5%AE%A4&LXRMC=%E5%BC%A0%E4%BF%A1&GDDH=17721126866&YDDH=17721126866&DH3=&sszzjg_mc=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84&sszzjg_dm=1911071636050001&FWRY_ZZJG_DM=1911071636050001&FWRY_ZZJG_MC=%E5%85%89%E6%98%8E%E5%8A%9E%E4%BA%8B%E5%A4%84&JBR_MC=%E5%BC%A0%E6%B7%91%E8%8A%AC&JBR_DM=2106181057340001&FWRY_MC=%E5%BC%A0%E6%B7%91%E8%8A%AC&FWRY_DM=2106181057340001&ZZDJH=1&ZZDJH_TEMP=&ZZDJH_SAME=&hf_bz=N&FWCP_DM=2&OTHER=&CPYXQK=%E6%AD%A3%E5%B8%B8&CLBF=%E6%94%B6%E8%B4%B9&FWPJ=0&FWZTPJYY=%E6%97%A0&FWZTPJ=0&LRR_DM=1909231647080001&LRR_MC=%E9%87%91%E7%A3%8A1&CPPJ=0&CPZTPJYY=%E6%97%A0&CPZTPJ=0&HFKSSJ=2023-04-27&qxxkdm=11191";
        String requesths = "KHSH=91310120MA1HKBF17K&KHBH=xj1603181222249001&PYT=&editTage=N&KHMC=%E4%B8%8A%E6%B5%B7%E6%9C%9B%E8%B0%B7%E5%AE%A4%E5%86%85%E8%A3%85%E9%A5%B0%E8%AE%BE%E8%AE%A1%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&DZ=%E5%BE%90%E6%B1%87%E5%8C%BA%E6%96%87%E5%AE%9A%E8%B7%AF258%E5%8F%B7c211%E5%AE%A4&LXRMC=%E5%AD%99%E8%83%9C%E5%9B%BD&GDDH=15618048948&YDDH=15618048948&DH3=&sszzjg_mc=%E5%8D%97%E6%A1%A5%E5%8A%9E%E4%BA%8B%E5%A4%84&sszzjg_dm=1911071636070001&FWRY_ZZJG_DM=1911071636070001&FWRY_ZZJG_MC=%E5%8D%97%E6%A1%A5%E5%8A%9E%E4%BA%8B%E5%A4%84&JBR_MC=%E9%BB%84%E7%8F%8A1&JBR_DM=2106281349000001&FWRY_MC=%E9%BB%84%E7%8F%8A1&FWRY_DM=2106281349000001&ZZDJH=1&ZZDJH_TEMP=&ZZDJH_SAME=&hf_bz=N&FWCP_DM=2&OTHER=&CPYXQK=%E6%AD%A3%E5%B8%B8&CLBF=%E6%94%B6%E8%B4%B9&FWPJ=0&FWZTPJYY=%E6%97%A0&FWZTPJ=0&LRR_DM=2106281349000001&LRR_MC=%E9%BB%84%E7%8F%8A1&CPPJ=0&CPZTPJYY=%E6%97%A0&CPZTPJ=0&HFKSSJ=2023-03-29&qxxkdm=11191";
        String requestlc = "KHSH=91310120599753470F&KHBH=xj1712061519000005&PYT=&editTage=N&KHMC=%E4%B8%8A%E6%B5%B7%E9%9B%80%E7%87%95%E6%9C%BA%E7%94%B5%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&DZ=%E5%8D%97%E4%BA%AD%E5%85%AC%E8%B7%AF1176%E5%8F%B7&LXRMC=%E6%B2%88%E5%AE%89%E6%85%B0&GDDH=18918368150&YDDH=18918368150&DH3=&sszzjg_mc=%E5%8D%97%E6%A1%A5%E5%8A%9E%E4%BA%8B%E5%A4%84&sszzjg_dm=1911071636070001&FWRY_ZZJG_DM=1911071636070001&FWRY_ZZJG_MC=%E5%8D%97%E6%A1%A5%E5%8A%9E%E4%BA%8B%E5%A4%84&JBR_MC=%E9%99%86%E8%B6%85&JBR_DM=2011041022260001&FWRY_MC=%E9%99%86%E8%B6%85&FWRY_DM=2011041022260001&ZZDJH=1&ZZDJH_TEMP=&ZZDJH_SAME=&hf_bz=N&FWCP_DM=2&OTHER=&CPYXQK=%E6%AD%A3%E5%B8%B8&CLBF=%E6%94%B6%E8%B4%B9&FWPJ=0&FWZTPJYY=%E6%97%A0&FWZTPJ=0&LRR_DM=2011041022260001&LRR_MC=%E9%99%86%E8%B6%85&CPPJ=0&CPZTPJYY=%E6%97%A0&CPZTPJ=0&HFKSSJ=2023-04-27&qxxkdm=11191";
        String filePath = "F:\\JAVA\\autoAdd-dev\\202304\\202304zsfx";
        String decode = URLDecoder.decode(requestzsf, "UTF-8");



        String failOutputPath = filePath + "failOutPut.txt";
        String xlsPath = filePath + ".xlsx";
        File file = new File(failOutputPath);

        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("文件:【" + failOutputPath + "】创建成功");
            } else {
                System.out.println("文件:【" + failOutputPath + "】已存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(failOutputPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);




        String[] split = decode.split("&");
        String LRR_DM = null;
        String LRR_MC = null;
        String JBR_MC = null;
        String JBR_DM = null;
        String FWRY_MC = null;
        String FWRY_DM = null;
        String sszzjg_dm = null;
        String sszzjg_mc = null;
        for (String s : split) {
            if (s.contains("LRR_DM")) {
                LRR_DM = s.substring(s.indexOf("LRR_DM") + 1 + "LRR_DM".length());
            }
            if (s.contains("LRR_MC")) {
                LRR_MC = s.substring(s.indexOf("LRR_MC") + 1 + "LRR_MC".length());

            }
            if (s.contains("JBR_MC")) {
                JBR_MC = s.substring(s.indexOf("JBR_MC") + 1 + "JBR_MC".length());

            }
            if (s.contains("JBR_DM")) {
                JBR_DM = s.substring(s.indexOf("JBR_DM") + 1 + "JBR_DM".length());

            }
            if (s.contains("FWRY_MC")) {
                FWRY_MC = s.substring(s.indexOf("FWRY_MC") + 1 + "FWRY_MC".length());

            }
            if (s.contains("FWRY_DM")) {
                FWRY_DM = s.substring(s.indexOf("FWRY_DM") + 1 + "FWRY_DM".length());

            }
            if (s.contains("sszzjg_dm")) {
                sszzjg_dm = s.substring(s.indexOf("sszzjg_dm") + 1 + "sszzjg_dm".length());

            }
            if (s.contains("sszzjg_mc")) {
                sszzjg_mc = s.substring(s.indexOf("sszzjg_mc") + 1 + "sszzjg_mc".length());

            }

        }
        System.out.println(LRR_DM);
        System.out.println(LRR_MC);
        System.out.println(JBR_MC);
        System.out.println(JBR_DM);
        System.out.println(FWRY_MC);
        System.out.println(FWRY_DM);
        System.out.println(sszzjg_dm);
        System.out.println(sszzjg_mc);


        StepTest2 step = new StepTest2();
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
            } catch (Exception e) {
                System.out.println("公司查询不出来++++" + companyDetail.toString());
                continue;
            }
//            System.out.println(companyResponseDto);


            try {
                boolean step32 = step.step32(companyResponseDto, LRR_DM, LRR_MC, JBR_MC, JBR_DM, FWRY_MC, FWRY_DM, sszzjg_dm, sszzjg_mc);
                if (!step32) {
                    System.out.println("【" + companyResponseDto.getRECORD().getKHMC() + "】公司请求失败");
                    bufferedWriter.write(companyResponseDto.getRECORD().getKHMC() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("第一次****----添加失败  " + companyResponseDto.toString());
                try {
                    step.step32(companyResponseDto, LRR_DM, LRR_MC, JBR_MC, JBR_DM, FWRY_MC, FWRY_DM, sszzjg_dm, sszzjg_mc);
                } catch (IOException ex) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e2) {
                        throw new RuntimeException(e2);
                    }
                    e.printStackTrace();
                    System.out.println("第二次****----添加失败  " + companyResponseDto.toString());
                } catch (DocumentException ex) {
                    e.printStackTrace();
                    System.out.println("****----添加失败  " + companyResponseDto.toString());
                }
            } catch (DocumentException e) {
                e.printStackTrace();
                System.out.println("****----添加失败  " + companyResponseDto.toString());
            }

//
            String format = "yyyy-MM-dd HH:mm:ss";
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            String message = simpleDateFormat.format(date);
            System.out.println("当前时间" + message);

        }

        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
