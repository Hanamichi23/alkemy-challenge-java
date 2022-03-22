package com.alkemy.disney.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Pelicula")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pelicula {

    @Id
    @SequenceGenerator(name = "pelicula_sequence_generator", sequenceName = "pelicula_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pelicula_sequence_generator")
    private Long id;

    @NotNull
    @Column(length = 127)
    private String titulo;

    @NotNull
    @Column(length = 255)
    private String imagenUrl;

    private LocalDate fechaCreacion;

    @Min(1)
    @Max(5)
    private Integer calificacion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pelicula_x_personaje",
            joinColumns = { @JoinColumn(name = "pelicula_id") },                // Sin especificar el nombre de las columnas
            inverseJoinColumns = { @JoinColumn(name = "personaje_id") } )       // los pone con el nombre en plural
    private Set<Personaje> personajes = new HashSet<>();


    public Pelicula(Long id, String titulo, String imagenUrl, LocalDate fechaCreacion, Integer calificacion)
    {
        this.id = id;
        this.titulo = titulo;
        this.imagenUrl = imagenUrl;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
    }
}