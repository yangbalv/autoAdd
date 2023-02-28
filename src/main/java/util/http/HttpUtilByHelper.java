package util.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
//源网址
//https://www.cnblogs.com/JunkingBoy/p/15042522.html

public class HttpUtilByHelper {
    //    具体的调用接口实现
    //分析接口需要用到的属性
    /*
    1、Url
    2、Cookie
    3、Params
    */
    //定义接口地址
    private static final String url = "";
    //捕获到本次登录的Cookie值
    private static final String Cookie = "";
    //定义参数格式
    private static final String params = "platform=shopee&platform_accountid=12034&startTime=2021-07-16&endTime=2021-07-17&orderid=";

    public static void aa() {

//    具体方法实现：

        //由上面分析了该接口的传参形式，所以我们需要构造好一个POST请求体里面传参数
//构造Cookie存储
        //创建Cookie存储
        CookieStore cookieStore = new BasicCookieStore();
        //设置Cookie值
        BasicClientCookie cookie = new BasicClientCookie("Cookie", Cookie);
        //设置域
        cookie.setDomain("");
        //设置路径
        cookie.setPath("/");
        //将设置好的Cookie放入接口引用中
        cookieStore.addCookie(cookie);
        //使用HttpClient创建登录客户端
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        //创建响应模型，通过请求对象获取响应对象--->发送登录请求
        CloseableHttpResponse response = null;
//    创建请求体：(由于是直接把POST请求体的内容转换拼接，所以无需构造对象参数容器)--->上一篇内容是通过构造参数对象容器将容器内容放入Body中进行的传参

        /*创建请求实体*/
        try {
            //创建登录方式引用
            HttpPost httpPost = new HttpPost(url);
            //设置请求配置
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(3000).setSocketTimeout(3000).build();
            //设置POST请求配置
            httpPost.setConfig(requestConfig);
            //添加请求头
            httpPost.addHeader("Content-Type", "x-www-form-urlencoded; charset=utf-8");
            //设置接收形式
            httpPost.setHeader("Accept", "application/json");
            System.out.println(httpPost);
            //创建登录实体
            httpPost.setEntity(new StringEntity(params, Charset.forName("UTF-8")));

            System.out.println(httpPost);
            //判断请求实体情况
            if (httpPost == null) {
                System.out.println("请求实体为空!");
                return;
            } else {
                response = httpClient.execute(httpPost);
            }
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
//    创建响应对象实体：

        /*创建响应实体*/
        try {
            //从响应模型中获得响应实体
            HttpEntity responseEntity = response.getEntity();
            //打印响应状态
            System.out.println("响应状态为:" + responseEntity.getContentType());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                //判断客户端实体
                if (httpClient != null) {
                    httpClient.close();
                }
                //判断响应实体
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

