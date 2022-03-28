package com.alkemy.disney.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;


@Schema(name = "PelículaListado")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class PeliculaListadoDTO {

    private Long id;
    private String titulo;
    private String imagenUrl;
    private LocalDate fechaCreacion;
}