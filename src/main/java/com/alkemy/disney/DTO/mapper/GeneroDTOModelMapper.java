package com.alkemy.disney.DTO.mapper;

import com.alkemy.disney.DTO.GeneroDTO;
import com.alkemy.disney.model.Genero;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class GeneroDTOModelMapper {

    ObjectMapper objectMapper;

    @Autowired
    public GeneroDTOModelMapper(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    public GeneroDTO toDTO(Genero categoria)
    {
        return objectMapper.convertValue(categoria, GeneroDTO.class);
    }

    public Genero toModel(GeneroDTO generoDTO)
    {
        return objectMapper.convertValue(generoDTO, Genero.class);
    }

    public List<GeneroDTO> toDTOList(List<Genero> generos)
    {
        return objectMapper.convertValue(generos, new TypeReference<List<GeneroDTO>>(){});
    }
}
