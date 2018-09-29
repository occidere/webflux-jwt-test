package org.occidere.webfluxjwttest.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class JwtHandler {

	public Mono<ServerResponse> getPathVariableToken(ServerRequest request) {
		Mono<String> token = Mono.just(request.pathVariable("token"));
		return ServerResponse.ok().body(token, String.class);
	}

	public Mono<ServerResponse> getTokenFromHeader(ServerRequest request) {
		try {
			Mono<String> token = Mono.just(request.headers().header("X-Auth-Token").get(0));
			return ServerResponse.ok().body(token, String.class);

		} catch (Exception e) {
			return ServerResponse.status(HttpStatus.FORBIDDEN).body(Mono.just("X-Auth-Token is not exist in header!"), String.class);
		}
	}

}
