package com.raysson.financas.test.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.raysson.financas.exception.AutenticacaoException;
import com.raysson.financas.exception.ServiceException;
import com.raysson.financas.model.Usuario;
import com.raysson.financas.repository.UsuarioRepository;
import com.raysson.financas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("teste")
public class UsuarioService {
	
	private UsuarioServiceImpl service;
	@MockBean // cria uma instancia fake para testes...
	private UsuarioRepository repository;
	
	@BeforeEach //  método de teste executado antes de qualquer teste... deve ser estático
	public void setUp() {
		service = new UsuarioServiceImpl(repository);
	}
	
	@Test
	public void deveSalvarUsuario() {
		// cenário
		Usuario user = Usuario.builder()
					   .nome("raysson")
					   .email("rayssonxavier@gmail.com")
					   .senha("123").build();
		Mockito.when(repository.save(user)).thenReturn(user);
		//ação
		Assertions.assertThatCode(()->service.salvarUsuario(user)).doesNotThrowAnyException();
	}
	
	@Test
	public void deveAutenticarUmUsuario() {
		//cenario
		Usuario user = Usuario.builder()
				       .nome("raysson")
				       .email("rayssonxavier@gmail.com")
				       .senha("123")
				       .id(1L).build();
		//ação
		
		Mockito.when(repository.findByEmail("rayssonxavier@gmail.com")).thenReturn(Optional.of(user));
		
		//validação
		
		Assertions.assertThatCode(()->service.autenticar("rayssonxavier@gmail.com", "123")).doesNotThrowAnyException();
		
		
	}
	
	@Test
	public void deveRetornarErroDeUsuarioNaoEncontrado() {
		String email = "rayssonxavier@gmail.com";
		String senha = "123";
		
		// ação 
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.empty());
		AutenticacaoException exception = assertThrows(AutenticacaoException.class,
				  
					()-> service.autenticar(email, senha)
					 
				);
		String msg = exception.getMessage();
		String msgEsperada = "Usuário não encontrado para o e-mail fornecido!";
		
		assertTrue(msg.contains(msgEsperada));
	}
	
	@Test
	public void deveRetornarErroDeSenhaIncorreta() {
		
		String nome = "Raysson";
		String email = "rayssonxavier@gmail.com";
		String senha = "123";
		Long id = 1L;
		
		Usuario user = Usuario.builder()
				.nome(nome)
				.email(email)
				.senha(senha)
				.id(id).build();
		
		// ação
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));
		AutenticacaoException exception = assertThrows(AutenticacaoException.class,
				()->
					service.autenticar(email, "1234")
						
				);
		// validacao
		
		String msg = exception.getMessage();
		String msgEsperada = "Senha invalida para o e-mail fornecido.";
		
		assertTrue(msg.contains(msgEsperada));
				
	}
	
	@Test
	public void deveRetornarUmServiceExeption() {
		//cenario
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		// acão/ execução
			
		ServiceException exception = assertThrows(ServiceException.class,
				()-> {
					service.validarEmail("rayssonxavier@gmail.com");
				} );
		
		String msgEsperada = "Usuário não disponível";
		String msg = exception.getMessage();
		
		// validação
		assertTrue(msg.contains(msgEsperada));
				
	}
	@Test
	public void deveNaoLancarNehumaExcecao() {
	
		// cenario		
	   // this.repository.deleteAll();
		
		
		// acão/ execução
		Assertions.assertThatCode(()-> this.service.validarEmail("rayssonxavier@gmail.com")
				).doesNotThrowAnyException();
				
		
		
	}
	
}
