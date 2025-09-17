package com.cursos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cursos.entity.Aluno;
import com.cursos.entity.Curso;
import com.cursos.repository.AlunoRepository;
import com.cursos.repository.CursoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {
	
	@Autowired
	AlunoController alunoController;
	
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
		when(alunoRepository.findById(aluno.getId())).thenReturn(Optional.of(aluno));
		when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);
	}
	
	@Test
	void matricularAlunoComMockitoOk() {
		
		ResponseEntity<String> retorno = this.alunoController.matriculaAluno(aluno.getId(), curso.getId());
		assertEquals(HttpStatus.OK, retorno.getStatusCode());
		
	}
	
	@Test
	void matricularAlunoComMockitoErroNomeAluno() {
		
		aluno.setNome(" ");
		
		ResponseEntity<String> retorno = this.alunoController.matriculaAluno(aluno.getId(), curso.getId());
		assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
		
	}
	
	@Test
	void matricularAlunoComMockitoErroNomeCurso() {
		
		curso.setNome("");
		
		ResponseEntity<String> retorno = this.alunoController.matriculaAluno(aluno.getId(), curso.getId());
		assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
		
	}
	
	@Test
	void matricularAlunoComMockMvc() throws Exception{
		
		mockMvc.perform(put("/api/cursos/aluno/"+aluno.getId()+"/matricular/"+curso.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
