package practice.askmaterest.model;

public class Question extends DbObject{
    public long userId;
    public WebUser user;
    public String title;
    public String message;
}
