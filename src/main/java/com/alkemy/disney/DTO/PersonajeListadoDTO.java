package com.alkemy.disney.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Schema(name = "PersonajeListado")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PersonajeListadoDTO {

    private Long id;
    private String nombre;
    private String imagenUrl;
}