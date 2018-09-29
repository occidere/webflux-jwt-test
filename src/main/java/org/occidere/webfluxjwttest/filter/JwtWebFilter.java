package org.occidere.webfluxjwttest.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtWebFilter implements WebFilter {
	private final String authHeader = "X-Auth-Token";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String token = exchange.getRequest()
				.getHeaders()
				.getFirst(authHeader);

		if (StringUtils.isBlank(token)) {
			exchange.getResponse()
					.getHeaders()
					.add(authHeader, "None");
		}

		log.info("X-Auth-Token: {}",
				exchange.getRequest()
						.getHeaders()
						.getFirst(authHeader));

		return chain.filter(exchange);
	}
}
