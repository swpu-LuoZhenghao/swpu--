package com.example.ceshi.dao;

import com.example.ceshi.model.User;

import java.util.List;

/**
 * 用户数据访问接口
 */
public interface UserDao {
    boolean register(User user);
    List<User> getAllUsers();
    boolean delete(String name);
    boolean update(String name, String address, String phone);
}
