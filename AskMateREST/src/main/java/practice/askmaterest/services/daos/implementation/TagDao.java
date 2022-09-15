package practice.askmaterest.services.daos.implementation;

import org.springframework.stereotype.Service;
import practice.askmaterest.model.Tag;
import practice.askmaterest.services.daos.ITagDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagDao implements ITagDao {
    private final DataSource dataSource;

    public TagDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Tag add(Tag objToAdd) {
        try(Connection conn = dataSource.getConnection()){
            String query = "Insert Into tag (name) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, objToAdd.getName());
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

    @Override
    public void delete(long id) {
        try(Connection conn = dataSource.getConnection()){
            String query = "DELETE FROM tag WHERE id = ?";
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
    public Tag get(long id) {
        try(Connection conn = dataSource.getConnection()){
            String query = "SELECT id, name FROM tag WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())return null;
            return createTagFromQuery(resultSet);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tag> getAllTags() {
        try(Connection conn = dataSource.getConnection()){
            String query = "SELECT id, name FROM tag";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<Tag> queryResults = new ArrayList<>();
            while(resultSet.next())
            {
                queryResults.add(createTagFromQuery(resultSet));
            }
            return queryResults;
        }
            catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Tag createTagFromQuery(ResultSet resultSet) throws SQLException
    {
        Tag resultTag = new Tag();
        resultTag.setId(resultSet.getLong("id"));
        resultTag.setName(resultSet.getString("name"));
        return resultTag;
    }
}
