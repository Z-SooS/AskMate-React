package practice.askmaterest.model;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Post {
    @Id
    private Long id;
    @ManyToOne
    private WebUser user;
    private String title;
    private String message;
    @OneToMany
    private Set<Tag> tags;
    @Column(columnDefinition = "integer default 0")
    private int score;
    private Timestamp dateCreated;
}
