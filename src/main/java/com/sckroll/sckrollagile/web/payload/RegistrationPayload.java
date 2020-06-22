package com.sckroll.sckrollagile.web.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sckroll.sckrollagile.domain.application.commands.RegistrationCommand;

public class RegistrationPayload {

	@Size(min = 2, max = 50, message = "사용자명은 2 ~ 50자여야 합니다.")
	@NotNull
	private String username;

	@Email(message = "유효한 이메일 주소 양식이어야 합니다.")
	@Size(max = 100, message = "이메일 주소는 100자를 넘을 수 없습니다.")
	@NotNull
	private String emailAddress;

	@Size(min = 6, max = 30, message = "비밀번호는 6 ~ 30자여야 합니다.")
	@NotNull
	private String password;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RegistrationCommand toCommand() {
		return new RegistrationCommand(this.username, this.emailAddress, this.password);
	}
}
