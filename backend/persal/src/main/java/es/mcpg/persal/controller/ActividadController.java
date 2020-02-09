package es.mcpg.persal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mcpg.persal.dtos.ActividadDto;
import es.mcpg.persal.service.ActividadService;

@RestController
@RequestMapping("service/actividad")
public class ActividadController {
	
	@Autowired
	private ActividadService actividadService;
	
	@GetMapping("/lista")
	public Page<ActividadDto> getClientesPage(@RequestParam(value="id") Integer id,
			@RequestParam(value="nif_cliente") String nif_cliente,
			@RequestParam(value="tipo_actividad") Integer tipo_actividad,
			@RequestParam(value="page") int page,
			@RequestParam(value="size") int size){
		return this.actividadService.getAllActividades(id, nif_cliente, tipo_actividad, PageRequest.of(page, size));
	}
	
}
