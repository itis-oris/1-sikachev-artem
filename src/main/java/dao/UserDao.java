package dao;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DbException;
import util.DbWork;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    final static Logger logger = LogManager.getLogger(UserDao.class);

    private DbWork dbWork;

    public UserDao(DbWork dbWork) throws DbException {
        this.dbWork = dbWork;
    }

    public User getUserByEmailAndPassword(String email, String password) throws DbException {
        User user = null;
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUserName(resultSet.getString("username"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setPhone_number(resultSet.getString("phone_number"));
                    user.setPlace_of_living(resultSet.getString("place_of_living"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DbException("Can't find user.", e);
        }
        return user;
    }

    public void saveUser(User user) throws DbException {
        String query = "INSERT INTO users (username, first_name, last_name, email, password, phone_number, place_of_living, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getPhone_number());
            statement.setString(7, user.getPlace_of_living());
            statement.setString(8, user.getRole());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DbException("Error saving user", e);
        }
    }

    public String getPasswordByEmail(String email) throws DbException{
        String query = "SELECT * FROM users WHERE email = ?";
        String password = null;

        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    password = resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DbException("No such email behind users.", e);
        }
        return password;
    }

    public void updateUser(User user) throws DbException {
        String query = "UPDATE users SET username = ?, first_name = ?, last_name = ?, phone_number = ?, place_of_living = ? WHERE id = ?";

        try (Connection connection = dbWork.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPhone_number());
            statement.setString(5, user.getPlace_of_living());
            statement.setInt(6, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DbException("Error updating user", e);
        }
    }
}
