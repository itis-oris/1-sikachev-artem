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
import service.UserService;
import util.DbException;

import java.io.IOException;

@WebServlet("/profile")
public class EditProfileServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(EditProfileServlet.class);

    private UserDao userDao;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = (UserDao) getServletContext().getAttribute("userDao");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!userService.isAuth(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = userService.getUser(request);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/view/security/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!userService.isAuth(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = userService.getUser(request);
        String userName = request.getParameter("userName");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone_number = request.getParameter("phone");
        String place = request.getParameter("place");

        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone_number(phone_number);
        user.setPlace_of_living(place);

        try {
            userDao.updateUser(user);
            response.sendRedirect(request.getContextPath() + "/profile");
        } catch (DbException e) {
            logger.info(e);
            request.setAttribute("error", "Ошибка при обновлении информации.");
            request.getRequestDispatcher("/WEB-INF/view/security/profile.jsp").forward(request, response);
        }
    }
}

