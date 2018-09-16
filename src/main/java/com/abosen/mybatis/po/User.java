package com.abosen.mybatis.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qiubaisen
 * @date 2018/9/3
 */

@Data
public class User implements Serializable {
    private int id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;
}
