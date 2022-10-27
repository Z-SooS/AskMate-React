package practice.askmaterest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.askmaterest.model.Answer;
import practice.askmaterest.services.AnswerService;

import java.util.List;

@RestController
@RequestMapping("${answer_service_path}")
@AllArgsConstructor
public class AnswerController {
    private AnswerService answerService;

    @GetMapping("/{postId}/{page}")
    public ResponseEntity<List<Answer>> getAnswersForPost(@PathVariable long postId,
                                                         @PathVariable int page,
                                                         @RequestParam(name="order",defaultValue = "score") String orderBy,
                                                         @RequestParam(name="direction",defaultValue = "DESC") String orderDirString) {
        var answers = answerService.getAnswersToPost(postId,page,orderBy,orderDirString);
        if (answers.size() <= 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.OK).body(answers);
    }
}
