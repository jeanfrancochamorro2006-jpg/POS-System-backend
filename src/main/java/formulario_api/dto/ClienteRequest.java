package formulario_api.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public class ClienteRequest {
 @NotBlank(message = "Los nombres son obligatorios")
 @Size(min = 3, max = 80, message = "Los nombres deben tener entre 3 y 80 caracteres")
 private String nombres;
 @NotBlank(message = "Los apellidos son obligatorios")
 private String apellidos;
 @NotBlank(message = "El correo es obligatorio")
 @Email(message = "El correo no tiene un formato válido")
 private String correo;
 @NotNull(message = "La edad es obligatoria")
 @Min(value = 18, message = "La edad mínima es 18")
 @Max(value = 80, message = "La edad máxima es 80")
 private Integer edad;
 @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
 private String telefono;
 @NotBlank(message = "Seleccione el tipo de documento")
 private String tipoDocumento;
 @NotBlank(message = "Ingrese el número de documento")
 private String numeroDocumento;
 @AssertTrue(message = "Debe aceptar los términos")
 private Boolean aceptaTerminos;

 
 public String getNombres() { return nombres; }
 public void setNombres(String nombres) { this.nombres = nombres; }
 public String getApellidos() { return apellidos; }
 public void setApellidos(String apellidos) { this.apellidos = apellidos; }
 public String getCorreo() { return correo; }
 public void setCorreo(String correo) { this.correo = correo; }
 public Integer getEdad() { return edad; }
 public void setEdad(Integer edad) { this.edad = edad; }
 public String getTelefono() { return telefono; }
 public void setTelefono(String telefono) { this.telefono = telefono; }
 public String getTipoDocumento() { return tipoDocumento; }
 public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
 public String getNumeroDocumento() { return numeroDocumento; }
 public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
 public Boolean getAceptaTerminos() { return aceptaTerminos; }
 public void setAceptaTerminos(Boolean aceptaTerminos) { this.aceptaTerminos = aceptaTerminos; }
}