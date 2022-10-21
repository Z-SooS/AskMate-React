package practice.askmaterest.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    private Timestamp dateCreated;
}
