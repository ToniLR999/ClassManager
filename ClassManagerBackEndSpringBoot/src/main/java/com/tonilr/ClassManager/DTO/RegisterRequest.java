package com.tonilr.ClassManager.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 30)
    private String username;

    @Email(message = "Debe ser un email válido")
    @NotBlank
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

	public RegisterRequest(
			@NotBlank(message = "El nombre es obligatorio") @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres") String name,
			@NotBlank(message = "El nombre de usuario es obligatorio") @Size(min = 3, max = 30) String username,
			@Email(message = "Debe ser un email válido") @NotBlank String email,
			@NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
