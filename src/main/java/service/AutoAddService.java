package service;

import mode.CheckCodeRequestDto;
import mode.CheckCodeResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class AutoAddService {
    public final Logger logger = LoggerFactory.getLogger(getClass());

    public CheckCodeResponseDto checkCode(CheckCodeRequestDto requestDto) throws IOException {
        String checkCodeUrl = "http://oa.htjs.net/manage/checkCode.jsp?USERNAME=" + requestDto.getUserName() + "&PASSWORD=" + requestDto.getPassword();
        CheckCodeResponseDto responseDto = new CheckCodeResponseDto();
        StringBuffer stringBuffer = new StringBuffer();

        URL url = new URL(checkCodeUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);

        httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        httpURLConnection.connect();

        int responseCode = httpURLConnection.getResponseCode();


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\n");
        }
        logger.info(" end the postRequest of url: {}", checkCodeUrl);
        return responseDto;
    }
}
