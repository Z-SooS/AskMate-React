package practice.askmaterest.services.daos.implementation;

import org.springframework.stereotype.Service;
import practice.askmaterest.model.Question;
import practice.askmaterest.services.daos.IQuestionDao;
import practice.askmaterest.utility.utilenums.TagOperation;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionDao implements IQuestionDao {
    private final DataSource dataSource;

    public QuestionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Question add(Question objToAdd) {
        try(Connection conn = dataSource.getConnection())
        {
            String query = "INSERT INTO question (user_id, title, message) VALUES (?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1,objToAdd.getUserId());
            statement.setString(2,objToAdd.getTitle());
            statement.setString(3,objToAdd.getMessage());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
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
        try(Connection conn = dataSource.getConnection())
        {
            String query = "DELETE FROM question WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1,id);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Question get(long id) {
        try(Connection conn = dataSource.getConnection())
        {
            String query = "SELECT user_id, title, message FROM question WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            Question queryQuestion = new Question();
            queryQuestion.setId(id);
            queryQuestion.setUserId(resultSet.getLong("user_id"));
            queryQuestion.setTitle( resultSet.getString("title"));
            queryQuestion.setMessage( resultSet.getString("message"));
            return queryQuestion;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Question> getQuestionsForUser(long userId) {
        try(Connection conn = dataSource.getConnection())
        {
            String query = "SELECT id, user_id, title, message FROM question WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1,userId);
            return readQuestions(statement.executeQuery());
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void tagQuestion(long questionId, int tagId, TagOperation operation) {
        try(Connection conn = dataSource.getConnection())
        {
            String query = operation.equals(TagOperation.TAG)
                    ?"INSERT INTO question_tag VALUES (?,?)"
                    :"DELETE FROM question_tag WHERE question_id = ? AND tag_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1,questionId);
            statement.setInt(2,tagId);
            statement.executeUpdate();
        }
            catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Question> getQuestionsForTag(int tagId) {
        try(Connection conn = dataSource.getConnection())
        {
            String query = "SELECT id, user_id, title, message FROM question q" +
                    " INNER JOIN question_tag qt ON q.id = qt.question_id " +
                    "WHERE qt.tag_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,tagId);
            return readQuestions(statement.executeQuery());
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private List<Question> readQuestions(ResultSet resultSet) throws SQLException {
        List<Question> result = new ArrayList<>();
        while(resultSet.next())
        {
            Question resultQuestion = new Question();
            resultQuestion.setId(resultSet.getLong("id"));
            resultQuestion.setUserId(resultSet.getLong("user_id"));
            resultQuestion.setTitle(resultSet.getString("title"));
            resultQuestion.setMessage(resultSet.getString("message"));
            result.add(resultQuestion);
        }
        return result;
    }
}
