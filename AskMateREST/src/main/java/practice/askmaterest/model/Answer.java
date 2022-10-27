package practice.askmaterest.model;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Answer{
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Post post;
    @ManyToOne(cascade=CascadeType.ALL)
    private WebUser user;
    private String message;
    private Timestamp dateCreated;
    @Column(columnDefinition = "integer default 0")
    private int score;
}
