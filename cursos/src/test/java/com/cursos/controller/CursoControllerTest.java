package com.cursos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
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

import com.cursos.entity.Curso;
import com.cursos.repository.CursoRepository;

@SpringBootTest //para poder usar as demais anotaçoes no contexto de testes (sempre usar)
@AutoConfigureMockMvc //simular comportamento de requisiçoes HTTP - POST JSON 
public class CursoControllerTest {

	@Autowired
	CursoController cursoController; //classe que vai testar
	
	@MockitoBean
	CursoRepository cursoRepository; //classe que vai simular/classe que acessa o banco
	
	@Autowired
	MockMvc mockMvc;
	
	Curso curso = new Curso();
	List<Curso> cursos = new ArrayList<>();
	
	//setup que será executado antes de cada teste
	@BeforeEach
	void setup() {
		curso.setId(3); //por estar mockando o cursoRepository posso passar o ID, mas na realidade daria erro pois o ID está como auto_increment (@GeneratedValue (strategy = GenerationType.IDENTITY))
		curso.setNome("BSI");
		curso.setDescricao("Curso de TI");
	}
	
	@Test
	void saveComMockito() { //sem visibilidade (public/private) e sem retorno/void
		
		when(this.cursoRepository.save(any(Curso.class))).thenReturn(curso);
		
		ResponseEntity<String> retorno = this.cursoController.save(curso);
		assertEquals(HttpStatus.CREATED, retorno.getStatusCode()); //(valor que voce espera, valor que realmente deu)
		//se ambos baterem o teste retorna true
		
	}
	
	@Test
	void saveComMockMvc() throws Exception {
		
		//passando o nome do curso sem nada gerando um badRequest, pois nome esta anotado como @NotBlank
		//Bean Validation com @NotBlank nem deixa chegar na service. O Spring intercepta antes de invocar o método da controller e já devolve 400
		String Json = """
				{
				"id":3,
				"nome":" ",
				"descricao":"Curso de TI"
				}
				""";
		mockMvc.perform(post("/api/cursos/save").contentType(MediaType.APPLICATION_JSON).content(Json)).andExpect(status().isBadRequest());
		
		
	}
	
	@Test
	void deleteOk() {
		
		doNothing().when(cursoRepository).deleteById(anyLong());
		
		ResponseEntity<String> retorno = cursoController.removeCurso(curso.getId());
		assertEquals(HttpStatus.OK, retorno.getStatusCode());
		assertEquals("Curso removido com sucesso", retorno.getBody());
			
	}
	
	@Test
	void deleteErro() {
		
		curso.setId(0);
		//doNothing().when(cursoRepository).deleteById(curso.getId()); //como cai na exceçao nem chega ao repository
		
		ResponseEntity<String> retorno = cursoController.removeCurso(curso.getId());
		assertEquals(HttpStatus.NOT_FOUND, retorno.getStatusCode());
			
	}
	
	@Test
	void findByIdOk() {
		curso.setId(1);
		when(cursoRepository.findById(curso.getId())).thenReturn(Optional.of(curso));
		
		ResponseEntity<Curso> retorno = cursoController.findById(curso.getId());
		assertEquals(HttpStatus.OK, retorno.getStatusCode());
	}
	
	@Test
	void findByIdErro() {
		curso.setId(0); //tem uma regra de negócio na service para id=0 lançando uma exceçao	
		when(cursoRepository.findById(curso.getId())).thenReturn(Optional.of(curso));
		
		ResponseEntity<Curso> retorno = cursoController.findById(curso.getId());
		assertEquals(HttpStatus.NOT_FOUND, retorno.getStatusCode());
	}
	
	@Test
	void findAllErro() {
		when(cursoRepository.findAll()).thenReturn(cursos);
		
		ResponseEntity<List<Curso>> retorno = cursoController.findAll();
		assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
	}
	
	@Test
	void findAllOk() {
		cursos.add(curso);
		
		when(cursoRepository.findAll()).thenReturn(cursos);
		
		ResponseEntity<List<Curso>> retorno = cursoController.findAll();
		assertEquals(HttpStatus.OK, retorno.getStatusCode());  //compara código de retorno
		assertEquals(1, retorno.getBody().size()); //compara tamanho
	    assertEquals("BSI", retorno.getBody().get(0).getNome()); //compara nome do elemento 0 da lista
	}
	
	@Test
	void updateOk() {
		
		when(this.cursoRepository.existsById(curso.getId())).thenReturn(true);
		when(this.cursoRepository.save(any(Curso.class))).thenReturn(curso);
		
		ResponseEntity<String> retorno = cursoController.update(curso, curso.getId());
		assertEquals("Curso atualizado com sucesso!", retorno.getBody());
		assertEquals(HttpStatus.OK, retorno.getStatusCode());
		
	}
	
	@Test
	void updateErro() {
		
		when(this.cursoRepository.existsById(anyLong())).thenReturn(false);
		
		ResponseEntity<String> retorno = cursoController.update(curso, anyLong());
		assertEquals("Erro ao atualizar o curso", retorno.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
		
	}
	
}
