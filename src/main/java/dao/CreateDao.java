package dao;

import model.Advertisement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DbException;
import util.DbWork;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateDao {
    final static Logger logger = LogManager.getLogger(CreateDao.class);

    private DbWork dbWork;

    public CreateDao(DbWork dbWork) throws DbException {
        this.dbWork = dbWork;
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
