package com.alkemy.disney.DTO.mapper;

import com.alkemy.disney.DTO.GeneroListadoDTO;
import com.alkemy.disney.model.Genero;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class GeneroListadoDTOModelMapper {

    ObjectMapper objectMapper;

    @Autowired
    public GeneroListadoDTOModelMapper(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    public GeneroListadoDTO toDTO(Genero genero)
    {
        return objectMapper.convertValue(genero, GeneroListadoDTO.class);
    }

    public Genero toModel(GeneroListadoDTO generoListadoDTO)
    {
        return objectMapper.convertValue(generoListadoDTO, Genero.class);
    }

    public List<GeneroListadoDTO> toDTOList(List<Genero> generos)
    {
        return objectMapper.convertValue(generos, new TypeReference<List<GeneroListadoDTO>>(){});
    }
}