package util.xml;

import mode.CompanyResponseDto;
import mode.RECORD;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.Iterator;

//使用org.dom4j.Document;
public class XmlUtilOne {

//    文章来源
//    https://blog.csdn.net/qq_36551991/article/details/119063761

//    读取读取读取读取读取读取
//    读取读取读取读取读取读取
//    读取读取读取读取读取读取
//    读取读取读取读取读取读取
//    读取读取读取读取读取读取
//    读取读取读取读取读取读取
//xml数据
//<?xml version="1.0" encoding="utf-8"?>
//<responsedata>
//  <resultcode>xxx</resultcode>
//  <resultdesc>xxx</resultdesc>
//</responsedata>

    //  读取String形式的xml内容
    public static void readXmlByFromString(String xmlString) throws DocumentException {

        //将给定的String文本解析为XML文档并返回新创建的document
        Document document = DocumentHelper.parseText(xmlString);
        //获取根节点,在例子中就是responsedata节点
        Element rootElement = document.getRootElement();
        //获取根节点下的某个元素
        Element resultcode = rootElement.element("resultcode");
        Element resultdesc = rootElement.element("resultdesc");
        //getData返回元素的数据
        String resultcodeData = (String) resultcode.getData();
        String data = (String) resultdesc.getData();
        //遍历所有子节点
        for (Iterator i = rootElement.elementIterator(); i.hasNext(); ) {
            Element next = (Element) i.next();
            System.out.println(next.getName() + "：" + next.getData());
        }
        //遍历某个子节点，如resultcode
        for (Iterator i = rootElement.elementIterator("resultcode"); i.hasNext(); ) {
            Element next = (Element) i.next();
            System.out.println(next.getName() + "：" + next.getData());
        }
    }

//    DOM4J解析xml字符串流程：
//1、使用parseText方法将xml字符串转换成Document对象
//
//2、获取Root节点，XML解析都是从Root元素开始的
//
//3、使用element方法直接获取某个子节点或者使用elementIterator迭代器进行遍历

    //  读取xml文件的内容
    public static void readXmlByFromFile(String xmlPath) throws DocumentException {
        //读取XML文件,获得document对象.
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(xmlPath));
    }

//    生成生成生成生成生成生成
//    生成生成生成生成生成生成
//    生成生成生成生成生成生成
//    生成生成生成生成生成生成
//    生成生成生成生成生成生成
//    生成生成生成生成生成生成
//    生成实例

//<?xml version="1.0" encoding="UTF-8"?>
//<requestData>
//  <username>xxx</username>
//   <password>yyy</password>
//</requestData>

//    addComment：添加注释
//    addAttribute：添加属性
//    addElement：添加子元素
//    setText：设置节点的内容

//    注：setText和addAttribute是不一样的效果
//    例：username.addAttribute("username","xxx");
//    生成的格式如下：<username username="xxx"/>

    /**
     * 生成xml格式的字符串
     *
     * @return
     */
    public static String createXmlString() {
        //创建document对象
        Document document = DocumentHelper.createDocument();
        //设置编码
        document.setXMLEncoding("UTF-8");
        //创建根节点
        Element requestData = document.addElement("requestData");
        //在根节点加入username子节点
        Element username = requestData.addElement("username");
        //往子节点添加内容
        username.setText("xxx");
        //加入password子节点
        Element password = requestData.addElement("password");
        //添加内容
        password.setText("yyy");
        //将document对象转换成字符串
        String xml = document.asXML();
        System.out.println(xml);
        return xml;
    }

    public static void main(String[] args) throws DocumentException {

        String xmlString = "<DATA><AUTHOR>aaa</AUTHOR><CREATE_TIME>2023-01-02</CREATE_TIME><RECORD><KHBH>xj1702101129100001</KHBH><GDDH>15000265235</GDDH><SSZZJG_DM>1911071636050001</SSZZJG_DM><SJ_KHBH></SJ_KHBH><KHMC_PYT></KHMC_PYT><YZBM></YZBM><YDDH>15000265235</YDDH><LXR>阳永桥</LXR><SSSWJG_DM>1310226</SSSWJG_DM><GSZGRY_DM>2106181057340001</GSZGRY_DM><KHBM></KHBM><DH3></DH3><SSXZQH_DM>310120</SSXZQH_DM><KHYH></KHYH><KHMC>上海沿鹤液压器材有限公司</KHMC><KHSH>91310120MA1HLQRR2B</KHSH><SSZZJG_MC>光明办事处</SSZZJG_MC><YHZH></YHZH><SSSWJG_MC>上海市奉贤区税务局</SSSWJG_MC><KHLX_DM>11</KHLX_DM><GSZGRY_MC>张淑芬</GSZGRY_MC><TXDZ>青村镇奉永路399号</TXDZ></RECORD></DATA>\n";
        XmlUtilOne xmlUtilOne = new XmlUtilOne();
        xmlUtilOne.analysisCompanyResponse(xmlString);
    }

    public CompanyResponseDto analysisCompanyResponse(String xmlString) throws DocumentException {
        CompanyResponseDto res = new CompanyResponseDto();
        //将给定的String文本解析为XML文档并返回新创建的document
        Document document = DocumentHelper.parseText(xmlString);
        //获取根节点,在例子中就是data节点
        Element data = document.getRootElement();
        //获取根节点下的某个元素
        Element author = data.element("AUTHOR");
        res.setAUTHOR((String) author.getData());
        Element createTime = data.element("CREATE_TIME");
        res.setCREATE_TIME((String) createTime.getData());
        Element recordString = data.element("RECORD");
        RECORD record = new RECORD();
        Element khbh = recordString.element("KHBH");
        record.setKHBH((String) khbh.getData());

        Element gddh = recordString.element("GDDH");
        record.setGDDH((String) gddh.getData());

        Element sszzjgDm = recordString.element("SSZZJG_DM");
        record.setSSZZJG_DM((String) sszzjgDm.getData());

        Element sjKhbh = recordString.element("SJ_KHBH");
        record.setSJ_KHBH((String) sjKhbh.getData());

        Element khmcPyt = recordString.element("KHMC_PYT");
        record.setKHMC_PYT((String) khmcPyt.getData());

        Element yzbm = recordString.element("YZBM");
        record.setYZBM((String) yzbm.getData());

        Element yddh = recordString.element("YDDH");
        record.setYDDH((String) yddh.getData());

        Element lxr = recordString.element("LXR");
        record.setLXR((String) lxr.getData());

        Element ssswjgDm = recordString.element("SSSWJG_DM");
        record.setSSSWJG_DM((String) ssswjgDm.getData());

        Element gszgryDm = recordString.element("GSZGRY_DM");
        record.setGSZGRY_DM((String) gszgryDm.getData());

        Element khbm = recordString.element("KHBM");
        record.setKHBM((String) khbm.getData());

        Element dh3 = recordString.element("DH3");
        record.setDH3((String) dh3.getData());

        Element ssxzqhDm = recordString.element("SSXZQH_DM");
        record.setSSXZQH_DM((String) ssxzqhDm.getData());

        Element khyh = recordString.element("KHYH");
        record.setKHYH((String) khyh.getData());

        Element khmc = recordString.element("KHMC");
        record.setKHMC((String) khmc.getData());

        Element khsh = recordString.element("KHSH");
        record.setKHSH((String) khsh.getData());

        Element sszzjgMc = recordString.element("SSZZJG_MC");
        record.setSSZZJG_MC((String) sszzjgMc.getData());

        Element yhzh = recordString.element("YHZH");
        record.setYHZH((String) yhzh.getData());

        Element ssswjgMc = recordString.element("SSSWJG_MC");
        record.setSSSWJG_MC((String) ssswjgMc.getData());

        Element khlxDm = recordString.element("KHLX_DM");
        record.setKHLX_DM((String) khlxDm.getData());

        Element gszgryMc = recordString.element("GSZGRY_MC");
        record.setGSZGRY_MC((String) gszgryMc.getData());

        Element txdz = recordString.element("TXDZ");
        record.setTXDZ((String) txdz.getData());

        res.setRECORD(record);

        return res;
    }

}
