package bilibili.request;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HeartBeat {
    private String response;
    private static final String ACTION = "/v2/app/heartbeat";
    //    private static final long APP_ID = 3463268406164140L;
//    private String signatureNonce;

    public HeartBeat() {
    }

    public HeartBeat(String response) {
        this.response = response;

    }


    public void heartbeatStart() throws IOException {
        Thread thread = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                super.run();
                while (true) {
                    sleep(20 * 1000);
                    try {
                        String heartbeat = heartbeat();
                        System.out.println("heartbeat is:" + heartbeat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String format = "yyyy-MM-dd HH:mm:ss";
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                    String message = simpleDateFormat.format(date);
                    System.out.println("当前时间" + message);


                }
            }
        };
        thread.start();
        System.out.println("线程已经开启");
    }

    public String heartbeat() throws IOException {
        JSONObject parse = JSONObject.parseObject(response);
        JSONObject data = (JSONObject) parse.get("data");
        JSONObject game_info = (JSONObject) data.get("game_info");
        String game_id = (String) game_info.get("game_id");
        System.out.println(game_id);
        Map<String, Object> params = new HashMap<>();
//        params.put("app_id", APP_ID);
        params.put("game_id", game_id);
        Author author = new Author();
        Long nowTime = System.currentTimeMillis();
        String signatureNonce = "heartbeat" + nowTime;
        String response = author.authorize(ACTION, params, signatureNonce);
        return response;
    }
}
