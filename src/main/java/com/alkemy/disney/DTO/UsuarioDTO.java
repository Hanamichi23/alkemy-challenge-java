package com.alkemy.disney.DTO;

import com.alkemy.disney.model.Rol;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Schema(name = "Usuario")
@JsonInclude(JsonInclude.Include.NON_NULL)  // Para evitar el 'contrasena: null' en el JSON
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioDTO {

    private Long id;
    @NotBlank(message = "El email no puede ser nulo ni vacío")
    private String email;
    @NotBlank(message = "La contraseña no puede ser nula ni vacía")
    private String contrasena;

    @Schema(hidden = true)
    private Rol rol;

    public UsuarioDTO(String email, String contrasena, Rol rol)
    {
        this.email = email;
        this.contrasena = contrasena;
        this.rol = rol;
    }
}
