<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng ký</title>
        <style>
            .register-container {
                min-height: 500px;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #f5f5f5;
                padding: 20px;
            }

            .register-form {
                background: white;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-group label {
                display: block;
                margin-bottom: 8px;
                font-weight: 500;
                color: #333;
            }

            .form-group input {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 16px;
                transition: border-color 0.3s;
            }

            .form-group input:focus {
                border-color: #4CAF50;
                outline: none;
            }

            .submit-btn {
                background-color: #3498db;
                color: white;
                padding: 12px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                width: 100%;
                font-size: 16px;
                transition: background-color 0.3s;
            }

            .submit-btn:hover {
                background-color: aqua;
            }

            .form-title {
                text-align: center;
                margin-bottom: 30px;
                color: #333;
            }

            .error {
                color: red;
                font-size: 14px;
                margin-top: 5px;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="register-container">
            <div class="register-form">
                <h2 class="form-title">Đăng ký</h2>
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="register" />

                    <div class="form-group">
                        <label for="userId">Tên đăng nhập</label>
                        <input type="text" id="userId" name="txtUserID" required />
                        <% if (request.getAttribute("userId_error") != null) { %>
                            <div class="error"><%= request.getAttribute("userId_error") %></div>
                        <% } %>
                    </div>

                    <div class="form-group">
                        <label for="fullName">Họ và tên</label>
                        <input type="text" id="fullName" name="txtFullName" required />
                    </div>

                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <input type="password" id="password" name="txtPassword" required />
                    </div>

                    <div class="form-group">
                        <label for="roleId">Vai trò (USER hoặc ADMIN)</label>
                        <input type="text" id="roleId" name="txtRoleID" value="USER" required />
                    </div>

                    <button type="submit" class="submit-btn">Đăng ký</button>

                    <%
                        String message = (String) request.getAttribute("message");
                        if (message != null && !message.isEmpty()) {
                    %>
                        <div class="error"><%= message %></div>
                    <% } %>
                </form>
                <p style="text-align: center; margin-top: 10px;">
                    Đã có tài khoản? <a href="login.jsp">Đăng nhập</a>
                </p>
            </div>
        </div>
        <jsp:include page="footer.jsp"/>
    </body>

</html>