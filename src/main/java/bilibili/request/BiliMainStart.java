package bilibili.request;

import java.io.IOException;

public class BiliMainStart {
    public static void main(String[] args) throws IOException, InterruptedException {
        BiliMainStart biliMain = new BiliMainStart();
        BiliMainEnd biliMainEnd = new BiliMainEnd();
        biliMain.startApp();
//        System.out.println("开始睡眠");
//        Thread.sleep(3 * 60 * 1000);
//        System.out.println("关闭线程");
//        biliMainEnd.endApp();
    }

    public void startApp() throws IOException {
        AppStart appStart = new AppStart();
        String start = appStart.appStart();
        System.out.println("start is: " + start);
        HeartBeat heartBeat = new HeartBeat(start);
        heartBeat.heartbeatStart();
    }


}
