package com.todo.repository;

import java.util.List;

import com.todo.model.ToDoItem;

/**
 * Todo 资源库
 * @author zhu
 *
 */
public interface ToDoRepository {

	/**
	 * 查询所有
	 * @return
	 */
	List<ToDoItem> findAll();
	/**
	 * 查询单个
	 * @param id
	 * @return
	 */
	ToDoItem findById(Long id);
	/**
	 * 增
	 * @param toDoItem
	 * @return
	 */
	Long insert(ToDoItem toDoItem);
	/**
	 * 改
	 * @param toDoItem
	 */
	void update(ToDoItem toDoItem);
	/**
	 * 删
	 * @param toDoItem
	 */
	void delete(ToDoItem toDoItem);
}
