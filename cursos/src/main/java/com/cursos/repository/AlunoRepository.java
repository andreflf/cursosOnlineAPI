package com.cursos.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cursos.entity.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
