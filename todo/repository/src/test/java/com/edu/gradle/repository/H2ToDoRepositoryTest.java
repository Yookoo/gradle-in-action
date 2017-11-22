package com.edu.gradle.repository;

import com.edu.gradle.model.ToDoItem;
import com.edu.gradle.utils.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */
public class H2ToDoRepositoryTest {
    private static final String SQL_CREATE = "CREATE TABLE todo_item (id BIGINT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(255), completed TINYINT(1));";

    private ToDoRepository toDoRepository = new H2ToDoRepository();
    //@Before
    public void setUp() throws Exception {
        Connection connection = JDBCUtil.getH2Connection();
        PreparedStatement statement = connection.prepareStatement(SQL_CREATE);
        statement.execute();
        JDBCUtil.close(connection);
    }

    @Test
    public void findAll() throws Exception {
        List<ToDoItem> list = toDoRepository.findAll();
        for (ToDoItem toDoItem : list) {
            System.out.println(toDoItem.toString());
        }
    }

    @Test
    public void findById() throws Exception {
        ToDoItem toDoItem = toDoRepository.findById(1L);
        System.out.println(toDoItem.toString());

    }

    @Test
    public void insert() throws Exception {
        Long id = toDoRepository.insert(new ToDoItem("123", false));
        System.out.println("id :" + id);

    }

}