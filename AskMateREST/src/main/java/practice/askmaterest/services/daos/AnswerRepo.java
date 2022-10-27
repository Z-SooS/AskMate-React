package practice.askmaterest.services.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.askmaterest.model.Answer;

public interface AnswerRepo extends JpaRepository<Answer, Long> {
}
