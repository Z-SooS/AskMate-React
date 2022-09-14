package practice.askmaterest.services.daos;

public interface IDao<T> {
    T add(T objToAdd);
    void delete(long id);
    T get(long id);

}
