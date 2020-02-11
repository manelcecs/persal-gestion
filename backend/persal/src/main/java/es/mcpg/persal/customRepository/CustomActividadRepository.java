package es.mcpg.persal.customRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.mcpg.persal.models.Actividad;



public interface CustomActividadRepository extends PagingAndSortingRepository<Actividad, Integer>, JpaRepository<Actividad, Integer> {
	Page<Actividad> findByIdAndNifClienteAndTipoActividad(Integer id, String nif_cliente, Integer tipo_actividad, Pageable page);
}
