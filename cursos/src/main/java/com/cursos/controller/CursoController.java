package com.cursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursos.entity.Curso;
import com.cursos.service.CursoService;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

	@Autowired
	private CursoService cursoService;


	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Curso curso) {
		try {
			String retorno = this.cursoService.save(curso);
			return new ResponseEntity<String>(retorno, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<String>("Erro ao salvar curso", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> removeCurso (@PathVariable long id) {
		try {
			String retorno = this.cursoService.removeCurso(id);
			return new ResponseEntity<String>(retorno, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Erro ao salvar curso", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<Curso> findById(@PathVariable long id) {
		try {
			Curso curso = this.cursoService.findById(id);
			return new ResponseEntity<Curso>(curso, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Curso>> findAll(){
		try {
			List<Curso> curso = this.cursoService.findAll();
			return new ResponseEntity<>(curso, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Curso curso, @PathVariable long id) {
		try {
			String retorno = this.cursoService.update(curso,id);
			return new ResponseEntity<String>(retorno, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Erro ao atualizar o curso", HttpStatus.BAD_REQUEST);
		}
	}

}
