package com.alkemy.disney.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Genero")
@Getter @Setter @NoArgsConstructor
public class Genero {

    @Id
    @SequenceGenerator(name = "genero_sequence_generator", sequenceName = "genero_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genero_sequence_generator")
    private Long id;

    @NotNull
    @Column(length = 127)
    private String nombre;

    @NotNull
    @Column(length = 255)
    private String imagenUrl;

    // Hacer la relación bidireccional es más eficiente, pero la dejo unidireccional porque así está en el enunciado
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // Esto es para que Hibernate no cree una nueva tabla para mapear la asociación
    @JoinColumn(name = "genero_id")
    private List<Pelicula> peliculas = new ArrayList<>();


    public Genero(String nombre, String imagenUrl, List<Pelicula> peliculas)
    {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        if (peliculas != null) this.peliculas = peliculas;
    }

    public Genero(Long id, String nombre, String imagenUrl, List<Pelicula> peliculas)
    {
        this.id = id;
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        if (peliculas != null) this.peliculas = peliculas;
    }
}
