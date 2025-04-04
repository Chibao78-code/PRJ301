package controller;

import dao.BookDAO;
import dao.ProjectDAO;
import dao.UserDAO;
import dto.BookDTO;
import dto.ProjectDTO;
import dto.UserDTO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AuthUtils;

/**
 * Main Controller for handling user, book, and project operations
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class Maincontroller extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String PROJECT_DASHBOARD = "projectDashboard.jsp";
    private static final String PROJECT_FORM = "projectForm.jsp";
    private static final String SEARCH_PAGE = "search.jsp";
    private static final String BOOK_FORM = "bookForm.jsp";
    private static final String FORGOT_PASSWORD_PAGE = "forgotPassword.jsp";
    private static final String REGISTER_PAGE = "register.jsp";

    private final BookDAO bookDAO = new BookDAO();
    private final UserDAO userDAO = new UserDAO();
    private final ProjectDAO projectDAO = new ProjectDAO();

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        try {
            String action = request.getParameter("action");
            System.out.println("Action: " + action);

            if (action == null) {
                url = LOGIN_PAGE;
            } else {
                switch (action) {
                    case "login":
                        handleLogin(request, response);
                        url = AuthUtils.isLoggedIn(request.getSession()) ? PROJECT_DASHBOARD : LOGIN_PAGE;
                        break;

                    case "logout":
                        request.getSession().invalidate();
                        url = LOGIN_PAGE;
                        break;

                    case "search":
                        handleSearch(request, response);
                        url = SEARCH_PAGE;
                        break;

                    case "delete":
                        handleDelete(request, response);
                        url = SEARCH_PAGE;
                        break;

                    case "add":
                        handleAddBook(request, response);
                        url = (request.getAttribute("message") == null && bookDAO != null) ? SEARCH_PAGE : BOOK_FORM;
                        break;

                    case "viewProjects":
                        handleViewProjects(request, response);
                        url = PROJECT_DASHBOARD;
                        break;

                    case "createProject":
                        handleCreateProject(request, response);
                        url = AuthUtils.isAdmin(request.getSession()) ? PROJECT_DASHBOARD : LOGIN_PAGE;
                        break;

                    case "updateProjectStatus":
                        handleUpdateProjectStatus(request, response);
                        url = PROJECT_DASHBOARD;
                        break;

                    case "forgotPassword":
                        handleForgotPassword(request, response);
                        url = FORGOT_PASSWORD_PAGE;
                        break;

                    case "register":
                        handleRegister(request, response);
                        url = REGISTER_PAGE;
                        break;

                    default:
                        url = LOGIN_PAGE;
                        break;
                }
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
            request.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
            url = LOGIN_PAGE;
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            if (rd != null) {
                rd.forward(request, response);
            }
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strUserID = request.getParameter("txtUserID");
        String strPassword = request.getParameter("txtPassword");
        if (isValidLogin(strUserID, strPassword)) {
            UserDTO user = getUser(strUserID);
            request.getSession().setAttribute("user", user);
            handleViewProjects(request, response); // Tải danh sách dự án khi đăng nhập
        } else {
            request.setAttribute("message", "Incorrect UserID or Password");
        }
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        if (searchTerm == null) searchTerm = "";
        List<BookDTO> books = bookDAO.searchByTitle2(searchTerm);
        request.setAttribute("books", books);
        request.setAttribute("searchTerm", searchTerm);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.trim().isEmpty()) {
            bookDAO.updateQuantityToZero(id);
            handleSearch(request, response);
        } else {
            request.setAttribute("message", "Invalid book ID for deletion");
        }
    }

    private void handleAddBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            boolean checkError = false;

            String bookID = request.getParameter("txtBookID");
            String title = request.getParameter("txtTitle");
            String author = request.getParameter("txtAuthor");
            int publishYear = Integer.parseInt(request.getParameter("txtPublishYear"));
            double price = Double.parseDouble(request.getParameter("txtPrice"));
            int quantity = Integer.parseInt(request.getParameter("txtQuantity"));

            if (bookID == null || bookID.trim().isEmpty()) {
                checkError = true;
                request.setAttribute("txtBookID_error", "Book ID cannot be empty.");
            }
            if (quantity < 0) {
                checkError = true;
                request.setAttribute("txtQuantity_error", "Quantity must be >= 0.");
            }

            BookDTO book = new BookDTO(bookID, title, author, publishYear, price, quantity);

            if (!checkError) {
                if (bookDAO.create(book)) {
                    request.setAttribute("message", "Book added successfully!");
                    handleSearch(request, response);
                } else {
                    request.setAttribute("message", "Failed to add book.");
                }
            } else {
                request.setAttribute("book", book);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid number format: " + e.getMessage());
            request.setAttribute("book", new BookDTO(
                request.getParameter("txtBookID"),
                request.getParameter("txtTitle"),
                request.getParameter("txtAuthor"),
                request.getParameter("txtPublishYear") != null ? Integer.parseInt(request.getParameter("txtPublishYear")) : 0,
                request.getParameter("txtPrice") != null ? Double.parseDouble(request.getParameter("txtPrice")) : 0.0,
                request.getParameter("txtQuantity") != null ? Integer.parseInt(request.getParameter("txtQuantity")) : 0
            ));
        } catch (Exception e) {
            request.setAttribute("message", "Error adding book: " + e.getMessage());
        }
    }

    private void handleViewProjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ProjectDTO> projects = projectDAO.readAll();
        request.setAttribute("projects", projects);
        UserDTO user = AuthUtils.getUser(request.getSession());
        request.setAttribute("loggedInUser", user != null ? user.getFullName() : "Guest");
    }

    private void handleCreateProject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtils.isAdmin(request.getSession())) {
            request.setAttribute("message", "Only Admins can create projects!");
            return;
        }

        String projectID = request.getParameter("txtProjectID");
        String projectName = request.getParameter("txtProjectName");
        String description = request.getParameter("txtDescription");
        String status = request.getParameter("txtStatus");
        String launchDateStr = request.getParameter("txtLaunchDate");

        boolean checkError = false;
        if (projectID == null || projectID.trim().isEmpty()) {
            checkError = true;
            request.setAttribute("txtProjectID_error", "Project ID cannot be empty.");
        }
        if (projectName == null || projectName.trim().isEmpty()) {
            checkError = true;
            request.setAttribute("txtProjectName_error", "Project Name cannot be empty.");
        }
        Date launchDate = null;
        if (launchDateStr != null && !launchDateStr.trim().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                launchDate = sdf.parse(launchDateStr);
            } catch (ParseException e) {
                checkError = true;
                request.setAttribute("txtLaunchDate_error", "Invalid date format. Use yyyy-MM-dd.");
            }
        }

        if (!checkError) {
            ProjectDTO project = new ProjectDTO(projectID, projectName, description, status, launchDate);
            if (projectDAO.create(project)) {
                request.setAttribute("message", "Project created successfully!");
                handleViewProjects(request, response);
            } else {
                request.setAttribute("message", "Failed to create project.");
            }
        } else {
            request.setAttribute("project", new ProjectDTO(projectID, projectName, description, status, launchDate));
        }
    }

    private void handleUpdateProjectStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthUtils.isAdmin(request.getSession())) {
            request.setAttribute("message", "Only Admins can update project status!");
            return;
        }

        String projectID = request.getParameter("projectID");
        String newStatus = request.getParameter("newStatus");

        if (projectID != null && newStatus != null && !projectID.trim().isEmpty()) {
            ProjectDTO project = projectDAO.readById(projectID);
            if (project != null) {
                project.setStatus(newStatus);
                if (projectDAO.update(project)) {
                    request.setAttribute("message", "Project status updated to '" + newStatus + "' successfully!");
                } else {
                    request.setAttribute("message", "Failed to update project status.");
                }
                handleViewProjects(request, response);
            } else {
                request.setAttribute("message", "Project not found.");
            }
        } else {
            request.setAttribute("message", "Invalid project ID or status.");
        }
    }

    private void handleForgotPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = request.getParameter("txtUserID");
        String email = request.getParameter("txtEmail");
        UserDTO user = userDAO.readById(userID);
        if (user == null) {
            request.setAttribute("message", "Username does not exist!");
        } else {
            request.setAttribute("message", "New password sent to your email!");
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = request.getParameter("txtUserID");
        String fullName = request.getParameter("txtFullName");
        String password = request.getParameter("txtPassword");
        String roleID = request.getParameter("txtRoleID");

        boolean checkError = false;
        if (userID == null || userID.trim().isEmpty()) {
            checkError = true;
            request.setAttribute("userId_error", "Username cannot be empty!");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            checkError = true;
            request.setAttribute("fullName_error", "Full Name cannot be empty!");
        }
        if (password == null || password.trim().isEmpty()) {
            checkError = true;
            request.setAttribute("password_error", "Password cannot be empty!");
        }
        if (roleID == null || roleID.trim().isEmpty() || roleID.length() > 10) {
            checkError = true;
            request.setAttribute("roleId_error", "Role ID invalid (max 10 characters)!");
        }

        if (!checkError) {
            UserDTO existingUser = userDAO.readById(userID);
            if (existingUser != null) {
                request.setAttribute("userId_error", "Username already exists!");
            } else {
                UserDTO newUser = new UserDTO(userID, fullName, roleID, password);
                if (userDAO.create(newUser)) {
                    request.setAttribute("message", "Registration successful! Please login.");
                } else {
                    request.setAttribute("message", "Registration failed. Try again.");
                }
            }
        }
    }

    private UserDTO getUser(String strUserID) {
        return userDAO.readById(strUserID);
    }

    private boolean isValidLogin(String strUserID, String strPassword) {
        UserDTO user = getUser(strUserID);
        return user != null && user.getPassword().equals(strPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Main Controller for handling user, book, and project operations";
    }
}