package com.alkemy.disney.controller;

import com.alkemy.disney.DTO.PersonajeDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.service.impl.PersonajeService;
import com.alkemy.disney.util.JsonResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Personajes")
@RestController
@RequestMapping("/characters")
public class PersonajeController {

    private final PersonajeService personajeService;

    @Autowired
    public PersonajeController(PersonajeService personajeService)
    {
        this.personajeService = personajeService;
    }


    @Operation(summary = "Registrar un nuevo personaje")
    @PostMapping
    public ResponseEntity<PersonajeDTO> agregar(@RequestBody PersonajeDTO personajeDTO) throws BadRequestException
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(personajeService.agregar(personajeDTO));
    }

    @Operation(summary = "Buscar un personaje por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PersonajeDTO> buscar(@PathVariable Long id) throws ResourceNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(personajeService.buscar(id));
    }

    @Operation(summary = "Obtener todos los personajes")
    @GetMapping
    public ResponseEntity<List<PersonajeDTO>> listar()
    {
        return ResponseEntity.ok(personajeService.listar());
    }

    @Operation(summary = "Actualizar un personaje")
    @PutMapping
    public ResponseEntity<PersonajeDTO> actualizar(@RequestBody PersonajeDTO personajeDTO) throws BadRequestException, ResourceNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(personajeService.actualizar(personajeDTO));
    }

    @Operation(summary = "Eliminar un personaje por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponseMessage> eliminar(@PathVariable Long id) throws ResourceNotFoundException, BadRequestException
    {
        personajeService.eliminar(id);

        // Si llega hasta acá es porque no hubo excepción
        return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseMessage("El personaje con el ID " + id + " fue eliminado correctamente."));
    }
}