package practice.askmaterest.services.daos;

import practice.askmaterest.model.Tag;

import java.util.List;

public interface ITagDao extends IDao<Tag>{
    List<Tag> getAllTags();
}
