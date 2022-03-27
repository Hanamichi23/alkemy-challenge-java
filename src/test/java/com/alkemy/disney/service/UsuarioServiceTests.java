package com.alkemy.disney.service;

import com.alkemy.disney.DTO.UsuarioDTO;
import com.alkemy.disney.exceptions.BadRequestException;
import com.alkemy.disney.exceptions.ResourceConflictException;
import com.alkemy.disney.exceptions.ResourceNotFoundException;
import com.alkemy.disney.model.Rol;
import com.alkemy.disney.repository.RolRepository;
import com.alkemy.disney.repository.UsuarioRepository;
import com.alkemy.disney.service.impl.UsuarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioServiceTests {

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
        rolRepository.deleteAll();
    }

    @Test
    public void agregarUsuario() throws BadRequestException, ResourceConflictException
    {
        UsuarioDTO usuarioAgregadoDTO = usuarioService.registrar(new UsuarioDTO(
                "usuario_nuevo@test.com",
                "password1234",
                rolRepository.findByNombre("ROLE_USER").get()
        ));
        Assertions.assertNotNull(usuarioAgregadoDTO.getId());
    }

    @Test
    public void agregarUsuarioEmailRepetido() throws BadRequestException, ResourceConflictException
    {
        UsuarioDTO usuarioAgregadoDTO = usuarioService.registrar(new UsuarioDTO(
                "usuario_nuevo@test.com",
                "password1234",
                rolRepository.findByNombre("ROLE_USER").get()
        ));

        Assertions.assertNotNull(usuarioAgregadoDTO.getId());
        Assertions.assertThrows(ResourceConflictException.class, () ->
                usuarioService.registrar(new UsuarioDTO("usuario_nuevo@test.com", "pass", rolRepository.findByNombre("ROLE_USER").get())));
    }

    @Test
    public void buscarUsuario() throws BadRequestException, ResourceNotFoundException, ResourceConflictException
    {
        UsuarioDTO usuarioAgregadoDTO = usuarioService.registrar(new UsuarioDTO(
                "usuario_nuevo@test.com",
                "password1234",
                rolRepository.findByNombre("ROLE_USER").get()
        ));

        Assertions.assertDoesNotThrow( () -> usuarioService.buscar(usuarioAgregadoDTO.getId()) );
        UsuarioDTO usuarioBuscadoDTO = usuarioService.buscar(usuarioAgregadoDTO.getId());
        Assertions.assertNotNull(usuarioBuscadoDTO);
        Assertions.assertEquals(usuarioAgregadoDTO.getId(), usuarioBuscadoDTO.getId());
    }

    @Test
    public void buscarUsuarioInexistente()
    {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> usuarioService.buscar(9999L));
    }

    @Test
    public void listarTodos() throws BadRequestException, ResourceConflictException
    {
        usuarioService.registrar(new UsuarioDTO(
                "usuario_nuevo@test.com",
                "password1234",
                rolRepository.findByNombre("ROLE_USER").get()
        ));
        usuarioService.registrar(new UsuarioDTO(
                "usuario_nuevo_2@test.com",
                "testpass555",
                rolRepository.findByNombre("ROLE_USER").get()
        ));
        usuarioService.registrar(new UsuarioDTO(
                "usuario_nuevo_3@test.com",
                "contrasena987",
                rolRepository.findByNombre("ROLE_USER").get()
        ));
        List<UsuarioDTO> usuariosDTO = usuarioService.listar();
        Assertions.assertTrue(!usuariosDTO.isEmpty());
        Assertions.assertTrue(usuariosDTO.size() == 3);
    }

    @Test
    public void actualizarUsuario() throws BadRequestException, ResourceNotFoundException, ResourceConflictException
    {
        UsuarioDTO usuarioAgregadoDTO = usuarioService.registrar(new UsuarioDTO(
                "usuario_nuevo@test.com",
                "password1234",
                rolRepository.findByNombre("ROLE_USER").get()
        ));

        UsuarioDTO usuarioBuscadoDTO = usuarioService.buscar(usuarioAgregadoDTO.getId());
        Assertions.assertNotNull(usuarioBuscadoDTO);
        Assertions.assertEquals("usuario_nuevo@test.com", usuarioBuscadoDTO.getEmail());

        usuarioBuscadoDTO.setEmail("usuario_nuevo_actualizado@test.com");
        // Set del password para que no tire error de password null (debido al JsonProperty.Access.WRITE_ONLY)
        usuarioBuscadoDTO.setContrasena("password1234");
        UsuarioDTO usuarioActualizadoDTO = usuarioService.actualizar(usuarioBuscadoDTO);
        UsuarioDTO usuarioActualizadoBuscadoDTO = usuarioService.buscar(usuarioActualizadoDTO.getId());
        Assertions.assertNotNull(usuarioActualizadoBuscadoDTO);
        Assertions.assertEquals("usuario_nuevo_actualizado@test.com", usuarioActualizadoBuscadoDTO.getEmail());
    }

    @Test
    public void eliminarUsuario() throws ResourceNotFoundException, BadRequestException, ResourceConflictException
    {
        UsuarioDTO usuarioAgregadoDTO = usuarioService.registrar(new UsuarioDTO(
                "usuario_nuevo@test.com",
                "password1234",
                rolRepository.findByNombre("ROLE_USER").get()
        ));

        usuarioService.eliminar(usuarioAgregadoDTO.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> usuarioService.buscar(usuarioAgregadoDTO.getId()));
    }
}