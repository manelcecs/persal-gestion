package es.mcpg.persal.customRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.mcpg.persal.models.Cliente;

public interface CustomClienteRepository extends PagingAndSortingRepository<Cliente, Integer>, JpaRepository<Cliente, Integer>{

	Page<Cliente> findByIdAndNombreAndPoblacionAndCodigoPostal(String id, String nombre, String poblacion, String codigoPostal, Pageable page);
}
