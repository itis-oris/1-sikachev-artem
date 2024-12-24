package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

import java.sql.Connection;
import java.sql.SQLException;

public class DbWork {

    final static Logger logger = LogManager.getLogger(DbWork.class);

    private static DbWork instance;

    private static HikariDataSource dataSource;

    private DbWork() throws DbException {

        try {
            Class.forName("org.postgresql.Driver");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/sem_one");
            config.setUsername("postgres");
            config.setPassword("38965712");
            config.setConnectionTimeout(50000);
            config.setMaximumPoolSize(10);
            dataSource = new HikariDataSource(config);

            Flyway flyway = Flyway.configure().dataSource(dataSource).load();
            logger.info("start migration");
            flyway.migrate();
            logger.info("migration done");
        } catch (Exception e) {
            logger.error(e);
            throw new DbException("Cannot connect to DB", e);
        }
    }

    public static DbWork getInstance() throws DbException {
        if (instance == null) {
            synchronized (DbWork.class) {
                if (instance == null) {
                    instance = new DbWork();
                }
            }
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        return connection;
    }

    public synchronized void releasConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public void destroy() {
        dataSource.close();
    }
}
