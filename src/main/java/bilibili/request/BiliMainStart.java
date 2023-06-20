package bilibili.request;

import java.io.IOException;

public class BiliMainStart {
    public static void main(String[] args) throws IOException, InterruptedException {
        BiliMainStart bilibiliMain = new BiliMainStart();
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
        String start = "{\"code\":0,\"message\":\"0\",\"request_id\":\"1671048261895106560\",\"data\":{\"anchor_info\":{\"room_id\":30080228,\"uface\":\"https://i2.hdslb.com/bfs/face/43e4b8d1a623a76e528ff2f3467c5a7704c89034.jpg\",\"uid\":3461578856860568,\"uname\":\"望美人汐\"},\"game_info\":{\"game_id\":\"6cb49d08-6ce4-4117-b241-157596839897\"},\"websocket_info\":{\"auth_body\":\"{\\\"roomid\\\":30080228,\\\"protover\\\":2,\\\"uid\\\":8362283907087588,\\\"key\\\":\\\"SDBLtq-znum0q75MwDLnIcsN9z5f2ChStbesbsr4dEFnf80h61K60PT8N8wgP5AQe2mLqhYnWe7FiSjpZevX_cSkjd9GP0iTPzO8fYe0NE9NDb527uho2wW7EsCQLm1htzUQ2x2qBqifWzyjbRZrJ0UatrK5z5Oa2A==\\\",\\\"group\\\":\\\"open\\\"}\",\"wss_link\":[\"wss://tx-bj-live-comet-11.chat.bilibili.com:443/sub\",\"wss://hw-gz-live-comet-03.chat.bilibili.com:443/sub\",\"wss://broadcastlv.chat.bilibili.com:443/sub\"]}}}";
        AppEnd appEnd = new AppEnd(start);
        String append = appEnd.appEnd();
        System.out.println("append is: " + append);
    }
}
