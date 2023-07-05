package com.aa.domain;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 45, nullable = false)
	@NotBlank(message = "First name is required!")
	@Length(min = 2, max = 45, message = "First name must have between 2-45 characters!")
	private String firstName;
	
	@Column(length = 45, nullable = false)
	@NotBlank(message = "Last name is required!")
	@Length(min = 2, max = 45, message = "Last name must have between 2-45 characters!")
	private String lastName;
	
	@Column(length = 125, nullable = false, unique = true)
	@NotBlank(message = "E-mail is required!")
	@Length(min = 5, max = 125, message = "E-mail must have between 5-125 characters!")
	@Email(message = "E-mail is not valid!")
	private String email;
	
	@Column(length = 64, nullable = false)
	@NotBlank(message = "Password is required!")
	@Length(min = 5, max = 64, message = "Password must have between 5-64 characters!")
	private String password;
	
	private boolean isEnabled;
	
	private boolean isNonLocked;
	
	@ManyToMany
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet<>();
	
	public User() {}

	public User(String firstName, String lastName, String email, String password, boolean isEnabled,
			boolean isNonLocked, Role role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isEnabled = isEnabled;
		this.isNonLocked = isNonLocked;
		this.roles.add(role);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isNonLocked() {
		return isNonLocked;
	}

	public void setNonLocked(boolean isNonLocked) {
		this.isNonLocked = isNonLocked;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", isEnabled=" + isEnabled + ", isNonLocked=" + isNonLocked + ", roles="
				+ roles + "]";
	}
}
