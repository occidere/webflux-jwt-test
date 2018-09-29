package org.occidere.webfluxjwttest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.occidere.webfluxjwttest.domain.User;
import org.occidere.webfluxjwttest.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class WebfluxJwtTestApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	private String jwtToken;

	@Test
	public void test() {
		User testUser = new User();
		testUser.setId("testId");
		testUser.setPw("testPw");
		testUser.setName("Test Name");
		testUser.setAge(25);
		System.out.println(testUser.toJsonString());

		jwtToken = JwtService.generateJwtString(testUser);
		System.out.println(jwtToken);

		EntityExchangeResult<String> result = webTestClient.get()
				.uri("/check/jwt")
				.header("X-Auth-Token", jwtToken)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.returnResult();

		System.out.println(result);
	}

}
