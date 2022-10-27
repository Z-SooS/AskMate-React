package practice.askmaterest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.askmaterest.model.Post;
import practice.askmaterest.model.ResponsePostDetails;
import practice.askmaterest.model.Tag;
import practice.askmaterest.services.AnswerService;
import practice.askmaterest.services.PostService;
import practice.askmaterest.services.WebUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("${post_service_path}")
public class PostServiceController {
    private final PostService postService;
    private final AnswerService answerService;
    private final WebUserService webUserService;

    public PostServiceController(PostService postService, AnswerService answerService, WebUserService webUserService) {
        this.postService = postService;
        this.answerService = answerService;
        this.webUserService = webUserService;
    }
    @PostMapping("/add-post")
    public ResponseEntity<Post> addPost(@RequestBody Post newPost, HttpServletRequest request) {
        var webUser = webUserService.getUserByUsername(request.getHeader("user"));
        if(webUser == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        newPost.setUser(webUser);
        postService.SavePost(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/posts/{page}")
    public ResponseEntity<Set<ResponsePostDetails>> getPostsWithDetails(@PathVariable int page,
                                                        @RequestParam(defaultValue = "score") String orderBy,
                                                        @RequestParam(defaultValue = "DESC") String orderDirString) {
        var posts = postService.getPostsForPage(page,orderBy, orderDirString);
        return getResponseEntityDetailSet(posts);
    }

        @GetMapping("/tagged-posts/{page}")
    public ResponseEntity<Set<ResponsePostDetails>> getPostDetailsTaggedWith(@PathVariable int page,
                                                              @RequestParam(defaultValue = "score") String orderBy,
                                                              @RequestParam(defaultValue = "DESC") String orderDirString,
                                                              @RequestParam int[] tagIds) {
        Set<Tag> tagSet = new HashSet<>();
        Arrays.stream(tagIds).forEach(tid -> tagSet.add(Tag.builder().id(tid).build()));
        var posts = postService.getPostsForPageWithTag(page,tagSet,orderBy,orderDirString);
        return getResponseEntityDetailSet(posts);
    }

    private ResponseEntity<Set<ResponsePostDetails>> getResponseEntityDetailSet(List<Post> posts) {
        if (posts.size()<=0) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var details = new HashSet<ResponsePostDetails>();
        for (Post post : posts) {
            var answersToPost = answerService.getAnswerPreviewToPost(post);
            details.add(ResponsePostDetails.builder().post(post).answers(answersToPost).build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(details);
    }
}
