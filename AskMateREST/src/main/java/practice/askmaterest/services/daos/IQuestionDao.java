package practice.askmaterest.services.daos;

import practice.askmaterest.model.Question;
import practice.askmaterest.utility.utilenums.TagOperation;

import java.util.List;

public interface IQuestionDao extends IDao<Question> {
    List<Question> getQuestionsForUser(long userId);
    void tagQuestion(long questionId, int tagId, TagOperation operation);
    List<Question> getQuestionsForTag(int tagId);
}
