package com.raysson.financas.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UsuarioDto {
	
	private String nome;
	private String email;
	private String senha;
	
}
