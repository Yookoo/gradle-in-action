package com.todo.model;
/**
 * TODO 的实体
 * 
 * @author zhu
 *
 */
public class ToDoItem implements Comparable<ToDoItem>{

	private Long id;
	private String name;
	private boolean completed;

	@Override
	public int compareTo(ToDoItem o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ToDoItem [id=" + id + ", name=" + name + ", completed=" + completed + "]";
	}
	
	
}
