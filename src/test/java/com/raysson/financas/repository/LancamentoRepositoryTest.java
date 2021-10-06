package com.raysson.financas.repository;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.raysson.financas.model.Lancamento;
import com.raysson.financas.model.StatusLancamento;
import com.raysson.financas.model.TipoLancamento;
import com.raysson.financas.model.Usuario;

@ExtendWith(SpringExtension.class) 
@ActiveProfiles("teste") // ativa o profile que escolhemos para fazer conexão com o banco
@DataJpaTest // sobe somente o necessário para fazer o teste... não sobe todo o contexto do spring
@AutoConfigureTestDatabase(replace = Replace.NONE) // desabilita a auto configuração do database em memória
public class LancamentoRepositoryTest {
	
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Test
	public void deveSalvarSemExcessoes() {
		// cenario
		Lancamento lancamento = criarLancamento();
		
		//ação
		
		Lancamento lancamentoSalvo = this.lancamentoRepository.save(lancamento);
		
		// validação
		
		Assertions.assertThat(lancamentoSalvo.getId()).isNotNull();
		
	}
	
	
	
	private Lancamento criarLancamento() {
		return Lancamento.builder()
			   .descricao("gastos")
			   .ano(2021)
			   .mes(5)
			   .status(StatusLancamento.PENDENTE)
			   .tipo(TipoLancamento.DESPESA)
			   .usuario(criaUsuario()).build();
				
	}
	
	private Usuario criaUsuario() {
		      
	  return this.entityManager.merge(Usuario.builder()
				  .nome("raysson")
				  .email("rayssonxavier@gmail.com")
				  .senha("123")
				  .id(1L)
				  .build());
			
	}
}
