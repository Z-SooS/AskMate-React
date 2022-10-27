package practice.askmaterest.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Answer{
    @Id
    private Long id;
    @ManyToOne
    private Post post;
    @ManyToOne
    private WebUser user;
    private String message;
    private String imageUrl;
}
