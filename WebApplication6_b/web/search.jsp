<%@page import="utils.AuthUtils"%>
<%@page import="dto.BookDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách sách</title>
        <style>
            /* Giữ nguyên CSS từ mã gốc */
            .book-table {
                width: 100%;
                border-collapse: collapse;
                margin: 25px 0;
                font-size: 14px;
                font-family: Arial, sans-serif;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            }
            .book-table thead th {
                background-color: #009879;
                color: #ffffff;
                text-align: left;
                font-weight: bold;
                padding: 12px 15px;
            }
            .book-table tbody tr {
                border-bottom: 1px solid #dddddd;
            }
            .book-table tbody tr:nth-of-type(even) {
                background-color: #f3f3f3;
            }
            .book-table tbody tr:last-of-type {
                border-bottom: 2px solid #009879;
            }
            .book-table tbody td {
                padding: 12px 15px;
            }
            .book-table tbody tr:hover {
                background-color: #f5f5f5;
                transition: 0.3s ease;
            }
            .search-section {
                background-color: #fff;
                border-radius: 8px;
                padding: 20px;
                margin-bottom: 20px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                display: flex;
                align-items: center;
            }
            .search-section form {
                display: flex;
                align-items: center;
                flex-grow: 1;
            }
            .search-section label {
                margin-right: 10px;
                font-weight: bold;
                color: #333;
            }
            .search-input {
                flex-grow: 1;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 14px;
                margin-right: 10px;
                transition: border-color 0.3s;
            }
            .search-input:focus {
                border-color: #009879;
                outline: none;
                box-shadow: 0 0 0 2px rgba(0, 152, 121, 0.2);
            }
            .search-btn {
                background-color: #009879;
                color: white;
                border: none;
                border-radius: 4px;
                padding: 10px 15px;
                cursor: pointer;
                font-weight: bold;
                transition: background-color 0.3s;
            }
            .search-btn:hover {
                background-color: #00806a;
            }
            .add-btn {
                display: inline-block;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 4px;
                padding: 10px 15px;
                margin-bottom: 20px;
                font-weight: bold;
                transition: background-color 0.3s;
            }
            .add-btn:hover {
                background-color: #0069d9;
                text-decoration: none;
            }
            .add-btn::before {
                content: "+";
                margin-right: 5px;
                font-weight: bold;
            }
            @media screen and (max-width: 600px) {
                .book-table {
                    font-size: 12px;
                }
                .book-table thead th,
                .book-table tbody td {
                    padding: 8px 10px;
                }
            }
            /* Thêm CSS cho thông báo chào mừng */
            .welcome-message {
                background-color: #4CAF50;
                color: white;
                text-align: center;
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 5px;
                font-size: 18px;
                font-weight: bold;
            }
            .user-info {
                text-align: right;
                margin-bottom: 10px;
                font-size: 14px;
                color: #333;
            }
            .logout-btn {
                background-color: #e74c3c;
                color: white;
                border: none;
                padding: 5px 10px;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            .logout-btn:hover {
                background-color: #c0392b;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div style="min-height: 500px; padding: 10px">
            <%
                if (AuthUtils.isLoggedIn(session)) {
                    
            %>

            <div class="user-info">
                Xin chào, <%= user.getFullName() %> | 
                <form action="MainController" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="logout"/>
                    <input type="submit" value="Đăng xuất" class="logout-btn"/>
                </form>
            </div>

      
            <%
                String message = (String) request.getAttribute("message");
                if (message != null && !message.isEmpty()) {
            %>
                <div class="welcome-message"><%= message %></div>
            <% } %>

            <%
                String searchTerm = request.getAttribute("searchTerm") + "";
                searchTerm = searchTerm.equals("null") ? "" : searchTerm;
            %>
            <div class="search-section">
                <form action="MainController" method="get">
                    <input type="hidden" name="action" value="search"/>
                    <label for="searchInput">Tìm kiếm sách:</label>
                    <input type="text" id="searchInput" name="searchTerm" value="<%= searchTerm %>" 
                           class="search-input" placeholder="Nhập tiêu đề, tác giả hoặc ID sách..."/>
                    <input type="submit" value="Tìm kiếm" class="search-btn"/>
                </form>
            </div>

            <% if (AuthUtils.isAdmin(session)) { %>
                <a href="bookForm.jsp" class="add-btn">Thêm sách mới</a>
            <% } %>

            <%
                if (request.getAttribute("books") != null) {
                    List<BookDTO> books = (List<BookDTO>) request.getAttribute("books");
                    if (!books.isEmpty()) {
            %>
            <table class="book-table">
                <thead>
                    <tr>
                        <th>BookID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>PublishYear</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <% if (AuthUtils.isAdmin(session)) { %>
                            <th>Action</th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <% for (BookDTO b : books) { %>
                        <tr>
                            <td><%= b.getBookID() %></td>
                            <td><%= b.getTitle() %></td>
                            <td><%= b.getAuthor() %></td>
                            <td><%= b.getPublishYear() %></td>
                            <td><%= b.getPrice() %></td>
                            <td><%= b.getQuantity() %></td>
                            <% if (AuthUtils.isAdmin(session)) { %>
                                <td>
                                    <a href="MainController?action=delete&id=<%= b.getBookID() %>&searchTerm=<%= searchTerm %>">
                                        <img src="img/delete-button-trash-can-bin-symbol-delete-web-icon-illustration-vector.jpg" style="height: 25px"/>
                                    </a>
                                </td>
                            <% } %>
                        </tr>
                    <% } %>
                </tbody>
            </table>
            <% } else { %>
                <p style="text-align: center; color: #666;">Không tìm thấy sách nào phù hợp.</p>
            <% } %>
            <% } %>
            <% } else { %>
                <p style="text-align: center; color: red;">Bạn không có quyền truy cập nội dung này. Vui lòng đăng nhập!</p>
            <% } %>
        </div>
        <jsp:include page="footer.jsp"/>
    </body>
</html>