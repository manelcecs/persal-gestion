package es.mcpg.persal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mcpg.persal.dtos.ClienteDto;
import es.mcpg.persal.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String>{

	
	List<Cliente> findAll();
	
	Optional<Cliente> findById(String nif);
	
	Cliente save(ClienteDto clienteDto);
	
	void delete(Cliente cliente);
	
	
}
