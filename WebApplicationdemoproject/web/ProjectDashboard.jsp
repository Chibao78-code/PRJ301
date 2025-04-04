<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dto.ProjectDTO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h2 { color: #333; }
        .user-info { margin-bottom: 20px; color: #0066cc; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .action-btn { margin: 5px; text-decoration: none; color: #007bff; }
        .message { color: green; }
        .no-data { color: #888; text-align: center; }
    </style>
</head>
<body>
    <h2>Project Dashboard</h2>
    <div class="user-info">
        Welcome, <%= request.getAttribute("loggedInUser") %> | <a href="MainController?action=logout">Logout</a>
    </div>
    <p class="message"><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %></p>
    
    <!-- Nút tạo dự án mới (chỉ cho Admin) -->
    <% 
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null && "AD".equals(user.getRoleID())) { 
    %>
        <a href="projectForm.jsp" class="action-btn">Create New Project</a>
    <% } %>

    <!-- Bảng danh sách dự án -->
    <h3>Startup Projects</h3>
    <table>
        <tr>
            <th>Project ID</th>
            <th>Project Name</th>
            <th>Description</th>
            <th>Status</th>
            <th>Estimated Launch</th>
            <th>Action</th>
        </tr>
        <% 
            List<ProjectDTO> projects = (List<ProjectDTO>) request.getAttribute("projects");
            if (projects == null || projects.isEmpty()) {
        %>
            <tr><td colspan="6" class="no-data">No projects available.</td></tr>
        <% 
            } else {
                for (ProjectDTO project : projects) {
        %>
            <tr>
                <td><%= project.getProjectID() != null ? project.getProjectID() : "N/A" %></td>
                <td><%= project.getProjectName() != null ? project.getProjectName() : "N/A" %></td>
                <td><%= project.getDescription() != null ? project.getDescription() : "N/A" %></td>
                <td><%= project.getStatus() != null ? project.getStatus() : "N/A" %></td>
                <td><%= project.getLaunchDate() != null ? new SimpleDateFormat("yyyy-MM-dd").format(project.getLaunchDate()) : "N/A" %></td>
                <% if (user != null && "AD".equals(user.getRoleID())) { %>
                    <td>
                        <form action="MainController" method="POST" style="display:inline;">
                            <input type="hidden" name="action" value="updateProjectStatus" />
                            <input type="hidden" name="projectID" value="<%= project.getProjectID() %>" />
                            <select name="newStatus">
                                <option value="Pending" <%= "Pending".equals(project.getStatus()) ? "selected" : "" %>>Pending</option>
                                <option value="Development" <%= "Development".equals(project.getStatus()) ? "selected" : "" %>>Development</option>
                                <option value="Launch" <%= "Launch".equals(project.getStatus()) ? "selected" : "" %>>Launch</option>
                            </select>
                            <input type="submit" value="Update Status" />
                        </form>
                    </td>
                <% } else { %>
                    <td>N/A</td>
                <% } %>
            </tr>
        <% 
                }
            }
        %>
    </table>
</body>
</html>