package com.raysson.financas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raysson.financas.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	boolean existsByEmail(String email);
	
	Optional<Usuario> findByEmail(String email);
}
