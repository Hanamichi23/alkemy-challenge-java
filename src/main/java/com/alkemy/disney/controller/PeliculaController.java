package com.alkemy.disney.controller;

import com.alkemy.disney.DTO.PeliculaDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.service.impl.PeliculaService;
import com.alkemy.disney.util.JsonResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Películas")
@RestController
@RequestMapping("/movies")
public class PeliculaController {

    private final PeliculaService peliculaService;

    @Autowired
    public PeliculaController(PeliculaService peliculaService)
    {
        this.peliculaService = peliculaService;
    }


    @Operation(summary = "Registrar una nueva película")
    @PostMapping
    public ResponseEntity<PeliculaDTO> agregar(@RequestBody PeliculaDTO peliculaDTO) throws BadRequestException
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaService.agregar(peliculaDTO));
    }

    @Operation(summary = "Buscar una película por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> buscar(@PathVariable Long id) throws ResourceNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(peliculaService.buscar(id));
    }

    @Operation(summary = "Obtener todas las películas")
    @GetMapping
    public ResponseEntity<List<PeliculaDTO>> listar()
    {
        return ResponseEntity.ok(peliculaService.listar());
    }

    @Operation(summary = "Actualizar una película")
    @PutMapping
    public ResponseEntity<PeliculaDTO> actualizar(@RequestBody PeliculaDTO peliculaDTO) throws BadRequestException, ResourceNotFoundException
    {
        return ResponseEntity.status(HttpStatus.OK).body(peliculaService.actualizar(peliculaDTO));
    }

    @Operation(summary = "Eliminar una película por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponseMessage> eliminar(@PathVariable Long id) throws ResourceNotFoundException, BadRequestException
    {
        peliculaService.eliminar(id);

        // Si llega hasta acá es porque no hubo excepción
        return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseMessage("La película con el ID " + id + " fue eliminada correctamente."));
    }
}