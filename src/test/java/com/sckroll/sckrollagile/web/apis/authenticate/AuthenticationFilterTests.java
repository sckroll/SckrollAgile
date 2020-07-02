package com.sckroll.sckrollagile.web.apis.authenticate;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AuthenticationFilterTests {

	@MockBean
	private AuthenticationManager authenticationManagerMock;

	@Test(expected = InsufficientAuthenticationException.class)
	public void attemptAuthentication_요청_바디가_비어있으면_실패한다()
		throws IOException {
		MockHttpServletRequest request =

		new MockHttpServletRequest("POST", "/api/authentications");
		AuthenticationFilter filter = new AuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerMock);
		filter.attemptAuthentication(request, new MockHttpServletResponse());
	}

	@Test(expected = InsufficientAuthenticationException.class)
	public void attemptAuthentication_요청_바디가_유효하지_않은_JSON_문자열이면_실패한다()
		throws IOException {

		MockHttpServletRequest request =
			new MockHttpServletRequest("POST", "/api/authentications");
		request.setContent("username=test&password=testPassword".getBytes());
		AuthenticationFilter filter = new AuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerMock);
		filter.attemptAuthentication(request, new MockHttpServletResponse());
	}

	@Test
	public void attemptAuthentication_요청_바디가_유효한_JSON_문자열이면_성공한다()
		throws IOException {

		MockHttpServletRequest request =
			new MockHttpServletRequest("POST", "/api/authentications");
		request.setContent("{\"username\": \"test\", \"password\": \"testPassword\"}".getBytes());
		AuthenticationFilter filter = new AuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerMock);
		filter.attemptAuthentication(request, new MockHttpServletResponse());
		UsernamePasswordAuthenticationToken token =
		  new UsernamePasswordAuthenticationToken("test", "testPassword");
		verify(authenticationManagerMock).authenticate(token);
	}
}
