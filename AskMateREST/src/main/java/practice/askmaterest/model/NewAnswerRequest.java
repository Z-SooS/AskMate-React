package practice.askmaterest.model;

import lombok.Getter;

@Getter
public class NewAnswerRequest {
    private String message;
    private Long postId;

    public Answer toAnswer(Post repliedPost, WebUser currentUser) {
        return Answer.builder().message(message).post(repliedPost).user(currentUser).build();
    }
}
