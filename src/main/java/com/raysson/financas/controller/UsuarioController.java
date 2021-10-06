package com.raysson.financas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raysson.financas.dto.UsuarioDto;
import com.raysson.financas.exception.AutenticacaoException;
import com.raysson.financas.exception.ServiceException;
import com.raysson.financas.model.Usuario;
import com.raysson.financas.service.UsuarioService;

@RestController
@RequestMapping(value = "usuario" )
public class UsuarioController {
	
	private UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@PostMapping
	public ResponseEntity<?> salvarUsuario(@RequestBody UsuarioDto usuarioDto){
		Usuario user = Usuario.builder()
					   .nome(usuarioDto.getNome())
					   .email(usuarioDto.getEmail())
					   .senha(usuarioDto.getSenha()).build();
		
		try {
			Usuario usuario = this.usuarioService.salvarUsuario(user);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
		}catch (ServiceException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping(value = "/autenticar")
	public ResponseEntity<?> autenticarUsuario(@RequestBody UsuarioDto usuarioDto){
		
		try {
			
			Usuario usuario = this.usuarioService.autenticar(usuarioDto.getEmail(), usuarioDto.getSenha());
			return ResponseEntity.ok(usuario); // retorna um corpo da requisição e seja um obj ou qualquer coisa junto com o status 200 (ok)
		}catch(AutenticacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
}
