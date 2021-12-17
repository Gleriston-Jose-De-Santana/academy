package br.com.academy.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.academy.model.Aluno;

public interface AlunoDao extends JpaRepository<Aluno, Integer>{
	
	@Query("select j from Aluno j where j.status = 'ATIVO'")
	public List<Aluno> findByStatusAtivos();

	@Query("select a from Aluno a where a.status = 'INATIVO'")
	public List<Aluno> findByStatusInativos();
	
	@Query("select a from Aluno a where a.status = 'TRANCADO'")
	public List<Aluno> findByStatusTrancados();
	
	@Query("select a from Aluno a where a.status = 'CANCELADO'")
	public List<Aluno> findByStatusCancelados();
	
	public List<Aluno> findByNomeContainingIgnoreCase(String nome);
}
