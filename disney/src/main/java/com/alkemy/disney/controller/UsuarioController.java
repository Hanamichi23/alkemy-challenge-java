package com.alkemy.disney.controller;

import com.alkemy.disney.DTO.UsuarioDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceConflictException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.service.impl.EmailSenderService;
import com.alkemy.disney.service.impl.UsuarioService;
import com.alkemy.disney.util.JsonResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Tag(name = "Usuarios")
@CrossOrigin
@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, EmailSenderService emailSenderService)
    {
        this.usuarioService = usuarioService;
        this.emailSenderService = emailSenderService;
    }


    @Operation(summary = "Registrar un nuevo usuario")
    @PostMapping("/auth/register")
    public ResponseEntity<UsuarioDTO> registrar(@Valid @RequestBody UsuarioDTO usuarioDTO) throws BadRequestException, ResourceConflictException, IOException
    {
        UsuarioDTO usuarioRegistradoDTO = usuarioService.registrar(usuarioDTO);
        emailSenderService.sendRegistrationEmail(usuarioDTO.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistradoDTO);
    }

    @Operation(summary = "Buscar un usuario por ID")
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) throws ResourceNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscar(id));
    }

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> listar()
    {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @Operation(summary = "Actualizar un usuario")
    @PutMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> actualizar(@Valid @RequestBody UsuarioDTO usuarioDTO) throws BadRequestException, ResourceNotFoundException, ResourceConflictException
    {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.actualizar(usuarioDTO));
    }

    @Operation(summary = "Eliminar un usuario por ID")
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<JsonResponseMessage> eliminar(@PathVariable Long id) throws ResourceNotFoundException, BadRequestException
    {
        usuarioService.eliminar(id);

        // Si llega hasta acá es porque no hubo excepción
        return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseMessage("El usuario con el ID " + id + " fue eliminado correctamente."));
    }
}