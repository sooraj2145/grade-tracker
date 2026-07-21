package com.gradetracker.dao;

import com.gradetracker.model.Student;
import com.gradetracker.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private Student mapRow(ResultSet rs) throws SQLException {
        Student student = new Student();

        student.setId(rs.getInt("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setEmail(rs.getString("email"));
        student.setDepartment(rs.getString("department"));
        student.setSemester(rs.getInt("semester"));

        return student;
    }

    public boolean addStudent(Student student) {

        String sql = """
                INSERT INTO students
                (first_name, last_name, email, department, semester)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getDepartment());
            stmt.setInt(5, student.getSemester());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Student getStudentById(int id) {

        String sql = """
                SELECT
                    id,
                    first_name,
                    last_name,
                    email,
                    department,
                    semester
                FROM students
                WHERE id = ?
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapRow(rs);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Student> getStudents(int offset, int limit, String sortBy, String direction) {

        List<Student> students = new ArrayList<>();


        String sql = """
                SELECT
                    id,
                    first_name,
                    last_name,
                    email,
                    department,
                    semester
                FROM students
                ORDER BY %s %s
                LIMIT ?
                OFFSET ?
                """.formatted(sortBy, direction);

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    students.add(mapRow(rs));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = """
                SELECT * FROM students ORDER BY first_name, last_name;
        """;
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                students.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean updateStudent(Student student) {

        String sql = """
                UPDATE students
                SET first_name = ?,
                    last_name = ?,
                    email = ?,
                    department = ?,
                    semester = ?
                WHERE id = ?
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getDepartment());
            stmt.setInt(5, student.getSemester());
            stmt.setInt(6, student.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudentById(int id) {

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> searchStudents(String keyword,
                                        int offset,
                                        int limit,
                                        String sortBy,
                                        String direction) {

        List<Student> students = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    first_name,
                    last_name,
                    email,
                    department,
                    semester
                FROM students
                WHERE first_name LIKE ?
                   OR last_name LIKE ?
                   OR email LIKE ?
                   OR department LIKE ?
                ORDER BY %s %s
                LIMIT ?
                OFFSET ?
                """.formatted(sortBy, direction);

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword.trim() + "%";

            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);
            stmt.setString(4, searchKeyword);
            stmt.setInt(5, limit);
            stmt.setInt(6, offset);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    students.add(mapRow(rs));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public int getStudentCount() {

        String sql = """
                SELECT COUNT(*) AS total
                FROM students
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getStudentCount(String keyword) {

        String sql = """
                SELECT COUNT(*) AS total
                FROM students
                WHERE first_name LIKE ?
                   OR last_name LIKE ?
                   OR email LIKE ?
                   OR department LIKE ?
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword.trim() + "%";

            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);
            stmt.setString(4, searchKeyword);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("total");
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}