package dto;

public class ExamDTO {
    private int examId;        // INT, Primary Key
    private String examTitle;  // VARCHAR(100), Not null
    private String subject;    // VARCHAR(50), Not null
    private int categoryId;    // INT, Foreign Key references tblExamCategories
    private int totalMarks;    // INT, Not null
    private int duration;      // INT, Not null (exam duration in minutes)

    // Default constructor
    public ExamDTO() {
    }

    // Parameterized constructor
    public ExamDTO(int examId, String examTitle, String subject, int categoryId, int totalMarks, int duration) {
        this.examId = examId;
        this.examTitle = examTitle;
        this.subject = subject;
        this.categoryId = categoryId;
        this.totalMarks = totalMarks;
        this.duration = duration;
    }

    // Getters and Setters
    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}