package com.example.ceshi.dao;

import com.example.ceshi.model.User;
import com.example.ceshi.util.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据访问实现类
 */
public class UserDaoImpl implements UserDao {
    private static final String url = DBConfig.getURL();
    private static final String username = DBConfig.getUsername();
    private static final String password = DBConfig.getPassword();

    @Override
    public boolean register(User user) {
        // 注册用户到数据库
        String sql = "INSERT INTO contacts (name, address, phone) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getAddress());
            pstmt.setString(3, user.getPhone());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        // 从数据库获取所有用户信息
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM contacts";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public boolean delete(String name) {
        // 从数据库删除用户
        String sql = "DELETE FROM contacts WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(String name, String address, String phone) {
        // 更新数据库中的用户信息
        String sql = "UPDATE contacts SET address = ?, phone = ? WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, address);
            pstmt.setString(2, phone);
            pstmt.setString(3, name);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
