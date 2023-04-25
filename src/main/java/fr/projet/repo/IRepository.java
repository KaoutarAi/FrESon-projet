package fr.projet.repo;

import java.util.List;

public interface IRepository<T, ID> {
    public T save(T entity);
    public List<T> findAll();
    public void deleteById(ID id);
}
