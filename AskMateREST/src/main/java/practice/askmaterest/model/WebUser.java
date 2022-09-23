package practice.askmaterest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
public class WebUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 8647904518137212569L;
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
