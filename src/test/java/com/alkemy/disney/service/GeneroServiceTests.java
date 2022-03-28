package com.alkemy.disney.service;

import com.alkemy.disney.DTO.GeneroDTO;
import com.alkemy.disney.DTO.GeneroListadoDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceConflictException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.repository.GeneroRepository;
import com.alkemy.disney.service.impl.GeneroService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class GeneroServiceTests {

    @Autowired
    private GeneroService generoService;
    @Autowired
    private GeneroRepository generoRepository;

    @BeforeEach
    public void setUp()
    {
        generoRepository.deleteAll();
    }

    @AfterAll
    public void cleanUp()
    {
        generoRepository.deleteAll();
    }

    @Test
    public void agregarGenero() throws BadRequestException, ResourceConflictException
    {
        GeneroDTO generoAgregadoDTO = generoService.agregar(new GeneroDTO(
                "Thriller",
                "http://www.imagen-genero.com/thriller.png",
                null
        ));
        Assertions.assertNotNull(generoAgregadoDTO.getId());
    }

    @Test
    public void agregarGeneroNombreRepetido() throws BadRequestException, ResourceConflictException
    {
        GeneroDTO generoAgregadoDTO = generoService.agregar(new GeneroDTO(
                "Thriller",
                "http://www.imagen-genero.com/thriller.png",
                null
        ));

        Assertions.assertNotNull(generoAgregadoDTO.getId());
        Assertions.assertThrows(ResourceConflictException.class, () ->
                generoService.agregar(new GeneroDTO("Thriller", null, null)));
    }

    @Test
    public void buscarGenero() throws BadRequestException, ResourceNotFoundException, ResourceConflictException
    {
        GeneroDTO generoAgregadoDTO = generoService.agregar(new GeneroDTO(
                "Thriller",
                "http://www.imagen-genero.com/thriller.png",
                null
        ));

        Assertions.assertDoesNotThrow( () -> generoService.buscar(generoAgregadoDTO.getId()) );
        GeneroDTO generoBuscadoDTO = generoService.buscar(generoAgregadoDTO.getId());
        Assertions.assertNotNull(generoBuscadoDTO);
        Assertions.assertEquals(generoAgregadoDTO.getId(), generoBuscadoDTO.getId());
    }

    @Test
    public void buscarGeneroInexistente()
    {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> generoService.buscar(9999L));
    }

    @Test
    public void listarTodos() throws BadRequestException, ResourceConflictException
    {
        generoService.agregar(new GeneroDTO(
                "Thriller",
                "http://www.imagen-genero.com/thriller.png",
                null
        ));
        generoService.agregar(new GeneroDTO(
                "Aventura",
                "http://www.imagen-genero.com/aventura.png",
                null
        ));
        generoService.agregar(new GeneroDTO(
                "Acción",
                "http://www.imagen-genero.com/accion.png",
                null
        ));
        List<GeneroListadoDTO> generosDTO = generoService.listar();
        Assertions.assertTrue(!generosDTO.isEmpty());
        Assertions.assertTrue(generosDTO.size() == 3);
    }

    @Test
    public void actualizarGenero() throws BadRequestException, ResourceNotFoundException, ResourceConflictException
    {
        GeneroDTO generoAgregadoDTO = generoService.agregar(new GeneroDTO(
                "Thriller",
                "http://www.imagen-genero.com/thriller.png",
                null
        ));

        GeneroDTO generoBuscadoDTO = generoService.buscar(generoAgregadoDTO.getId());
        Assertions.assertNotNull(generoBuscadoDTO);
        Assertions.assertEquals("http://www.imagen-genero.com/thriller.png", generoBuscadoDTO.getImagenUrl());

        generoBuscadoDTO.setImagenUrl("http://www.imagen-genero.com/thr.png");
        GeneroDTO generoActualizadoDTO = generoService.actualizar(generoBuscadoDTO);
        GeneroDTO generoActualizadoBuscadoDTO = generoService.buscar(generoActualizadoDTO.getId());
        Assertions.assertNotNull(generoActualizadoBuscadoDTO);
        Assertions.assertEquals("http://www.imagen-genero.com/thr.png", generoActualizadoBuscadoDTO.getImagenUrl());
    }

    @Test
    public void eliminarGenero() throws ResourceNotFoundException, BadRequestException, ResourceConflictException
    {
        GeneroDTO generoAgregadoDTO = generoService.agregar(new GeneroDTO(
                "Thriller",
                "http://www.imagen-genero.com/thriller.png",
                null
        ));

        generoService.eliminar(generoAgregadoDTO.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> generoService.buscar(generoAgregadoDTO.getId()));
    }
}
