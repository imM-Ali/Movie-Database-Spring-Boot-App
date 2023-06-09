package com.web.dssapp.dto;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
	
    @Id
    private int _id;
    @NotBlank(message = "First Name should not be empty")
	@Size(min = 3, max = 15, message = "Incorrect length for First Name")
    private String firstName;
    @NotBlank(message = "Last name should not be empty")
	@Size(min = 3, max = 15, message = "Incorrect length for Last Name")
    private String lastName;
    @NotBlank(message = "Username should not be empty")
	@Size(min = 5, max = 25, message = "Incorrect length for User Name")
    private String username;
    @NotBlank(message = "Email should not be empty")
    @Email
    private String email;   
    @Pattern(regexp = "(((?=\\S*[A-Z])(?=\\S*[a-z])(?=\\S*\\d)(?=\\S*[\\!\\§\\$\\%\\&\\/\\(\\)\\=\\?\\+\\*\\#\\'\\^\\°\\,\\;\\.\\:\\<\\>\\ä\\ö\\ü\\Ä\\Ö\\Ü\\ß\\?\\|\\@\\~\\´\\`])\\S{8,}))", message = "Password must contain atleast 1 number, atleast 1 upper case character and atleast 1 special character")
    private String password;
    private int role_id;


	public int get_id() {
		return _id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public void set_id(int id) {
		this._id = id;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	


   
}
