package bilibili.longConnect;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BiliLongConnect {
    MyWebSocketClient myWebSocketClient;
    String auth_body;

    public BiliLongConnect() {
    }

    public BiliLongConnect(String url, String auth_body) {
        this.auth_body = auth_body;
        try {
            this.myWebSocketClient = new MyWebSocketClient(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        System.out.println("建立websocket连接");
        myWebSocketClient.connect();
        if (null != myWebSocketClient) {
            while (!myWebSocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("还没有打开");
            }
        } else {
            System.out.println("未初始化");
        }
    }

    public void send(String message) {
        if (null != myWebSocketClient) {
            myWebSocketClient.send(message);

        } else {
            System.out.println("未初始化");
        }
    }


    public void longConnectStart() {
        connect();
        Map<String, Object> params = new HashMap<>();
        params.put("operation", "OP_AUTH");
        params.put("body", auth_body);
        String jsonString = JSONObject.toJSONString(params);
//        Operation字段置为OP_AUTH，Body字段为连接字符串，格式为json。JSON字符串为/v2/app/start接口获取的auth_body字段。
        send(auth_body);
    }

    public void longConnectHeartbeat() {
        Thread thread = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                super.run();
                while (true) {
                    sleep(20 * 1000);
                    send("");
                    String format = "yyyy-MM-dd HH:mm:ss:SSS";
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                    String message = simpleDateFormat.format(date);
                    System.out.println("当前时间" + message);


                }
            }
        };
        thread.start();
        System.out.println("长连接项目心跳已经开启");
    }


}
