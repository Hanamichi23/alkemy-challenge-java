package com.alkemy.disney.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "Roles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Rol {

    @Id
    @SequenceGenerator(name = "rol_sequence_generator", sequenceName = "rol_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_sequence_generator")
    private Long id;

    @NotNull
    @Column(length = 63)
    private String nombre;

    public Rol(String nombre)
    {
        this.nombre = nombre;
    }
}
