package service;

import dao.UserDao;
import jakarta.servlet.http.HttpServletRequest;
import model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import util.DbException;
import util.DbWork;

import java.security.SecureRandom;

public class UserService {

    private BCryptPasswordEncoder passwordEncoder;
    private DbWork dbWork;

    public UserService(DbWork dbWork) {
        this.dbWork = dbWork;
        this.passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
    }

    public void registerUser(User user) throws DbException {
        // Хеширование пароля с солью
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        UserDao userDao = new UserDao(dbWork);
        userDao.saveUser(user);
    }

    public void auth(User user, HttpServletRequest req){
        req.getSession().setAttribute("user", user);
    }

    public boolean isAuth(HttpServletRequest req){
        return req.getSession().getAttribute("user") != null;
    }

    public User getUser(HttpServletRequest req){
        return (User) req.getSession().getAttribute("user");
    }

    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    public void logout(HttpServletRequest req) {
        req.getSession().invalidate();
    }
}
