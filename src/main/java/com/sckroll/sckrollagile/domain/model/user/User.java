package com.sckroll.sckrollagile.domain.model.user;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false, length = 50, unique = true)
	private String username;

	@Column(name = "email_address", nullable = false, length = 100, unique = true)
	private String emailAddress;

	@Column(name = "password", nullable = false, length = 30)
	private String password;

	@Column(name = "first_name", nullable = false, length = 45)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 45)
	private String lastName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	public User() { }

	public static User create(String username, String emailAddress, String password) {
		User user = new User();
		user.username = username;
		user.emailAddress = emailAddress;
		user.password = password;
		user.firstName = "";
		user.lastName = "";
		user.createdDate = new Date();
		return user;
	}

	public void updateName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public UserId getId() {
		return new UserId(id);
	}

	public String getUsername() {
		return username;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	// equals(), hashCode(), toString() 재정의
	// 두 User 객체를 username과 emailAddress 필드로만 비교하기 위함
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return Objects.equals(username, user.username) &&
			Objects.equals(emailAddress, user.emailAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, emailAddress);
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", username='" + username + '\'' +
			", emailAddress='" + emailAddress + '\'' +
			", password=<Protected> " +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", createdDate=" + createdDate +
			'}';
	}
}
