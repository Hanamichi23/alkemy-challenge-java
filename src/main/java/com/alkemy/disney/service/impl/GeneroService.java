package com.alkemy.disney.service.impl;

import com.alkemy.disney.DTO.GeneroDTO;
import com.alkemy.disney.DTO.GeneroListadoDTO;
import com.alkemy.disney.DTO.mapper.GeneroDTOModelMapper;
import com.alkemy.disney.DTO.mapper.GeneroListadoDTOModelMapper;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.model.Genero;
import com.alkemy.disney.repository.GeneroRepository;
import com.alkemy.disney.service.IGeneroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Transactional(readOnly = true)
@Service
public class GeneroService implements IGeneroService {

    private final GeneroRepository generoRepository;
    private final GeneroDTOModelMapper generoDTOModelMapper;
    private final GeneroListadoDTOModelMapper generoListadoDTOModelMapper;

    @Autowired
    public GeneroService(GeneroRepository generoRepository, GeneroDTOModelMapper generoDTOModelMapper,
                         GeneroListadoDTOModelMapper generoListadoDTOModelMapper)
    {
        this.generoRepository = generoRepository;
        this.generoDTOModelMapper = generoDTOModelMapper;
        this.generoListadoDTOModelMapper = generoListadoDTOModelMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneroDTO agregar(GeneroDTO generoDTO) throws BadRequestException
    {
        Genero genero = generoDTOModelMapper.toModel(generoDTO);

        if (genero.getId() != null)
            throw new BadRequestException("El registro de géneros no puede recibir un ID.");

        Genero generoSaved = generoRepository.save(genero);
        log.info("Se registró un género con ID " + generoSaved.getId() + ".");

        return generoDTOModelMapper.toDTO(generoSaved);
    }

    @Override
    public GeneroDTO buscar(Long id) throws ResourceNotFoundException
    {
        Optional<Genero> optionalGenero = generoRepository.findById(id);

        if (optionalGenero.isEmpty())
            throw new ResourceNotFoundException("El género con el ID " + id + " no existe.");

        return generoDTOModelMapper.toDTO(optionalGenero.get());
    }

    @Override
    public List<GeneroListadoDTO> listar()
    {
        return generoListadoDTOModelMapper.toDTOList(generoRepository.findAll());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneroDTO actualizar(GeneroDTO generoDTO) throws BadRequestException, ResourceNotFoundException
    {
        Genero genero = generoDTOModelMapper.toModel(generoDTO);

        if (genero.getId() == null)
            throw new BadRequestException("La actualización de géneros requiere de un ID.");

        if (generoRepository.findById(genero.getId()).isEmpty())
            throw new ResourceNotFoundException("El género con el ID " + genero.getId() + " no existe.");

        return generoDTOModelMapper.toDTO(generoRepository.save(genero));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void eliminar(Long id) throws ResourceNotFoundException, BadRequestException
    {
        Optional<Genero> genero = generoRepository.findById(id);
        if (genero.isPresent())
        {
            generoRepository.deleteById(id);
            log.info("Se eliminó el género con ID " + id + ".");
        }
        else
            throw new ResourceNotFoundException("El género con el ID " + id + " no existe.");
    }
}