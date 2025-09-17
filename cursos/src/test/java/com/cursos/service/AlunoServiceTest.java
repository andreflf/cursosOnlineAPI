package com.cursos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cursos.entity.Aluno;
import com.cursos.entity.Curso;
import com.cursos.repository.AlunoRepository;
import com.cursos.repository.CursoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoServiceTest {

	@Autowired
	AlunoService alunoService;
	
	@MockitoBean
	AlunoRepository alunoRepository;
	
	@MockitoBean
	CursoRepository cursoRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	Aluno aluno = new Aluno();
	Curso curso = new Curso();
	
	@BeforeEach //para nao precisar escrever o mesmo código em cada teste
	void setup() {
		aluno.setId(1);
		aluno.setNome("Carlos");
		aluno.setEmail("cccccc@hotmail.com");
		aluno.setCursos(new ArrayList<>());
		
		curso.setId(3);
		curso.setNome("Medicina");
		curso.setDescricao("Curso voltado para área da saúde");
		
		when(cursoRepository.findById(curso.getId())).thenReturn(Optional.of(curso));
		when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);
	}
	
	@Test
	void matricularAlunoComId() {
		
		when(alunoRepository.findById(aluno.getId())).thenReturn(Optional.of(aluno));
		
		String retorno = this.alunoService.matriculaAluno(aluno.getId(), curso.getId());
		assertEquals("Aluno Matriculado com Sucesso", retorno);
	}
	
	@Test
	void matricularAlunoSemId() {
		
		when(alunoRepository.findById(aluno.getId())).thenReturn(Optional.empty());
		
		String retorno = this.alunoService.matriculaAluno(aluno.getId(), curso.getId());
		assertEquals("Dados inválidos (ID do aluno ou curso inexistentes)", retorno);
	}
}
