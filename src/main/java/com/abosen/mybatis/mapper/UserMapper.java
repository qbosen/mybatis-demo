package com.abosen.mybatis.mapper;

import com.abosen.mybatis.po.User;

/**
 * @author qiubaisen
 * @date 2018/9/3
 */
public interface UserMapper {
    User findUserById(int id);
}
