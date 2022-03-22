package com.alkemy.disney.service;

import com.alkemy.disney.DTO.PersonajeDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;

import java.util.List;


public interface IPersonajeService {

    public PersonajeDTO agregar(PersonajeDTO personajeDTO) throws BadRequestException;
    public PersonajeDTO buscar(Long id) throws ResourceNotFoundException;
    public List<PersonajeDTO> listar();
    public PersonajeDTO actualizar(PersonajeDTO personajeDTO) throws BadRequestException, ResourceNotFoundException;
    public void eliminar(Long id) throws ResourceNotFoundException, BadRequestException;
}
