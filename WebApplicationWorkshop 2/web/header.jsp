<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Online Examination System</title>
    <style>
        /* CSS cho header */
        header {
            background-color: #2c3e50;
            color: white;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            position: fixed; 
            top: 0;
            width: 100%; 
            box-sizing: border-box; 
            z-index: 1000; 
        }
        .logo {
            font-size: 24px;
            font-weight: bold;
            color: #ecf0f1;
            margin-right: 20px; 
        }
        .nav-menu {
            list-style: none;
            display: flex;
            margin: 0;
            padding: 0;
            flex-grow: 1; 
            justify-content: flex-start; 
        }
        .nav-menu li {
            margin-right: 20px; 
        }
        .nav-menu li a {
            color: white;
            text-decoration: none;
            font-size: 16px;
            transition: color 0.3s;
        }
        .nav-menu li a:hover {
            color: #3498db;
        }
        .user-info {
            color: #ecf0f1;
            font-size: 14px;
            margin-left: 20px; 
            white-space: nowrap; 
        }
    </style>
</head>
<body>
    <header>
        <div class="logo">Online Exam</div>
        <ul class="nav-menu">
            <li><a href="MainController?action=login">Home</a></li>
            <li><a href="MainController?action=viewExamsByCategory&categoryId=1">Dashboard</a></li>
            <li><a href="MainController?action=logout">Logout</a></li>
        </ul>
        <div class="user-info">
            <% if (session.getAttribute("user") != null) { %>
                Welcome, <%= ((dto.UserDTO) session.getAttribute("user")).getName() %>!
            <% } else { %>
                Guest
            <% } %>
        </div>
    </header>
</body>
</html>