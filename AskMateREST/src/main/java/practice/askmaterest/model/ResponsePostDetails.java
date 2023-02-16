package practice.askmaterest.model;

import lombok.Getter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
public class ResponsePostDetails {
    private Long id;
    private WebUser user;
    private Timestamp dateCreated;
    private Set<Tag> tags;
    private String title;
    private String message;
    private int numberOfAnswers;
    private int score;

    public static ResponsePostDetails ConvertFromPost(Post post) {
        var result = new ResponsePostDetails();
        result.id = post.getId();
        result.user = post.getUser();
        result.dateCreated  = post.getDateCreated();
        result.score = post.getScore();
        result.title = post.getTitle();
        result.message = post.getMessage();
        result.tags = post.getTags();
        result.numberOfAnswers = post.getAnswers().size();
        return result;
    }
}
