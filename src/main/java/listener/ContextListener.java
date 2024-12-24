package listener;

import dao.NewsDao;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
//            sce.getServletContext().setAttribute("advertismentDao", new AdvertismentDao(connection));
        } catch (DbException e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DbWork.getInstance().destroy();
            logger.info("contextClosed");
        } catch (DbException e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }
}
