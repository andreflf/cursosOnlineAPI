package com.cursos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cursos.entity.Curso;
import com.cursos.repository.CursoRepository;

@SpringBootTest
@AutoConfigureMockMvc //simular comportamento de requisiçoes HTTP - POST JSON 
public class CursoControllerTest {

	@Autowired
	CursoController cursoController;
	
	@MockitoBean
	CursoRepository cursoRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void saveComMockito() {
		
		Curso curso = new Curso();
		curso.setId(3); //por estar mockando o cursoRepository posso passar o ID, mas na realidade daria erro pois o ID está como auto_increment (@GeneratedValue (strategy = GenerationType.IDENTITY))
		curso.setNome("BSI");
		curso.setDescricao("Curso de TI");
		
		when(this.cursoRepository.save(any(Curso.class))).thenReturn(curso);
		
		ResponseEntity<String> retorno = this.cursoController.save(curso);
		
		assertEquals(HttpStatus.CREATED, retorno.getStatusCode());
		
	}
	
	@Test
	void saveComMockMvc() throws Exception {
		
		//passando o nome do curso sem nada gerando um badRequest, pois nome esta anotado como @NotBlank
		String Json = """
				{
				"id":3,
				"nome":" ",
				"descricao":"Curso de TI"
				}
				""";
		mockMvc.perform(post("/api/cursos/save").contentType(MediaType.APPLICATION_JSON).content(Json)).andExpect(status().isBadRequest());
		
		
	}
}
