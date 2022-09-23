package practice.askmaterest.services.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.askmaterest.model.Friendship;

public interface FriendshipRepo extends JpaRepository<Friendship, Long> {
}
