package practice.askmaterest.model;


import lombok.*;
import practice.askmaterest.model.modelenum.FriendStatus;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Friendship {
    @Id
    private Long id;
    @ManyToOne
    private WebUser friend;
    private FriendStatus status;
}
