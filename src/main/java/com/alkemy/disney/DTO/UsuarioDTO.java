package com.alkemy.disney.DTO;

import com.alkemy.disney.model.Rol;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Schema(name = "Usuario")
@JsonInclude(JsonInclude.Include.NON_NULL)  // Para evitar el 'contrasena: null' en el JSON
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String email;
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
