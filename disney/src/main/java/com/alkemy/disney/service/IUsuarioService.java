package com.alkemy.disney.service;

import com.alkemy.disney.DTO.UsuarioDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceConflictException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IUsuarioService {

    public UsuarioDTO registrar(UsuarioDTO usuarioDTO) throws BadRequestException, ResourceConflictException;;
    public UsuarioDTO buscar(Long id) throws ResourceNotFoundException;
    public List<UsuarioDTO> listar();
    public UsuarioDTO actualizar(UsuarioDTO usuarioDTO) throws BadRequestException, ResourceNotFoundException, ResourceConflictException;;
    public void eliminar(Long id) throws ResourceNotFoundException, BadRequestException;
}
