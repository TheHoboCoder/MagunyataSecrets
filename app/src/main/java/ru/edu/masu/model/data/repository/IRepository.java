package ru.edu.masu.model.data.repository;

import java.io.IOException;
import java.util.List;

public interface IRepository<T> {
    List<T> getAll() throws IOException;
    void update(T item);
}
