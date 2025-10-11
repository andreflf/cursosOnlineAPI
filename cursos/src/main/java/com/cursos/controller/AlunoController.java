package com.cursos.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cursos.service.AlunoService;


@RestController
@RequestMapping("api/cursos/aluno")
public class AlunoController {
	
	@Autowired
	private AlunoService alunoService;

	@PutMapping("/{idAluno}/matricular/{idCurso}")
	public ResponseEntity<String> matriculaAluno(@PathVariable long idAluno,@PathVariable long idCurso) {
		try {
			String retorno = this.alunoService.matriculaAluno(idAluno, idCurso);
			return new ResponseEntity<String>(retorno, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao matricular", HttpStatus.BAD_REQUEST);
		}
	}
}
