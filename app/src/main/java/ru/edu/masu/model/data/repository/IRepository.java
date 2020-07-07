package ru.edu.masu.model.data.repository;

import java.util.List;

public interface IRepository<T> {
    List<T> getAll();
    void update(T item);
}
