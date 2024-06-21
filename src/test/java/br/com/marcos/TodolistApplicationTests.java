package br.com.marcos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.RequestBodySpec;

import br.com.marcos.model.Todo;
@SpringBootTest(classes = TodolistApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT)

@AutoConfigureWebTestClient
@ActiveProfiles("test")

class TodolistApplicationTests {

	@Autowired
	private WebTestClient webTestClient;
	
	
	@Test
	void createTodoSucces() {


		var todo = new Todo("todo 1","descricao todo 1",false,1);
		
		webTestClient.post()
		.uri("/todo")
		.bodyValue(todo)
		.exchange().
		expectStatus().isOk()
		.expectBody()
		.jsonPath("$.todo").isEqualTo(todo.getTodo())
		.jsonPath("$.description").isEqualTo(todo.getDescription())
		.jsonPath("$.priority").isEqualTo(todo.getPriority())
		.jsonPath("$.completed").isEqualTo(todo.getCompleted());
		}
	
	@Test
	void createTodoFailure() {
		webTestClient.post()
		.uri("/todo")
		.bodyValue(new Todo("","",false,0)).exchange()
		.expectStatus().isBadRequest();
	}
	
	@Test
	void updateTodoSucess() {
		var todo = new Todo("todo update sucess","descricao todo update  sucess",false,1);
		
		var response = webTestClient.post()
				 	.uri("/todo")
		            .contentType(MediaType.APPLICATION_JSON)
		            .bodyValue(todo)
		            .exchange()
		            .expectStatus().isOk()
		            .expectBody(Todo.class)
		            .returnResult().getResponseBody();
		
	  assertNotNull(response);
	  assertNotNull(response.getId());
	  
	  response.setTodo("todo 1 atualizado sucess");
	  response.setDescription("descricao atualizado sucess");
	  
	  webTestClient.put()
	  .uri("/todo")
      .contentType(MediaType.APPLICATION_JSON)
	  .bodyValue(response)
      .exchange()
      .expectStatus().isOk();
	}
	
	@Test
	void updateTodoFailure() {
		var todo = new Todo("todo update failure","descricao todo update failure",false,1);
		
		
	  
	  webTestClient.put()
	  .uri("/todo")
      .contentType(MediaType.APPLICATION_JSON)
	  .bodyValue(todo)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody(String.class)
      .isEqualTo("Todo not found with id");;
	}
	
	@Test
	void getTodoSucess() {
		var todo = new Todo("todo get Sucess","descricao todo get Sucess",false,1);
		var response = webTestClient.post()
				.uri("/todo")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(todo)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Todo.class)
				.returnResult().getResponseBody();
		
		
		assertNotNull(response);
		assertNotNull(response.getId());
		  // Obtenção de todos os Todos
	    webTestClient.get()
	        .uri("/todo")
	        .exchange()
	        .expectStatus().isOk()
	        .expectBodyList(Todo.class)
	        .value(todoList -> {
	            assertNotNull(todoList);
	            assertFalse(todoList.isEmpty());
	            
	            // Verifica se o Todo recém-criado está na lista
	            var createdTodo = todoList.stream()
	                                      .filter(t -> t.getId().equals(response.getId()))
	                                      .findFirst()
	                                      .orElse(null);
	                                      
	            assertNotNull(createdTodo);
	            assertEquals(todo.getTodo(), createdTodo.getTodo());
	            assertEquals(todo.getDescription(), createdTodo.getDescription());
	            assertEquals(todo.getCompleted(), createdTodo.getCompleted());
	            assertEquals(todo.getPriority(), createdTodo.getPriority());
	        });
	}
	
	@Test
	void getTodoFailure() {
	    webTestClient.get()
	        .uri("/todo")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus().isNotFound()
	        .expectBody(String.class)
	        .isEqualTo("No todos avaliable");
	          
	}
	
	@Test
	void deleteTodoSucess() {
		var todo = new Todo("todo delete succes","description delte sucess",false,1);
		
		var response = webTestClient.post()
				.uri("/todo")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(todo)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Todo.class)
				.returnResult().getResponseBody();
		


		
		((RequestBodySpec) webTestClient.delete()
        .uri("/todo/{id}", response.getId()))
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(todo)
        .exchange()
        .expectStatus().isNoContent();
	}
	
	@Test
	void deleteTodoFailure() {
		
		((RequestBodySpec) webTestClient.delete()
        .uri("/todo/{id}",99999))
        .contentType(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isNotFound();
	}
	

}
