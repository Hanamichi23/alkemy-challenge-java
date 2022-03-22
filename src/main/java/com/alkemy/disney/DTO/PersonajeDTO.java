package com.alkemy.disney.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Schema(name = "Personaje")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PersonajeDTO {

    private Long id;
    private String nombre;
    private String imagenUrl;
    private Integer edad;
    private Double peso;
    private String historia;


    public PersonajeDTO(String nombre, String imagenUrl, Integer edad, Double peso, String historia)
    {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
    }
}
