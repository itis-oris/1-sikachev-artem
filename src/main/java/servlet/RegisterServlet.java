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
import util.IsValid;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(RegisterServlet.class);

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
        request.getRequestDispatcher("/WEB-INF/view/security/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userName = null;
            String email = null;
            String password = null;
            String check_pass = request.getParameter("check_password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phoneNumber = request.getParameter("phoneNumber");
            String placeOfLiving = request.getParameter("placeOfLiving");
            String role = "USER";

            if (IsValid.validateUserName(request.getParameter("userName"))){
                userName = request.getParameter("userName");
            } else {
                request.setAttribute("error", "Вы ввели некорректные данные в поле <Имя пользователя>.");
            }

            if (IsValid.validateEmail(request.getParameter("email"))){
                email = request.getParameter("email");
            } else {
                request.setAttribute("error", "Вы ввели некорректные данные в поле <Email>.");
            }

            if (IsValid.validatePassword(request.getParameter("password"))){
                password = request.getParameter("password");
            } else {
                request.setAttribute("error", "Вы ввели некорректные данные в поле <Пароль>.");
            }

            if(userName != null && email != null && password != null){
                if(password.equals(check_pass)){
                    User user = new User(userName, role, firstName, lastName, email, password, phoneNumber, placeOfLiving);

                    try {
                        userService.registerUser(user);
                        logger.info("Created new User with email: " + user.getEmail() + ", password: " + user.getPassword() + ".");
                        response.sendRedirect(request.getContextPath() + "/login");
                        return;
                    } catch (DbException e) {
                        logger.info(e);
                        request.setAttribute("error", "Ошибка при регистрации. Попробуйте снова.");
                    }
                } else {
                    logger.info("Во время регистрации дублирующий пароль не совпал.");
                    request.setAttribute("error", "Вы неправельно ввели повтор пороля.");
                }
            }
            request.getRequestDispatcher("/WEB-INF/view/security/register.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}

