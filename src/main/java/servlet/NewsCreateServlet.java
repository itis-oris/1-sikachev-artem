package servlet;

import dao.NewsDao;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.News;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import util.DbException;
import util.SaveImage;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/news/create")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class NewsCreateServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(NewsCreateServlet.class);
    private NewsDao newsDao;
    private UserService userService;
    private String imageDirectory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        newsDao = (NewsDao) getServletContext().getAttribute("newsDao");
        userService = (UserService) getServletContext().getAttribute("userService");

        imageDirectory = getServletContext().getInitParameter("imageDirectory");

        // Если путь не задан, используем значение по умолчанию
        if (imageDirectory == null || imageDirectory.isEmpty()) {
            imageDirectory = System.getProperty("user.dir") + "/images"; // Используем текущую директорию
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (userService.isAuth(request)) {
            request.getRequestDispatcher("/WEB-INF/view/news/newsCreate.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (userService.isAuth(request)) {
            String title = request.getParameter("title");
            String content = request.getParameter("content");

            Part imagePart = request.getPart("image");
            String imageUrl = SaveImage.saveImage(imagePart, imageDirectory);

            News news = new News(title, content, new Timestamp(System.currentTimeMillis()), imageUrl);

            try {
                newsDao.create(news);
                response.sendRedirect(request.getContextPath() + "/");
            } catch (DbException e) {
                logger.error("Error creating news", e);
                throw new ServletException("Failed to create news", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }
}
