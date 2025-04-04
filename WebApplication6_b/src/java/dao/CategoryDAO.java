package dao;

import dto.CategoryDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

public class CategoryDAO implements IDAO<CategoryDTO, Integer> {

    @Override
    public boolean create(CategoryDTO entity) {
        String sql = "INSERT INTO tblExamCategories (category_id, category_name, description) VALUES (?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, entity.getCategoryId());
            ps.setString(2, entity.getCategoryName());
            ps.setString(3, entity.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error in CategoryDAO.create: " + e.toString());
            return false;
        }
    }

    @Override
    public List<CategoryDTO> readAll() {
        List<CategoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblExamCategories";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CategoryDTO category = new CategoryDTO(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description")
                );
                list.add(category);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error in CategoryDAO.readAll: " + e.toString());
        }
        return list;
    }

    @Override
    public CategoryDTO readById(Integer id) {
        String sql = "SELECT * FROM tblExamCategories WHERE category_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CategoryDTO(
                            rs.getInt("category_id"),
                            rs.getString("category_name"),
                            rs.getString("description")
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error in CategoryDAO.readById: " + e.toString());
        }
        return null;
    }

    @Override
    public boolean update(CategoryDTO entity) {
        String sql = "UPDATE tblExamCategories SET category_name = ?, description = ? WHERE category_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getCategoryName());
            ps.setString(2, entity.getDescription());
            ps.setInt(3, entity.getCategoryId());
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error in CategoryDAO.update: " + e.toString());
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM tblExamCategories WHERE category_id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error in CategoryDAO.delete: " + e.toString());
            return false;
        }
    }

    @Override
    public List<CategoryDTO> search(String searchTerm) {
        List<CategoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblExamCategories WHERE category_name LIKE ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + searchTerm + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryDTO category = new CategoryDTO(
                            rs.getInt("category_id"),
                            rs.getString("category_name"),
                            rs.getString("description")
                    );
                    list.add(category);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error in CategoryDAO.search: " + e.toString());
        }
        return list;
    }
}