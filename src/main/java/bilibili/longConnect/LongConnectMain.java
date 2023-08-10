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
                "wss://hw-bj-live-comet-05.chat.bilibili.com:443/sub",
                " {\"roomid\":30080228,\"protover\":2,\"uid\":8362283907087588,\"key\":\"kPT-Jy8By5ROuMEnSNeZFVCfO9KuHgDOfgSv7pelHUZlaZSMn9griscV4_eP3yo31-xvZv8aJ_ZbtDJFvRNX9Ky6weIefefYYvchxSAE0MLcKJDYXfTwAs-haoyjVps06obI3l4RLWnGZjJrptD3A4v6BUiagXZSPQ==\",\"group\":\"open\"}");
//        biliLongConnect.connect();
        biliLongConnect.longConnectStart();
        biliLongConnect.longConnectHeartbeat();
    }
}
