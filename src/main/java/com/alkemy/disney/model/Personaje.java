package com.alkemy.disney.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Personaje")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
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

    @ManyToMany(mappedBy = "personajes")
    private Set<Pelicula> peliculas = new HashSet<>();


    public Personaje(String nombre, String imagenUrl, Integer edad, Double peso, String historia)
    {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
    }
}