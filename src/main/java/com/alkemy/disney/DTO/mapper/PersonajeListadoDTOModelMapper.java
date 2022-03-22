package com.alkemy.disney.DTO.mapper;

import com.alkemy.disney.DTO.PersonajeListadoDTO;
import com.alkemy.disney.model.Personaje;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PersonajeListadoDTOModelMapper {

    ObjectMapper objectMapper;

    @Autowired
    public PersonajeListadoDTOModelMapper(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    public PersonajeListadoDTO toDTO(Personaje personaje)
    {
        return objectMapper.convertValue(personaje, PersonajeListadoDTO.class);
    }

    public Personaje toModel(PersonajeListadoDTO personajeListadoDTO)
    {
        return objectMapper.convertValue(personajeListadoDTO, Personaje.class);
    }

    public List<PersonajeListadoDTO> toDTOList(List<Personaje> personajes)
    {
        return objectMapper.convertValue(personajes, new TypeReference<List<PersonajeListadoDTO>>(){});
    }
}