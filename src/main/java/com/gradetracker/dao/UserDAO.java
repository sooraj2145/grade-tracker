package com.gradetracker.dao;

import com.gradetracker.enums.Role;
import com.gradetracker.model.User;
import com.gradetracker.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDAO {

    private static final String FIND_BY_USERNAME = """
            SELECT *
            FROM users 
            WHERE username = ?
            """;

    public User findByUsername(String username) {
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(FIND_BY_USERNAME)){

            stmt.setString(1, username);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return mapRow(rs);
                }
            }


        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setRole(Role.valueOf(rs.getString("role")));
        user.setActive(rs.getBoolean("is_active"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));

        return user;

    }

    public List<User> findAll(){

        List<User> users = new ArrayList<>();

        String  sql = """
                SELECT *
                FROM users
                ORDER BY id
        """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                users.add(mapRow(rs));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return users;
    }



    public User findById(Integer id){

        if(id == null) return null;

        String sql = """
                    SELECT * 
                    FROM users
                    WHERE id = ?
                """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ){
                stmt.setInt(1, id);

                try(ResultSet rs = stmt.executeQuery()){
                    if(rs.next()){
                        return mapRow(rs);
                    }
                }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean save(User user) {

        String sql = """
                    INSERT INTO users
                    (
                        username,
                        password,
                        full_name,
                        email,
                        role,
                        is_active
                    )
                    VALUES (?, ?, ?, ?, ?, ?)
        """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getRole().name());
            stmt.setBoolean(6, user.getActive());

            return stmt.executeUpdate() > 0;

        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(User user) {

        String sql = """
                UPDATE users
                SET 
                    username = ?,
                    full_name = ?,
                    email = ?,
                    role = ?,
                    is_active = ?
                WHERE id = ?    
                """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole().name());
            stmt.setBoolean(5, user.getActive());
            stmt.setInt(6, user.getId());

            return stmt.executeUpdate() > 0;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deactivate(Integer id) {

        if(id == null) return false;
        String sql = """
                    UPDATE users
                    SET is_active = false
                    WHERE id = ?
        """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql) ){

            stmt.setInt(1, id);

            return stmt.executeUpdate() >0;

        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;

    }

    public boolean updatePassword(Integer id, String hashedPassword){

        if(id == null || hashedPassword == null || hashedPassword.isBlank()) return false;

        String sql = """
                    UPDATE users
                    SET password = ?
                    WHERE id = ?
        """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setString(1,hashedPassword);
                stmt.setInt(2, id);

                return stmt.executeUpdate() > 0;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByUsername(String username) {

        String sql = """
                    SELECT 1
                    FROM users
                    WHERE username = ?
                    LIMIT 1;
                """;

        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);

            try(ResultSet rs = stmt.executeQuery()){
                return rs.next();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByEmail(String email) {
        String sql = """
                    SELECT 1
                    FROM users
                    WHERE email = ?
                    LIMIT 1;
        """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql) ){

            stmt.setString(1, email);

            try(ResultSet rs = stmt.executeQuery()){
                return rs.next();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByUsernameExceptId(String username, Integer id) {
        String sql = """
                    SELECT 1
                    FROM users
                    WHERE username = ?
                     AND id <> ?
                     LIMIT 1;
                """;

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql) ){

                stmt.setString(1, username);
                stmt.setInt(2, id);

                try(ResultSet rs = stmt.executeQuery()){
                    return rs.next();
                }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByEmailExceptId(String email, Integer id) {
        String sql = """
                    SELECT 1
                    FROM users
                    WHERE email = ?
                    AND id <> ?
                    LIMIT 1;
        """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql) ){
             stmt.setString(1, email);
             stmt.setInt(2, id);

             try(ResultSet rs = stmt.executeQuery()){
                 return rs.next();
             }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;

    }




}
