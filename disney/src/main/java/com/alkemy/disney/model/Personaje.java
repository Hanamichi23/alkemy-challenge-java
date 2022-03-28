package com.alkemy.disney.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Personajes")
@Getter @Setter @NoArgsConstructor
public class Personaje {

    @Id
    @SequenceGenerator(name = "personaje_sequence_generator", sequenceName = "personaje_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personaje_sequence_generator")
    private Long id;

    @NotNull
    @Column(length = 127)
    private String nombre;

    @NotNull
    @Column(length = 255)
    private String imagenUrl;

    private Integer edad;

    // En kilogramos
    private Double peso;

    @Column(columnDefinition = "TEXT")
    private String historia;

    @ManyToMany(mappedBy = "personajes", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "personajes")
    private Set<Pelicula> peliculas = new HashSet<>();


    public Personaje(String nombre, String imagenUrl, Integer edad, Double peso, String historia, Set<Pelicula> peliculas)
    {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        if (peliculas != null) this.peliculas = peliculas;
    }

    public Personaje(Long id, String nombre, String imagenUrl, Integer edad, Double peso, String historia, Set<Pelicula> peliculas)
    {
        this.id = id;
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        if (peliculas != null) this.peliculas = peliculas;
    }

    public void setPeliculas(Set<Pelicula> peliculas)
    {
        this.peliculas.clear();
        if (peliculas != null) {
            this.peliculas.addAll(peliculas);
        }
    }

    public void addPelicula(Pelicula pelicula)
    {
        this.peliculas.add(pelicula);
        pelicula.addPersonaje(this);
    }

    public void removePelicula(Pelicula pelicula)
    {
        this.peliculas.remove(pelicula);
        pelicula.removePersonaje(this);
    }
}