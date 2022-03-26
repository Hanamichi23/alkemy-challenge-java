package com.alkemy.disney.repository;

import com.alkemy.disney.model.Rol;
import com.alkemy.disney.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@SuppressWarnings("unused")
@Repository
@Transactional(readOnly = true)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query
    public Optional<Usuario> findByEmail(String email);

    @Query
    public List<Rol> findByRol(Rol rol);
}