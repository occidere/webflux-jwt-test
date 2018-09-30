package org.occidere.webfluxjwttest.handler;

import org.occidere.webfluxjwttest.domain.User;
import org.occidere.webfluxjwttest.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class JwtHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

	@Override
	public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
//		String token = request.pathVariable("token");
		String token = request.headers().header("X-Auth-Token").get(0);

		try {
			User user = JwtService.pasreToken(token);
			return ServerResponse
					.ok()
					.body(Mono.just(user.toJsonString()), String.class);
		} catch (Exception e) {
			return ServerResponse
					.status(HttpStatus.FORBIDDEN)
					.body(Mono.just("Not Authorized!"), String.class);

		}
	}

}
