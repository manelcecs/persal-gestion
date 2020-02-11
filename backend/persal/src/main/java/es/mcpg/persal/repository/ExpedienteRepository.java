package es.mcpg.persal.repository;

import java.util.List;
import java.util.Optional;

import es.mcpg.persal.dtos.ExpedienteDto;
import es.mcpg.persal.models.Expediente;

public interface ExpedienteRepository {

		
	List<Expediente> findAll();
	
	Optional<Expediente> findById(Integer id);
	
	Expediente save(ExpedienteDto expedienteDto);
	
	void delete(Expediente expediente);
	
}
