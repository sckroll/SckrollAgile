package com.sckroll.sckrollagile.config;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "app")
@Validated
public class ApplicationProperties {

	/**
	 * 시스템에 의해 전송된 이메일의 기본 'from' 값
	 */
	@Email
	@NotBlank
	private String mailFrom;

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailFrom() {
		return mailFrom;
	}
}
