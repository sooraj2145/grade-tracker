package com.gradetracker.dao;

import com.gradetracker.model.Grade;
import com.gradetracker.util.DBUtil;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    private Grade mapRow(ResultSet rs) throws SQLException {
        Grade grade = new Grade();
        grade.setId(rs.getInt("id"));
        grade.setStudentId(rs.getInt("student_id"));
        grade.setSubjectId(rs.getInt("subject_id"));
        grade.setStudentName(rs.getString("student_name"));
        grade.setSubjectName(rs.getString("subject_name"));
        grade.setMarks(rs.getDouble("marks"));
        grade.setGrade(rs.getString("grade"));
        grade.setRemarks(rs.getString("remarks"));

        return grade;
    }

    public boolean addGrade(Grade grade) {
        String sql = """
            INSERT INTO grades
            (student_id, subject_id, marks, grade, remarks)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,grade.getStudentId());
            stmt.setInt(2, grade.getSubjectId());
            stmt.setDouble(3, grade.getMarks());
            stmt.setString(4, grade.getGrade());
            stmt.setString(5, grade.getRemarks());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Grade getGradeById(Integer id) {
        String sql = """
                SELECT 
                    g.id,
                    g.student_id,
                    CONCAT(s.first_name,' ',s.last_name) AS student_name,
                    g.subject_id,
                    sub.name AS subject_name,
                    g.marks,
                    g.grade,
                    g.remarks
                    FROM grades g
                    JOIN students s
                        ON g.student_id = s.id
                    JOIN subjects sub
                        ON g.subject_id = sub.id   
                        WHERE g.id = ? 
                """;
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt= conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    public List<Grade> getGrades(
            int offset,
            int pageSize,
            String sortBy,
            String direction){

        sortBy = validateSortColumn(sortBy);
        direction = validateSortDirection(direction);
        List<Grade> grades = new ArrayList<>();

        String sql = """
                SELECT 
                    g.id,
                    g.student_id,
                    CONCAT(s.first_name,' ',s.last_name) AS student_name,
                    g.subject_id,
                    sub.name AS subject_name,
                    g.marks,
                    g.grade,
                    g.remarks
                    FROM grades g
                    JOIN students s
                        ON g.student_id = s.id
                    Join subjects sub
                        ON g.subject_id = sub.id
                    ORDER BY %s %s
                    LIMIT ?, ?        
                """.formatted(sortBy, direction);

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
             stmt.setInt(1,offset);
             stmt.setInt(2,pageSize);

             try(ResultSet rs = stmt.executeQuery()){
                 while(rs.next()){
                     grades.add(mapRow(rs));
                 }
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    private String validateSortColumn(String sortBy) {
        return switch (sortBy) {
            case "id" -> "g.id";
            case "marks" -> "g.marks";
            case "grade" -> "g.grade";
            case "student_name" -> "s.first_name";
            case "subject_name" -> "sub.name";
            default -> "g.id";
        };
    }

    private String validateSortDirection(String direction) {
        return "desc".equalsIgnoreCase(direction)
                ? "DESC"
                : "ASC";
    }

    public boolean updateGrade(Grade grade) {
        String sql = """
                UPDATE grades
                SET student_id = ?, subject_id = ?, marks = ?, grade = ?, remarks = ?
                WHERE id = ?
        """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, grade.getStudentId());
            stmt.setInt(2, grade.getSubjectId());
            stmt.setDouble(3, grade.getMarks());
            stmt.setString(4, grade.getGrade());
            stmt.setString(5, grade.getRemarks());
            stmt.setInt(6, grade.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteGradeById(Integer id) {
        String sql = """
                DELETE FROM grades
                WHERE id = ?
        """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql) ){
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getGradeCount(){
        String sql = """
                SELECT COUNT(*) FROM grades
        """;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public int getGradeCount(String keyword){
       String sql= """
               SELECT COUNT(*)
               FROM grades g
               JOIN students s
                ON g.student_id = s.id
               JOIN subjects sub
               ON g.subject_id = sub.id
               WHERE CONCAT(s.first_name,' ',s.last_name) LIKE ?
                OR sub.name LIKE ?
                OR g.grade LIKE ?
                OR g.remarks LIKE ? ;  
               """;

        String search = "%"+keyword+"%";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, search);
            stmt.setString(2, search);
            stmt.setString(3, search);
            stmt.setString(4, search);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;


    }

    public List<Grade> searchGrades(
            String keyword,
            int offset,
            int pageSize,
            String sortBy,
            String direction){

        sortBy = validateSortColumn(sortBy);
        direction = validateSortDirection(direction);

        String search = "%"+keyword+"%";

        List<Grade> grades = new ArrayList<>();

        String sql = """
                SELECT 
                    g.id,
                    g.student_id,
                    CONCAT(s.first_name,' ',s.last_name) AS student_name,
                    g.subject_id,
                    sub.name AS subject_name,
                    g.marks,
                    g.grade,
                    g.remarks
                    FROM grades g
                    JOIN students s
                    ON g.student_id = s.id
                    JOIN subjects sub
                    ON g.subject_id = sub.id
                    WHERE CONCAT(s.first_name,' ',s.last_name) LIKE ?
                    OR sub.name LIKE ?
                    OR g.grade LIKE ?
                    OR g.remarks LIKE ?
                    ORDER BY %s %s
                    LIMIT ?, ?
                """.formatted(sortBy, direction);

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, search);
                stmt.setString(2, search);
                stmt.setString(3, search);
                stmt.setString(4, search);
                stmt.setInt(5, offset);
                stmt.setInt(6, pageSize);

                try(ResultSet rs = stmt.executeQuery()){
                    while(rs.next()){
                        grades.add(mapRow(rs));
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }


}
