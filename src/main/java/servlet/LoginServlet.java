package servlet;


import dao.UserDao;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AdminService;
import service.UserService;
import util.DbException;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(LoginServlet.class);

    private UserDao userDao;
    private UserService userService;
    private AdminService adminService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = (UserDao) getServletContext().getAttribute("userDao");
        userService = (UserService) getServletContext().getAttribute("userService");
        adminService = (AdminService) getServletContext().getAttribute("adminService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/security/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            String storedPassword = userDao.getPasswordByEmail(email);
            if (userService.checkPassword(password, storedPassword)) {
                User user = userDao.getUserByEmailAndPassword(email, storedPassword);
                userService.auth(user, request);
                adminService.role(user, request);
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            } else {
                request.setAttribute("error", "Неверный email или пароль.");
                logger.info("Неудачная попытка авторизации");
            }
        } catch (DbException e) {
            request.setAttribute("error", "Ошибка при авторизации.");
            logger.info("Произошла ошибка в авторизации при получении пароля по email.");
        }
        request.getRequestDispatcher("/WEB-INF/view/security/login.jsp").forward(request, response);
    }
}

