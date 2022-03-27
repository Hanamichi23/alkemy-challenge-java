package com.alkemy.disney.service;

import com.alkemy.disney.DTO.PersonajeDTO;
import com.alkemy.disney.DTO.PersonajeListadoDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.repository.PersonajeRepository;
import com.alkemy.disney.service.impl.PersonajeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonajeServiceTests {

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
    public void agregarPersonaje() throws BadRequestException
    {
        PersonajeDTO personajeAgregadoDTO = personajeService.agregar(new PersonajeDTO(
                "Samwise Gamgee",
                "http://www.imagen-personaje.com/sam.png",
                34,
                82.4,
                "Es un hobbit de la Comarca, nacido en el año 1380. Jardinero de Frodo Bolsón. Sam acompaña a Frodo hasta el Orodruin a destruir el Anillo Único.",
                null));
        Assertions.assertNotNull(personajeAgregadoDTO.getId());
    }

    @Test
    public void buscarPersonaje() throws BadRequestException, ResourceNotFoundException
    {
        PersonajeDTO personajeAgregadoDTO = personajeService.agregar(new PersonajeDTO(
                "Samwise Gamgee",
                "http://www.imagen-personaje.com/sam.png",
                34,
                82.4,
                "Es un hobbit de la Comarca, nacido en el año 1380. Jardinero de Frodo Bolsón. Sam acompaña a Frodo hasta el Orodruin a destruir el Anillo Único.",
                null));

        Assertions.assertDoesNotThrow( () -> personajeService.buscar(personajeAgregadoDTO.getId()) );
        PersonajeDTO personajeBuscadoDTO = personajeService.buscar(personajeAgregadoDTO.getId());
        Assertions.assertNotNull(personajeBuscadoDTO);
        Assertions.assertEquals(personajeAgregadoDTO.getId(), personajeBuscadoDTO.getId());
    }

    @Test
    public void buscarPersonajeInexistente()
    {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> personajeService.buscar(9999L));
    }

    @Test
    public void listarTodos() throws BadRequestException
    {
        personajeService.agregar(new PersonajeDTO(
                "Samwise Gamgee",
                "http://www.imagen-personaje.com/sam.png",
                34,
                82.4,
                "Es un hobbit de la Comarca, nacido en el año 1380. Jardinero de Frodo Bolsón. Sam acompaña a Frodo hasta el Orodruin a destruir el Anillo Único.",
                null));
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
        List<PersonajeListadoDTO> personajesDTO = personajeService.listar();
        Assertions.assertTrue(!personajesDTO.isEmpty());
        Assertions.assertTrue(personajesDTO.size() == 3);
    }

    @Test
    public void actualizarPersonaje() throws BadRequestException, ResourceNotFoundException
    {
        PersonajeDTO personajeAgregadoDTO = personajeService.agregar(new PersonajeDTO(
                "Samwise Gamgee",
                "http://www.imagen-personaje.com/sam.png",
                34,
                82.4,
                "Es un hobbit de la Comarca, nacido en el año 1380. Jardinero de Frodo Bolsón. Sam acompaña a Frodo hasta el Orodruin a destruir el Anillo Único.",
                null));

        PersonajeDTO personajeBuscadoDTO = personajeService.buscar(personajeAgregadoDTO.getId());
        Assertions.assertNotNull(personajeBuscadoDTO);
        Assertions.assertEquals(34, personajeBuscadoDTO.getEdad());

        personajeBuscadoDTO.setEdad(29);
        PersonajeDTO personajeActualizadoDTO = personajeService.actualizar(personajeBuscadoDTO);
        PersonajeDTO personajeActualizadoBuscadoDTO = personajeService.buscar(personajeActualizadoDTO.getId());
        Assertions.assertNotNull(personajeActualizadoBuscadoDTO);
        Assertions.assertEquals(29, personajeActualizadoBuscadoDTO.getEdad());
    }

    @Test
    public void eliminarPersonaje() throws ResourceNotFoundException, BadRequestException
    {
        PersonajeDTO personajeAgregadoDTO = personajeService.agregar(new PersonajeDTO(
                "Samwise Gamgee",
                "http://www.imagen-personaje.com/sam.png",
                34,
                82.4,
                "Es un hobbit de la Comarca, nacido en el año 1380. Jardinero de Frodo Bolsón. Sam acompaña a Frodo hasta el Orodruin a destruir el Anillo Único.",
                null));

        personajeService.eliminar(personajeAgregadoDTO.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> personajeService.buscar(personajeAgregadoDTO.getId()));
    }
}
