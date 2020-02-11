package es.mcpg.persal.service;

import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.mcpg.persal.customRepository.CustomActividadRepository;
import es.mcpg.persal.dtos.ActividadDto;
import es.mcpg.persal.models.Actividad;
import es.mcpg.persal.repository.ActividadRepository;

@Service
public class ActividadService {
	
	@Autowired
	ActividadRepository actividadRepository;
	
	@Autowired
	CustomActividadRepository customActividadRepository;
		
	public ActividadDto getActividad(Integer id) {
		ModelMapper mapper = new ModelMapper();
		Actividad actividadDB = this.actividadRepository.findById(id).orElse(null);
		return mapper.map(actividadDB, ActividadDto.class);
	}
	
	public Page<ActividadDto> getAllActividades(Integer id,
			String nif_cliente,
			Integer tipo_actividad,
		    Pageable page){
		ModelMapper mapper = new ModelMapper();
		Page<Actividad> pageActividad;
		Type listType = new TypeToken<Page<ActividadDto>>() {}.getType();
		
		pageActividad = this.customActividadRepository.findByIdAndNifClienteAndTipoActividad(id, nif_cliente, tipo_actividad, page);
	
		return mapper.map(pageActividad, listType);
	
}
	public ActividadDto save(ActividadDto actividad) throws Exception {
		this.validateActividad(actividad);
		
		try {
			Actividad actividadDto = this.actividadRepository.save(actividad);
			return new ModelMapper().map(actividadDto, ActividadDto.class);
		}catch(Exception e) {
			throw new Exception("Error al guardar la actividad");
		}
	}
	
	private void validateActividad(ActividadDto actividad) throws Exception{
		
	}

}
