package bilibili.request;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppEnd {
    private String response;
    private static final String ACTION = "/v2/app/end";
    private static final long APP_ID = 3463268406164140L;
//    private String signatureNonce;

    public AppEnd() {
    }

    public AppEnd(String response) {
        this.response = response;

    }

    public String appEnd() throws IOException {
        JSONObject parse = JSONObject.parseObject(response);
        JSONObject data = (JSONObject) parse.get("data");
        JSONObject game_info = (JSONObject) data.get("game_info");
        String game_id = (String) game_info.get("game_id");
        System.out.println(game_id);
        Map<String, Object> params = new HashMap<>();
        params.put("app_id", APP_ID);
        params.put("game_id", game_id);
        Author author = new Author();
        Long nowTime = System.currentTimeMillis();
        String signatureNonce = "appEnd" + nowTime;
        String response = author.authorize(ACTION, params, signatureNonce);
        return response;
    }


}
