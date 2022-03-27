package com.alkemy.disney.DTO;

import com.alkemy.disney.model.Pelicula;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Schema(name = "Género")
@Getter @Setter @NoArgsConstructor @ToString
public class GeneroDTO {

    private Long id;
    private String nombre;
    private String imagenUrl;

    @JsonIgnoreProperties(value = "personajes")
    private Set<Pelicula> peliculas = new HashSet<>();


    public GeneroDTO(String nombre, String imagenUrl, Set<Pelicula> peliculas)
    {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        if (peliculas != null) this.peliculas = peliculas;
    }

    public GeneroDTO(Long id, String nombre, String imagenUrl, Set<Pelicula> peliculas)
    {
        this.id = id;
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        if (peliculas != null) this.peliculas = peliculas;
    }
}
