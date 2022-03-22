package com.alkemy.disney.controller;

import com.alkemy.disney.DTO.GeneroDTO;
import com.alkemy.disney.DTO.GeneroListadoDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.service.impl.GeneroService;
import com.alkemy.disney.util.JsonResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Géneros")
@RestController
@RequestMapping("/genres")
public class GeneroController {

    private final GeneroService generoService;

    @Autowired
    public GeneroController(GeneroService generoService)
    {
        this.generoService = generoService;
    }


    @Operation(summary = "Registrar un nuevo género")
    @PostMapping
    public ResponseEntity<GeneroDTO> agregar(@RequestBody GeneroDTO generoDTO) throws BadRequestException
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(generoService.agregar(generoDTO));
    }

    @Operation(summary = "Buscar un género por ID")
    @GetMapping("/{id}")
    public ResponseEntity<GeneroDTO> buscar(@PathVariable Long id) throws ResourceNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(generoService.buscar(id));
    }

    @Operation(summary = "Obtener todos los géneros")
    @GetMapping
    public ResponseEntity<List<GeneroListadoDTO>> listar()
    {
        return ResponseEntity.ok(generoService.listar());
    }

    @Operation(summary = "Actualizar un género")
    @PutMapping
    public ResponseEntity<GeneroDTO> actualizar(@RequestBody GeneroDTO generoDTO) throws BadRequestException, ResourceNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(generoService.actualizar(generoDTO));
    }

    @Operation(summary = "Eliminar un género por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponseMessage> eliminar(@PathVariable Long id) throws ResourceNotFoundException, BadRequestException
    {
        generoService.eliminar(id);

        // Si llega hasta acá es porque no hubo excepción
        return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseMessage("El género con el ID " + id + " fue eliminado correctamente."));
    }
}