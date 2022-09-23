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
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private int reputation;
    @OneToMany
    private Set<Friendship> friendships;


    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
