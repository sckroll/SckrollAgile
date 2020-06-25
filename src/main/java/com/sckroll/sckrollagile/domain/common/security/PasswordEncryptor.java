package com.sckroll.sckrollagile.domain.common.security;

public interface PasswordEncryptor {

	String encrypt(String rawPassword);
}
