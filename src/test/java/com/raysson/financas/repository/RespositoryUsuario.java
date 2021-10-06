package com.raysson.financas.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.raysson.financas.model.Usuario;

@ExtendWith(SpringExtension.class) 
@ActiveProfiles("teste") // ativa o profile que escolhemos para fazer conexão com o banco
@DataJpaTest // sobe somente o necessário para fazer o teste... não sobe todo o contexto do spring
@AutoConfigureTestDatabase(replace = Replace.NONE) // desabilita a auto configuração do database em memória
public class RespositoryUsuario {
	
	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	
	@Test
	public void deveVerificarAExistenciaDoEmail() {
		//cenario
			Usuario usuario = criaUsuario();
			this.entityManager.persist(usuario);
			
		//acão / execução
		boolean exists = this.repository.existsByEmail(usuario.getEmail());
		// verificação
		Assertions.assertThat(exists).isTrue();
		
	}
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
		//cenario
		
		//acao/ execucao
		boolean exists = this.repository.existsByEmail("rayssonxavier@gmail.com");
		Assertions.assertThat(exists).isFalse();
		
	}
	
	@Test
	public void deveSalvarOUsuario() {
		//cenario 
		Usuario usuario = criaUsuario();
		
		//ação
		Usuario usuarioSalvo = this.repository.save(usuario);
		
		// verificação
		
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	@Test
	public void deveBuscarOUsuario() {
		// cenario
		Usuario usuario = criaUsuario();
		this.entityManager.persist(usuario);
		// ação
		Optional<Usuario> usuarioOpt= this.repository.findByEmail("rayssonxavier@gmail.com");
		
		Assertions.assertThat(usuarioOpt.isPresent()).isTrue();
	}
	
	@Test
	public void deveNaoEncontrarOUsuario() {
		
		// ação
		Optional<Usuario> usuarioOpt= this.repository.findByEmail("rayssonxavier@gmail.com");
		
		Assertions.assertThat(usuarioOpt.isPresent()).isFalse();
	}
	
	private static Usuario criaUsuario() {
	  return Usuario.builder()
						  .nome("raysson")
						  .email("rayssonxavier@gmail.com")
						  .senha("123")
						  .build();
		
	}
	
	
}
