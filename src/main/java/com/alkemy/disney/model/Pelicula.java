package com.alkemy.disney.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
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
@Table(name = "Peliculas")
@Getter @Setter @NoArgsConstructor
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
    // Sin especificar el nombre de las columnas los pone con el nombre en plural
    @JoinTable(name = "Pelicula_X_Personaje",
            joinColumns = { @JoinColumn(name = "pelicula_id") },
            inverseJoinColumns = { @JoinColumn(name = "personaje_id") } )
    // El allowSetters es para evitar un error del ObjectMapper en caso de dejar la anotación habilitada
    @JsonIgnoreProperties(value = "peliculas")
    private Set<Personaje> personajes = new HashSet<>();


    public Pelicula(String titulo, String imagenUrl, LocalDate fechaCreacion, Integer calificacion, Set<Personaje> personajes)
    {
        this.titulo = titulo;
        this.imagenUrl = imagenUrl;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        if (personajes != null) this.personajes = personajes;
    }

    public Pelicula(Long id, String titulo, String imagenUrl, LocalDate fechaCreacion, Integer calificacion, Set<Personaje> personajes)
    {
        this.id = id;
        this.titulo = titulo;
        this.imagenUrl = imagenUrl;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        if (personajes != null) this.personajes = personajes;
    }

    public void setPersonajes(Set<Personaje> personajes)
    {
        this.personajes.clear();
        if (personajes != null) {
            this.personajes.addAll(personajes);
        }
    }

    public void addPersonaje(Personaje personaje)
    {
        this.personajes.add(personaje);
        personaje.addPelicula(this);
    }

    public void removePersonaje(Personaje personaje)
    {
        this.personajes.remove(personaje);
        personaje.removePelicula(this);
    }
}