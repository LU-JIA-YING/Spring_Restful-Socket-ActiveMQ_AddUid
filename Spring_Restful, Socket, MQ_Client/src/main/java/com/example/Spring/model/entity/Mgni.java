package com.example.Spring.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonPropertyOrder(value = {"time","id"})   //  Spring Boot解決向前端返回對象時的屬性無序問題 https://blog.csdn.net/weixin_44072535/article/details/109964618
//@JsonPropertyOrder(alphabetic = true)   //  定義Java類成員變數的序列化順序
public class Mgni  {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private String type;
    private String cmNo;
    private String kacType;
    private String bankNo;
    private String ccy;
    private String pvType;
    private String bicaccNo;

    @JsonProperty(value = "iType")  //  設定Response回傳json時名字樣式
    private String iType;

    @JsonProperty(value = "pReason")
    private String pReason;
    private BigDecimal amt;
    private String ctName;
    private String ctTel;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "uTime")
    private LocalDateTime uTime;
    private List<Cashi> cashiList;
}
