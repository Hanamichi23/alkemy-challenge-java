package com.alkemy.disney.DTO;

import com.alkemy.disney.model.Personaje;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Schema(name = "Pelicula")
@Getter @Setter @NoArgsConstructor
public class PeliculaDTO {

    private Long id;
    private String titulo;
    private String imagenUrl;
    private LocalDate fechaCreacion;
    private Integer calificacion;

    @JsonIgnoreProperties(value = "peliculas")
    private Set<Personaje> personajes = new HashSet<>();


    public PeliculaDTO(String titulo, String imagenUrl, LocalDate fechaCreacion, Integer calificacion, Set<Personaje> personajes) {
        this.id = id;
        this.titulo = titulo;
        this.imagenUrl = imagenUrl;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        if (personajes != null) this.personajes = personajes;
    }

    public PeliculaDTO(Long id, String titulo, String imagenUrl, LocalDate fechaCreacion, Integer calificacion, Set<Personaje> personajes) {
        this.id = id;
        this.titulo = titulo;
        this.imagenUrl = imagenUrl;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        if (personajes != null) this.personajes = personajes;
    }
}
