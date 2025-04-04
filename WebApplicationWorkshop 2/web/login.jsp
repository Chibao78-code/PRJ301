<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Online Examination System</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            padding-top: 60px; 
            display: flex;
            flex-direction: column;
            min-height: 100vh; 
        }
        .content {
            flex: 1; 
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        .login-container {
            max-width: 400px;
            width: 100%;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            
            text-align: center;
            background-color: #f9f9f9;
        }
        .login-container input[type="text"],
        .login-container input[type="password"] {
            width: 80%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .login-container input[type="submit"] {
            width: 80%;
            padding: 10px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .login-container input[type="submit"]:hover {
            background-color: #2980b9;
        }
        .error {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="content">
        <div class="login-container">
            <h2>Login</h2>
            <form action="MainController" method="POST">
                <input type="hidden" name="action" value="login">
                <div>
                    <label>Username:</label><br>
                    <input type="text" name="txtUsername" required>
                </div>
                <div>
                    <label>Password:</label><br>
                    <input type="password" name="txtPassword" required>
                </div>
                <input type="submit" value="Login">
            </form>
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>