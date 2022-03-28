package com.alkemy.disney.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Schema(name = "PersonajeListado")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class PersonajeListadoDTO {

    private Long id;
    private String nombre;
    private String imagenUrl;
}