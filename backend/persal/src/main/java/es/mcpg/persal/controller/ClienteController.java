package es.mcpg.persal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mcpg.persal.dtos.ClienteDto;
import es.mcpg.persal.service.ClienteService;

@RestController
@RequestMapping("service/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/lista")
	public Page<ClienteDto> getClientesPage(@RequestParam(value="nif") String nif,
			@RequestParam(value="nombre") String nombre,
			@RequestParam(value="poblacion") String poblacion,
			@RequestParam(value="codigoPostal") String codigoPostal,
			@RequestParam(value="page") int page,
			@RequestParam(value="size") int size){
		return this.clienteService.getAllClientes(nif, nombre, poblacion, codigoPostal, PageRequest.of(page, size));
	}
	
	@PostMapping("/save")
	public ClienteDto saveCliente(@RequestBody ClienteDto cliente) throws Exception{
		return this.clienteService.save(cliente);
	}

}
