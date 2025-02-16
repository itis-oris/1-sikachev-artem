package listener;

import dao.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AdminService;
import service.UserService;
import util.DbException;
import util.DbWork;

@WebListener
public class ContextListener implements ServletContextListener {

    final static Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce){
        try {
            DbWork connection = DbWork.getInstance();
            logger.info("contextInitialized");
            sce.getServletContext().setAttribute("newsDao", new NewsDao(connection));
            sce.getServletContext().setAttribute("advertisementsDao", new AdvertisementsDao(connection));
            sce.getServletContext().setAttribute("checkDao", new CheckDao(connection));
            sce.getServletContext().setAttribute("userDao", new UserDao(connection));
            sce.getServletContext().setAttribute("userService", new UserService(connection));
            sce.getServletContext().setAttribute("adminService", new AdminService());

        } catch (DbException e) {
            logger.info("Error during context initialization", e);
            throw new RuntimeException(e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            sce.getServletContext().removeAttribute("newsDao");
            sce.getServletContext().removeAttribute("advertisementsDao");
            sce.getServletContext().removeAttribute("checkDao");
            sce.getServletContext().removeAttribute("userDao");
            sce.getServletContext().removeAttribute("userService");
            sce.getServletContext().removeAttribute("adminService");
            DbWork.getInstance().destroy();
            logger.info("contextDestroyed: Resources have been cleaned up");
        } catch (DbException e) {
            logger.info("Error during context destruction", e);
            throw new RuntimeException(e);
        }
    }
}
