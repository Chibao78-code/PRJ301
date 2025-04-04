package controller;

import dao.CategoryDAO;
import dao.ExamDAO;
import dao.QuestionDAO;
import dao.UserDAO;
import dto.CategoryDTO;
import dto.ExamDTO;
import dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AuthUtils;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    
    private final UserDAO userDAO = new UserDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final ExamDAO examDAO = new ExamDAO();
    private final QuestionDAO questionDAO = new QuestionDAO();

    
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String DASHBOARD_PAGE = "dashboard.jsp";
    private static final String ERROR_PAGE = "error.jsp";

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE; 

        try {
         
            String action = request.getParameter("action");
            System.out.println("Action received: " + action); 

           
            if (action == null) {
                url = LOGIN_PAGE;
            }

            else if (action.equals("login")) {
                String username = request.getParameter("txtUsername");
                String password = request.getParameter("txtPassword");
                System.out.println("Login attempt: username=" + username + ", password=" + password);

               
                UserDTO user = userDAO.readById(username);
                if (user == null) {
                    System.out.println("User not found for username: " + username);
                    request.setAttribute("error", "Invalid username");
                } else if (!user.getPassword().equals(password)) {
                    System.out.println("Password mismatch for username: " + username + ", DB password: " + user.getPassword());
                    request.setAttribute("error", "Invalid password");
                } else {
                    
                    System.out.println("Login successful for username: " + username);
                    request.getSession().setAttribute("user", user);
                    // Tải danh sách danh mục để hiển thị trên dashboard
                    List<CategoryDTO> categories = categoryDAO.readAll();
                    request.setAttribute("categories", categories);
                    url = DASHBOARD_PAGE;
                }
            }
        
            else if (action.equals("logout")) {
                request.getSession().invalidate();
                System.out.println("User logged out");
                url = LOGIN_PAGE;
            }
            
            else if (action.equals("viewExamsByCategory")) {
                
                if (!AuthUtils.isLoggedIn(request.getSession())) {
                    request.setAttribute("error", "Please login to continue");
                    url = LOGIN_PAGE;
                } else {
                    int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                    System.out.println("Viewing exams for categoryId: " + categoryId);


                    List<ExamDTO> exams = examDAO.getExamsByCategory(categoryId);
                    request.setAttribute("exams", exams);

                    
                    List<CategoryDTO> categories = categoryDAO.readAll();
                    request.setAttribute("categories", categories);
                    url = DASHBOARD_PAGE;
                }
            }
         
            else {
                request.setAttribute("error", "Invalid action: " + action);
                url = ERROR_PAGE;
            }
        } catch (Exception e) {
            
            System.err.println("Error in MainController: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "System error: " + e.getMessage());
            url = ERROR_PAGE;
        } finally {
            
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }
}