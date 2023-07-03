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
        String start = "{\"roomid\":30080228,\"protover\":2,\"uid\":8362283907087588,\"key\":\"W8KyXWfrg3SPa20J4Iak3uAyZGyzIrjVkJ-syh1Ar7kBNGEQnFjmQiECAXjEuvdB0yPv9IXo6xhN9pVsvCl5Yjx2QDYNyb-CG-YpvggpvXF20NVVQye4vMHFNlRlM3lPtAWIYCJ91ZWStn0OW_g6953JsmbRma4=\",\"group\":\"open\"}\n";
        AppEnd appEnd = new AppEnd(start);
        String append = appEnd.appEnd();
        System.out.println("append is: " + append);
    }
}
