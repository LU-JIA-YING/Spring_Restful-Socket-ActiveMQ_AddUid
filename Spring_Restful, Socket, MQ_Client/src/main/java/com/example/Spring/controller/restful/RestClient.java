package com.example.Spring.controller.restful;

import com.example.Spring.controller.dto.request.ClearingMarginRequest;
import com.example.Spring.controller.dto.request.SearchCashiRequest;
import com.example.Spring.controller.dto.request.SearchMgniRequest;
import com.example.Spring.controller.dto.response.MgniResponse;
import com.example.Spring.controller.dto.response.StatusResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//https://www.bilibili.com/video/BV1tJ41147uj/?p=3
//https://blog.csdn.net/weixin_40461281/article/details/83540604
public class RestClient {
    private static final Logger logger = Logger.getLogger(RestClient.class);
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void sendRestFulMessage(String message) {


        String url = "http://localhost:8080/api/mgn";

        String response = "";

        try {
            //  使用Gson处理多层json格式的value和key值:https://blog.51cto.com/u_15127527/4523031
            JsonParser jp = new JsonParser();
            JsonObject jo = jp.parse(message).getAsJsonObject();

            switch (jo.get("requestType").getAsString()) {
                case "1": {
                    logger.info("執行Mgni查詢全部");
                    url += "/findAllMgniJsonAndXml";
                    MgniResponse mgniResponse = restTemplate.getForObject(url, MgniResponse.class);
                    response = writeResponse(mgniResponse);
                    //  {"requestType":"1","request":{}}
                    break;
                }
                case "2": {
                    logger.info("Mgni新增");
                    url += "/createClearingMargin";
                    ClearingMarginRequest clearingMarginRequest = new Gson().fromJson(jo.get("request").getAsJsonObject(), ClearingMarginRequest.class);
                    StatusResponse statusResponse = restTemplate.postForObject(url, clearingMarginRequest, StatusResponse.class);
                    response = writeResponse(statusResponse);
                    //  {"requestType":"2","request":{"cmNo":"9","kacType":"1","bankNo":"999","ccy":"TWD","pvType":"1","bicaccNo":"0000000","iType":"1","pReason":"money","clearingAccountList":[{"accNo":"1","amt":10},{"accNo":"2","amt":20}],"ctName":"Joey","ctTel":"12345678"}}
                    break;
                }
                case "3": {
                    logger.info("Mgni更新");
                    url += "/updateClearingMargin";
                    ClearingMarginRequest clearingMarginRequest = new Gson().fromJson(jo.get("request").getAsJsonObject(), ClearingMarginRequest.class);
                    StatusResponse statusResponse = restTemplate.postForObject(url, clearingMarginRequest, StatusResponse.class);
                    response = writeResponse(statusResponse);
                    //  {"requestType":"3","request":{"id":"MGI20221004222746688","cmNo":"3","kacType":"1","bankNo":"999","ccy":"TWD","pvType":"1","bicaccNo":"0000000","iType":"1","pReason":"deposit some money in the bank","clearingAccountList":[{"accNo":"1","amt":10000},{"accNo":"2","amt":200}],"ctName":"Joey","ctTel":"12345678"}}
                    break;
                }
                case "4": {
                    logger.info("資料刪除");
                    url += "/delete";
                    StatusResponse statusResponse = restTemplate.postForObject(url, jo.get("request").getAsJsonObject().get("id").getAsString(), StatusResponse.class);
                    response = writeResponse(statusResponse);
                    //  {"requestType":"4","request":{"id":"MGI20221021171159182"}}
                    break;
                }
                case "5": {
                    logger.info("Mgni動態查詢");
                    url += "/search/Mgni";
                    SearchMgniRequest searchMgniRequest = new Gson().fromJson(jo.get("request").getAsJsonObject(), SearchMgniRequest.class);
                    List mgniResponse = restTemplate.postForObject(url, searchMgniRequest, List.class);
                    response = writeResponse(mgniResponse);
                    //  {"requestType":"5","request":{"id":null,"kacType":"1","ccy":"TWD","date":"20221019"}}
                    break;
                }
                case "6": {
                    logger.info("Cashi動態查詢");
                    url += "/search/Cashi";
                    SearchCashiRequest searchCashiRequest = new Gson().fromJson(jo.get("request").getAsJsonObject(), SearchCashiRequest.class);
                    List cashiResponse = restTemplate.postForObject(url, searchCashiRequest, List.class);
                    response = writeResponse(cashiResponse);
                    //  {"requestType":"6","request":{"id":null,"accNo":"000000","ccy":"TWD","page":1,"size":4}}
                    break;
                }
                default: {
                    response = mapper.writeValueAsString("請輸入 1~6");
                }
            }

        } catch (Exception e) {
            logger.error("Error Message: " + e.getMessage());
        } finally {
            System.out.println(response);
            System.out.println("===============================================");
        }

    }

    private static String writeResponse(Object object) throws JsonProcessingException {

        //  https://stackoverflow.com/questions/69831153/jackson-objectmapper-findandregistermodules-not-working-to-serialise-localdate
        //  可以自動搜索所有模塊，不需要我們手動註冊
        mapper.findAndRegisterModules();

        //  把一个对象转化为json字符串
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }


}
