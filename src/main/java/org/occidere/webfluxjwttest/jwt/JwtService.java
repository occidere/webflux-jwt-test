package org.occidere.webfluxjwttest.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.occidere.webfluxjwttest.domain.User;

import java.util.Base64;
import java.util.Date;

@Slf4j
public class JwtService {
	private JwtService() {}

	private static final String BASE64_SECRET_KEY = Base64.getEncoder().encodeToString("my secret key".getBytes());

	public static String generateJwtString(User user) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		JwtBuilder builder = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setHeaderParam("arg", "HS512")
				.setIssuedAt(now)
				.setSubject(user.toJsonString())
				.signWith(SignatureAlgorithm.HS512, BASE64_SECRET_KEY);

		String token = builder.compact();

		log.info("Token: {}", token);

		return token;
	}

	public static User pasreToken(String jwt) {
		User user = null;
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(BASE64_SECRET_KEY)
					.parseClaimsJws(jwt)
					.getBody();

			log.info("ID: {}", claims.getId());
			log.info("Subject: {}", claims.getSubject());
			log.info("Issued At: {}", claims.getIssuedAt());

			user = new ObjectMapper().readValue(claims.getSubject(), User.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("User: {}", user);
		return user;
	}

}
