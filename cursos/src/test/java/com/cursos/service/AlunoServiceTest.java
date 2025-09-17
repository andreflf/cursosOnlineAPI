package com.cursos.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.cursos.entity.Aluno;
import com.cursos.repository.AlunoRepository;
import com.cursos.repository.CursoRepository;

@SpringBootTest
public class AlunoServiceTest {

	@Autowired
	AlunoService alunoService;
	
	@MockitoBean
	AlunoRepository alunoRepository;
	
	@MockitoBean
	CursoRepository cursoRepository;
	
	@Test
	void matricularAluno() {
		
		long idCurso;
		long idAluno;
		
	
		
	//	when(alunoRepository.save(any(Aluno.class))).thenReturn(null, null)
		
		String retorno = this.alunoService.matriculaAluno(1, 3);
		
		
	}
}
