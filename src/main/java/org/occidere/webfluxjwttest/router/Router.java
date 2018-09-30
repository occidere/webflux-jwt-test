package org.occidere.webfluxjwttest.router;

import org.occidere.webfluxjwttest.handler.JwtHandler;
import org.occidere.webfluxjwttest.handler.JwtHandlerFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class Router {
	@Bean
	public RouterFunction<ServerResponse> route(JwtHandler jwtHandler) {
		return RouterFunctions
				.route(RequestPredicates.GET("/jwt/check"), jwtHandler::getTokenFromHeader)
//				.route(RequestPredicates.GET("/check/{token}"), jwtHandler::getToken)
				.filter(new JwtHandlerFilterFunction());
	}
}
