package dao;

import dto.ProjectDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utlis.DBUtils;

/**
 * Data Access Object for managing Project entities
 */
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
            ps.setDate(5, entity.getLaunchDate() != null ? new java.sql.Date(entity.getLaunchDate().getTime()) : null);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in ProjectDAO.create: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<ProjectDTO> readAll() {
        List<ProjectDTO> projects = new ArrayList<>();
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
                    rs.getDate("launchDate") // Lấy java.sql.Date và tự động chuyển sang java.util.Date
                );
                projects.add(project);
            }
            System.out.println("Loaded " + projects.size() + " projects from database.");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in ProjectDAO.readAll: " + ex.getMessage());
        }
        return projects;
    }

    @Override
    public ProjectDTO readById(String id) {
        String sql = "SELECT * FROM tblProjects WHERE projectID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProjectDTO(
                        rs.getString("projectID"),
                        rs.getString("projectName"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("launchDate") // Lấy java.sql.Date
                    );
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in ProjectDAO.readById: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(ProjectDTO entity) {
        String sql = "UPDATE tblProjects SET projectName = ?, description = ?, status = ?, launchDate = ? WHERE projectID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getProjectName());
            ps.setString(2, entity.getDescription());
            ps.setString(3, entity.getStatus());
            ps.setDate(4, entity.getLaunchDate() != null ? new java.sql.Date(entity.getLaunchDate().getTime()) : null);
            ps.setString(5, entity.getProjectID());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in ProjectDAO.update: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM tblProjects WHERE projectID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in ProjectDAO.delete: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<ProjectDTO> search(String searchTerm) {
        List<ProjectDTO> projects = new ArrayList<>();
        String sql = "SELECT * FROM tblProjects WHERE projectName LIKE ? OR description LIKE ? OR status LIKE ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProjectDTO project = new ProjectDTO(
                        rs.getString("projectID"),
                        rs.getString("projectName"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("launchDate")
                    );
                    projects.add(project);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in ProjectDAO.search: " + ex.getMessage());
        }
        return projects;
    }
}