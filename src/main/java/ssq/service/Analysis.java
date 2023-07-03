package ssq.service;

import util.http.HttpUtil;

import java.io.IOException;

public class Analysis {
    public static void main(String[] args) {
        try {
            step1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void step1() throws IOException {
        HttpUtil httpUtil = new HttpUtil();
        String html = httpUtil.doPost("http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice?name=ssq","");
        System.out.println(html.replaceAll(" ", ""));
    }
}
