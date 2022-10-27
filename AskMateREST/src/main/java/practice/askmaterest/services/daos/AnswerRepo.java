package practice.askmaterest.services.daos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.askmaterest.model.Answer;

import java.util.List;

public interface AnswerRepo extends JpaRepository<Answer, Long> {
    List<Answer> findAllByPost_Id(Long postId, Pageable pageable);
}
