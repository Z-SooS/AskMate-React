package practice.askmaterest.services.daos;

import practice.askmaterest.model.Answer;
import practice.askmaterest.utility.utilenums.GetQueryFor;

import java.util.List;

public interface IAnswerDao extends IDao<Answer>{
    List<Answer> getAllAnswersForUser(long searchedId, GetQueryFor target);
}
