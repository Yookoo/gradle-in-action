package com.edu.gradle.repository;

import com.edu.gradle.model.ToDoItem;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ToDoRepositoryTest {

    private ToDoRepository toDoRepository;
    @Before
    public void setUp() throws Exception {
        toDoRepository = new InMemoryToDoRepository();
    }

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void findAllActive() throws Exception {
    }

    @Test
    public void findAllCompleted() throws Exception {
    }

    @Test
    public void findById() throws Exception {
    }

    @Test
    public void insert() throws Exception {
//        ToDoItem toDoItem = new ToDoItem();
//        toDoItem.setName("测试");
//        toDoItem.setCompleted(false);
//        toDoRepository.insert(toDoItem);
//
//        System.out.println(list.size());

        int items = System.getProperty("items") != null ?Integer.parseInt(System.getProperty("items")):1;
        createAndInsertTodoItems(items);
        List<ToDoItem> list = toDoRepository.findAll();
        assertEquals(items, list.size());
    }

    private void createAndInsertTodoItems(int items) {
        System.out.println("Creating " + items + "todo items!");

        for(int i = 1;i <= items; i++){
            ToDoItem toDoItem = new ToDoItem();
            toDoItem.setName("TO DO task " + i);
            toDoRepository.insert(toDoItem);
        }
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

}