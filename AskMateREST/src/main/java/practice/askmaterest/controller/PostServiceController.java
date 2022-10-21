package practice.askmaterest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.askmaterest.model.Post;
import practice.askmaterest.model.ResponsePostDetails;
import practice.askmaterest.services.AnswerService;
import practice.askmaterest.services.PostService;
import practice.askmaterest.services.TagService;
import practice.askmaterest.services.WebUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("${post_service_path}")
public class PostServiceController {
    private final PostService postService;
    private final AnswerService answerService;
    private final TagService tagService;
    private final WebUserService webUserService;

    public PostServiceController(PostService postService, AnswerService answerService, TagService tagService, WebUserService webUserService) {
        this.postService = postService;
        this.answerService = answerService;
        this.tagService = tagService;
        this.webUserService = webUserService;
    }
    @PostMapping("/add-post")
    public ResponseEntity<Post> addPost(@RequestBody Post newPost, HttpServletRequest request) {
        // TODO: 2022. 10. 21. Web user service dies from null username
        System.out.println(request.getHeader("user"));
        var optionalWebUser = webUserService.getUserByUsername(request.getHeader("user"));
        if(optionalWebUser.isEmpty()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        newPost.setUser(optionalWebUser.get());
        System.out.println(newPost.getUser().getUsername());
        return ResponseEntity.status(HttpStatus.CONTINUE).build();
    }

    @GetMapping("/get-post-page/{page}")
    public Set<ResponsePostDetails> getPostsWithDetails(@PathVariable int page) {
        var posts = postService.getPostsForPage(page);
        var details = new HashSet<ResponsePostDetails>();
        for (Post post : posts) {
            var answersToPost = answerService.getAnswerPreviewToPost(post);
            details.add(ResponsePostDetails.builder().post(post).answers(answersToPost).build());
        }
        return details;
    }


}
