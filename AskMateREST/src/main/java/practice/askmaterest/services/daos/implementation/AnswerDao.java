package practice.askmaterest.services.daos.implementation;

import org.springframework.stereotype.Service;
import practice.askmaterest.model.Answer;
import practice.askmaterest.services.daos.IAnswerDao;
import practice.askmaterest.utility.utilenums.GetQueryFor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerDao implements IAnswerDao {
    private final DataSource dataSource;

    public AnswerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Answer add(Answer objToAdd) {
        try(Connection conn = dataSource.getConnection())
        {
            String query = "INSERT INTO answer (user_id, question_id, message) VALUES (?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, objToAdd.getUserId());
            statement.setLong(2, objToAdd.getQuestionId());
            statement.setString(3, objToAdd.getMessage());
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
        try(Connection conn = dataSource.getConnection())
        {
            String query = "DELETE FROM answer WHERE id = ?";
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
    public Answer get(long id) {
        try(Connection conn = dataSource.getConnection())
        {
            String query = "SELECT id, user_id, question_id, message FROM answer WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? createAnswerFromResult(resultSet) : null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Answer> getAllAnswersForUser(long searchedId, GetQueryFor target) {
        try(Connection conn = dataSource.getConnection())
        {
            String column = target.equals(GetQueryFor.USER) ? "user_id" : "question_id";
            String query = String.format("SELECT id, user_id, question_id, message FROM answer WHERE %s = ?",column);
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1,searchedId);
            ResultSet resultSet = statement.executeQuery();
            List<Answer> answers = new ArrayList<>();
            while (resultSet.next())
            {
                answers.add(createAnswerFromResult(resultSet));
            }
            return answers;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Answer createAnswerFromResult(ResultSet resultSet) throws SQLException {
        Answer answer = new Answer();
        answer.setId(resultSet.getLong("id"));
        answer.setUserId(resultSet.getLong("user_id"));
        answer.setQuestionId(resultSet.getLong("question_id"));
        answer.setMessage(resultSet.getString("message"));
        return answer;
    }
}
