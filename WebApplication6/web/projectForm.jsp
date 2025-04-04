<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.ProjectDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create New Project</title>
    <style>
        .error { color: red; }
    </style>
</head>
<body>
    <h2>Create New Project</h2>
    <form action="MainController" method="POST">
        <input type="hidden" name="action" value="createProject" />
        <table>
            <tr>
                <td>Project ID:</td>
                <td><input type="text" name="txtProjectID" value="${project.projectID}" /></td>
                <td><span class="error">${txtProjectID_error}</span></td>
            </tr>
            <tr>
                <td>Project Name:</td>
                <td><input type="text" name="txtProjectName" value="${project.projectName}" /></td>
                <td><span class="error">${txtProjectName_error}</span></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><textarea name="txtDescription">${project.description}</textarea></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td>
                    <select name="txtStatus">
                        <option value="Pending" ${project.status == 'Pending' ? 'selected' : ''}>Pending</option>
                        <option value="In Progress" ${project.status == 'In Progress' ? 'selected' : ''}>In Progress</option>
                        <option value="Completed" ${project.status == 'Completed' ? 'selected' : ''}>Completed</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Launch Date:</td>
                <td><input type="date" name="txtLaunchDate" value="${project.launchDate != null ? sdf.format(project.launchDate) : ''}" /></td>
                <td><span class="error">${txtLaunchDate_error}</span></td>
            </tr>
            <tr>
                <td><input type="submit" value="Create Project" /></td>
            </tr>
        </table>
    </form>
    <p>${message}</p>
    <a href="MainController?action=search">Back to Search</a>
</body>
</html>