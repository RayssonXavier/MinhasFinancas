package com.raysson.financas.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raysson.financas.exception.AutenticacaoException;
import com.raysson.financas.exception.ServiceException;
import com.raysson.financas.model.Usuario;
import com.raysson.financas.repository.UsuarioRepository;
import com.raysson.financas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.usuarioRepository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		// obtem o usuario...
		Optional<Usuario> usuario = this.usuarioRepository.findByEmail(email);
		
		
		if(!usuario.isPresent()) { // verifica se o usuário não existe
			throw new AutenticacaoException("Usuário não encontrado para o e-mail fornecido!");
		}
		else if(!usuario.get().getSenha().equals(senha)) { // verifica se a senha não está correta
			throw new AutenticacaoException("Senha invalida para o e-mail fornecido.");
		}
			
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return this.usuarioRepository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		
		boolean exists = this.usuarioRepository.existsByEmail(email);
		
		if(exists) {
			throw new ServiceException("Usuário não disponível");
		}
	} 
	
	
	
}
