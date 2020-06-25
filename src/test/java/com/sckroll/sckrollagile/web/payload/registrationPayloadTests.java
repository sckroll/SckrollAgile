package com.sckroll.sckrollagile.web.payload;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

public class registrationPayloadTests {

	private Validator validator;

	@Before
	public void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void validate_빈_payload는_실패() {
		RegistrationPayload payload = new RegistrationPayload();

		Set<ConstraintViolation<RegistrationPayload>> violations =
			validator.validate(payload);
		assertEquals(3, violations.size());
	}

	@Test
	public void validate_유효하지_않은_이메일_주소는_실패() {
		RegistrationPayload payload = new RegistrationPayload();
		payload.setUsername("sckroll");
		payload.setEmailAddress("badEmailAddress");
		payload.setPassword("q1w2e3r4");

		Set<ConstraintViolation<RegistrationPayload>> violations =
			validator.validate(payload);
		assertEquals(1, violations.size());
	}

	@Test
	public void validate_이메일_주소가_100자가_넘으면_실패() {
		// The maximium allowed localPart is 64 characters
		// http://www.rfc-editor.org/errata_search.php?rfc=3696&eid=1690
		int maxLocalParthLength = 64;
		String localPart =
			RandomStringUtils.random(maxLocalParthLength, true, true);
		int usedLength = maxLocalParthLength + "@".length() + ".com".length();
		String domain = RandomStringUtils.random(101 - usedLength, true, true);

		RegistrationPayload payload = new RegistrationPayload();
		payload.setUsername("sckroll");
		payload.setEmailAddress(localPart + "@" + domain + ".com");
		payload.setPassword("q1w2e3r4");

		Set<ConstraintViolation<RegistrationPayload>> violations =
			validator.validate(payload);
		assertEquals(1, violations.size());
	}

	@Test
	public void validate_사용자명이_2자_미만이면_실패() {
		RegistrationPayload payload = new RegistrationPayload();
		String usernameTooShort = RandomStringUtils.random(1);

		payload.setUsername(usernameTooShort);
		payload.setEmailAddress("sckroll@sckroll.com");
		payload.setPassword("sckroll");

		Set<ConstraintViolation<RegistrationPayload>> violations =
			validator.validate(payload);
		assertEquals(1, violations.size());
	}

	@Test
	public void validate_사용자명이_50자가_넘으면_실패() {
		RegistrationPayload payload = new RegistrationPayload();
		String usernameTooLong = RandomStringUtils.random(51);

		payload.setUsername(usernameTooLong);
		payload.setEmailAddress("sckroll@sckroll.com");
		payload.setPassword("q1w2e3r4");

		Set<ConstraintViolation<RegistrationPayload>> violations =
			validator.validate(payload);
		assertEquals(1, violations.size());
	}

	@Test
	public void validate_비밀번호가_6자_미만이면_실패() {
		RegistrationPayload payload = new RegistrationPayload();
		String passwordTooShort = RandomStringUtils.random(5);

		payload.setUsername("sckroll");
		payload.setEmailAddress("sckroll@sckroll.com");
		payload.setPassword(passwordTooShort);

		Set<ConstraintViolation<RegistrationPayload>> violations =
			validator.validate(payload);
		assertEquals(1, violations.size());
	}

	@Test
	public void validate_비밀번호가_30자가_넘으면_실패() {
		RegistrationPayload payload = new RegistrationPayload();
		String passwordTooLong = RandomStringUtils.random(31);

		payload.setUsername("sckroll");
		payload.setEmailAddress("sckroll@sckroll.com");
		payload.setPassword(passwordTooLong);

		Set<ConstraintViolation<RegistrationPayload>> violations =
			validator.validate(payload);
		assertEquals(1, violations.size());
	}
}
