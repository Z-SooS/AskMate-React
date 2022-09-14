package practice.askmaterest.services.daos;

import practice.askmaterest.model.WebUser;

public interface IUserDao extends IDao<WebUser>{
    void updateEmail(long id, String newEmail);
    void updatePassword(long id, String newPassword);
    void updateReputation(long id, int newReputation);
    WebUser getByEmail(String email);
}
