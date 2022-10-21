package practice.askmaterest.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import practice.askmaterest.model.Post;
import practice.askmaterest.services.daos.PostRepo;

import java.util.List;

@Service
public class PostService {
    private final PostRepo postRepo;

    private final int numberOfPostsPerPage = 6;

    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public List<Post> getPostsForPage(int page) {
        return postRepo.findAll(PageRequest.of(page,numberOfPostsPerPage)).getContent();
    }
}
