package com.cursos.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cursos.entity.Aluno;
import com.cursos.entity.Curso;
import com.cursos.repository.AlunoRepository;
import com.cursos.repository.CursoRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	public String matriculaAluno(long idAluno, long idCurso) {
		
		Aluno aluno = this.alunoRepository.findById(idAluno).get();
		Curso curso = this.cursoRepository.findById(idCurso).get();
		aluno.getCursos().add(curso);
		this.alunoRepository.save(aluno);
		return "Aluno Matriculado com Sucesso";
	}
}
