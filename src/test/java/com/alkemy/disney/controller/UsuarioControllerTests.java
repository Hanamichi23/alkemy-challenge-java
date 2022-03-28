package com.alkemy.disney.controller;

import com.alkemy.disney.DTO.GeneroDTO;
import com.alkemy.disney.DTO.UsuarioDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceConflictException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.model.Rol;
import com.alkemy.disney.repository.RolRepository;
import com.alkemy.disney.repository.UsuarioRepository;
import com.alkemy.disney.service.impl.UsuarioService;
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
import java.util.List;


@AutoConfigureMockMvc(addFilters = false)       // El addFilters es para desabilitar la seguridad de la API
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UsuarioControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;


    @BeforeAll
    public void setUpAll()
    {
        rolRepository.save(new Rol("ROLE_USER"));
    }

    @BeforeEach
    public void setUp()
    {
        usuarioRepository.deleteAll();
    }

    @AfterAll
    public void cleanUp()
    {
        usuarioRepository.deleteAll();
    }

    @Test
    public void agregarUsuario() throws Exception
    {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                "usuario_nuevo@test.com",
                "password1234",
                null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        UsuarioDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), UsuarioDTO.class);
        List<UsuarioDTO> lista = usuarioService.listar();
        Assertions.assertEquals(lista.get(lista.size()-1).getId(), responseAsObject.getId());
    }

    @Test
    public void agregarUsuarioEmailNulo() throws Exception
    {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                null,
                "password1234",
                null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void obtenerUsuario() throws Exception
    {
        UsuarioDTO usuarioAgregadoDTO = addUsuarioUsingService();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/usuarios/"+usuarioAgregadoDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        UsuarioDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), UsuarioDTO.class);
        Assertions.assertEquals(usuarioAgregadoDTO.getId(), responseAsObject.getId());
    }

    @Test
    public void obtenerUsuarioInexistente() throws Exception
    {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/usuarios/9999")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        JsonResponseError responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), JsonResponseError.class);
        Assertions.assertEquals(404, responseAsObject.getStatus());
        Assertions.assertEquals("El usuario con el ID 9999 no existe.", responseAsObject.getError());
    }

    @Test
    public void obtenerTodos() throws Exception
    {
        addUsuarioUsingService();
        usuarioService.registrar(new UsuarioDTO(
                "usuario_nuevo_2@test.com",
                "testpass555",
                rolRepository.findByNombre("ROLE_USER").get()));
        usuarioService.registrar(new UsuarioDTO(
                "usuario_nuevo_3@test.com",
                "contrasena987",
                rolRepository.findByNombre("ROLE_USER").get()));

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        List<UsuarioDTO> responseAsObjet =
                objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<List<UsuarioDTO>>(){});
        Assertions.assertFalse(responseAsObjet.isEmpty());
        Assertions.assertEquals(3, responseAsObjet.size());
    }

    @Test
    public void actualizarUsuario() throws Exception
    {
        UsuarioDTO usuarioDTO = addUsuarioUsingService();
        Assertions.assertEquals("usuario_nuevo@test.com", usuarioDTO.getEmail());

        usuarioDTO.setEmail("usuario_nuevo_actualizado@test.com");
        // Set del password para que no tire error de password null (debido al JsonProperty.Access.WRITE_ONLY)
        usuarioDTO.setContrasena("password1234");

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        UsuarioDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), UsuarioDTO.class);
        Assertions.assertEquals("usuario_nuevo_actualizado@test.com", responseAsObject.getEmail());
    }

    @Test
    public void eliminarUsuario() throws Exception
    {
        UsuarioDTO usuarioAgregadoDTO = addUsuarioUsingService();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/usuarios/"+usuarioAgregadoDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        JsonResponseMessage responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), JsonResponseMessage.class);
        Assertions.assertEquals("El usuario con el ID "+usuarioAgregadoDTO.getId()+" fue eliminado correctamente.", responseAsObject.getMessage());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> usuarioService.buscar(usuarioAgregadoDTO.getId()));
    }


    // Helper method
    public UsuarioDTO addUsuarioUsingService() throws BadRequestException, ResourceConflictException
    {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                "usuario_nuevo@test.com",
                "password1234",
                rolRepository.findByNombre("ROLE_USER").get());

        return usuarioService.registrar(usuarioDTO);
    }
}