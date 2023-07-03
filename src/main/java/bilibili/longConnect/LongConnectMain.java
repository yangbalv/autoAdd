package bilibili.longConnect;

import bilibili.request.AppStart;
import bilibili.request.HeartBeat;

import java.io.IOException;

public class LongConnectMain {
    public static void main(String[] args) throws IOException {
        LongConnectMain connectMain = new LongConnectMain();
        connectMain.LongConnectStart();
    }

    public void LongConnectStart() throws IOException {
        BiliLongConnect biliLongConnect = new BiliLongConnect(
                "wss://tx-sh-live-comet-11.chat.bilibili.com:443/sub",
                "{\"roomid\":30080228,\"protover\":2,\"uid\":8362283907087588,\"key\":\"W8KyXWfrg3SPa20J4Iak3uAyZGyzIrjVkJ-syh1Ar7kBNGEQnFjmQiECAXjEuvdB0yPv9IXo6xhN9pVsvCl5Yjx2QDYNyb-CG-YpvggpvXF20NVVQye4vMHFNlRlM3lPtAWIYCJ91ZWStn0OW_g6953JsmbRma4=\",\"group\":\"open\"}\n");
//        biliLongConnect.connect();
        biliLongConnect.longConnectStart();
        biliLongConnect.longConnectHeartbeat();
    }
}
