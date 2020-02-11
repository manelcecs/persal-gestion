package es.mcpg.persal.service;

import java.lang.reflect.Type;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.mcpg.persal.customRepository.CustomExpedienteRepository;
import es.mcpg.persal.dtos.ExpedienteDto;
import es.mcpg.persal.models.Expediente;
import es.mcpg.persal.repository.ExpedienteRepository;

@Service
public class ExpedienteService {
	@Autowired
	ExpedienteRepository expedienteRepository;
	
	@Autowired
	CustomExpedienteRepository customExpedienteRepository;
		
	public ExpedienteDto getExpediente(Integer id) {
		ModelMapper mapper = new ModelMapper();
		Expediente expedienteDB = this.expedienteRepository.findById(id).orElse(null);
		return mapper.map(expedienteDB, ExpedienteDto.class);
	}
	
	public Page<ExpedienteDto> getAllExpedientes(Integer id,
			Integer idActividad,
			Date fechaApertura,
			Date fechaCierre, Pageable page){
		ModelMapper mapper = new ModelMapper();
		Page<Expediente> pageExpediente;
		Type listType = new TypeToken<Page<ExpedienteDto>>() {}.getType();
		
		pageExpediente = this.customExpedienteRepository.findByIdAndIdActividadAndfechaAperturaAndFechaCierre(id, idActividad, fechaApertura, fechaCierre, page);
		
		return mapper.map(pageExpediente, listType);
		
	}
	
	public ExpedienteDto save(ExpedienteDto expediente) throws Exception {
		this.validateExpediente(expediente);
		
		try {
			Expediente expedienteDto = this.expedienteRepository.save(expediente);
			return new ModelMapper().map(expedienteDto, ExpedienteDto.class);
		}catch(Exception e) {
			throw new Exception("Error al guardar el expediente");
		}
	}
	private void validateExpediente(ExpedienteDto expediente) throws Exception{
	
	}
}
