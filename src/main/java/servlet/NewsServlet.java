package servlet;

import dao.NewsDao;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.News;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DbException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("")
public class NewsServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(NewsServlet.class);
    private NewsDao newsDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        newsDao = (NewsDao) getServletContext().getAttribute("newsDao");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
            int limit = 10;

            List<News> newsList = newsDao.getPage(page, limit);

            int totalNews = newsDao.getCount();
            int totalPages = (int) Math.ceil(totalNews / (double) limit);

            request.setAttribute("newsList", newsList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/WEB-INF/view/news/newsList.jsp").forward(request, response);
        } catch (DbException e){
            logger.info(e);
            throw new ServletException(e);
        }
    }
}
