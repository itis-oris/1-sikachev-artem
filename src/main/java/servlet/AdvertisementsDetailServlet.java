package servlet;

import dao.AdvertisementsDao;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Advertisement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DbException;

import java.io.IOException;

@WebServlet("/advertisements/detail")
public class AdvertisementsDetailServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(AdvertisementsDetailServlet.class);
    private AdvertisementsDao advertisementsDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        advertisementsDao = (AdvertisementsDao) getServletContext().getAttribute("advertisementsDao");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            int advertisementId = Integer.parseInt(request.getParameter("id"));
            Advertisement advertisement = advertisementsDao.getAdvertisementById(advertisementId, "принят");
            if (advertisement != null){
                request.setAttribute("advertisement", advertisement);
                request.getRequestDispatcher("/WEB-INF/view/advertisements/advertisementsDetail.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Sorry, the advertisement you are looking for does not exist.");
                request.getRequestDispatcher("/WEB-INF/exceptions/errorAdvDetail.jsp").forward(request, response);
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

