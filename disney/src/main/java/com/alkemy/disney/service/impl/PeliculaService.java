package com.alkemy.disney.service.impl;

import com.alkemy.disney.DTO.PeliculaDTO;
import com.alkemy.disney.DTO.PeliculaListadoDTO;
import com.alkemy.disney.DTO.mapper.PeliculaDTOModelMapper;
import com.alkemy.disney.DTO.mapper.PeliculaListadoDTOModelMapper;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.model.Pelicula;
import com.alkemy.disney.repository.PeliculaRepository;
import com.alkemy.disney.service.IPeliculaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Transactional(readOnly = true)
@Service
public class PeliculaService implements IPeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final PeliculaDTOModelMapper peliculaDTOModelMapper;
    private final PeliculaListadoDTOModelMapper peliculaListadoDTOModelMapper;

    @Autowired
    public PeliculaService(PeliculaRepository peliculaRepository, PeliculaDTOModelMapper peliculaDTOModelMapper,
                           PeliculaListadoDTOModelMapper peliculaListadoDTOModelMapper)
    {
        this.peliculaRepository = peliculaRepository;
        this.peliculaDTOModelMapper = peliculaDTOModelMapper;
        this.peliculaListadoDTOModelMapper = peliculaListadoDTOModelMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PeliculaDTO agregar(PeliculaDTO peliculaDTO) throws BadRequestException
    {
        Pelicula pelicula = peliculaDTOModelMapper.toModel(peliculaDTO);

        if (pelicula.getId() != null)
            throw new BadRequestException("El registro de películas no puede recibir un ID.");

        Pelicula peliculaSaved = peliculaRepository.save(pelicula);
        log.info("Se registró una película con ID " + peliculaSaved.getId() + ".");

        return peliculaDTOModelMapper.toDTO(peliculaSaved);
    }

    @Override
    public PeliculaDTO buscar(Long id) throws ResourceNotFoundException
    {
        Optional<Pelicula> optionalPelicula = peliculaRepository.findById(id);

        if (optionalPelicula.isEmpty())
            throw new ResourceNotFoundException("La película con el ID " + id + " no existe.");

        return peliculaDTOModelMapper.toDTO(optionalPelicula.get());
    }

    @Override
    public List<PeliculaListadoDTO> listar()
    {
        return peliculaListadoDTOModelMapper.toDTOList(peliculaRepository.findAll());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PeliculaDTO actualizar(PeliculaDTO peliculaDTO) throws BadRequestException, ResourceNotFoundException
    {
        Pelicula pelicula = peliculaDTOModelMapper.toModel(peliculaDTO);

        if (pelicula.getId() == null)
            throw new BadRequestException("La actualización de películas requiere de un ID.");

        if (peliculaRepository.findById(pelicula.getId()).isEmpty())
            throw new ResourceNotFoundException("La película con el ID " + pelicula.getId() + " no existe.");

        return peliculaDTOModelMapper.toDTO(peliculaRepository.save(pelicula));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void eliminar(Long id) throws ResourceNotFoundException, BadRequestException
    {
        Optional<Pelicula> pelicula = peliculaRepository.findById(id);
        if (pelicula.isPresent())
        {
            peliculaRepository.deleteById(id);
            log.info("Se eliminó la película con ID " + id + ".");
        }
        else
            throw new ResourceNotFoundException("La película con el ID " + id + " no existe.");
    }
}