package com.alkemy.disney.controller;

import com.alkemy.disney.DTO.GeneroDTO;
import com.alkemy.disney.DTO.GeneroListadoDTO;
import com.alkemy.disney.DTO.PeliculaDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceConflictException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.repository.GeneroRepository;
import com.alkemy.disney.service.impl.GeneroService;
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
public class GeneroControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
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
    public void agregarGenero() throws Exception
    {
        GeneroDTO generoDTO = new GeneroDTO(
                "Thriller",
                "http://www.imagen-genero.com/thriller.png",
                null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generoDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        GeneroDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), GeneroDTO.class);
        List<GeneroListadoDTO> lista = generoService.listar();
        Assertions.assertEquals(lista.get(lista.size()-1).getId(), responseAsObject.getId());
    }

    @Test
    public void agregarGeneroNombreNulo() throws Exception
    {
        GeneroDTO generoDTO = new GeneroDTO(
                null,
                "http://www.imagen-genero.com/thriller.png",
                null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generoDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void obtenerGenero() throws Exception
    {
        GeneroDTO generoAgregadoDTO = addGeneroUsingService();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/genres/"+generoAgregadoDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        GeneroDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), GeneroDTO.class);
        Assertions.assertEquals(generoAgregadoDTO.getId(), responseAsObject.getId());
    }

    @Test
    public void obtenerGeneroInexistente() throws Exception
    {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/genres/9999")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        JsonResponseError responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), JsonResponseError.class);
        Assertions.assertEquals(404, responseAsObject.getStatus());
        Assertions.assertEquals("El género con el ID 9999 no existe.", responseAsObject.getError());
    }

    @Test
    public void obtenerTodos() throws Exception
    {
        addGeneroUsingService();
        generoService.agregar(new GeneroDTO(
                "Aventura",
                "http://www.imagen-genero.com/aventura.png",
                null));
        generoService.agregar(new GeneroDTO(
                "Acción",
                "http://www.imagen-genero.com/accion.png",
                null));

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/genres")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        List<GeneroListadoDTO> responseAsObjet =
                objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<List<GeneroListadoDTO>>(){});
        Assertions.assertFalse(responseAsObjet.isEmpty());
        Assertions.assertEquals(3, responseAsObjet.size());
    }

    @Test
    public void actualizarGenero() throws Exception
    {
        GeneroDTO generoDTO = addGeneroUsingService();
        Assertions.assertEquals("http://www.imagen-genero.com/thriller.png", generoDTO.getImagenUrl());

        generoDTO.setImagenUrl("http://www.imagen-genero.com/thr.png");

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generoDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        GeneroDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), GeneroDTO.class);
        Assertions.assertEquals("http://www.imagen-genero.com/thr.png", responseAsObject.getImagenUrl());
    }

    @Test
    public void eliminarGenero() throws Exception
    {
        GeneroDTO generoAgregadoDTO = addGeneroUsingService();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/genres/"+generoAgregadoDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        JsonResponseMessage responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), JsonResponseMessage.class);
        Assertions.assertEquals("El género con el ID "+generoAgregadoDTO.getId()+" fue eliminado correctamente.", responseAsObject.getMessage());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> generoService.buscar(generoAgregadoDTO.getId()));
    }


    // Helper method
    public GeneroDTO addGeneroUsingService() throws BadRequestException, ResourceConflictException
    {
        GeneroDTO generoDTO = new GeneroDTO(
                "Thriller",
                "http://www.imagen-genero.com/thriller.png",
                null);

        return generoService.agregar(generoDTO);
    }
}