package es.mcpg.persal.customRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.mcpg.persal.models.Actividad;


public interface CustomActividadRepository {
	Page<Actividad> findByIdAndNifClienteAndTipoActividad(Integer id, String nif_cliente, Integer tipo_actividad, Pageable page);
}
