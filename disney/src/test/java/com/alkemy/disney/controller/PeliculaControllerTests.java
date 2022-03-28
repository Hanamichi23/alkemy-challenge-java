package com.alkemy.disney.controller;

import com.alkemy.disney.DTO.PeliculaDTO;
import com.alkemy.disney.DTO.PeliculaListadoDTO;
import com.alkemy.disney.DTO.PersonajeDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.repository.PeliculaRepository;
import com.alkemy.disney.service.impl.PeliculaService;
import com.alkemy.disney.util.JsonResponseError;
import com.alkemy.disney.util.JsonResponseMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;


@AutoConfigureMockMvc(addFilters = false)       // El addFilters es para desabilitar la seguridad de la API
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class PeliculaControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
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
    public void agregarPelicula() throws Exception
    {
        PeliculaDTO peliculaDTO = new PeliculaDTO(
                "Harry Potter y la Piedra Filosofal",
                "http://www.imagen-pelicula.com/piedrafilosofal.png",
                LocalDate.parse("2001-05-08"),
                4,
                null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(peliculaDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        PeliculaDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), PeliculaDTO.class);
        List<PeliculaListadoDTO> lista = peliculaService.listar();
        Assertions.assertEquals(lista.get(lista.size()-1).getId(), responseAsObject.getId());
    }

    @Test
    public void agregarPeliculaTituloNulo() throws Exception
    {
        PeliculaDTO peliculaDTO = new PeliculaDTO(
                null,
                "http://www.imagen-pelicula.com/piedrafilosofal.png",
                LocalDate.parse("2001-05-08"),
                4,
                null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(peliculaDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void obtenerPelicula() throws Exception
    {
        PeliculaDTO peliculaAgregadaDTO = addPeliculaUsingService();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/movies/"+peliculaAgregadaDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        PeliculaDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), PeliculaDTO.class);
        Assertions.assertEquals(peliculaAgregadaDTO.getId(), responseAsObject.getId());
    }

    @Test
    public void obtenerPeliculaInexistente() throws Exception
    {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/movies/9999")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        JsonResponseError responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), JsonResponseError.class);
        Assertions.assertEquals(404, responseAsObject.getStatus());
        Assertions.assertEquals("La película con el ID 9999 no existe.", responseAsObject.getError());
    }

    @Test
    public void obtenerTodos() throws Exception
    {
        addPeliculaUsingService();
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

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/movies")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        List<PeliculaListadoDTO> responseAsObjet =
                objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<List<PeliculaListadoDTO>>(){});
        Assertions.assertFalse(responseAsObjet.isEmpty());
        Assertions.assertEquals(3, responseAsObjet.size());
    }

    @Test
    public void actualizarPelicula() throws Exception
    {
        PeliculaDTO peliculaDTO = addPeliculaUsingService();
        Assertions.assertEquals(LocalDate.parse("2001-05-08"), peliculaDTO.getFechaCreacion());

        peliculaDTO.setFechaCreacion(LocalDate.parse("2001-06-24"));

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(peliculaDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        PeliculaDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), PeliculaDTO.class);
        Assertions.assertEquals(LocalDate.parse("2001-06-24"), responseAsObject.getFechaCreacion());
    }

    @Test
    public void eliminarPelicula() throws Exception
    {
        PeliculaDTO peliculaAgregadaDTO = addPeliculaUsingService();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/movies/"+peliculaAgregadaDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        JsonResponseMessage responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), JsonResponseMessage.class);
        Assertions.assertEquals("La película con el ID "+peliculaAgregadaDTO.getId()+" fue eliminada correctamente.", responseAsObject.getMessage());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> peliculaService.buscar(peliculaAgregadaDTO.getId()));
    }


    // Helper method
    public PeliculaDTO addPeliculaUsingService() throws BadRequestException
    {
        PeliculaDTO peliculaDTO = new PeliculaDTO(
                "Harry Potter y la Piedra Filosofal",
                "http://www.imagen-pelicula.com/piedrafilosofal.png",
                LocalDate.parse("2001-05-08"),
                4,
                null);

        return peliculaService.agregar(peliculaDTO);
    }
}