# Spring_Restful-Socket-ActiveMQ(有加uid_針對ActiveMQ可以處理同一筆reques+reponse機制)
* [Http、RESTful、RPC、MQ、Socket 概念與區別](https://www.jianshu.com/p/e077193efe79)

## JSON Web Token (JWT)

[Spring Security](https://hackmd.io/@IDdlPCCwQoeX-9DvmEbLyw/r1V_j74Vs)

- [ ] JWT pros
- [ ] JWT structrue

## Object-oriented programming (OOP)

* [Java 四大特性](https://hackmd.io/@IDdlPCCwQoeX-9DvmEbLyw/Hyuo8LSaq)
* [物件導向程式設計基本原則 - SOLID](https://skyyen999.gitbooks.io/-study-design-pattern-in-java/content/oodPrinciple.html)

- [ ] 四大特性實作
- [ ] SOLID

## Aspect-oriented Programming (AOP)
- [ ] 理解 AOP 用途
- [ ] Spring AOP Annotaion 學習
- [ ] 程式實作

## 各種 util 應用
- [ ] 金額處理
- [ ] 幣別處理
- [ ] 字串處理
- [ ] 日期、時間處理
- [ ] 加密處理

## Sprint 4 實作
- [X] 整合 Restful, Socket, MQ (CRUD)

下載 ActiveMQ : http://activemq.apache.org/components/classic/download/ (下載 zip 文件並將其解壓縮)  
執行 apache-activemq-5.17.2\bin\win64\activemq.bat的檔案(程式中config->OpenExe有寫啟動檔，可以不用手動開)  
ActiveMQ 控制台 : http://localhost:8161/admin/ 帳密admin  
  
* 1:查詢Cashi全部資料/ 2:用Id查詢Mgni/ 3:用Id查詢Cashi/ 4:Mgni動態查詢/ 5:Mgni新增/ 6:Mgni更新/ 7:Mgni刪除
* 新增、修改、刪除時，Mgni跟Cashi會連動

```java=
< Ture >  
      
	{"requestType":"1","request":{}}

        {"requestType":"2","request":{"cmNo":"9","kacType":"1","bankNo":"999","ccy":"TWD","pvType":"1","bicaccNo":"0000000","iType":"1","pReason":"money","clearingAccountList":[{"accNo":"1","amt":10},{"accNo":"2","amt":20}],"ctName":"Joey","ctTel":"12345678"}}

        {"requestType":"3","request":{"id":"MGI20221004222746688","cmNo":"3","kacType":"1","bankNo":"999","ccy":"TWD","pvType":"1","bicaccNo":"0000000","iType":"1","pReason":"deposit some money in the bank","clearingAccountList":[{"accNo":"1","amt":10000},{"accNo":"2","amt":200}],"ctName":"Joey","ctTel":"12345678"}}

        {"requestType":"3","request":{"id":"MGI20221004222746688","cmNo":"3","kacType":"1","bankNo":"999","ccy":"TWD","pvType":"1","bicaccNo":"0000000","iType":"1","pReason":"deposit some money in the bank","clearingAccountList":[{"accNo":"1","amt":10000}],"ctName":"Joey","ctTel":"12345678"}}

        {"requestType":"4","request":{"id":"MGI20221021171159182"}}


        {"requestType":"5","request":{"id":null,"kacType":"1","ccy":"TWD","date":"20221019"}}

        {"requestType":"6","request":{"id":null,"accNo":"000000","ccy":"TWD","page":1,"size":4}}

        ===============================================================================================================
< Error >

        {"requestType":"2","request":{"cmNo":"9","kacType":"1","bankNo":"999","ccy":"TWD","pvType":"3","bicaccNo":"0000000","iType":"1","pReason":"money","clearingAccountList":[{"accNo":"1","amt":10},{"accNo":"2","amt":20}],"ctName":"Joey","ctTel":"1234578"}}

        {"requestType":"3","request":{"id":"MGI20221004222746688","cmNo":"3","kacType":"1","bankNo":"999","ccy":"HKD","pvType":"3","bicaccNo":"0000000","iType":"1","pReason":"deposit some money in the bank","clearingAccountList":[{"accNo":"1","amt":10000},{"accNo":"2","amt":200}],"ctName":"Joey","ctTel":"12345678"}}


        {"requestType":"4","request":{"id":"MGI20220929171333131"}}
        
```
