package com.cursos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.cursos.entity.Curso;
import com.cursos.repository.CursoRepository;

@SpringBootTest
public class CursoServiceTest {
	
	@Autowired
	CursoService cursoService;
	
	@MockitoBean
	CursoRepository cursoRepository;

	@Test
	void criacaoDeCurso() {
		
		Curso curso = new Curso();
		curso.setId(1);
		curso.setNome("Pedagogia");
		curso.setDescricao("curso para área da educaçao");
		
		when(cursoRepository.save(any(Curso.class))).thenReturn(curso);
		
		String retorno = this.cursoService.save(curso);
		assertEquals("Curso adicionado com sucesso!", retorno);
		
	}
	
	@Test
	void criacaoDeCursoSemNome() {
		//testando exception de nome do curso do CursoService
		Curso curso = new Curso();
		curso.setId(3);
		curso.setNome(" ");
		curso.setDescricao("curso para área da educaçao");
		
		when(cursoRepository.save(any(Curso.class))).thenReturn(curso);
		
		assertThrows(IllegalArgumentException.class, () -> cursoService.save(curso));
		
	}
}
