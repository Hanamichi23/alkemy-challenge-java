package com.alkemy.disney.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Schema(name = "GeneroListado")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class GeneroListadoDTO {

    private Long id;
    private String nombre;
    private String imagenUrl;
}