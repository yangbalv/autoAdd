package util.xml;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;
import java.io.IOException;

public class XmlExecute {
//1接口返回xml格式

//    设置响应体格式
//    response.setContentType("text/xml");
//    判断请求方式
//    if(request.getMethod()==null || !request.getMethod().equalsIgnoreCase("post")){
//        return null;
//    }

//    2.发送xml：

    /**
     * 响应xml
     *
     * @param response
     * @param content
     */
    public static void responseContent(HttpServletResponse response, String content) {
        try {
            //把xml字符串写入响应
            byte[] xmlData = content.getBytes();

            response.setContentLength(xmlData.length);

            ServletOutputStream os = response.getOutputStream();
            os.write(xmlData);

            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //3.接收xml：
//    //解析对方发来的xml数据
//    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//    DocumentBuilder db = dbf.newDocumentBuilder();
//    Document doc = db.parse(request.getInputStream());

    //
////4.获取xml中某一节点内容：
//    public static String getValueByTagName(Document doc, String tagName){
//        if(doc == null || StringUtil.isNull(tagName)){
//            return "";
//        }
//        NodeList pl = doc.getElementsByTagName(tagName);
//        if(pl != null && pl.getLength() > 0){
//            return StringUtil.dealParam(StringUtil.convertNull( pl.item(0).getTextContent()));
//        }
//        return "";
//    }
//    public static void main(String[] args) {
//
//        Document document = ProtocolUtil.newSuccessDocument(ProtocolUtil.SYNC_TAG);
//        org.dom4j.Element rootElement = document.getRootElement();
//        org.dom4j.Element caseVector = rootElement.addElement("caseVector");
//        org.dom4j.Element caseStruct = caseVector.addElement("caseStruct");
//        caseStruct.addElement("taskId111");
//        caseStruct.addElement("taskId").addText("111");
//        org.dom4j.Element fileInfoElement = null;
//        fileInfoElement = caseStruct.addElement("fileInfo");
//        fileInfoElement.addElement("caseId").addText("案件id");
//        fileInfoElement.addElement("ip").addText("我是ip");
//        fileInfoElement.addElement("port").addText(String.valueOf(BusinessServerPortConstant.FILEANYWHERE_PORT));
//        fileInfoElement.addElement("intact").addText("我是intact");
//        org.dom4j.Element evidenceVector = fileInfoElement.addElement("evidenceVector");
//
//        String xml = document.asXML();
//
//        System.out.println(xml);
//
//
//    }
}
