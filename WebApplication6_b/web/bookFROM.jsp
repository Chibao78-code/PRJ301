<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.BookDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Book</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        h2 {
            color: #333;
            text-align: center;
        }
        .form-container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        td {
            padding: 10px;
            vertical-align: top;
        }
        input[type="text"], input[type="number"], textarea {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
            font-size: 0.9em;
            display: block;
        }
        .message {
            color: #d9534f;
            text-align: center;
            margin-bottom: 10px;
        }
        a {
            display: block;
            text-align: center;
            margin-top: 10px;
            color: #007bff;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Add New Book</h2>
        <% 
            String message = (String) request.getAttribute("message");
            if (message != null && !message.isEmpty()) {
        %>
            <p class="message"><%= message %></p>
        <% } %>
        <form action="MainController" method="POST">
            <input type="hidden" name="action" value="add" />
            <table>
                <tr>
                    <td><label for="txtBookID">Book ID:</label></td>
                    <td><input type="text" id="txtBookID" name="txtBookID" value="${book.bookID}" /></td>
                    <td><span class="error">${txtBookID_error}</span></td>
                </tr>
                <tr>
                    <td><label for="txtTitle">Title:</label></td>
                    <td><input type="text" id="txtTitle" name="txtTitle" value="${book.title}" /></td>
                    <td></td>
                </tr>
                <tr>
                    <td><label for="txtAuthor">Author:</label></td>
                    <td><input type="text" id="txtAuthor" name="txtAuthor" value="${book.author}" /></td>
                    <td></td>
                </tr>
                <tr>
                    <td><label for="txtPublishYear">Publish Year:</label></td>
                    <td><input type="number" id="txtPublishYear" name="txtPublishYear" value="${book.publishYear}" min="1900" max="2100" /></td>
                    <td></td>
                </tr>
                <tr>
                    <td><label for="txtPrice">Price:</label></td>
                    <td><input type="number" id="txtPrice" name="txtPrice" step="0.01" value="${book.price}" min="0" /></td>
                    <td></td>
                </tr>
                <tr>
                    <td><label for="txtQuantity">Quantity:</label></td>
                    <td><input type="number" id="txtQuantity" name="txtQuantity" value="${book.quantity}" min="0" /></td>
                    <td><span class="error">${txtQuantity_error}</span></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <input type="submit" value="Add Book" />
                    </td>
                </tr>
            </table>
        </form>
        <a href="MainController?action=search">Back to Search</a>
    </div>
</body>
</html>