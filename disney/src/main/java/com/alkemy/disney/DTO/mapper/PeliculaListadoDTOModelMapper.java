package com.alkemy.disney.DTO.mapper;

import com.alkemy.disney.DTO.PeliculaListadoDTO;
import com.alkemy.disney.model.Pelicula;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PeliculaListadoDTOModelMapper {

    ObjectMapper objectMapper;

    @Autowired
    public PeliculaListadoDTOModelMapper(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    public PeliculaListadoDTO toDTO(Pelicula pelicula)
    {
        return objectMapper.convertValue(pelicula, PeliculaListadoDTO.class);
    }

    public Pelicula toModel(PeliculaListadoDTO peliculaListadoDTO)
    {
        return objectMapper.convertValue(peliculaListadoDTO, Pelicula.class);
    }

    public List<PeliculaListadoDTO> toDTOList(List<Pelicula> peliculas)
    {
        return objectMapper.convertValue(peliculas, new TypeReference<List<PeliculaListadoDTO>>(){});
    }
}