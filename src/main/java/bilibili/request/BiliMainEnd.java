package bilibili.request;

import java.io.IOException;

public class BiliMainEnd {
    public static void main(String[] args) throws IOException, InterruptedException {
        BiliMainEnd bilibiliMain = new BiliMainEnd();
//        bilibiliMain.startApp();
//        Thread.sleep(3 * 60 * 1000);
//        System.out.println("关闭线程");
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
        String start = "{\"code\":0,\"message\":\"0\",\"request_id\":\"1671094059995414528\",\"data\":{\"anchor_info\":{\"room_id\":30080228,\"uface\":\"https://i2.hdslb.com/bfs/face/43e4b8d1a623a76e528ff2f3467c5a7704c89034.jpg\",\"uid\":3461578856860568,\"uname\":\"望美人汐\"},\"game_info\":{\"game_id\":\"08ab839e-919d-441b-b58c-2da4fd55c214\"},\"websocket_info\":{\"auth_body\":\"{\\\"roomid\\\":30080228,\\\"protover\\\":2,\\\"uid\\\":8362283907087588,\\\"key\\\":\\\"9t7F-OeL2SKEkQjERkXAqhUJKXanUGrZyZn7XTZmQGaqTRb85L6ImvKtLcozLzD_77nBOZyStM5uDLZRjCPOPQ14Epm2e3ydYNrod_ewNv14xRHI3pBo_UUzeXEsg8xaFlAq0O_0iy3oPejQiU27GeevniXPHkQ=\\\",\\\"group\\\":\\\"open\\\"}\",\"wss_link\":[\"wss://tx-gz-live-comet-10.chat.bilibili.com:443/sub\",\"wss://hw-gz-live-comet-05.chat.bilibili.com:443/sub\",\"wss://broadcastlv.chat.bilibili.com:443/sub\"]}}}\n";
        AppEnd appEnd = new AppEnd(start);
        String append = appEnd.appEnd();
        System.out.println("append is: " + append);
    }
}
