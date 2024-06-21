package br.com.marcos.model;

import java.util.Date;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.marcos.record.TodoDto;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "todo-list")
public class Todo {

	@Id
	private String id;
	
	 @NotBlank
	private String todo;
	@NotBlank
	private String description;
	
	private Boolean completed;
	
	private int priority;
	
	private Date createdAt;
	
	private Date updateAt;

	public Todo(String id, String todo, String description, Boolean completed,int priority, Date createdAt, Date updateAt) {
		this.id = id;
		this.todo = todo;
		this.description = description;
		this.completed = completed;
		this.priority = priority;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
	}
	
	

	public Todo(String id, @NotBlank String todo, @NotBlank String description, Boolean completed, int priority) {
		super();
		this.id = id;
		this.todo = todo;
		this.description = description;
		this.completed = completed;
		this.priority = priority;
	}



	public Todo(String todo, String description, Boolean completed,int priority, Date createdAt, Date updateAt) {
		this.todo = todo;
		this.description = description;
		this.completed = completed;
		this.priority = priority;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
	}
	
	public Todo(TodoDto todoDto,Date createdAt, Date updateAt) {
		this.todo = todoDto.todo();
		this.description = todoDto.description();
		this.completed = todoDto.completed();
		this.priority = todoDto.priority();
		this.createdAt = createdAt;
		this.updateAt = updateAt;
	}
	
	

	public Todo(String todo, String description, Boolean completed, int priority) {
		super();
		this.todo = todo;
		this.description = description;
		this.completed = completed;
		this.priority = priority;
	}

	public Todo() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
	
	
	
	
}
