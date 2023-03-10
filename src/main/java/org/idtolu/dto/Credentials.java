package org.idtolu.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Credentials {
	  @NotNull(message = "El nombre de usuario es requerido")
	  @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
	  private String username;

	  @NotNull(message = "La contraseña es requerida")
	  @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
	  private String password;
	  @NotNull(message = "El Role es requerido debe ser 'ADMIN' o 'USER' ")
	  @Pattern(regexp="(ADMIN|USER)", message="El valor del role debe ser 'ADMIN' o 'USER'")
	  private String role;

    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
