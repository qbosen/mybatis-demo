package com.abosen.mybatis.mapper;

import com.abosen.mybatis.po.*;

import java.util.List;

/**
 * @author qiubaisen
 * @date 2018/9/3
 */
public interface UserMapper {
    User findUserById(int id);

    void insertUser(User user);

    void deleteUserById(int id);

    void updateUser(User user);

    // 下面两个为方便测试使用

    UserExtend selectUserWithOrders(int id);

    OrderExtend findOrdersByUserId();

    Item findItemByItemId();
}
