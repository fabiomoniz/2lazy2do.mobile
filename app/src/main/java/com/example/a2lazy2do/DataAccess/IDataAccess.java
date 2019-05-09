package com.example.a2lazy2do.DataAccess;

import com.example.a2lazy2do.BE.Task;

import java.util.ArrayList;

public interface IDataAccess {

    void delete();

    void update(Task t);

    ArrayList<Task> getAll();
}
