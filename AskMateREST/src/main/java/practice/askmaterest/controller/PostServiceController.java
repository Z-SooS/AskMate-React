package practice.askmaterest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.askmaterest.model.Post;
import practice.askmaterest.model.ResponsePostDetails;
import practice.askmaterest.model.WebUser;
import practice.askmaterest.security.CookieMethods;
import practice.askmaterest.security.EncoderAgent;
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
    private final WebUserService webUserService;

    private final EncoderAgent encoderAgent;

    public PostServiceController(PostService postService, WebUserService webUserService, EncoderAgent encoderAgent) {
        this.postService = postService;
        this.webUserService = webUserService;
        this.encoderAgent = encoderAgent;
    }
    @PostMapping("/add-post")
    public ResponseEntity<Post> addPost(@RequestBody Post newPost, HttpServletRequest request) {
        String username = encoderAgent.getValueFromJwtCookie("subject", CookieMethods.getCookieValue(request,CookieMethods.headerPayloadCookie64)+"."+CookieMethods.getCookieValue(request,CookieMethods.signatureCookie64));
        WebUser webUser = webUserService.getUserByUsername(username);
        if(webUser == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        newPost.setUser(webUser);
        postService.SavePost(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/posts/{page}")
    public ResponseEntity<Set<ResponsePostDetails>> getPostsWithDetails(@PathVariable int page,
                                                        @RequestParam(name="order",defaultValue = "score") String orderBy,
                                                        @RequestParam(name="direction",defaultValue = "DESC") String orderDirString) {
        var posts = postService.getPostsForPage(page,orderBy, orderDirString);
        return getResponseEntityDetailSet(posts);
    }

        @GetMapping(value = "/posts/{page}", params = "tag")
    public ResponseEntity<Set<ResponsePostDetails>> getPostDetailsTaggedWith(@PathVariable int page,
                                                                             @RequestParam(name="order",defaultValue = "score") String orderBy,
                                                                             @RequestParam(name="direction",defaultValue = "DESC") String orderDirString,
                                                                             @RequestParam("tag") int[] tagIds) {
        Set<Integer> tagSet = new HashSet<>();
        Arrays.stream(tagIds).forEach(tagSet::add);
        var posts = postService.getPostsForPageWithTag(page,tagSet,orderBy,orderDirString);
        return getResponseEntityDetailSet(posts);
    }

    private ResponseEntity<Set<ResponsePostDetails>> getResponseEntityDetailSet(List<Post> posts) {
        if (posts.size()<=0) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var details = new HashSet<ResponsePostDetails>();
        for (Post post : posts) {
            details.add(ResponsePostDetails.ConvertFromPost(post));
        }
        return ResponseEntity.status(HttpStatus.OK).body(details);
    }
}
