package bilibili.request;

import java.io.IOException;

public class BiliMainEnd {
    public static void main(String[] args) throws IOException, InterruptedException {
        BiliMainEnd bilibiliMain = new BiliMainEnd();
        String start = "{\"code\":0,\"message\":\"0\",\"request_id\":\"1689540909257994240\",\"data\":{\"anchor_info\":{\"room_id\":30080228,\"uface\":\"https://i2.hdslb.com/bfs/face/43e4b8d1a623a76e528ff2f3467c5a7704c89034.jpg\",\"uid\":3461578856860568,\"uname\":\"望美人汐\"},\"game_info\":{\"game_id\":\"99f3d7c5-1990-4577-9453-25eafc871825\"},\"websocket_info\":{\"auth_body\":\"{\\\"roomid\\\":30080228,\\\"protover\\\":2,\\\"uid\\\":8362283907087588,\\\"key\\\":\\\"AWAmAk8H3UnXIYla5airF0IeVJ5kA0jm_wuQ_lI0bCZ-Fs2jh1ELMM2RLxJ83cEB-2RSzfRIlyiskQP16Cs0tD3VivtzrTYfMEjrOUHYt_NcioPkRM5H4_0RKiwxmyhVpG5m2N58o1vsq-ux4Y-Zar9lwX4MJzOZNQ==\\\",\\\"group\\\":\\\"open\\\"}\",\"wss_link\":[\"wss://tx-sh-live-comet-05.chat.bilibili.com:443/sub\",\"wss://ali-sh-live-comet-07.chat.bilibili.com:443/sub\",\"wss://broadcastlv.chat.bilibili.com:443/sub\"]}}}\n";
        bilibiliMain.endApp(start);
    }

    public void startApp() throws IOException {
        AppStart appStart = new AppStart();
        String start = appStart.appStart();
        System.out.println("start is: " + start);
        HeartBeat heartBeat = new HeartBeat(start);
        heartBeat.heartbeatStart();
    }

    public void endApp() throws IOException {
        String start = AppStart.start;
        AppEnd appEnd = new AppEnd(start);
        String append = appEnd.appEnd();
        System.out.println("append is: " + append);
    }

    public void endApp(String start) throws IOException {
        AppEnd appEnd = new AppEnd(start);
        String append = appEnd.appEnd();
        System.out.println("append is: " + append);
    }
}
