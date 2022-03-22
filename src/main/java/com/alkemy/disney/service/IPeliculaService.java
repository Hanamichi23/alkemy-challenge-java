package com.alkemy.disney.service;

import com.alkemy.disney.DTO.PeliculaDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IPeliculaService {

    public PeliculaDTO agregar(PeliculaDTO peliculaDTO) throws BadRequestException;
    public PeliculaDTO buscar(Long id) throws ResourceNotFoundException;
    public List<PeliculaDTO> listar();
    public PeliculaDTO actualizar(PeliculaDTO peliculaDTO) throws BadRequestException, ResourceNotFoundException;
    public void eliminar(Long id) throws ResourceNotFoundException, BadRequestException;
}
