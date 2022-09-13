package practice.askmaterest.model;

public class Comment extends DbObject{
    public long userId;
    public WebUser user;
    public long questionId;
    public long answerId;
    public String message;
}
