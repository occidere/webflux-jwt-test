package org.occidere.webfluxjwttest.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.occidere.webfluxjwttest.domain.User;

import java.util.Base64;
import java.util.Date;

@Slf4j
public class JwtService {
	private JwtService() {
	}

	private static final String BASE64_SECRET_KEY = Base64.getEncoder().encodeToString("my secret key".getBytes());

	public static String generateJwtString(User user) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		Date expireDate = new Date(nowMillis + 60_000); // 만료시간 1분

		JwtBuilder builder = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setHeaderParam("alg", "HS512")
				.setIssuer("occidere")
				.setIssuedAt(now)
				.setExpiration(expireDate)
				.setSubject(user.toJsonString())
				.signWith(SignatureAlgorithm.HS512, BASE64_SECRET_KEY);

		String token = builder.compact();

		log.info("BASE_64_SECRET_KEY: {}", BASE64_SECRET_KEY);
		log.info("Token: {}", token);
		log.info("Issued At: {}", now);
		log.info("Expire At: {}", expireDate);

		return token;
	}

	public static User pasreToken(String jwt) {
		User user = null;
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(BASE64_SECRET_KEY)
					.parseClaimsJws(jwt)
					.getBody();

			log.info("--- [Parsing] ---");
			log.info("ID: {}", claims.getId());
			log.info("Issuer: {}", claims.getIssuer());
			log.info("Subject: {}", claims.getSubject());
			log.info("Issued At: {}", claims.getIssuedAt());
			log.info("Expiration: {}", claims.getExpiration());

			user = new ObjectMapper().readValue(claims.getSubject(), User.class);
//			user = (User) claims.get("User");
		} catch (ExpiredJwtException e) {
//			e.printStackTrace();
			log.error("Token Expired!");
		} catch (SignatureException e) {
//			e.printStackTrace();
			log.error("Invalid Token!");
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("User: {}", user);
		return user;
	}

}
