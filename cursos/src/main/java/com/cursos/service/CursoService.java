package com.cursos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursos.entity.Curso;
import com.cursos.repository.CursoRepository;

@Service
public class CursoService {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	public String save(Curso curso) {
		this.cursoRepository.save(curso);
		return "Curso adicionado com sucesso!";
	}
	
	public String removeCurso (long id) {
		//implementar lógica de curso, curso nao pode ser removido se tiver alunos matriculados nele
		this.cursoRepository.deleteById(id);
		return "Curso removido com sucesso";
	}
	
	public Curso findById(long id) {
		return this.cursoRepository.findById(id).get();
	}
	
	public List<Curso> findAll(){
		return this.cursoRepository.findAll();
	}
	
	public String update(Curso curso, long id) {
		if(cursoRepository.existsById(id)) {
		curso.setId(id);
		this.cursoRepository.save(curso);
		return "Curso atualizado com sucesso!";
		}else
			return "Curso não encontrado, ID inválido.";
	}

}
