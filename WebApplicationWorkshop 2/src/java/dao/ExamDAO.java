package dao;

import dto.ExamDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

public class ExamDAO implements IDAO<ExamDTO, Integer> {

    @Override
    public boolean create(ExamDTO entity) {
        String sql = "INSERT INTO tblExams (exam_id, exam_title, subject, category_id, total_marks, duration) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, entity.getExamId());
            ps.setString(2, entity.getExamTitle());
            ps.setString(3, entity.getSubject());
            ps.setInt(4, entity.getCategoryId());
            ps.setInt(5, entity.getTotalMarks());
            ps.setInt(6, entity.getDuration());
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in ExamDAO.create: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<ExamDTO> readAll() {
        List<ExamDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblExams";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ExamDTO exam = new ExamDTO(
                        rs.getInt("exam_id"),
                        rs.getString("exam_title"),
                        rs.getString("subject"),
                        rs.getInt("category_id"),
                        rs.getInt("total_marks"),
                        rs.getInt("duration")
                );
                list.add(exam);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in ExamDAO.readAll: " + e.getMessage());
        }
        return list;
    }

    @Override
    public ExamDTO readById(Integer id) {
        String sql = "SELECT * FROM tblExams WHERE exam_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ExamDTO(
                            rs.getInt("exam_id"),
                            rs.getString("exam_title"),
                            rs.getString("subject"),
                            rs.getInt("category_id"),
                            rs.getInt("total_marks"),
                            rs.getInt("duration")
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in ExamDAO.readById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(ExamDTO entity) {
        String sql = "UPDATE tblExams SET exam_title = ?, subject = ?, category_id = ?, total_marks = ?, duration = ? WHERE exam_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getExamTitle());
            ps.setString(2, entity.getSubject());
            ps.setInt(3, entity.getCategoryId());
            ps.setInt(4, entity.getTotalMarks());
            ps.setInt(5, entity.getDuration());
            ps.setInt(6, entity.getExamId());
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in ExamDAO.update: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM tblExams WHERE exam_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in ExamDAO.delete: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<ExamDTO> search(String searchTerm) {
        List<ExamDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblExams WHERE exam_title LIKE ? OR subject LIKE ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ExamDTO exam = new ExamDTO(
                            rs.getInt("exam_id"),
                            rs.getString("exam_title"),
                            rs.getString("subject"),
                            rs.getInt("category_id"),
                            rs.getInt("total_marks"),
                            rs.getInt("duration")
                    );
                    list.add(exam);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in ExamDAO.search: " + e.getMessage());
        }
        return list;
    }

    public List<ExamDTO> getExamsByCategory(int categoryId) {
        List<ExamDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblExams WHERE category_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ExamDTO exam = new ExamDTO(
                            rs.getInt("exam_id"),
                            rs.getString("exam_title"),
                            rs.getString("subject"),
                            rs.getInt("category_id"),
                            rs.getInt("total_marks"),
                            rs.getInt("duration")
                    );
                    list.add(exam);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in ExamDAO.getExamsByCategory: " + e.getMessage());
        }
        return list;
    }
}