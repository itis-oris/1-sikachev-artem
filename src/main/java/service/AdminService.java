package service;

import dao.UserDao;
import jakarta.servlet.http.HttpServletRequest;
import model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import util.DbException;
import util.DbWork;

import java.security.SecureRandom;

public class AdminService {

    public void role(User user, HttpServletRequest req){
        req.getSession().setAttribute("user_role", user.getRole());
    }

    public boolean isRole(HttpServletRequest req){
        return req.getSession().getAttribute("user_role") != null && req.getSession().getAttribute("user_role").equals("ADMIN");
    }

    public String getUserRole(HttpServletRequest req){
        return (String) req.getSession().getAttribute("user_role");
    }
}
