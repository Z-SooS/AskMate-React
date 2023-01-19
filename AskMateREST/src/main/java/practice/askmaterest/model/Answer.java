package practice.askmaterest.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @JsonIgnore
    private Post post;
    @ManyToOne(cascade=CascadeType.ALL)
    private WebUser user;
    private String message;
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp dateCreated;
    @Column(columnDefinition = "integer default 0")
    private int score;
}
