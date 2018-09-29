package org.occidere.webfluxjwttest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Controller {

	/**
	 * 그냥 이름 주소로 받아서 리턴
	 * @param name
	 * @return
	 */
	@GetMapping(path = "/users/{name}")
	public Mono<String> getName(@PathVariable String name) {
		return Mono.just(name);
	}

	@GetMapping(path = "/")
	public Mono<String> hello() {
		return Mono.just("Hello World!");
	}
}
