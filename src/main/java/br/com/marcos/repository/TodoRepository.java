package br.com.marcos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.marcos.model.Todo;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String>{

}
