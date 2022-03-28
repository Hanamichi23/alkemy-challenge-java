package com.alkemy.disney.controller;

import com.alkemy.disney.DTO.PersonajeDTO;
import com.alkemy.disney.DTO.PersonajeListadoDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.model.Personaje;
import com.alkemy.disney.repository.PersonajeRepository;
import com.alkemy.disney.service.impl.PersonajeService;
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
public class PersonajeControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PersonajeService personajeService;
    @Autowired
    private PersonajeRepository personajeRepository;

    @BeforeEach
    public void setUp()
    {
        personajeRepository.deleteAll();
    }

    @AfterAll
    public void cleanUp()
    {
        personajeRepository.deleteAll();
    }

    @Test
    public void agregarPersonaje() throws Exception
    {
        PersonajeDTO personajeDTO = new PersonajeDTO(
                "Samwise Gamgee",
                "http://www.imagen-personaje.com/sam.png",
                34,
                82.4,
                "Es un hobbit de la Comarca, nacido en el año 1380. Jardinero de Frodo Bolsón. Sam acompaña a Frodo hasta el Orodruin a destruir el Anillo Único.",
                null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personajeDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        PersonajeDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), PersonajeDTO.class);
        List<PersonajeListadoDTO> lista = personajeService.listar();
        Assertions.assertEquals(lista.get(lista.size()-1).getId(), responseAsObject.getId());
    }

    @Test
    public void agregarPersonajeNombreNulo() throws Exception
    {
        PersonajeDTO personajeDTO = new PersonajeDTO(
                null,
                "http://www.imagen-personaje.com/sam.png",
                34,
                82.4,
                "Es un hobbit de la Comarca, nacido en el año 1380. Jardinero de Frodo Bolsón. Sam acompaña a Frodo hasta el Orodruin a destruir el Anillo Único.",
                null);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personajeDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void obtenerPersonaje() throws Exception
    {
        PersonajeDTO personajeAgregadoDTO = addPersonajeUsingService();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/characters/"+personajeAgregadoDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        PersonajeDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), PersonajeDTO.class);
        Assertions.assertEquals(personajeAgregadoDTO.getId(), responseAsObject.getId());
    }

    @Test
    public void obtenerPersonajeInexistente() throws Exception
    {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/characters/9999")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        JsonResponseError responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), JsonResponseError.class);
        Assertions.assertEquals(404, responseAsObject.getStatus());
        Assertions.assertEquals("El personaje con el ID 9999 no existe.", responseAsObject.getError());
    }

    @Test
    public void obtenerTodos() throws Exception
    {
        addPersonajeUsingService();
        personajeService.agregar(new PersonajeDTO(
                "Aragorn",
                "http://www.imagen-personaje.com/aragorn.png",
                38,
                87.1,
                "Era capitán de los montaraces del norte. Lideró la Comunidad del Anillo tras la caída de Gandalf en las Minas de Moria mientras luchaba contra el Balrog.",
                null));
        personajeService.agregar(new PersonajeDTO(
                "Legolas",
                "http://www.imagen-personaje.com/legolas.png",
                254,
                73.5,
                "Es un elfo sinda. Formó parte de la llamada Comunidad del Anillo, cuyo objetivo era la destrucción del Anillo Único de Sauron.",
                null));

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/characters")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        List<PersonajeListadoDTO> responseAsObjet =
                objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<List<PersonajeListadoDTO>>(){});
        Assertions.assertFalse(responseAsObjet.isEmpty());
        Assertions.assertEquals(3, responseAsObjet.size());
    }

    @Test
    public void actualizarPersonaje() throws Exception
    {
        PersonajeDTO personajeDTO = addPersonajeUsingService();
        Assertions.assertEquals(34, personajeDTO.getEdad());

        personajeDTO.setEdad(29);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personajeDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        PersonajeDTO responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), PersonajeDTO.class);
        Assertions.assertEquals(29, responseAsObject.getEdad());
    }

    @Test
    public void eliminarPersonaje() throws Exception
    {
        PersonajeDTO personajeAgregadoDTO = addPersonajeUsingService();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/characters/"+personajeAgregadoDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        JsonResponseMessage responseAsObject = objectMapper.readValue(response.getResponse().getContentAsString(StandardCharsets.UTF_8), JsonResponseMessage.class);
        Assertions.assertEquals("El personaje con el ID "+personajeAgregadoDTO.getId()+" fue eliminado correctamente.", responseAsObject.getMessage());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> personajeService.buscar(personajeAgregadoDTO.getId()));
    }


    // Helper method
    public PersonajeDTO addPersonajeUsingService() throws BadRequestException
    {
        PersonajeDTO personajeDTO = new PersonajeDTO(
                "Samwise Gamgee",
                "http://www.imagen-personaje.com/sam.png",
                34,
                82.4,
                "Es un hobbit de la Comarca, nacido en el año 1380. Jardinero de Frodo Bolsón. Sam acompaña a Frodo hasta el Orodruin a destruir el Anillo Único.",
                null);

        return personajeService.agregar(personajeDTO);
    }
}
