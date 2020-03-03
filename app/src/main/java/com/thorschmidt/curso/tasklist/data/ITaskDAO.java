package com.thorschmidt.curso.tasklist.data;

import com.thorschmidt.curso.tasklist.model.Task;

import java.util.List;

public interface ITaskDAO {

    boolean save(Task task);
    boolean update(Task task);
    boolean delete(Task task);
    List<Task> list();

}
