package servlet;

import dao.AdvertisementsDao;
import dao.CreateDao;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Advertisement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import util.DbException;
import util.SaveImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;


@WebServlet("/advertisements/create")
@MultipartConfig(
        location = "D:\\Semestr_Work_One\\images",
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class AdvertisementsCreateServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AdvertisementsCreateServlet.class);
    private CreateDao createDao;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        createDao = (CreateDao) getServletContext().getAttribute("createDao");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (userService.isAuth(request)) {
            request.getRequestDispatcher("/WEB-INF/view/advertisements/advertisementsCreate.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (userService.isAuth(request)) {
            String title = request.getParameter("title");
            String content = request.getParameter("content");

            Part imagePart = request.getPart("image");
            String imageUrl = SaveImage.saveImage(imagePart);

            Advertisement advertisement = new Advertisement(title, userService.getUser(request).getId(), content, new Timestamp(System.currentTimeMillis()), imageUrl);

            try {
                createDao.create(advertisement);
                response.sendRedirect(request.getContextPath() + "/advertisements");
            } catch (DbException e) {
                logger.error("Error creating advertisement", e);
                throw new ServletException("Failed to create advertisement", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }
}

