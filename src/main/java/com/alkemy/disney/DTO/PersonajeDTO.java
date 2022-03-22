package com.alkemy.disney.DTO;

import com.alkemy.disney.model.Pelicula;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Schema(name = "Personaje")
@Getter @Setter @NoArgsConstructor
public class PersonajeDTO {

    private Long id;
    private String nombre;
    private String imagenUrl;
    private Integer edad;
    private Double peso;
    private String historia;
    private Set<Pelicula> peliculas = new HashSet<>();


    public PersonajeDTO(String nombre, String imagenUrl, Integer edad, Double peso, String historia, Set<Pelicula> peliculas)
    {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        if (peliculas != null) this.peliculas = peliculas;
    }

    public PersonajeDTO(Long id, String nombre, String imagenUrl, Integer edad, Double peso, String historia, Set<Pelicula> peliculas)
    {
        this.id = id;
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        if (peliculas != null) this.peliculas = peliculas;
    }
}
