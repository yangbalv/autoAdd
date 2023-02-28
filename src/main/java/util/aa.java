package util;

import mode.CompanyResponseDto;
import org.dom4j.DocumentException;
import util.xml.XmlUtilOne;

public class aa {

    public static void main(String[] args) throws DocumentException {
        String xml = "<DATA><AUTHOR></AUTHOR><CREATE_TIME>2023-02-28</CREATE_TIME><RECORD><KHBH>xj1505271033151075</KHBH><GDDH>13916012769</GDDH><SSZZJG_DM>1911071636050001</SSZZJG_DM><SJ_KHBH></SJ_KHBH><KHMC_PYT></KHMC_PYT><YZBM></YZBM><YDDH>13916012769</YDDH><LXR>顾昊</LXR><SSSWJG_DM>1310226</SSSWJG_DM><GSZGRY_DM>1412020913480002</GSZGRY_DM><KHBM></KHBM><DH3></DH3><SSXZQH_DM>310120</SSXZQH_DM><KHYH></KHYH><KHMC>上海捷仁杰模具塑胶有限公司</KHMC><KHSH>91310120703100112H</KHSH><SSZZJG_MC>光明办事处</SSZZJG_MC><YHZH></YHZH><SSSWJG_MC>上海市奉贤区税务局</SSSWJG_MC><KHLX_DM>11</KHLX_DM><GSZGRY_MC>黄玉银</GSZGRY_MC><TXDZ>奉贤区民乐路328弄7号</TXDZ></RECORD></DATA>\n";
        XmlUtilOne xmlUtilOne = new XmlUtilOne();
        CompanyResponseDto companyResponseDto = xmlUtilOne.analysisCompanyResponse(xml);
        System.out.println(companyResponseDto);
    }

}
