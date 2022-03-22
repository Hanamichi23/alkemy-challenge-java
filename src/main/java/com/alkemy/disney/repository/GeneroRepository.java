package com.alkemy.disney.repository;

import com.alkemy.disney.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@SuppressWarnings("unused")
@Transactional(readOnly = true)
@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {


}