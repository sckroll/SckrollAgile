package com.sckroll.sckrollagile.domain.common.security;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptorDelegator implements PasswordEncryptor {

	@Override
	public String encrypt(String rawPassword) {
		return rawPassword;
	}
}
