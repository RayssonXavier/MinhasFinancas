package com.raysson.financas.service;

import java.util.List;

import com.raysson.financas.model.Lancamento;
import com.raysson.financas.model.StatusLancamento;

public interface LancamentoService {
	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento atualizar(Lancamento lancamento);
	
	void deletar(Lancamento lancamento);
	
	List<Lancamento> buscar(Lancamento lancamentoFiltro);
	
	void atualizarStatusLancamento(Lancamento lancamento, StatusLancamento statusLancamento);
	
	void validar(Lancamento lancamento);
}
