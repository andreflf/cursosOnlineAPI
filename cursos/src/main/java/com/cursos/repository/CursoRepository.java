package com.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursos.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

}
