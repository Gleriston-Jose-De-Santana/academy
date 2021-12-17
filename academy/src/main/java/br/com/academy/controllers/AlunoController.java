package br.com.academy.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.academy.Dao.AlunoDao;
import br.com.academy.model.Aluno;

@Controller
public class AlunoController {

	@Autowired
	private AlunoDao alunoRepositorio;
	
	@GetMapping("/inserirAlunos")
	public ModelAndView InserirAlunos(Aluno aluno) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Aluno/formAluno");
		mv.addObject("aluno", new Aluno());
		return mv;
	}
	
	@PostMapping("InsertAlunos")
	public ModelAndView inserirAlunos(@Valid Aluno aluno, BindingResult br) {
		ModelAndView mv = new ModelAndView();
		if(br.hasErrors()){
			mv.setViewName("aluno/formAluno");
			mv.addObject(aluno);
		}else {
		mv.setViewName("redirect:/alunos-adicionados");
		alunoRepositorio.save(aluno);
		}
		return mv;
	}
	
	@GetMapping("alunos-adicionados")
	public ModelAndView listagemAlunos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/listAlunos");
		mv.addObject("AlunosList", alunoRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Aluno/alterar");
		Aluno aluno = alunoRepositorio.getOne(id);
		mv.addObject("aluno", aluno);
		return mv;
	}
	
	@PostMapping("/Alterar")
	public ModelAndView alterar(Aluno aluno) {
		ModelAndView mv = new ModelAndView();
		alunoRepositorio.save(aluno);
		mv.setViewName("redirect:/alunos-adicionados");
		return mv;
	}
	
	@GetMapping("/excluir/{id}")
	public String excluirAluno(@PathVariable("id") Integer id) {
		alunoRepositorio.deleteById(id);
		return "redirect:/alunos-adicionados";
	}
	
	@GetMapping("filtro-alunos")
	public ModelAndView filtroAlunos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/filtrosAlunos");
		return mv;
	}
	
	@GetMapping("alunos-ativos")
	public ModelAndView listagemAlunosAtivos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/alunos-ativos");
		mv.addObject("AlunosAtivos", alunoRepositorio.findByStatusAtivos());
		return mv;
	}
	
	@GetMapping("alunos-inativos")
	public ModelAndView listagemAlunosInativos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/alunos-inativos");
		mv.addObject("AlunosInativos", alunoRepositorio.findByStatusInativos());
		return mv;
	}
	
	@GetMapping("alunos-trancados")
	public ModelAndView listagemAlunosTrancados() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/alunos-trancados");
		mv.addObject("alunosTrancados", alunoRepositorio.findByStatusTrancados());
		return mv;
	}
	
	@GetMapping("alunos-cancelados")
	public ModelAndView listagemAlunosCancelados() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("aluno/alunos-cancelados");
		mv.addObject("alunosCancelados", alunoRepositorio.findByStatusCancelados());
		return mv;
	}
	
	@PostMapping("pesquisar-aluno")
	public ModelAndView pesquisarAluno(@RequestParam(required = false) String nome) {
		ModelAndView mv = new ModelAndView();
		List<Aluno> listaAlunos;
		if(nome == null || nome.trim().isEmpty()){
			listaAlunos = alunoRepositorio.findAll();
		}else {
			listaAlunos = alunoRepositorio.findByNomeContainingIgnoreCase(nome);
		}
		mv.addObject("ListaDeAlunos", listaAlunos);
		mv.setViewName("Aluno/pesquisa-resultado");
		return mv;
	}
	
}
