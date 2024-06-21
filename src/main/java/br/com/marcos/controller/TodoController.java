package br.com.marcos.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcos.model.Todo;
import br.com.marcos.record.TodoDto;
import br.com.marcos.record.TodoUpdateDto;
import br.com.marcos.repository.TodoRepository;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {
	@Autowired
	private TodoRepository todoRepository;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllTodo(){
		Sort sort = Sort.by("priority").descending().and(Sort.by("todo").ascending());
		List<Todo> todos = todoRepository.findAll(sort);
		if(todos.size() > 0) {
			return new ResponseEntity<List<Todo>>(todos,HttpStatus.OK);
		}
		return new ResponseEntity<>("No todos avaliable", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Todo> saveTodo(@RequestBody @Valid TodoDto todoDto){
		Todo todo = new Todo(todoDto, new Date(), new Date());
		todoRepository.save(todo);
		return  ResponseEntity.ok(todo);
	}
	
	@DeleteMapping(value ="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteTodo(@PathVariable("id")String id){
		todoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTodo(@RequestBody TodoUpdateDto todoDto){
		var entity = todoRepository.findById(todoDto.id());
		if(entity.isPresent()) {
		Todo updateTodo = entity.get();
		updateTodo.setTodo(todoDto.todo());
		updateTodo.setDescription(todoDto.description());
		updateTodo.setPriority(todoDto.priority());
		updateTodo.setUpdateAt(new Date());
		todoRepository.save(updateTodo);
		return ResponseEntity.ok(updateTodo);
		}else {
			return new ResponseEntity<>("Todo not found with id "+todoDto.id(),HttpStatus.NOT_FOUND);
		}
	}
	

	
}