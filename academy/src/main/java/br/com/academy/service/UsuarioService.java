package br.com.academy.service;

import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academy.Dao.UsuarioDao;
import br.com.academy.exceptions.CriptoExistException;
import br.com.academy.exceptions.EmailExistsException;
import br.com.academy.model.Usuario;
import br.com.academy.util.Util;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;
	
	public void salvarUsuario(Usuario user) throws Exception{
	
	try {
		if(usuarioDao.findByEmail(user.getEmail()) != null) {
			throw new EmailExistsException("Já existe um email cadastrado para: " + user.getEmail());
		}
		
		user.setSenha(Util.md5(user.getEmail()));
		
	}catch (NoSuchAlgorithmException e) {
		
	throw new CriptoExistException("Erro na criptografia da senha");
		
	}
	
	usuarioDao.save(user);
	
  }
	
	public Usuario loginUser(String user, String senha) throws ServiceExc {
		
		Usuario userLogin = usuarioDao.buscarLogin(user, senha);
		return userLogin;
	}
	
}
