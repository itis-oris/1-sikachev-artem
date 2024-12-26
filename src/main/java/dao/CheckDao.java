package dao;

import model.Advertisement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DbException;
import util.DbWork;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckDao {
    final static Logger logger = LogManager.getLogger(CheckDao.class);

    private DbWork dbWork;

    public CheckDao(DbWork dbWork) throws DbException {
        this.dbWork = dbWork;
    }

    public void acceptAdvertisement(int advertisementId) throws DbException {
        String query = "UPDATE advertisements SET status = 'принят' WHERE id = ?";
        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, advertisementId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error accepting advertisement", e);
            throw new DbException("Error accepting advertisement", e);
        }
    }

    public void rejectAdvertisement(int advertisementId) throws DbException {
        String query = "DELETE FROM advertisements WHERE id = ?";
        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, advertisementId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error rejecting advertisement", e);
            throw new DbException("Error rejecting advertisement", e);
        }
    }
}

