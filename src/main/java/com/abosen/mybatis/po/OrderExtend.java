package com.abosen.mybatis.po;

import lombok.Data;

import java.util.Date;

/**
 * @author qiubaisen
 * @date 2018/9/16
 */
@Data
public class OrderExtend {
    private Integer id;
    private Integer userId;
    private String number;
    private Date createtime;
    private String note;
    private Item item;
}

