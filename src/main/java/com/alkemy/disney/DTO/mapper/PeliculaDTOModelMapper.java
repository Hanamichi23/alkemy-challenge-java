package com.alkemy.disney.DTO.mapper;

import com.alkemy.disney.DTO.PeliculaDTO;
import com.alkemy.disney.model.Pelicula;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PeliculaDTOModelMapper {

    ObjectMapper objectMapper;

    @Autowired
    public PeliculaDTOModelMapper(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    public PeliculaDTO toDTO(Pelicula pelicula)
    {
        return objectMapper.convertValue(pelicula, PeliculaDTO.class);
    }

    public Pelicula toModel(PeliculaDTO peliculaDTO)
    {
        return objectMapper.convertValue(peliculaDTO, Pelicula.class);
    }

    public List<PeliculaDTO> toDTOList(List<Pelicula> peliculas)
    {
        return objectMapper.convertValue(peliculas, new TypeReference<List<PeliculaDTO>>(){});
    }
}