/*
DTO (Data Transfer Object):
DTO là một design pattern dùng để đóng gói và truyền dữ liệu giữa các thành phần/layer trong ứng dụng
Là một class đơn giản chỉ chứa các thuộc tính (properties) và các phương thức getter/setter
Không chứa bất kỳ business logic nào
Mục đích chính là đóng gói dữ liệu để truyền qua network hoặc giữa các layer
 */
package dto;

/**
 *
 * @author tungi
 */
public class UserDTO {
    private String username;
    private String Name;
    private String Role;
    private String password;

    public UserDTO() {
    }

    public UserDTO(String userID, String fullName, String roleID, String password) {
        this.username = username;
        this.Name = Name;
        this.Role = Role;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "username=" + username + ", Name=" + Name + ", Role=" + Role + ", password=" + password + '}';
    }


    
}