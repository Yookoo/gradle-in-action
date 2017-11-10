package com.todo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.todo.model.ToDoItem;
/**
 * ToDoRepository 在内存中的实现
 * 
 * @author zhu
 *
 */
public class InMemoryToDoRepository implements ToDoRepository {
	
	AtomicLong primaryKey = new AtomicLong();
	Map<Long,ToDoItem> toDoItemMap = new ConcurrentHashMap<>();
	
	@Override
	public List<ToDoItem> findAll() { 
		return new ArrayList<ToDoItem>(toDoItemMap.values());
	}

	@Override
	public ToDoItem findById(Long id) {
		return toDoItemMap.get(id);
	}

	@Override
	public Long insert(ToDoItem toDoItem) {
		Long id = primaryKey.incrementAndGet();
		toDoItem.setId(id);
		toDoItemMap.put(id, toDoItem);
		return id;
	}

	@Override
	public void update(ToDoItem toDoItem) {
		toDoItemMap.putIfAbsent(toDoItem.getId(), toDoItem);
	}

	@Override
	public void delete(ToDoItem toDoItem) {
		toDoItemMap.remove(toDoItem.getId());
	}

}
