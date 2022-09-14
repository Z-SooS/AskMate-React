package practice.askmaterest.services.daos.implementation;

import org.springframework.stereotype.Service;
import practice.askmaterest.model.WebUser;
import practice.askmaterest.services.daos.IUserDao;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class UserDao implements IUserDao {

    private final DataSource datasource;

    public UserDao(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public WebUser add(WebUser objToAdd) {
        try(Connection conn = datasource.getConnection())
        {
            String query = "INSERT INTO web_user (username, password, email, reputation) VALUES(?,?,?,0)";
            PreparedStatement statement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, objToAdd.getUsername());
            statement.setString(2, objToAdd.getPassword());
            statement.setString(3, objToAdd.getEmail());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            objToAdd.setId(resultSet.getLong(1));
            return objToAdd;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void updateEmail(long id, String newEmail)
    {
        try(Connection conn = datasource.getConnection())
        {
            String query = "UPDATE web_user SET email = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,newEmail);
            statement.setLong(2,id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void updatePassword(long id, String newPassword)
    {
        try(Connection conn = datasource.getConnection()) {
            String query = "UPDATE web_user SET password = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,newPassword);
            statement.setLong(2,id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void updateReputation(long id, int newReputation)
    {
        try(Connection conn = datasource.getConnection()) {
            String query = "UPDATE web_user SET reputation = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, newReputation);
            statement.setLong(2,id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(Connection conn = datasource.getConnection()) {
            String query = "DELETE FROM web_user WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1,id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WebUser get(long id) {
        try(Connection conn = datasource.getConnection()) {
            String query = "SELECT id, username, email, reputation FROM web_user WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1,id);
            return getWebUser(statement);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    public WebUser getByEmail(String email)
    {
        try(Connection conn = datasource.getConnection()) {
            String query = "SELECT id, username, email, reputation FROM web_user WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,email);
            return getWebUser(statement);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private WebUser getWebUser(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }
        WebUser queryUser = new WebUser();
        queryUser.setId(resultSet.getLong("id"));
        queryUser.setUsername(resultSet.getString("username"));
        queryUser.setEmail(resultSet.getString("email"));
        queryUser.setReputation(resultSet.getInt("reputation"));
        return queryUser;
    }
}
