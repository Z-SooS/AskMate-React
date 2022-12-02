package practice.askmaterest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.askmaterest.model.Answer;
import practice.askmaterest.model.NewAnswerRequest;
import practice.askmaterest.model.Post;
import practice.askmaterest.model.WebUser;
import practice.askmaterest.security.CookieMethods;
import practice.askmaterest.security.EncoderAgent;
import practice.askmaterest.services.AnswerService;
import practice.askmaterest.services.PostService;
import practice.askmaterest.services.WebUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${answer_service_path}")
@AllArgsConstructor
public class AnswerController {
    private AnswerService answerService;
    private PostService postService;
    private WebUserService webUserService;
    private EncoderAgent encoderAgent;

    @GetMapping("/{postId}/{page}")
    public ResponseEntity<List<Answer>> getAnswersForPost(@PathVariable long postId,
                                                         @PathVariable int page,
                                                         @RequestParam(name="order",defaultValue = "score") String orderBy,
                                                         @RequestParam(name="direction",defaultValue = "DESC") String orderDirString) {
        var answers = answerService.getAnswersToPost(postId,page,orderBy,orderDirString);
        if (answers.size() <= 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.OK).body(answers);
    }

    @PostMapping("/add-answer")
    public void addAnswer(@RequestBody NewAnswerRequest addedAnswer, HttpServletRequest request) {
        Post answeredPost = postService.getPost(addedAnswer.getPostId());
        String username = encoderAgent.getValueFromJwtCookie("subject", CookieMethods.getCookieValue(request,CookieMethods.headerPayloadCookie64)+"."+CookieMethods.getCookieValue(request,CookieMethods.signatureCookie64));
        WebUser answeringUser = webUserService.getUserByUsername(username);
        answerService.addAnswer(addedAnswer.toAnswer(answeredPost,answeringUser));
    }
}
