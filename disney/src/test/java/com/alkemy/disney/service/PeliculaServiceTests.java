package com.alkemy.disney.service;

import com.alkemy.disney.DTO.PeliculaDTO;
import com.alkemy.disney.DTO.PeliculaListadoDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.repository.PeliculaRepository;
import com.alkemy.disney.service.impl.PeliculaService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class PeliculaServiceTests {

    @Autowired
    private PeliculaService peliculaService;
    @Autowired
    private PeliculaRepository peliculaRepository;

    @BeforeEach
    public void setUp()
    {
        peliculaRepository.deleteAll();
    }

    @AfterAll
    public void cleanUp()
    {
        peliculaRepository.deleteAll();
    }

    @Test
    public void agregarPelicula() throws BadRequestException
    {
        PeliculaDTO peliculaAgregadaDTO = peliculaService.agregar(new PeliculaDTO(
                "Harry Potter y la Piedra Filosofal",
                "http://www.imagen-pelicula.com/piedrafilosofal.png",
                LocalDate.parse("2001-05-08"),
                4,
                null));
        Assertions.assertNotNull(peliculaAgregadaDTO.getId());
    }

    @Test
    public void buscarPelicula() throws BadRequestException, ResourceNotFoundException
    {
        PeliculaDTO peliculaAgregadaDTO = peliculaService.agregar(new PeliculaDTO(
                "Harry Potter y la Piedra Filosofal",
                "http://www.imagen-pelicula.com/piedrafilosofal.png",
                LocalDate.parse("2001-05-08"),
                4,
                null));

        Assertions.assertDoesNotThrow( () -> peliculaService.buscar(peliculaAgregadaDTO.getId()) );
        PeliculaDTO peliculaBuscadaDTO = peliculaService.buscar(peliculaAgregadaDTO.getId());
        Assertions.assertNotNull(peliculaBuscadaDTO);
        Assertions.assertEquals(peliculaAgregadaDTO.getId(), peliculaBuscadaDTO.getId());
    }

    @Test
    public void buscarPeliculaInexistente()
    {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> peliculaService.buscar(9999L));
    }

    @Test
    public void listarTodas() throws BadRequestException
    {
        peliculaService.agregar(new PeliculaDTO(
                "Harry Potter y la Piedra Filosofal",
                "http://www.imagen-pelicula.com/piedrafilosofal.png",
                LocalDate.parse("2001-05-08"),
                4,
                null));
        peliculaService.agregar(new PeliculaDTO(
                "Harry Potter y la Cámara Secreta",
                "http://www.imagen-pelicula.com/camarasecreta.png",
                LocalDate.parse("2002-04-14"),
                4,
                null));
        peliculaService.agregar(new PeliculaDTO(
                "Harry Potter y el Prisionero de Azkaban",
                "http://www.imagen-pelicula.com/prisioneroazkaban.png",
                LocalDate.parse("2004-01-12"),
                4,
                null));
        List<PeliculaListadoDTO> peliculasDTO = peliculaService.listar();
        Assertions.assertTrue(!peliculasDTO.isEmpty());
        Assertions.assertTrue(peliculasDTO.size() == 3);
    }

    @Test
    public void actualizarPelicula() throws BadRequestException, ResourceNotFoundException
    {
        PeliculaDTO peliculaAgregadaDTO = peliculaService.agregar(new PeliculaDTO(
                "Harry Potter y la Piedra Filosofal",
                "http://www.imagen-pelicula.com/piedrafilosofal.png",
                LocalDate.parse("2001-05-08"),
                4,
                null));

        PeliculaDTO peliculaBuscadaDTO = peliculaService.buscar(peliculaAgregadaDTO.getId());
        Assertions.assertNotNull(peliculaBuscadaDTO);
        Assertions.assertEquals(LocalDate.parse("2001-05-08"), peliculaBuscadaDTO.getFechaCreacion());

        peliculaBuscadaDTO.setFechaCreacion(LocalDate.parse("2001-06-24"));
        PeliculaDTO peliculaActualizadaDTO = peliculaService.actualizar(peliculaBuscadaDTO);
        PeliculaDTO peliculaActualizadaBuscadaDTO = peliculaService.buscar(peliculaActualizadaDTO.getId());
        Assertions.assertNotNull(peliculaActualizadaBuscadaDTO);
        Assertions.assertEquals(LocalDate.parse("2001-06-24"), peliculaActualizadaBuscadaDTO.getFechaCreacion());
    }

    @Test
    public void eliminarPelicula() throws ResourceNotFoundException, BadRequestException
    {
        PeliculaDTO peliculaAgregadaDTO = peliculaService.agregar(new PeliculaDTO(
                "Harry Potter y la Piedra Filosofal",
                "http://www.imagen-pelicula.com/piedrafilosofal.png",
                LocalDate.parse("2001-05-08"),
                4,
                null));

        peliculaService.eliminar(peliculaAgregadaDTO.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> peliculaService.buscar(peliculaAgregadaDTO.getId()));
    }
}