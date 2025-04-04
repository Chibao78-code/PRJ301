<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exam Dashboard - Online Examination System</title>
    <style>
        /* CSS cho dashboard */
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            padding-top: 60px; 
           
        }
        .content {
            padding: 20px;
            min-height: 100vh;
        }
        h2, h3 {
            color: #2c3e50;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #3498db;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        a {
            color: #3498db;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="content">
        <h2>Exam Dashboard</h2>
        <p>Welcome, ${sessionScope.user.name}!</p>

        <h3>Exam Categories</h3>
        <table>
            <tr>
                <th>Category Name</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
            <c:forEach var="category" items="${categories}">
                <tr>
                    <td>${category.categoryName}</td>
                    <td>${category.description}</td>
                    <td>
                        <a href="MainController?action=viewExamsByCategory&categoryId=${category.categoryId}">View Exams</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <h3>Exams</h3>
        <c:if test="${not empty exams}">
            <table>
                <tr>
                    <th>Exam Title</th>
                    <th>Subject</th>
                    <th>Total Marks</th>
                    <th>Duration (minutes)</th>
                </tr>
                <c:forEach var="exam" items="${exams}">
                    <tr>
                        <td>${exam.examTitle}</td>
                        <td>${exam.subject}</td>
                        <td>${exam.totalMarks}</td>
                        <td>${exam.duration}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty exams}">
            <p class="error">No exams available for this category.</p>
        </c:if>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>