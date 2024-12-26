package servlet;

import dao.CheckDao;
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
import java.util.List;

@WebServlet("/advertisements/check")
public class AdvertisementsCheckServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(AdvertisementsCheckServlet.class);
    private AdvertisementsDao advertisementsDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        advertisementsDao = (AdvertisementsDao) getServletContext().getAttribute("advertisementsDao");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
            int limit = 10;

            List<Advertisement> advertisementsList = advertisementsDao.getPage(page, limit, "рассмотрение");

            int totalAdvertisement = advertisementsDao.getCount("рассмотрение");
            int totalPages = (int) Math.ceil(totalAdvertisement / (double) limit);

            request.setAttribute("advertisementsList", advertisementsList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/WEB-INF/view/adv_check/advertisementsCheckList.jsp").forward(request, response);
        } catch (DbException e){
            logger.info(e);
            throw new ServletException(e);
        }
    }
}