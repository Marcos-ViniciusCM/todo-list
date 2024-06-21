package br.com.marcos.record;

import java.util.Date;

public record TodoDto(String todo,String description, Boolean completed,int priority) {

}
