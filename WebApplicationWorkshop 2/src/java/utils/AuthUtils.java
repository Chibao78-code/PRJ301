package utils;

import dto.UserDTO;
import javax.servlet.http.HttpSession;

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

    public static boolean isInstructor(HttpSession session) {
        UserDTO user = getUser(session);
        return user != null && "Instructor".equals(user.getRole());
    }

    public static boolean isStudent(HttpSession session) {
        UserDTO user = getUser(session);
        return user != null && "Student".equals(user.getRole());
    }
}