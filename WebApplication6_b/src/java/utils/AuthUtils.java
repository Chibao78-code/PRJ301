package utils;

import javax.servlet.http.HttpSession;
import dto.UserDTO;

public class AuthUtils {

    public static boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute("user") != null;
    }

    public static UserDTO getUser(HttpSession session) {
        if (session != null) {
            return (UserDTO) session.getAttribute("user");
        }
        return null;
    }

    public static boolean isAdmin(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        return user != null && "AD".equals(user.getRole());
    }
}