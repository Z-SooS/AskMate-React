package practice.askmaterest.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import practice.askmaterest.model.Answer;
import practice.askmaterest.services.daos.AnswerRepo;

import java.util.List;


@Service
public class AnswerService {
    private final AnswerRepo answerRepo;

    private final int numberOfPreviewAnswers = 5;
    private final Pageable previewPage = PageRequest.of(0,numberOfPreviewAnswers,Sort.by(Sort.Direction.DESC,"score"));

    public AnswerService(AnswerRepo answerRepo) {
        this.answerRepo = answerRepo;
    }

    public List<Answer> getAnswerPreviewToPost(Long post) {
        return answerRepo.findAllByPost_Id(post,previewPage);
    }
    public List<Answer> getAnswersToPost(Long post, int page, String orderByColumn, String orderDirString) {
        Sort.Direction orderDir = orderDirString.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        int numberOfAnswersPerPage = 10;
        return answerRepo.findAllByPost_Id(post,PageRequest.of(page, numberOfAnswersPerPage,Sort.by(orderDir,orderByColumn)));
    }

    public void addAnswer(Answer newAnswer) {
        answerRepo.save(newAnswer);
    }
}
