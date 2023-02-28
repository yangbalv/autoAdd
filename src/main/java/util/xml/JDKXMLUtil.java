package util.xml;


import mode.CompanyResponseDto;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

//JDK原生的xml处理工具
public class JDKXMLUtil {
    //    读取读取读取读取读取读取
//    读取读取读取读取读取读取
//    读取读取读取读取读取读取
//    读取读取读取读取读取读取
//    读取读取读取读取读取读取
//    读取读取读取读取读取读取
//xml数据
//<?xml version="1.0" encoding="utf-8"?>
//<responsedata>
//  <resultcode>a</resultcode>
//  <resultcode>b</resultcode>
//  <resultcode>c</resultcode>
//  <resultdesc>xxx</resultdesc>
//</responsedata>
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        String xmlString = "<DATA><AUTHOR>aaa</AUTHOR><CREATE_TIME>2023-01-02</CREATE_TIME><RECORD><KHBH>xj1702101129100001</KHBH><GDDH>15000265235</GDDH><SSZZJG_DM>1911071636050001</SSZZJG_DM><SJ_KHBH></SJ_KHBH><KHMC_PYT></KHMC_PYT><YZBM></YZBM><YDDH>15000265235</YDDH><LXR>阳永桥</LXR><SSSWJG_DM>1310226</SSSWJG_DM><GSZGRY_DM>2106181057340001</GSZGRY_DM><KHBM></KHBM><DH3></DH3><SSXZQH_DM>310120</SSXZQH_DM><KHYH></KHYH><KHMC>上海沿鹤液压器材有限公司</KHMC><KHSH>91310120MA1HLQRR2B</KHSH><SSZZJG_MC>光明办事处</SSZZJG_MC><YHZH></YHZH><SSSWJG_MC>上海市奉贤区税务局</SSSWJG_MC><KHLX_DM>11</KHLX_DM><GSZGRY_MC>张淑芬</GSZGRY_MC><TXDZ>青村镇奉永路399号</TXDZ></RECORD></DATA>\n";
        JDKXMLUtil.analysisCompanyResponse(xmlString);
    }

    /**
     * 使用org.w3c.dom.Document来解析xml字符串
     */
    public static void parseXmlString() throws ParserConfigurationException, IOException, SAXException {
        //创建DOM解析器的工厂实例
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //从DOM工厂中获取解析器
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        //使用解析器生成Document实例
        String xmlString = "<responsedata>\n" +
                "  <resultcode>a</resultcode>\n" +
                "  <resultcode>b</resultcode>\n" +
                "  <resultcode>c</resultcode>\n" +
                "  <resultdesc>xxx</resultdesc>\n" +
                "</responsedata>";
        Document document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));
        //获取responsedata元素的节点列表
        NodeList responsedata = document.getElementsByTagName("responsedata");
        //遍历该节点列表
        for (int i = 0; i < responsedata.getLength(); i++) {
            //获取具体的元素
            Element node = (Element) responsedata.item(i);
            //输出值
            System.out.println("resultcode:" + node.getElementsByTagName("resultcode").item(0).getFirstChild().getNodeValue());
            System.out.println("resultdesc:" + node.getElementsByTagName("resultdesc").item(0).getFirstChild().getNodeValue());
        }
    }
//    注意：DOM解析的数据是保留在内存中的，这方便我们修改，但同时如果xml文件比较大的时候，占用的内存也会较大，可能会造成内存溢出


//    生成生成生成生成生成生成
//    生成生成生成生成生成生成
//    生成生成生成生成生成生成
//    生成生成生成生成生成生成
//    生成生成生成生成生成生成
//    生成生成生成生成生成生成
//    生成实例
//    org.w3c.dom.Document生成xml字符串

    /**
     * 使用org.w3c.dom.Document来生成xml字符串
     */
    public static String createXmlStr() {
        String xmlString = "";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //使用解析器生成Document实例
            Document document = documentBuilder.newDocument();
            //设置版本号
            document.setXmlVersion("1.0");
            //创建父元素
            Element requesData = document.createElement("requesData");

            //创建子元素
            Element username = document.createElement("username");
            //添加元素内容
            username.setTextContent("xxx");
            Element password = document.createElement("password");
            password.setTextContent("yyy");

            //将子元素添加到父元素
            requesData.appendChild(username);
            requesData.appendChild(password);

            //将父元素添加到Document
            document.appendChild(requesData);

            //创建转换器实例
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            //设置输出的编码
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //是否可以在输出结果树时添加额外的空格
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // xml转换String
            DOMSource domSource = new DOMSource(document);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            transformer.transform(domSource, new StreamResult(byteArrayOutputStream));
            xmlString = byteArrayOutputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        System.out.println(xmlString);
        return xmlString;
    }


    /**
     * 解析返回的公司的信息
     *
     * @param xmlString xml文件内容
     * @return 公司信息的实体类
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static CompanyResponseDto analysisCompanyResponse(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        CompanyResponseDto companyResponseDto = new CompanyResponseDto();
        //创建DOM解析器的工厂实例
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //从DOM工厂中获取解析器
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        //使用解析器生成Document实例
        Document document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));
//        document.getElementsByTagName返回的都是链表形式的数据

//        首先获取根结点<DATA>
//        document.getElementsByTagName获取所有<DATA>的元素
        NodeList dataList = document.getElementsByTagName("DATA");
//        由于只有一个<DATA>所以直接获取item0
        Element data = (Element) dataList.item(0);


//        node.getElementsByTagName("resultcode").item(0).getFirstChild().getNodeValue()

        Node AUTHOR = data.getElementsByTagName("AUTHOR").item(0).getFirstChild();
        Node item = data.getElementsByTagName("AUTHOR").item(0);

        System.out.println(item.toString());
        System.out.println(item.getChildNodes().item(0).getNodeValue());


        companyResponseDto.setAUTHOR(AUTHOR.getNodeValue());


        return companyResponseDto;
    }

}
