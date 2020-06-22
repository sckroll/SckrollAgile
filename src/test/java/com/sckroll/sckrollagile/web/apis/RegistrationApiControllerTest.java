package com.sckroll.sckrollagile.web.apis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.sckroll.sckrollagile.domain.application.UserService;
import com.sckroll.sckrollagile.domain.model.user.EmailAddressExistsException;
import com.sckroll.sckrollagile.domain.model.user.UsernameExistsException;
import com.sckroll.sckrollagile.utils.JsonUtils;
import com.sckroll.sckrollagile.web.payload.RegistrationPayload;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationApiController.class)
public class RegistrationApiControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService serviceMock;

	@Test
	public void 빈_payload는_등록_실패_후_400_리턴() throws Exception {
		mvc.perform(post("/api/registrations"))
			.andExpect(status().is(400));
	}

	@Test
	public void 존재하는_사용자명은_등록_실패_후_400_리턴() throws Exception {
		RegistrationPayload payload = new RegistrationPayload();
		payload.setUsername("exist");
		payload.setEmailAddress("test@sckroll.com");
		payload.setPassword("testPassword");

		doThrow(UsernameExistsException.class)
			.when(serviceMock)
			.register(payload.toCommand());

		mvc.perform(
			post("/api/registrations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtils.toJson(payload)))
			.andExpect(status().is(400))
			.andExpect(jsonPath("$.message").value("Username already exists"));
	}

	@Test
	public void 존재하는_이메일_주소는_등록_실패_후_400_리턴() throws Exception {
		RegistrationPayload payload = new RegistrationPayload();
		payload.setUsername("test");
		payload.setEmailAddress("exist@sckroll.com");
		payload.setPassword("testPassword");

		doThrow(EmailAddressExistsException.class)
			.when(serviceMock)
			.register(payload.toCommand());

		mvc.perform(
			post("/api/registrations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtils.toJson(payload)))
			.andExpect(status().is(400))
			.andExpect(jsonPath("$.message").value("Email address already exists"));
	}

	@Test
	public void 유효한_payload는_등록_성공_후_201_리턴() throws Exception {
		RegistrationPayload payload = new RegistrationPayload();
		payload.setUsername("test");
		payload.setEmailAddress("test@sckroll.com");
		payload.setPassword("testPassword");

		doNothing()
			.when(serviceMock)
			.register(payload.toCommand());

		mvc.perform(
			post("/api/registrations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtils.toJson(payload)))
			.andExpect(status().is(201));
	}
}
