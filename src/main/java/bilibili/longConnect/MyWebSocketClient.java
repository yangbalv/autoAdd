package bilibili.longConnect;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class MyWebSocketClient extends WebSocketClient {

    public MyWebSocketClient(String url) throws URISyntaxException {
        super(new URI(url));
    }

    @Override
    public void onOpen(ServerHandshake shake) {
        String format = "yyyy-MM-dd HH:mm:ss:SSS";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String message = simpleDateFormat.format(date);
        System.out.println("握手...");
        for (Iterator<String> it = shake.iterateHttpFields(); it.hasNext(); ) {
            String key = it.next();
            System.out.println(key + ":" + shake.getFieldValue(key));
        }
    }

    @Override
    public void onMessage(String paramString) {
        System.out.println("接收到消息：" + paramString);
    }

    @Override
    public void onClose(int paramInt, String paramString, boolean paramBoolean) {
        System.out.println("关闭...");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("异常" + e);

    }

    public static void main(String[] args) {
        try {
            MyWebSocketClient client = new MyWebSocketClient("wss://tx-sh-live-comet-02.chat.bilibili.com:443/sub");
            client.connect();
            while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("还没有打开");
            }
            System.out.println("建立websocket连接");
            client.send("{\"roomid\":30080228,\"protover\":2,\"uid\":8362283907087588,\"key\":\"NAiAah6I3njcHBEXKqi3psSBLCQZkstNBtO36PPV9O6DPmkIncOBWarLxZfMjbA26OQXMzaZi0hWaG4D7XN1SpCLNWhqRS2tf48zFKsepb3C1qTRbMIuGI-2iwRoBOiAcbLRib-YWU9396e-y-RW4JIk0xykHpqa\",\"group\":\"open\"}");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}