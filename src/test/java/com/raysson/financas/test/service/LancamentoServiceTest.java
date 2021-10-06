package com.raysson.financas.test.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.raysson.financas.exception.ServiceException;
import com.raysson.financas.model.Lancamento;
import com.raysson.financas.model.StatusLancamento;
import com.raysson.financas.model.TipoLancamento;
import com.raysson.financas.model.Usuario;
import com.raysson.financas.repository.LancamentoRepository;
import com.raysson.financas.service.impl.LancamentoServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("teste")
public class LancamentoServiceTest {
	
	private LancamentoServiceImpl lancamentoServiceImpl;
	@MockBean
	private LancamentoRepository lancamentoRepository;
	
	@BeforeEach
	public void setUp() {
		this.lancamentoServiceImpl = new LancamentoServiceImpl(this.lancamentoRepository); 
	}
	
	
	@Test
	public void deveSalvarOLancamentoSemExcessoes() {
		// cenário
		Lancamento lancamento = criarLancamento();
		
		// ação
		
		Assertions.assertThatCode(()->this.lancamentoServiceImpl.salvar(lancamento)).doesNotThrowAnyException();
		
	}
	
	@Test
	public void naoDeveRetornarNenhumaExeptionNoMetodoValidar() {
		//cenário
		Lancamento lancamento = criarLancamento();
		// ação
		Assertions.assertThatCode(()->this.lancamentoServiceImpl.validar(lancamento)).doesNotThrowAnyException();
	}
	
	@Test
	public void deveRetornarUmServiceExceptionAoPassarADescricaoVazia() {
		//cenário
		Lancamento lancamento = criarLancamento();
		lancamento.setDescricao("");
		
		//ação
		
		testeValidacao(lancamento,"Forneça uma Descrção válida!" );
	}
	
	@Test
	public void deveRetornarUmServiceExceptionAoPassarAUsuarioNulo() {
		//cenário
		Lancamento lancamento = criarLancamento();
		lancamento.setUsuario(null);
		// ação
		testeValidacao(lancamento,"Forneça um Usuário válido!" );
	}
	
	@Test
	public void deveRetornarUmServiceExceptionAoPassarAUsuarioComIdNulo() {
		//cenário
		Lancamento lancamento = criarLancamento();
		lancamento.getUsuario().setId(null);
		// ação
		testeValidacao(lancamento,"Forneça um Usuário válido!" );
	}
	
	@Test
	public void deveRetornarUmServiceExceptionAoPassarUmMesNulo() {
		//cenário
		Lancamento lancamento = criarLancamento();
		lancamento.setMes(null);
		// ação
		testeValidacao(lancamento,"Forneça um Mês válido!" );
	}
	
	@Test
	public void deveRetornarUmServiceExceptionAoPassarUmMesMenorQue1() {
		//cenário
		Lancamento lancamento = criarLancamento();
		lancamento.setMes(0);
		// ação
		testeValidacao(lancamento,"Forneça um Mês válido!" );
	}
	
	@Test
	public void deveRetornarUmServiceExceptionAoPassarUmMesMaiorQue12() {
		//cenário
		Lancamento lancamento = criarLancamento();
		lancamento.setMes(13);
		// ação
		testeValidacao(lancamento,"Forneça um Mês válido!" );
	}
	
	@Test
	public void deveRetornarUmServiceExceptionAoPassarUmAnoNulo() {
		//cenário
		Lancamento lancamento = criarLancamento();
		lancamento.setAno(null);
		// ação
		testeValidacao(lancamento,"Forneça um Ano válido!" );
	}
	
	@Test
	public void deveRetornarUmServiceExceptionAoPassarUmAnoComOCaractersMaiorQue4() {
		//cenário
		Lancamento lancamento = criarLancamento();
		lancamento.setAno(20212);
		// ação
		testeValidacao(lancamento,"Forneça um Ano válido!" );
	}
	
	@Test
	public void deveRetornarUmServiceExceptionAoPassarOValorNulo() {
		//cenário
		Lancamento lancamento = criarLancamento();
		lancamento.setValor(null);
		// ação
		testeValidacao(lancamento,"Forneça um Valor válido!" );
	}
	
	@Test
	public void deveRetornarUmServiceExceptionAoPassarOValorMenorOuIgualAZero() {
		//cenário
		Lancamento lancamento = criarLancamento();
		lancamento.setValor(new BigDecimal(0));
		// ação
		testeValidacao(lancamento,"Forneça um Valor válido!" );
	}
	
	@Test
	public void deveRetornarUmServiceExceptionAoPassarOTipoNulo() {
		//cenário
		Lancamento lancamento = criarLancamento();
		lancamento.setTipo(null);
		// ação
		testeValidacao(lancamento,"Forneça um Tipo válido!" );
	}
	
	@Test
	public void deveAtualizarSemExcessoes() {
		// cenário
				Lancamento lancamento = criarLancamento();
				lancamento.setId(1L);
		// ação
				
		Assertions.assertThatCode(()->this.lancamentoServiceImpl.atualizar(lancamento)).doesNotThrowAnyException();
	}
	
	@Test
	public void deveRetornarUmaNullPointerExceprionAoAtualizarLancamentoSemId() {
		// cenário
				Lancamento lancamento = criarLancamento();
		// ação
				
		NullPointerException exception = assertThrows(NullPointerException.class, 
						()-> this.lancamentoServiceImpl.atualizar(lancamento));

		
	}
	
	private Lancamento criarLancamento() {
		return Lancamento.builder()
			   .descricao("gastos")
			   .ano(2021)
			   .mes(5)
			   .valor(new BigDecimal(10.00))
			   .status(StatusLancamento.PENDENTE)
			   .tipo(TipoLancamento.DESPESA)
			   .usuario(criaUsuario()).build();
				
	}
	
	private Usuario criaUsuario() {
		      
	  return Usuario.builder()
				  .nome("raysson")
				  .email("rayssonxavier@gmail.com")
				  .senha("123")
				  .id(1L)
				  .build();
			
	}
	
	private void testeValidacao(Lancamento lancamento, String descricao) {
		
		ServiceException exception = assertThrows(ServiceException.class, 
				()-> this.lancamentoServiceImpl.validar(lancamento));
		// validação
		
		String msg = exception.getMessage();
		String msgEsperada = descricao;
		
		assertTrue(msg.contains(msgEsperada));
	}
	
}
