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

public class AdvertisementsDao {
    final static Logger logger = LogManager.getLogger(AdvertisementsDao.class);

    private DbWork dbWork;

    public AdvertisementsDao(DbWork dbWork) throws DbException {
        this.dbWork = dbWork;
    }


    public List<Advertisement> getPage(long page, long limit) throws DbException {
        List<Advertisement> advList = new ArrayList<>();
        try (Connection connection = dbWork.getConnection()) {
            String query = "SELECT * FROM advertisements WHERE status = 'принят' ORDER BY publish_date DESC LIMIT ? OFFSET ?";
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
                            "рассмотрение"
                    ));
                }
            }
        } catch (SQLException e) {
            logger.info(e);
            throw new DbException("Can't get advertisement from DB.", e);
        }
        return advList;
    }


    public Advertisement getAdvertisementById(int id) throws DbException{
        Advertisement adv = null;
        String query = "SELECT * FROM advertisements WHERE id = ? AND status = 'принят'";

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
        String query = "SELECT COUNT(id) FROM advertisements";

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

    public void create(Advertisement advertisement) throws DbException {
        String query = "INSERT INTO advertisements (sender_id, title, content, publish_date, image_url, status) VALUES (?, ?, ?, ?, ?, 'рассмотрение')";

        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, advertisement.getSender_id());
            statement.setString(2, advertisement.getTitle());
            statement.setString(3, advertisement.getContent());
            statement.setTimestamp(4, advertisement.getPublishDate());
            statement.setString(5, advertisement.getImageUrl());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error creating advertisement", e);
            throw new DbException("Can't create advertisement", e);
        }
    }
}
