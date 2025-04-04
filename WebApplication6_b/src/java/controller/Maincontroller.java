package controller;

import dao.CategoryDAO;
import dao.ExamDAO;
import dao.QuestionDAO;
import dao.UserDAO;
import dto.CategoryDTO;
import dto.ExamDTO;
import dto.QuestionDTO;
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

    private UserDAO userDAO = new UserDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private ExamDAO examDAO = new ExamDAO();
    private QuestionDAO questionDAO = new QuestionDAO();
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String CATEGORY_FORM = "categoryForm.jsp"; // Form for adding categories
    private static final String EXAM_FORM = "examForm.jsp";         // Form for adding exams
    private static final String SEARCH_PAGE = "search.jsp";
    private Object category;
    private Object exam;
    private Object question;

    public UserDTO getUser(String username) {
        return userDAO.readById(username);
    }

    public boolean isValidLogin(String username, String password) {
        UserDTO user = getUser(username);
        return user != null && user.getPassword().equals(password);
    }

    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            searchTerm = "";
        }
        // Search across exams and categories (example logic)
        List<ExamDTO> exams = examDAO.search(searchTerm);
        List<CategoryDTO> categories = categoryDAO.search(searchTerm);
        request.setAttribute("exams", exams);
        request.setAttribute("categories", categories);
        request.setAttribute("searchTerm", searchTerm);
        System.out.println("Search Term: " + searchTerm);
        System.out.println("Exams found: " + (exams != null ? exams.size() : "null"));
        System.out.println("Categories found: " + (categories != null ? categories.size() : "null"));
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
                    String username = request.getParameter("txtUsername"); // Changed from txtUserID
                    String password = request.getParameter("txtPassword");
                    if (isValidLogin(username, password)) {
                        url = SEARCH_PAGE;
                        UserDTO user = getUser(username);
                        request.getSession().setAttribute("user", user);
                        request.setAttribute("message", "Chào mừng " + user.getName() + " đã đăng nhập thành công!");
                        search(request, response);
                    } else {
                        request.setAttribute("message", "Incorrect Username or Password");
                        url = LOGIN_PAGE;
                    }
                } else if (action.equals("logout")) {
                    request.getSession().invalidate();
                    url = LOGIN_PAGE;
                } else if (action.equals("search")) {
                    search(request, response);
                    url = SEARCH_PAGE;
                } else if (action.equals("deleteExam")) { // Changed from delete
                    String id = request.getParameter("id");
                    examDAO.delete(id); // Assuming examDAO has delete method
                    search(request, response);
                    url = SEARCH_PAGE;
                } else if (action.equals("addCategory")) { // New action for adding categories
                    if (!AuthUtils.isAdmin(request.getSession())) {
                        request.setAttribute("message", "Bạn không có quyền thêm danh mục!");
                        url = SEARCH_PAGE;
                    } else {
                        int categoryId = Integer.parseInt(request.getParameter("txtCategoryId"));
                        String categoryName = request.getParameter("txtCategoryName");
                        String description = request.getParameter("txtDescription");

                        boolean checkError = false;
                        if (categoryName == null || categoryName.trim().isEmpty()) {
                            checkError = true;
                            request.setAttribute("txtCategoryName_error", "Category Name cannot be empty.");
                        }

                        if (!checkError) {
                            CategoryDTO category = new CategoryDTO(categoryId, categoryName, description);
                            if (categoryDAO.create(category)) {
                                request.setAttribute("message", "Thêm danh mục thành công!");
                                url = SEARCH_PAGE;
                            } else {
                                request.setAttribute("message", "Thêm danh mục thất bại!");
                                url = CATEGORY_FORM;
                            }
                        } else {
                            request.setAttribute("category", category);
                            url = CATEGORY_FORM;
                        }
                    }
                } else if (action.equals("addExam")) { // New action for adding exams
                    if (!AuthUtils.isAdmin(request.getSession())) {
                        request.setAttribute("message", "Bạn không có quyền thêm kỳ thi!");
                        url = SEARCH_PAGE;
                    } else {
                        int examId = Integer.parseInt(request.getParameter("txtExamId"));
                        String examTitle = request.getParameter("txtExamTitle");
                        String subject = request.getParameter("txtSubject");
                        int categoryId = Integer.parseInt(request.getParameter("txtCategoryId"));
                        int totalMarks = Integer.parseInt(request.getParameter("txtTotalMarks"));
                        int duration = Integer.parseInt(request.getParameter("txtDuration"));

                        boolean checkError = false;
                        if (examTitle == null || examTitle.trim().isEmpty()) {
                            checkError = true;
                            request.setAttribute("txtExamTitle_error", "Exam Title cannot be empty.");
                        }
                        if (subject == null || subject.trim().isEmpty()) {
                            checkError = true;
                            request.setAttribute("txtSubject_error", "Subject cannot be empty.");
                        }

                        if (!checkError) {
                            ExamDTO exam = new ExamDTO(examId, examTitle, subject, categoryId, totalMarks, duration);
                            if (examDAO.create(exam)) {
                                request.setAttribute("message", "Thêm kỳ thi thành công!");
                                url = SEARCH_PAGE;
                            } else {
                                request.setAttribute("message", "Thêm kỳ thi thất bại!");
                                url = EXAM_FORM;
                            }
                        } else {
                            request.setAttribute("exam", exam);
                            url = EXAM_FORM;
                        }
                    }
                } else if (action.equals("addQuestion")) { // New action for adding questions
                    if (!AuthUtils.isAdmin(request.getSession())) {
                        request.setAttribute("message", "Bạn không có quyền thêm câu hỏi!");
                        url = SEARCH_PAGE;
                    } else {
                        int questionId = Integer.parseInt(request.getParameter("txtQuestionId"));
                        int examId = Integer.parseInt(request.getParameter("txtExamId"));
                        String questionText = request.getParameter("txtQuestionText");
                        String optionA = request.getParameter("txtOptionA");
                        String optionB = request.getParameter("txtOptionB");

                        boolean checkError = false;
                        if (questionText == null || questionText.trim().isEmpty()) {
                            checkError = true;
                            request.setAttribute("txtQuestionText_error", "Question Text cannot be empty.");
                        }
                        if (optionA == null || optionA.trim().isEmpty()) {
                            checkError = true;
                            request.setAttribute("txtOptionA_error", "Option A cannot be empty.");
                        }
                        if (optionB == null || optionB.trim().isEmpty()) {
                            checkError = true;
                            request.setAttribute("txtOptionB_error", "Option B cannot be empty.");
                        }

                        if (!checkError) {
                            QuestionDTO question = new QuestionDTO(questionId, examId, questionText, optionA, optionB);
                            if (questionDAO.create(question)) {
                                request.setAttribute("message", "Thêm câu hỏi thành công!");
                                url = SEARCH_PAGE;
                            } else {
                                request.setAttribute("message", "Thêm câu hỏi thất bại!");
                                url = "questionForm.jsp"; // Assume a form for questions
                            }
                        } else {
                            request.setAttribute("question", question);
                            url = "questionForm.jsp";
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
        return "Main Controller for handling user, exam category, exam, and question operations";
    }
}