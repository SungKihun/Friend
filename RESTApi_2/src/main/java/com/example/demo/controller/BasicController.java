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

//@RestController 은 스프링 4.1부터 추가된 어노테이션.
//기존 컨트롤러 어노테이션에서 @ResponseBody을 추가한 것.
//@RestController를 사용하면 별도로 @ResponseBody를 사용하지 않고 REST API를 만듬.
//@ResponseBody: 실행결과에 대한 처리를 위한 어노테이션, 실행결과는 View를 거치지 않고 HTTP Response Body에 직접 입력
//스프링 부트는 JSON표현에 필요한 해당 라이브러리들을 이미 포함하고 있다
@RestController
@RequestMapping(value = "/basic")
public class BasicController {
	// Atomic: 원자성(더 이상 쪼개질 수 없는 성질)
	// Long 타입으로 선언한다면 서로 다른 스레드에서 하나의 변수를 사용시 문제가 발생할 수 있다
	// AtomicLong을 사용하면 thread-safe하게 처리한다
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
	// 응답헤더: 클라이언트에서 헤더 정보를 먼저 읽을 수 있어, 본문 내용을 읽을 필요가 없이 통신효율 상승.
	// ResponseEntity: HttpEntity를 상속받은 클래스. Http응답에 대한 상태값을 표현 가능
	public ResponseEntity<Todo> postBasicResponseEntity(@RequestParam(value = "todoTitle") String todoTitle){
		// Todo객체를 반환했던 부분을 ResponseEntity로 감쌈
		return new ResponseEntity<Todo>(new Todo(counter.incrementAndGet(), todoTitle), HttpStatus.OK);
		// HttpStatus: enum클래스로 400, 200, 201, 500과 같은 Http 상태코드에 대응
	}
	
	// URI템플릿: 주소값을 통한 값 전달.
	@RequestMapping(value = "/todos/{todoId}", method = RequestMethod.GET)
	// @PathVariable: URI를 통한 값 대응
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
	
	// HATEOS: 자기주소정보 표현
	@RequestMapping(value = "/todoh", method = RequestMethod.GET)
	public ResponseEntity<TodoResource> geth(@RequestParam(value = "todoTitle") String todoTitle){
		TodoResource todoResource = new TodoResource(todoTitle);
		// BasicController클래스의 geth메소드의 todoTitle값에 대한 withSelfRel() 자기주소를 linkTo 링크로 add추가한다.
		todoResource.add(linkTo(methodOn(BasicController.class).geth(todoTitle)).withSelfRel());
		
		return new ResponseEntity<TodoResource>(todoResource, HttpStatus.OK);
	}
	
	// UriComponentsBuilder 활용
	// String방식이 아닌 사이트정보의 구조화된 형태 표현
	@RequestMapping(value = "/todou")
	public URI getUri() {
		URI uri = UriComponentsBuilder.newInstance().scheme("http") //http:ftp:https
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
				// 파라미터 정보
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
				// 여러개의 파라미터
				.path("/book/{bookId}/{id}")
				.build().expand(bookId, id)
				.encode()
				.toUri();
		return uri;
	}
}
