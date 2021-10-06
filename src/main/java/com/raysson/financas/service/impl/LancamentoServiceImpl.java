package com.raysson.financas.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raysson.financas.exception.ServiceException;
import com.raysson.financas.model.Lancamento;
import com.raysson.financas.model.StatusLancamento;
import com.raysson.financas.repository.LancamentoRepository;
import com.raysson.financas.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {
	
	private LancamentoRepository lancamentoRepository;
	
	public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
		this.lancamentoRepository = lancamentoRepository;	
	}

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		
		this.validar(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
		return	this.lancamentoRepository.save(lancamento);
		 
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		
		this.validar(lancamento);
		Objects.requireNonNull(lancamento.getId()); // valida se o objeto ou variavel não for nula ... caso ser nula retorna um throws nullPointerException
		return this.lancamentoRepository.save(lancamento);
		 
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		
		Objects.requireNonNull(lancamento.getId()); // valida se o objeto ou variavel não for nula ... caso ser nula retorna um throws nullPointerException
		this.lancamentoRepository.delete(lancamento);
	}

	@Override
	@Transactional(readOnly = true) // faz uma transação de somente leitura....
	
	/* A interface Example faz uma pesquisa com os atributos de da classe Lancamento
	 * O segundo parametro da interface ExampleMatcher o (matching) traz outras propriedade
	 * interessante para a pesquisa, como ignoração de cases, pesquisa apartir de qualquer lugar da string... */
	
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		
		Example<Lancamento> example = Example.of(lancamentoFiltro, ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
				
		return lancamentoRepository.findAll(example);
	}

	@Override
	public void atualizarStatusLancamento(Lancamento lancamento, StatusLancamento statusLancamento) {
		
		lancamento.setStatus(statusLancamento);
		this.atualizar(lancamento);
		
	}

	@Override
	public void validar(Lancamento lancamento) {
		
		if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
			throw new ServiceException("Forneça uma Descrção válida!");
		}
		
		if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
			throw new ServiceException("Forneça um Usuário válido!");
		}
		
		if(lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
			throw new ServiceException("Forneça um Mês válido!");
		}
		
		if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
			throw new ServiceException("Forneça um Ano válido!");
		}
		
		if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1  ) {
			throw new ServiceException("Forneça um Valor válido!");
		}
		
		if(lancamento.getTipo() == null) {
			throw new ServiceException("Forneça um Tipo válido!");
		}
		
		
	}

	
	
	
}
