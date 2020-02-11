package es.mcpg.persal.customRepository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.mcpg.persal.models.Expediente;

public interface CustomExpedienteRepository extends PagingAndSortingRepository<Expediente, Integer>, JpaRepository<Expediente, Integer>{
	
	Page<Expediente> findByIdAndIdActividadAndfechaAperturaAndFechaCierre(Integer id, Integer idActividad, Date fechaApertura, Date fechaCierre, Pageable page);
}
