package main;

import com.alibaba.fastjson.JSONObject;
import mode.CheckCodeRequestDto;
import mode.CheckCodeResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AutoAddService;
import util.PropertiesUtil;

import java.io.IOException;
import java.util.Properties;

public class StepBetter {
    public final Logger logger = LoggerFactory.getLogger(getClass());
    public AutoAddService autoAddService;
    public Properties properties = PropertiesUtil.properties;


    public static void main(String[] args) {


    }

    public CheckCodeResponseDto checkCode(CheckCodeRequestDto requestDto) throws IOException {
        logger.info("checkCode requestDto is:{}", JSONObject.toJSONString(requestDto));
        CheckCodeResponseDto responseDto = new CheckCodeResponseDto();
        try {
            responseDto = autoAddService.checkCode(requestDto);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        logger.info("checkCode responseDto is:{}", responseDto.toString());
        return responseDto;
    }

}
