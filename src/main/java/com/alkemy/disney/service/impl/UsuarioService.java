package com.alkemy.disney.service.impl;

import com.alkemy.disney.DTO.UsuarioDTO;
import com.alkemy.disney.DTO.mapper.UsuarioDTOModelMapper;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceConflictException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.model.Usuario;
import com.alkemy.disney.repository.RolRepository;
import com.alkemy.disney.repository.UsuarioRepository;
import com.alkemy.disney.service.IUsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Transactional(readOnly = true)
@Service
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioDTOModelMapper usuarioDTOModelMapper;
    private final RolRepository rolRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioDTOModelMapper usuarioDTOModelMapper,
                          RolRepository rolRepository)
    {
        this.usuarioRepository = usuarioRepository;
        this.usuarioDTOModelMapper = usuarioDTOModelMapper;
        this.rolRepository = rolRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UsuarioDTO registrar(UsuarioDTO usuarioDTO) throws BadRequestException, ResourceConflictException
    {
        Usuario usuario = usuarioDTOModelMapper.toModel(usuarioDTO);

        if (usuario.getId() != null)
            throw new BadRequestException("El registro de usuarios no puede recibir un ID.");

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
            throw new ResourceConflictException("El usuario con el email '" + usuario.getEmail() + "' ya existe.");

        usuario.setRol(rolRepository.findByNombre("ROLE_USER").orElse(null));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        Usuario usuarioSaved = usuarioRepository.save(usuario);
        log.info("Se registró un usuario con ID " + usuarioSaved.getId() + ".");

        return usuarioDTOModelMapper.toDTO(usuarioSaved);
    }

    @Override
    public UsuarioDTO buscar(Long id) throws ResourceNotFoundException
    {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isEmpty())
            throw new ResourceNotFoundException("El usuario con el ID " + id + " no existe.");

        return usuarioDTOModelMapper.toDTO(optionalUsuario.get());
    }

    @Override
    public List<UsuarioDTO> listar()
    {
        return usuarioDTOModelMapper.toDTOList(usuarioRepository.findAll());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UsuarioDTO actualizar(UsuarioDTO usuarioDTO) throws BadRequestException, ResourceNotFoundException, ResourceConflictException
    {
        Usuario usuario = usuarioDTOModelMapper.toModel(usuarioDTO);

        if (usuario.getId() == null)
            throw new BadRequestException("La actualización de usuarios requiere de un ID.");

        if (usuarioRepository.findById(usuario.getId()).isEmpty())
            throw new ResourceNotFoundException("El usuario con el ID " + usuario.getId() + " no existe.");

        Optional<Usuario> usuarioMismoEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioMismoEmail.isPresent() && !usuarioMismoEmail.get().getId().equals(usuario.getId()))
            throw new ResourceConflictException("El usuario con el email '" + usuario.getEmail() + "' ya existe.");

        usuario.setRol(rolRepository.findByNombre("ROLE_USER").orElse(null));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        return usuarioDTOModelMapper.toDTO(usuarioRepository.save(usuario));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void eliminar(Long id) throws ResourceNotFoundException, BadRequestException
    {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent())
        {
            usuarioRepository.deleteById(id);
            log.info("Se eliminó el usuario con ID " + id + ".");
        }
        else
            throw new ResourceNotFoundException("El usuario con el ID " + id + " no existe.");
    }
}