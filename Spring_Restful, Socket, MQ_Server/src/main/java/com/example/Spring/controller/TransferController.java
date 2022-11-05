package com.example.Spring.controller;

import com.example.Spring.controller.dto.request.ClearingMarginRequest;
import com.example.Spring.controller.dto.request.SearchCashiRequest;
import com.example.Spring.controller.dto.request.SearchMgniRequest;
import com.example.Spring.controller.dto.response.MgniResponse;
import com.example.Spring.controller.dto.response.StatusResponse;
import com.example.Spring.model.entity.Cashi;
import com.example.Spring.model.entity.Mgni;
import com.example.Spring.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/mgn")
@Validated
public class TransferController {

    @Autowired
    private TransferService transferService;


    //  查詢 API 接收 Json And Xml 格式(Find All Mgni)
    //  https://blog.51cto.com/zhangxueliang/4958486
    //  https://juejin.cn/post/7133020498434195464
    @GetMapping(value = "/findAllMgniJsonAndXml")
    public MgniResponse getAllMgniJsonAndXml() throws Exception {

        MgniResponse response = transferService.getAllMgnMgni();
        System.out.println("====================================");
        return response;
    }

//==============================================================================================

    //  新增結算保證金and交割結算基金帳戶
    @PostMapping("/createClearingMargin")
    public StatusResponse createClearingMargin(@Valid @RequestBody ClearingMarginRequest request) throws Exception {

        StatusResponse response = transferService.createClearingMargin(request);
        return response;
    }

//==============================================================================================

    // 更新結算保證金and交割結算基金帳戶
    //  http://localhost:8080/api/mgn/updateClearingMargin?id=MGI20220930094203264
    @PostMapping("/updateClearingMargin")
    public StatusResponse updateMgni(@Valid @RequestBody ClearingMarginRequest request) throws Exception{

        StatusResponse response = transferService.updateClearingMargin(request.getId(), request);
        return response;
    }

//    //  http://localhost:8080/api/mgn/updateClearingMargin/:id + Path Variables => id MGI20220930094203264
//    @PutMapping("/updateClearingMargin/{id}")
//    public StatusResponse updateMgni(@PathVariable String id, @RequestBody ClearingMarginRequest request) {
//        StatusResponse response = transferService.updateClearingMargin(id, request);
//        return response;
//    }

//==============================================================================================

    //@Pattern(regexp = "^$|(MGI[0-9]{17})", message = "ID格式請輸入：MGI + 17位數字")
    // 刪除結算保證金and交割結算基金帳戶
    @PostMapping("/delete") //刪除訂單
    public StatusResponse deleteOrder(@NotEmpty  @RequestBody String id) throws Exception {

        String response = transferService.deleteClearingMargin(id);
        return new StatusResponse(response);
    }

//==============================================================================================

    //  Mgni複雜動態查詢及分頁，排序
    @PostMapping("/search/Mgni")
    public List<Mgni> searchTargetMgni(@RequestBody SearchMgniRequest request) throws Exception {

        List<Mgni> mgniList = transferService.searchTargetMgni(request);
        return mgniList;
    }

    //  Cashi複雜動態查詢及分頁，排序
    @PostMapping("/search/Cashi")
    public List<Cashi> searchTargetCashi(@RequestBody SearchCashiRequest request) throws Exception {

        List<Cashi> cashiList = transferService.searchTargetCashi(request);
        return cashiList;
    }

}
