package dao;

import dto.ProjectDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.DBUtils;

public class ProjectDAO implements IDAO<ProjectDTO, String> {

    @Override
    public boolean create(ProjectDTO entity) {
        String sql = "INSERT INTO tblProjects (projectID, projectName, description, status, launchDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getProjectID());
            ps.setString(2, entity.getProjectName());
            ps.setString(3, entity.getDescription());
            ps.setString(4, entity.getStatus());
            ps.setDate(5, new java.sql.Date(entity.getLaunchDate() != null ? entity.getLaunchDate().getTime() : new Date().getTime()));
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error in ProjectDAO.create: " + e.toString());
            return false;
        }
    }

    @Override
    public List<ProjectDTO> readAll() {
        List<ProjectDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblProjects";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProjectDTO project = new ProjectDTO(
                    rs.getString("projectID"),
                    rs.getString("projectName"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getDate("launchDate")
                );
                list.add(project);
            }
            System.out.println("Loaded " + list.size() + " projects from database.");
        } catch (Exception e) {
            System.out.println("Error in ProjectDAO.readAll: " + e.toString());
        }
        return list;
    }

    @Override
    public ProjectDTO readById(String id) {
        // Đã có trong mã trước, giữ nguyên
        return null;
    }

    @Override
    public boolean update(ProjectDTO entity) {
        // Đã có trong mã trước, giữ nguyên
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<ProjectDTO> search(String searchTerm) {
        return null;
    }
}