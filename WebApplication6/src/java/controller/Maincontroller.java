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

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class Maincontroller extends HttpServlet {

    private BookDAO bookDAO = new BookDAO();
    private ProjectDAO projectDAO = new ProjectDAO();
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String PROJECT_FORM = "projectForm.jsp";
    private static final String SEARCH_PAGE = "search.jsp";

    public UserDTO getUser(String strUserID) {
        UserDAO udao = new UserDAO();
        return udao.readById(strUserID);
    }

    public boolean isValidLogin(String strUserID, String strPassword) {
        UserDTO user = getUser(strUserID);
        return user != null && user.getPassword().equals(strPassword);
    }

public void search(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String searchTerm = request.getParameter("searchTerm");
    if (searchTerm == null) {
        searchTerm = "";
    }
    List<BookDTO> books = bookDAO.searchByTitle2(searchTerm);
    request.setAttribute("books", books);
    request.setAttribute("searchTerm", searchTerm);
    System.out.println("Search Term: " + searchTerm);
System.out.println("Books found: " + (books != null ? books.size() : "null"));
if (searchTerm == null || searchTerm.trim().isEmpty()) {
    searchTerm = "";
}

}

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
                if (action.equals("login")) {
                    String strUserID = request.getParameter("txtUserID");
                    String strPassword = request.getParameter("txtPassword");
                    if (isValidLogin(strUserID, strPassword)) {
                        url = SEARCH_PAGE;
                        UserDTO user = getUser(strUserID);
                        request.getSession().setAttribute("user", user);
                        request.setAttribute("message", "Chào mừng " + user.getFullName() + " đã đăng nhập thành công!");
                        search(request, response);
                    } else {
                        request.setAttribute("message", "Incorrect UserID or Password");
                        url = LOGIN_PAGE;
                    }
                } else if (action.equals("logout")) {
                    request.getSession().invalidate();
                    url = LOGIN_PAGE;
                } else if (action.equals("search")) {
                    search(request, response);
                    url = SEARCH_PAGE;
                } else if (action.equals("delete")) {
                    String id = request.getParameter("id");
                    bookDAO.updateQuantityToZero(id);
                    search(request, response);
                    url = SEARCH_PAGE;
                } else if (action.equals("add")) {

                } else if (action.equals("forgotPassword")) {

                } else if (action.equals("register")) {

                } else if (action.equals("createProject")) {

                    if (!AuthUtils.isAdmin(request.getSession())) {
                        request.setAttribute("message", "Bạn không có quyền tạo dự án!");
                        url = LOGIN_PAGE;
                    } else {
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
                            boolean success = projectDAO.create(project);
                            if (success) {
                                request.setAttribute("message", "Project created successfully!");
                                url = SEARCH_PAGE; 
                            } else {
                                request.setAttribute("message", "Failed to create project.");
                                url = PROJECT_FORM;
                            }
                        } else {
                            request.setAttribute("project", new ProjectDTO(projectID, projectName, description, status, launchDate));
                            url = PROJECT_FORM;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
            request.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
            url = LOGIN_PAGE;
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
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
