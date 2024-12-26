package servlet;

import dao.AdvertisementsDao;
import dao.AdvertisementsDao;
import dao.CheckDao;
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

@WebServlet("/advertisements/check/detail")
public class AdvertisementsCheckDetailServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(AdvertisementsCheckDetailServlet.class);
    private AdvertisementsDao advertisementsDao;
    private CheckDao checkDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        advertisementsDao = (AdvertisementsDao) getServletContext().getAttribute("advertisementsDao");
        checkDao = (CheckDao) getServletContext().getAttribute("checkDao");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int advertisementId = Integer.parseInt(request.getParameter("id"));
            String action = request.getParameter("action");

            if ("accept".equals(action)) {
                checkDao.acceptAdvertisement(advertisementId);
            } else if ("reject".equals(action)) {
                checkDao.rejectAdvertisement(advertisementId);
            }

            response.sendRedirect("/advertisements/check?page=1");  // Перенаправляем на страницу с объявлениями
        } catch (DbException e) {
            logger.error("Error updating advertisement status", e);
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            int advertisementId = Integer.parseInt(request.getParameter("id"));
            Advertisement advertisement = advertisementsDao.getAdvertisementById(advertisementId, "рассмотрение");
            if (advertisement != null){
                request.setAttribute("advertisement", advertisement);
                request.getRequestDispatcher("/WEB-INF/view/adv_check/advertisementsCheckDetail.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Sorry, the advertisement you are looking for does not exist.");
                request.getRequestDispatcher("/WEB-INF/exceptions/errorAdvDetailCheck.jsp").forward(request, response);
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
