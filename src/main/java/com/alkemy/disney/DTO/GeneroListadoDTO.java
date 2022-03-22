package com.alkemy.disney.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Schema(name = "GeneroListado")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GeneroListadoDTO {

    private Long id;
    private String nombre;
    private String imagenUrl;
}