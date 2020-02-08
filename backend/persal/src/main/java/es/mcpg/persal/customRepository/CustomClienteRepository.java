package es.mcpg.persal.customRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.mcpg.persal.models.Cliente;

public interface CustomClienteRepository extends PagingAndSortingRepository<Cliente, Integer>, JpaRepository<Cliente, Integer>{

	//busqueda por criterios (sin excluir los inactivos)
	Page<Cliente> findByCriteria(String id, String nombre, String poblacion, String codigoPostal, Pageable page);

	@Query(value="select * from clientes c join clientes_inactivos ci where c.nif <> ci.nif_cliente", nativeQuery=true)
	Page<Cliente> findAllActivos();

}
