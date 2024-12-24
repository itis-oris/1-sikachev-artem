package servlet;

import dao.NewsDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/about")
public class AboutServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(AboutServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.getRequestDispatcher("/WEB-INF/view/about.jsp").forward(request, response);
        } catch (ServletException e){
            logger.info(e);
            throw new ServletException(e);
        } catch (IOException e){
            logger.info(e);
            throw new IOException(e);
        }
    }
}

