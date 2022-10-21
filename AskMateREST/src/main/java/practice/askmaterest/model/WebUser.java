package practice.askmaterest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import practice.askmaterest.model.modelenum.AskRole;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
public class WebUser {

    @Column(unique = true)
    private String email;

    @Id
    private String username;
    @Column(length = 60)
    private String password;

    @Column(columnDefinition = "integer default 0")
    private int reputation;

    @Enumerated(EnumType.STRING)
    private AskRole role;

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
