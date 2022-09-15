package practice.askmaterest.services;

import org.springframework.stereotype.Service;

import practice.askmaterest.services.daos.IAnswerDao;
import practice.askmaterest.services.daos.IQuestionDao;
import practice.askmaterest.services.daos.ITagDao;
import practice.askmaterest.services.daos.IUserDao;

@Service
public class DaoService {

    public final IUserDao userDao;
    public final ITagDao tagDao;
    public final IAnswerDao answerDao;
    public final IQuestionDao questionDao;

    public DaoService(IUserDao userDao, ITagDao tagDao, IAnswerDao answerDao, IQuestionDao questionDao) {
        this.userDao = userDao;
        this.tagDao = tagDao;
        this.answerDao = answerDao;
        this.questionDao = questionDao;
    }
}
