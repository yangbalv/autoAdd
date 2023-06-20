package bilibili.request;

import java.io.IOException;

public class BilibiliMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        BilibiliMain bilibiliMain = new BilibiliMain();
        bilibiliMain.startApp();
        Thread.sleep(3 * 60 * 1000);
        System.out.println("关闭线程");
        bilibiliMain.endApp();
    }

    public void startApp() throws IOException {
        AppStart appStart = new AppStart();
        String start = appStart.appStart();
        System.out.println("start is: " + start);
        HeartBeat heartBeat = new HeartBeat(start);
        heartBeat.heartbeatStart();
    }

    public void endApp() throws IOException {
        String start = "{\"code\":0,\"message\":\"0\",\"request_id\":\"1671014321960431616\",\"data\":{\"anchor_info\":{\"room_id\":30080228,\"uface\":\"https://i2.hdslb.com/bfs/face/43e4b8d1a623a76e528ff2f3467c5a7704c89034.jpg\",\"uid\":3461578856860568,\"uname\":\"望美人汐\"},\"game_info\":{\"game_id\":\"d4ba5d2a-ea85-42c1-9115-6b5f5bd1cc40\"},\"websocket_info\":{\"auth_body\":\"{\\\"roomid\\\":30080228,\\\"protover\\\":2,\\\"uid\\\":8362283907087588,\\\"key\\\":\\\"jIi2BHm_Ef5mEpM-OFbLXAeshHjHNgl-XsXUr3G6TmacHJY9biv0yJBgJDj83uVAt4qZYHNcI7h_Cl1DTAP8fDePeYP1Sj_3iIRUZaVZyaNodiOd9kwl0UFhIPsw4OaD4PErrs7f0CMCP40s013VYBbG4G2421w=\\\",\\\"group\\\":\\\"open\\\"}\",\"wss_link\":[\"wss://tx-sh-live-comet-04.chat.bilibili.com:443/sub\",\"wss://hw-gz-live-comet-02.chat.bilibili.com:443/sub\",\"wss://broadcastlv.chat.bilibili.com:443/sub\"]}}}\n";
        AppEnd appEnd = new AppEnd(start);
        String append = appEnd.appEnd();
        System.out.println("append is: " + append);
    }
}
