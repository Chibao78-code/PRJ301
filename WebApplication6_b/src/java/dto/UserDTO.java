package dto;

public class UserDTO {
    private String username; // VARCHAR(50), Primary Key
    private String name;     // VARCHAR(100), Not null
    private String password; // VARCHAR(255), Not null
    private String role;     // VARCHAR(20), Not null (values: "Instructor", "Student")

    // Default constructor
    public UserDTO() {
    }

    // Parameterized constructor
    public UserDTO(String username, String name, String password, String role) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{username=" + username + ", name=" + name + ", password=" + password + ", role=" + role + "}";
    }
}