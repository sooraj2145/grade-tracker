package com.gradetracker.dao;

import com.gradetracker.model.Subject;
import com.gradetracker.util.DBUtil;
import org.eclipse.tags.shaded.org.apache.regexp.RE;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    private Subject mapRow(ResultSet rs) throws SQLException {
        Subject subject = new Subject();
        subject.setId(rs.getInt("id"));
        subject.setCode(rs.getString("code"));
        subject.setName(rs.getString("name"));
        subject.setCredits(rs.getInt("credits"));
        subject.setSemester(rs.getInt("semester"));
        return subject;
    }

    public boolean addSubject(Subject subject) {
        String sql = "INSERT INTO subjects(code,name,credits, semester) VALUES (?, ?, ?, ?)";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, subject.getCode());
            stmt.setString(2, subject.getName());
            stmt.setInt(3,subject.getCredits());
            stmt.setInt(4, subject.getSemester());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Subject getSubjectById(int id) {
        String sql = "SELECT id,code,name,credits,semester FROM subjects WHERE id = ?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql) ){

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return mapRow(rs);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();

        }
        return null;
    }

    public List<Subject> getSubjects(int offset, int limit, String sortBy, String direction) {
        String sql = """
                    SELECT id,code,name,credits,semester
                    FROM subjects
                    ORDER BY %s %s
                    LIMIT ?
                    OFFSET ?
                """.formatted(sortBy, direction);

        List<Subject> subjects = new ArrayList<>();
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    subjects.add(mapRow(rs));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();

        }
        return subjects;
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = """
                    SELECT *  FROM subjects ORDER BY name; 
                """;
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                subjects.add(mapRow(rs));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return subjects;
    }

    public boolean updateSubject(Subject subject) {
        String sql = """
                UPDATE subjects 
                SET 
                code = ?,
                 name = ?,
                  credits = ?,
                   semester = ?
                   WHERE id = ?
                """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, subject.getCode());
            stmt.setString(2, subject.getName());
            stmt.setInt(3, subject.getCredits());
            stmt.setInt(4,subject.getSemester());
            stmt.setInt(5, subject.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSubjectById(int id) {
        String sql = "DELETE FROM subjects WHERE id = ?";
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getSubjectCount() {
        String sql = """
                SELECT COUNT(*) AS total 
                FROM subjects
                """;
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            if(rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public int getSubjectCount(String keyword){
        String sql = """
                SELECT COUNT(*) AS total
                FROM subjects
                WHERE code LIKE ?
                OR name LIKE ?
                
                """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            String searchKeyword = "%" + keyword.trim() + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2,searchKeyword);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public List<Subject> searchSubjects(String keyword, int offset, int limit, String sortBy, String direction) {
        List<Subject> subjects = new ArrayList<>();
        String sql = """
                    SELECT 
                        id,
                        code,
                        name,
                        credits,
                        semester
                    FROM subjects
                    WHERE code LIKE ?
                    OR name LIKE ?
                    ORDER BY %s %s
                    LIMIT ?
                    OFFSET ?
                """.formatted(sortBy, direction);

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            String searchKeyword = "%" + keyword.trim() + "%";

            stmt.setString(1,searchKeyword);
            stmt.setString(2,searchKeyword);
            stmt.setInt(3, limit);
            stmt.setInt(4, offset);

            try (ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    subjects.add(mapRow(rs));
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return subjects;
    }
}
