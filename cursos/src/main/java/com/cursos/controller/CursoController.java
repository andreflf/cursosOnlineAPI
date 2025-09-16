package com.cursos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursos.service.CursoService;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	

}
