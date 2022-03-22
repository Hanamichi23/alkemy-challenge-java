package com.alkemy.disney.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Schema(name = "PelículaListado")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PeliculaListadoDTO {

    private Long id;
    private String titulo;
    private String imagenUrl;
    private LocalDate fechaCreacion;
}