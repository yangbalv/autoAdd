package bilibili.request;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HeartBeat {
    private String response;
    private static final String ACTION = "/v2/app/heartbeat";
    //    private static final long APP_ID = 3463268406164140L;
    private String signatureNonce;

    public HeartBeat() {
    }

    public HeartBeat(String response, String signatureNonce) {
        this.response = response;
        this.signatureNonce = "HeartBeat11";

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
        String response = author.authorize(ACTION, params, signatureNonce);
        return response;
    }

    public static void main(String[] args) throws IOException {
        String start = "{\"code\":0,\"message\":\"0\",\"request_id\":\"1671014321960431616\",\"data\":{\"anchor_info\":{\"room_id\":30080228,\"uface\":\"https://i2.hdslb.com/bfs/face/43e4b8d1a623a76e528ff2f3467c5a7704c89034.jpg\",\"uid\":3461578856860568,\"uname\":\"望美人汐\"},\"game_info\":{\"game_id\":\"d4ba5d2a-ea85-42c1-9115-6b5f5bd1cc40\"},\"websocket_info\":{\"auth_body\":\"{\\\"roomid\\\":30080228,\\\"protover\\\":2,\\\"uid\\\":8362283907087588,\\\"key\\\":\\\"jIi2BHm_Ef5mEpM-OFbLXAeshHjHNgl-XsXUr3G6TmacHJY9biv0yJBgJDj83uVAt4qZYHNcI7h_Cl1DTAP8fDePeYP1Sj_3iIRUZaVZyaNodiOd9kwl0UFhIPsw4OaD4PErrs7f0CMCP40s013VYBbG4G2421w=\\\",\\\"group\\\":\\\"open\\\"}\",\"wss_link\":[\"wss://tx-sh-live-comet-04.chat.bilibili.com:443/sub\",\"wss://hw-gz-live-comet-02.chat.bilibili.com:443/sub\",\"wss://broadcastlv.chat.bilibili.com:443/sub\"]}}}\n";
        HeartBeat appEnd = new HeartBeat(start, "vv");
        System.out.println(appEnd.heartbeat());
    }
}
