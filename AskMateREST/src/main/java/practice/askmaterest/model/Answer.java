package practice.askmaterest.model;

public class Answer extends DbObject{
    public long questionId;
    public long userId;
    public WebUser user;
    public String message;
}
