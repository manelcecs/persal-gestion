package es.mcpg.persal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mcpg.persal.dtos.ActividadDto;
import es.mcpg.persal.models.Actividad;

public interface ActividadRepository extends JpaRepository<Actividad, Integer> {

	List<Actividad> findAll();
	
	Optional<Actividad> findById(Integer id);
	
	Actividad save(ActividadDto actividadDto);
	
	void delete(Actividad actividad);
	
}
