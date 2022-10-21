package practice.askmaterest.services.daos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.askmaterest.model.Answer;
import practice.askmaterest.model.Post;

import java.util.List;

public interface AnswerRepo extends JpaRepository<Answer, Long> {
    List<Answer> findAllByPostOrderById(Post post, Pageable pageable);
}
