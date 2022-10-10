package practice.askmaterest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
public class WebUser{

    @Column(unique = true)
    private String email;

    @Id
    private String username;
    @Column(length = 60)
    private String password;

    @Column(columnDefinition = "integer default 0")
    private int reputation;

    @OneToMany
    private Set<Friendship> friendships;

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
