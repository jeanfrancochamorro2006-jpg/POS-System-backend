package formulario_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El usuario es obligatorio")
    private String username;

    // Opcional al editar (si viene vacío se mantiene la contraseña actual)
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private String rol;

    private Boolean activo = true;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
