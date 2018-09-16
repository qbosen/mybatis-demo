package com.abosen.mybatis.po;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author qiubaisen
 * @date 2018/9/16
 */

@Data
public class UserExtend {
    /**
     * 增加一对多的订单关联
     * 用于测试 懒加载
     */
    private int id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;
    private List<OrderExtend> orders;
}
