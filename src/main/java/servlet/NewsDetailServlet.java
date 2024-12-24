package servlet;

import dao.NewsDao;
import jakarta.servlet.ServletConfig;
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

@WebServlet("/news")
public class NewsDetailServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(NewsDetailServlet.class);
    private NewsDao newsDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        newsDao = (NewsDao) getServletContext().getAttribute("newsDao");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            int newsId = Integer.parseInt(request.getParameter("id"));
            News news = newsDao.getNewsById(newsId);
            if (news != null){
                request.setAttribute("news", news);
                request.getRequestDispatcher("/WEB-INF/view/news/newsDetail.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Sorry, the news you are looking for does not exist.");
                request.getRequestDispatcher("/WEB-INF/exceptions/errorPage.jsp").forward(request, response);
            }

        } catch (ServletException e){
            logger.info(e);
            throw new ServletException(e);
        } catch (IOException e){
            logger.info(e);
            throw new IOException(e);
        } catch (DbException e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }
}

