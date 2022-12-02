package practice.askmaterest.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponsePostDetails {
    private Post post;
    private List<Answer> answers;
}
