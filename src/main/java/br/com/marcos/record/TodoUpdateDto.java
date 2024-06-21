package br.com.marcos.record;

public record TodoUpdateDto(String id,String todo,String description, Boolean completed,int priority) {

}
