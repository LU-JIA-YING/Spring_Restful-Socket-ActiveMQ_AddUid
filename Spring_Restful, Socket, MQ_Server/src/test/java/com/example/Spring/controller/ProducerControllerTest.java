package com.example.Spring.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//  https://github.com/shirayner/java-knowledge-hierarchy/blob/master/doc/spring-boot/SpringBoot_02_%E6%9E%84%E5%BB%BARESTful%20API%E4%B8%8E%E5%8D%95%E5%85%83%E6%B5%8B%E8%AF%95.md
//  https://eamonyin.blog.csdn.net/article/details/112434830
//  https://blog.csdn.net/weixin_38405253/article/details/112855657
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class ProducerControllerTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(new ProducerController()).build();
//        mockMvc = MockMvcBuilders.standaloneSetup(wac).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
    }

    @Test
    @DisplayName("測試 getAllMgnMgniXml()")
    public void getAllMgnMgniXml() throws Exception {

        // 构造get请求
        RequestBuilder request = get("/api/mgn/findAllMgniJsonAndXml");

        // 执行get请求
        mockMvc.perform(request)
                .andExpect(status().isOk());  // 對請求結果進行期望，回覆的狀態為200

        System.out.println("測試 Mgni Find All !");
    }

//    @Test
//    @DisplayName("測試 getAllMgniJsonAndXml()")
//    void getAllMgniJsonAndXml() throws Exception {
//
//        // 构造get请求
//        RequestBuilder request = get("/api/mgn/findAllMgniJsonAndXml");
//
//        // 执行get请求
//        mockMvc.perform(request)
//                .andDo(print());// .andDo(print())輸出整個回應結果訊息
//
//        System.out.println("測試 Mgni Find All !");
//    }

//    @Test
//    void getAllMgniJsonAndXml() throws Exception {
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .get("/api/mgn/findAllMgniJsonAndXml")
//        );
//        // Assert (驗證結果)
//        // 驗證回傳的 http 狀態和 response body 的 json 格式中的 name 欄位是否正確
//        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
//    }

//===========================================================================================
//  3A 原則，分別是 Arrange（初始化）、Act（行為）、Assert（驗證結果）

    @Test
    @DisplayName("測試 searchTargetMgni()")
    void searchTargetMgni() throws Exception {

        String response ="[{\"id\":\"MGI20221013094256109\",\"time\":\"2022-10-13 09:42:56\",\"type\":\"1\",\"cmNo\":\"9\",\"kacType\":\"1\",\"bankNo\":\"999\",\"ccy\":\"TWD\",\"pvType\":\"1\",\"bicaccNo\":\"0000000\",\"amt\":30.0000,\"ctName\":\"Joey\",\"ctTel\":\"1234578\",\"status\":\"0\",\"cashiList\":[{\"id\":\"MGI20221013094256109\",\"accNo\":\"1\",\"ccy\":\"TWD\",\"amt\":10.0000},{\"id\":\"MGI20221013094256109\",\"accNo\":\"2\",\"ccy\":\"TWD\",\"amt\":20.0000}],\"iType\":\"1\",\"pReason\":\"money\",\"uTime\":\"2022-10-13 09:42:56\"}]";

        String requestBoby = "{\"id\":\"MGI20221013094256109\"}";
        ResultActions resultActions =
                // perform(request) 為做一個請求的建立，get(url) 為 request 的連結
                mockMvc.perform(
                                post("/api/mgn/search/Mgni").content(requestBoby).contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON)
                        )
                        // 輸出整個回應結果訊息
                        .andDo(print());
        // 取得回傳物件
        String actual = resultActions.andReturn().getResponse().getContentAsString();

        //  驗證結果
        Assert.assertEquals(response, actual);
    }
}