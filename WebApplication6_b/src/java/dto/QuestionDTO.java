package dto;

public class QuestionDTO {
    private int questionId;     // INT, Primary Key
    private int examId;         // INT, Foreign Key references tblExams
    private String questionText; // TEXT, Not null
    private String optionA;     // VARCHAR(100), Not null
    private String optionB;     // VARCHAR(100), Not null

    // Default constructor
    public QuestionDTO() {
    }

    // Parameterized constructor
    public QuestionDTO(int questionId, int examId, String questionText, String optionA, String optionB) {
        this.questionId = questionId;
        this.examId = examId;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
    }

    // Getters and Setters
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }
}