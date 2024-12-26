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

public class AdvertisementsCheckDao {
    final static Logger logger = LogManager.getLogger(AdvertisementsCheckDao.class);

    private DbWork dbWork;

    public AdvertisementsCheckDao(DbWork dbWork) throws DbException {
        this.dbWork = dbWork;
    }


    public List<Advertisement> getPageForCheck(long page, long limit) throws DbException {
        List<Advertisement> advList = new ArrayList<>();
        try (Connection connection = dbWork.getConnection()) {
            String query = "SELECT * FROM advertisements WHERE status = 'рассмотрение' ORDER BY publish_date DESC LIMIT ? OFFSET ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setLong(1, limit);
                statement.setLong(2, limit * (page - 1));

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    advList.add(new Advertisement(
                            rs.getInt("id"),
                            rs.getInt("sender_id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("publish_date"),
                            rs.getString("image_url"),
                            rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            logger.info(e);
            throw new DbException("Can't get advertisement from DB.", e);
        }
        return advList;
    }


    public Advertisement getAdvertisementByIdForCheck(int id) throws DbException{
        Advertisement adv = null;
        String query = "SELECT * FROM advertisements WHERE id = ? AND status = 'рассмотрение'";

        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    adv = new Advertisement();
                    adv.setId(resultSet.getInt("id"));
                    adv.setSender_id(resultSet.getInt("sender_id"));
                    adv.setTitle(resultSet.getString("title"));
                    adv.setContent(resultSet.getString("content"));
                    adv.setPublishDate(resultSet.getTimestamp("publish_date"));
                    adv.setImageUrl(resultSet.getString("image_url"));
                    adv.setStatus(resultSet.getString("status"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.info(e);
            throw new DbException("Can't take advertisement by id.", e);
        }
        return adv;
    }

    public int getCount() throws DbException {
        int total = 0;
        String query = "SELECT COUNT(id) FROM advertisements WHERE status = 'рассмотрение'";

        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getInt(1);
                }
                logger.info("Get the count of advertisements - correct");
            } catch (SQLException e) {
                logger.info(e);
                throw new DbException("Can't execute query.", e);
            }
        } catch (SQLException e) {
            logger.info(e);
            throw new DbException("Can't get total advertisement count.", e);
        }
        return total;
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

