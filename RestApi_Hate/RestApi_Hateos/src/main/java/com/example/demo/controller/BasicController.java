package com.example.demo.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Todo;
import com.example.demo.model.TodoResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/basic")
public class BasicController {
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value = "/todo")
	public Todo basic() {
		return new Todo(counter.incrementAndGet(), "라면사오기");
	}
	
	@RequestMapping(value = "/todop", method = RequestMethod.POST)
	public Todo postBasic(@RequestParam(value = "todoTitle") String todoTitle) {
		return new Todo(counter.incrementAndGet(), todoTitle);
	}
	
	@RequestMapping(value = "/todor", method = RequestMethod.POST)
	public ResponseEntity<Todo> postBasicResponseEntity(@RequestParam(value = "todoTitle") String todoTitle){
		return new ResponseEntity<Todo>(new Todo(counter.incrementAndGet(), todoTitle), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/todos/{todoId}", method = RequestMethod.GET)
	public Todo getPath(@PathVariable int todoId) {
		Todo todo1 = new Todo(1L, "문서쓰기");
		Todo todo2 = new Todo(2L, "기획회의");
		Todo todo3 = new Todo(3L, "운동");
		
		Map<Integer, Todo> todoMap = new HashMap<>();
		todoMap.put(1, todo1);
		todoMap.put(2, todo2);
		todoMap.put(3, todo3);
		
		return todoMap.get(todoId);
	}
	
	@RequestMapping(value = "/todoh", method = RequestMethod.GET)
	public ResponseEntity<TodoResource> geth(@RequestParam(value = "todoTitle") String todoTitle){
		TodoResource todoResource = new TodoResource(todoTitle);
		todoResource.add(linkTo(methodOn(BasicController.class).geth(todoTitle)).withSelfRel());
		
		return new ResponseEntity<TodoResource>(todoResource, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/todou")
	public URI getUri() {
		URI uri = UriComponentsBuilder.newInstance().scheme("http")
				.host("movie.naver.com")
				.port(80)
				.path("/movie/sdk/rank/rmovie.nhn")
				.build()
				.encode()
				.toUri();
		return uri;
	}
	
	@RequestMapping(value = "/todom")
	public URI getUriParam() {
		URI uri = UriComponentsBuilder.newInstance().scheme("http")
				.host("movie.naver.com")
				.port(80)
				.path("/movie/bi/mi/basic.nhn")
				.queryParam("code", 146506)
				.build()
				.encode()
				.toUri();
		return uri;
	}
	
	@RequestMapping(value = "/todov")
	public URI getUriPath() {
		String bookId = "1111";
		String id = "2222";
		URI uri = UriComponentsBuilder.newInstance().scheme("http")
				.host("test.book.com")
				.port(80)
				.path("/book/{bookId}/{id}")
				.build().expand(bookId, id)
				.encode()
				.toUri();
		return uri;
	}
}
