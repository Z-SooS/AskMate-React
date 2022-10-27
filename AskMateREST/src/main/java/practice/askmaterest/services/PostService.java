package practice.askmaterest.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import practice.askmaterest.model.Post;
import practice.askmaterest.model.Tag;
import practice.askmaterest.services.daos.PostRepo;

import java.util.List;
import java.util.Set;

@Service
public class PostService {
    private final PostRepo postRepo;

    private final int numberOfPostsPerPage = 6;

    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public List<Post> getPostsForPage(int page, String orderColumn, String orderDirString) {
        return postRepo.findAll(PageRequest.of(page,numberOfPostsPerPage),
                Sort.by(orderDirString.equals("DESC")? Sort.Direction.DESC: Sort.Direction.ASC,orderColumn));
    }
    public List<Post> getPostsForPageWithTag(int page, Set<Tag> tags, String orderColumn, String orderDirString) {
        return postRepo.findAllByTags(tags,
                PageRequest.of(page,numberOfPostsPerPage),
                Sort.by(orderDirString.equals("DESC")? Sort.Direction.DESC: Sort.Direction.ASC,orderColumn));
    }

    public void SavePost(Post post) {
        postRepo.save(post);
    }
}
