package com.alkemy.disney.DTO.mapper;

import com.alkemy.disney.DTO.PersonajeDTO;
import com.alkemy.disney.model.Personaje;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PersonajeDTOModelMapper {

    ObjectMapper objectMapper;

    @Autowired
    public PersonajeDTOModelMapper(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    public PersonajeDTO toDTO(Personaje personaje)
    {
        return objectMapper.convertValue(personaje, PersonajeDTO.class);
    }

    public Personaje toModel(PersonajeDTO personajeDTO)
    {
        return objectMapper.convertValue(personajeDTO, Personaje.class);
    }

    public List<PersonajeDTO> toDTOList(List<Personaje> personajes)
    {
        return objectMapper.convertValue(personajes, new TypeReference<List<PersonajeDTO>>(){});
    }
}
