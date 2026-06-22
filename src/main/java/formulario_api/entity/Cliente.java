package formulario_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "clientes")
public class Cliente {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 @Column(nullable = false, length = 80)
 private String nombres;
 @Column(nullable = false, length = 80)
 private String apellidos;
 @Column(nullable = false, unique = true, length = 120)
 private String correo;
 @Column(nullable = false)
 private Integer edad;
 @Column(length = 15)
 private String telefono;
 @Column(nullable = false, length = 20)
 private String tipoDocumento;
 @Column(nullable = false, length = 20)
 private String numeroDocumento;
 @Column(nullable = false)
 private Boolean aceptaTerminos;
@Column(nullable = false)
 private LocalDateTime fechaRegistro;


 @PrePersist
 public void prePersist() {
 this.fechaRegistro = LocalDateTime.now();
 }
 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }
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
 public LocalDateTime getFechaRegistro() { return fechaRegistro; }
 public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}   