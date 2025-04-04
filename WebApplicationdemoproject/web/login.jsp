<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập</title>
        <style>
            .login-container {
                min-height: 500px;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #f5f5f5;
                padding: 20px;
            }

            .login-form {
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

            .register-link {
                text-align: center;
                margin-top: 15px;
                font-size: 14px;
            }

            .register-link a {
                color: #3498db;
                text-decoration: none;
            }

            .register-link a:hover {
                text-decoration: underline;
            }

            .error {
                color: red;
                font-size: 14px;
                text-align: center;
                margin-bottom: 15px;
            }

            .success {
                color: green;
                font-size: 14px;
                text-align: center;
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="login-container">
            <div class="login-form">
                <h2 class="form-title">Đăng nhập</h2>
                <%                    String message = (String) request.getAttribute("message");
                    if (message != null && !message.isEmpty()) {
                        if (message.contains("thành công")) {
                %>
                <div class="success"><%= message%></div>
                <%
                } else {
                %>
                <div class="error"><%= message%></div>
                <%
                        }
                    }
                %>
                <form action="MainController" method="post">                    
                    <input type="hidden" name="action" value="login" />

                    <div class="form-group">
                        <label for="userId">Tên đăng nhập</label>
                        <input type="text" id="userId" name="txtUserID" required onblur="this.value = this.value.trim();" />
                    </div>

                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <input type="password" id="password" name="txtPassword" required onblur="this.value = this.value.trim();" />
                    </div>

                    <button type="submit" class="submit-btn">Đăng nhập</button>

                    <div class="register-link">
                        Chưa có tài khoản? <a href="register.jsp">Đăng ký ngay</a>
                    </div>
                    <div class="forgot-password-link" style="text-align: center; margin-top: 10px;">
                        <a href="forgotPassword.jsp">Quên mật khẩu?</a>
                    </div>
                </form>
            </div>
        </div>
               
        <jsp:include page="footer.jsp"/>
    </body>
</html>