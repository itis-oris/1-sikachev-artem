package dao;

import model.Advertisement;
import model.News;
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

public class NewsDao {

    final static Logger logger = LogManager.getLogger(NewsDao.class);

    private DbWork dbWork;

    public NewsDao(DbWork dbWork) throws DbException {
        this.dbWork = dbWork;
    }


    public List<News> getPage(long page, long limit) throws DbException {
        List<News> newsList = new ArrayList<>();
        try (Connection connection = dbWork.getConnection()) {
            String query = "SELECT * FROM news ORDER BY publish_date DESC LIMIT ? OFFSET ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setLong(1, limit);
                statement.setLong(2, limit * (page - 1));

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    newsList.add(new News(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("publish_date"),
                            rs.getString("image_url")
                    ));
                }
            }
        } catch (SQLException e) {
            logger.info(e);
            throw new DbException("Can't get news from DB.", e);
        }
        return newsList;
    }


    public News getNewsById(int id) throws DbException{
        News news = null;
        String query = "SELECT * FROM news WHERE id = ?";

        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    news = new News();
                    news.setId(resultSet.getLong("id"));
                    news.setTitle(resultSet.getString("title"));
                    news.setContent(resultSet.getString("content"));
                    news.setPublishDate(resultSet.getTimestamp("publish_date"));
                    news.setImageUrl(resultSet.getString("image_url"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.info(e);
            throw new DbException("Can't take news by id.", e);
        }
        return news;
    }

    public int getCount() throws DbException {
        int total = 0;
        String query = "SELECT COUNT(id) FROM news";

        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getInt(1);
                }
                logger.info("Get the count of news - correct");
            } catch (SQLException e) {
                logger.info(e);
                throw new DbException("Can't execute query.", e);
            }
        } catch (SQLException e) {
            logger.info(e);
            throw new DbException("Can't get total news count.", e);
        }

        return total;
    }

    public void create(News news) throws DbException {
        String query = "INSERT INTO news (title, content, publish_date, image_url) VALUES (?, ?, ?, ?)";

        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, news.getTitle());
            statement.setString(2, news.getContent());
            statement.setTimestamp(3, news.getPublishDate());
            statement.setString(4, news.getImageUrl());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error creating news", e);
            throw new DbException("Can't create news", e);
        }
    }
}
