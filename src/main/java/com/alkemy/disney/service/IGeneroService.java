package com.alkemy.disney.service;

import com.alkemy.disney.DTO.GeneroDTO;
import com.alkemy.disney.DTO.GeneroListadoDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceConflictException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;

import java.util.List;


public interface IGeneroService {

    public GeneroDTO agregar(GeneroDTO generoDTO) throws BadRequestException, ResourceConflictException;
    public GeneroDTO buscar(Long id) throws ResourceNotFoundException;
    public List<GeneroListadoDTO> listar();
    public GeneroDTO actualizar(GeneroDTO generoDTO) throws BadRequestException, ResourceNotFoundException, ResourceConflictException;
    public void eliminar(Long id) throws ResourceNotFoundException, BadRequestException;
}
