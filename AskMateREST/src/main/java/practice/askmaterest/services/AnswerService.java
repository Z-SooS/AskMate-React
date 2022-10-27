package practice.askmaterest.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import practice.askmaterest.model.Answer;
import practice.askmaterest.model.Post;
import practice.askmaterest.services.daos.AnswerRepo;

import java.util.List;


@Service
public class AnswerService {
    private final AnswerRepo answerRepo;

    private final int numberOfPreviewAnswers = 5;
    private final Pageable previewPage = PageRequest.of(0,numberOfPreviewAnswers);

    public AnswerService(AnswerRepo answerRepo) {
        this.answerRepo = answerRepo;
    }

    public List<Answer> getAnswerPreviewToPost(Long post) {
        return answerRepo.findAllByPost_Id(post,previewPage, Sort.by(Sort.Direction.DESC,"score"));
    }
    public List<Answer> getAnswersToPost(Post post, int page) {
        int numberOfAnswersPerPage = 10;
        return answerRepo.findAllByPostOrderById(post,PageRequest.of(page, numberOfAnswersPerPage));
    }
}
