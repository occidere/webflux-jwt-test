package org.occidere.webfluxjwttest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.occidere.webfluxjwttest.domain.User;
import org.occidere.webfluxjwttest.jwt.JwtService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
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

	@PostMapping(path = "/jwt/create")
	public Mono<String> createToken(@RequestBody String body) {
		try {
			User user = new ObjectMapper().readValue(body, User.class);

			String token = JwtService.generateJwtString(user);

			return Mono.just(token);
		} catch (Exception e) {
			log.error("Invalid user data format!");

			return Mono.just("Invalid user data format!");
		}
	}
}
