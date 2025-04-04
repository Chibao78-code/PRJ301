<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quên mật khẩu</title>
        <style>
            .forgot-container {
                min-height: 500px;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #f5f5f5;
                padding: 20px;
            }

            .forgot-form {
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

            .message {
                color: green;
                font-size: 14px;
                text-align: center;
                margin-bottom: 15px;
            }

            .error {
                color: red;
                font-size: 14px;
                text-align: center;
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="forgot-container">
            <div class="forgot-form">
                <h2 class="form-title">Quên mật khẩu</h2>
                <%
                    String message = (String) request.getAttribute("message");
                    if (message != null && !message.isEmpty()) {
                        if (message.contains("thành công")) {
                %>
                    <div class="message"><%= message %></div>
                <%
                        } else {
                %>
                    <div class="error"><%= message %></div>
                <%
                        }
                    }
                %>
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="forgotPassword" />

                    <div class="form-group">
                        <label for="userId">Tên đăng nhập</label>
                        <input type="text" id="userId" name="txtUserID" required />
                    </div>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="txtEmail" required />
                    </div>

                    <button type="submit" class="submit-btn">Gửi yêu cầu</button>
                </form>
            </div>
        </div>
        <jsp:include page="footer.jsp"/>
    </body>
    
</html>