package com.edu.gradle.repository;

import com.edu.gradle.model.ToDoItem;
import com.edu.gradle.utils.JDBCUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/22.
 */
public class H2ToDoRepository implements ToDoRepository {

    private static final String SQL_FIND_ALL = "SELECT id,name,completed FROM todo_item;";
    private static final String SQL_FIND_BY_ID = "SELECT id,name,completed FROM todo_item WHERE id = ?;";
    private static final String SQL_CREATE = "CREATE TABLE todo_item (id BIGINT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(255), completed TINYINT(1));";
    private static final String SQL_INSERT = "INSERT INTO todo_item (name, completed) VALUES(?, ?);";
    private static final String SQL_FIND_STATUS = "SELECT id,name,completed FROM todo_item WHERE completed = ?;";
    private static final String SQL_UPDATE = "UPDATE todo_item SET name=?, completed= ? WHERE ID=?;";
    private static final String SQL_DELETE = "delete from todo_item where id = ? ;";

    /**
     * 查询所有
     *
     * @return
     */

    @Override
    public List<ToDoItem> findAll() {

        return this.queryList(SQL_FIND_ALL, null);
    }

    @Override
    public List<ToDoItem> findAllActive() {
        List prams = new ArrayList<>();
        prams.add(false);
        return this.queryList(SQL_FIND_STATUS, prams);
    }

    @Override
    public List<ToDoItem> findAllCompleted() {
        List prams = new ArrayList<>();
        prams.add(true);
        return this.queryList(SQL_FIND_STATUS, prams);
    }

    @Override
    public ToDoItem findById(Long id) {

        Connection connection = null;
        ToDoItem toDoItem = new ToDoItem();

        List prarms = new ArrayList<>();
        prarms.add(id);

        try {
            connection = JDBCUtil.getH2Connection();
            Map map = JDBCUtil.executeQueryOne(connection, SQL_FIND_BY_ID, prarms);
            toDoItem = changeMapToTodoItem(map);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(connection);
        }
        return toDoItem;
    }


    @Override
    public Long insert(ToDoItem toDoItem) {
        Connection connection = null;
        Long key = 0L;
        List prarms = new ArrayList<>();
        prarms.add(toDoItem.getName());
        prarms.add(toDoItem.isCompleted());
        try {
            connection = JDBCUtil.getH2Connection();
            key = JDBCUtil.executeInsert(connection, SQL_INSERT, prarms);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(connection);
        }
        return key;
    }

    @Override
    public void update(ToDoItem toDoItem) {
        Connection connection = null;
        List prarms = new ArrayList<>();
        prarms.add(toDoItem.getName());
        prarms.add(toDoItem.isCompleted());
        prarms.add(toDoItem.getId());
        try {
            connection = JDBCUtil.getH2Connection();
            JDBCUtil.executeInsert(connection, SQL_UPDATE, prarms);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(connection);
        }
    }

    @Override
    public void delete(ToDoItem toDoItem) {
        Connection connection = null;
        List prarms = new ArrayList<>();
        prarms.add(toDoItem.getId());
        try {
            connection = JDBCUtil.getH2Connection();
            JDBCUtil.executeInsert(connection, SQL_DELETE, prarms);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(connection);
        }
    }

    private List<ToDoItem> queryList(String Sql, List prams){

        List<ToDoItem> list = new ArrayList<>();
        Connection connection = null;
        ToDoItem toDoItem;
        List prams = new ArrayList<>();
        prams.add(true);
        try {
            connection = JDBCUtil.getH2Connection();
            List<Map<String, Object>> maps = JDBCUtil.executeQueryList(connection, Sql, prams);
            for (Map<String, Object> map : maps) {
                toDoItem = changeMapToTodoItem(map);
                list.add(toDoItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(connection);
        }
        return list;
    }

    private ToDoItem changeMapToTodoItem(Map map) {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setId((Long) map.get("ID"));
        toDoItem.setName((String) map.get("NAME"));
        toDoItem.setCompleted(Boolean.parseBoolean(String.valueOf(map.get("COMPLETED"))));
        return toDoItem;
    }

}
