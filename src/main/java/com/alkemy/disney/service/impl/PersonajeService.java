package com.alkemy.disney.service.impl;

import com.alkemy.disney.DTO.PersonajeDTO;
import com.alkemy.disney.DTO.mapper.PersonajeDTOModelMapper;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.model.Personaje;
import com.alkemy.disney.repository.PersonajeRepository;
import com.alkemy.disney.service.IPersonajeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Transactional(readOnly = true)
@Service
public class PersonajeService implements IPersonajeService {

    private final PersonajeRepository personajeRepository;
    private final PersonajeDTOModelMapper personajeDTOModelMapper;

    @Autowired
    public PersonajeService(PersonajeRepository personajeRepository, PersonajeDTOModelMapper personajeDTOModelMapper)
    {
        this.personajeRepository = personajeRepository;
        this.personajeDTOModelMapper = personajeDTOModelMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PersonajeDTO agregar(PersonajeDTO personajeDTO) throws BadRequestException
    {
        Personaje personaje = personajeDTOModelMapper.toModel(personajeDTO);

        if (personaje.getId() != null)
            throw new BadRequestException("El registro de personajes no puede recibir un ID.");

        Personaje personajeSaved = personajeRepository.save(personaje);
        log.info("Se registró un personaje con ID " + personajeSaved.getId() + ".");

        return personajeDTOModelMapper.toDTO(personajeSaved);
    }

    @Override
    public PersonajeDTO buscar(Long id) throws ResourceNotFoundException
    {
        Optional<Personaje> optionalPersonaje = personajeRepository.findById(id);

        if (optionalPersonaje.isEmpty())
            throw new ResourceNotFoundException("El personaje con el ID " + id + " no existe.");

        return personajeDTOModelMapper.toDTO(optionalPersonaje.get());
    }

    @Override
    public List<PersonajeDTO> listar()
    {
        return personajeDTOModelMapper.toDTOList(personajeRepository.findAll());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PersonajeDTO actualizar(PersonajeDTO personajeDTO) throws BadRequestException, ResourceNotFoundException
    {
        Personaje personaje = personajeDTOModelMapper.toModel(personajeDTO);

        if (personaje.getId() == null)
            throw new BadRequestException("La actualización de personajes requiere de un ID.");

        if (personajeRepository.findById(personaje.getId()).isEmpty())
            throw new ResourceNotFoundException("El personaje con el ID " + personaje.getId() + " no existe.");

        return personajeDTOModelMapper.toDTO(personajeRepository.save(personaje));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void eliminar(Long id) throws ResourceNotFoundException, BadRequestException
    {
        Optional<Personaje> personaje = personajeRepository.findById(id);
        if (personaje.isPresent())
        {
            personajeRepository.deleteById(id);
            log.info("Se eliminó el personaje con ID " + id + ".");
        }
        else
            throw new ResourceNotFoundException("El personaje con el ID " + id + " no existe.");
    }
}