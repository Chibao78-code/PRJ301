package dao;

import dto.QuestionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

public class QuestionDAO implements IDAO<QuestionDTO, Integer> {

    @Override
    public boolean create(QuestionDTO entity) {
        String sql = "INSERT INTO tblQuestions (question_id, exam_id, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, entity.getQuestionId());
            ps.setInt(2, entity.getExamId());
            ps.setString(3, entity.getQuestionText());
            ps.setString(4, entity.getOptionA());
            ps.setString(5, entity.getOptionB());
            ps.setString(6, entity.getOptionC());
            ps.setString(7, entity.getOptionD());
            ps.setString(8, entity.getCorrectOption());
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in QuestionDAO.create: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<QuestionDTO> readAll() {
        List<QuestionDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblQuestions";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                QuestionDTO question = new QuestionDTO(
                        rs.getInt("question_id"),
                        rs.getInt("exam_id"),
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option")
                );
                list.add(question);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in QuestionDAO.readAll: " + e.getMessage());
        }
        return list;
    }

    @Override
    public QuestionDTO readById(Integer id) {
        String sql = "SELECT * FROM tblQuestions WHERE question_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new QuestionDTO(
                            rs.getInt("question_id"),
                            rs.getInt("exam_id"),
                            rs.getString("question_text"),
                            rs.getString("option_a"),
                            rs.getString("option_b"),
                            rs.getString("option_c"),
                            rs.getString("option_d"),
                            rs.getString("correct_option")
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in QuestionDAO.readById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(QuestionDTO entity) {
        String sql = "UPDATE tblQuestions SET exam_id = ?, question_text = ?, option_a = ?, option_b = ?, option_c = ?, option_d = ?, correct_option = ? WHERE question_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, entity.getExamId());
            ps.setString(2, entity.getQuestionText());
            ps.setString(3, entity.getOptionA());
            ps.setString(4, entity.getOptionB());
            ps.setString(5, entity.getOptionC());
            ps.setString(6, entity.getOptionD());
            ps.setString(7, entity.getCorrectOption());
            ps.setInt(8, entity.getQuestionId());
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in QuestionDAO.update: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM tblQuestions WHERE question_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in QuestionDAO.delete: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<QuestionDTO> search(String searchTerm) {
        List<QuestionDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblQuestions WHERE question_text LIKE ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + searchTerm + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    QuestionDTO question = new QuestionDTO(
                            rs.getInt("question_id"),
                            rs.getInt("exam_id"),
                            rs.getString("question_text"),
                            rs.getString("option_a"),
                            rs.getString("option_b"),
                            rs.getString("option_c"),
                            rs.getString("option_d"),
                            rs.getString("correct_option")
                    );
                    list.add(question);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in QuestionDAO.search: " + e.getMessage());
        }
        return list;
    }

    public List<QuestionDTO> getQuestionsByExam(int examId) {
        List<QuestionDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblQuestions WHERE exam_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, examId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    QuestionDTO question = new QuestionDTO(
                            rs.getInt("question_id"),
                            rs.getInt("exam_id"),
                            rs.getString("question_text"),
                            rs.getString("option_a"),
                            rs.getString("option_b"),
                            rs.getString("option_c"),
                            rs.getString("option_d"),
                            rs.getString("correct_option")
                    );
                    list.add(question);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in QuestionDAO.getQuestionsByExam: " + e.getMessage());
        }
        return list;
    }
}