package es.mcpg.persal.service;

import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.mcpg.persal.customRepository.CustomClienteRepository;
import es.mcpg.persal.dtos.ClienteDto;
import es.mcpg.persal.models.Cliente;
import es.mcpg.persal.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	CustomClienteRepository customClienteRepository;
		
	public ClienteDto getCliente(String nif) {
		ModelMapper mapper = new ModelMapper();
		Cliente clienteDB = this.clienteRepository.findById(nif).orElse(null);
		return mapper.map(clienteDB, ClienteDto.class);
	}
	
	public Page<ClienteDto> getAllClientes(String nif,
			String nombre,
			String poblacion,
			String codigoPostal, Pageable page){
		ModelMapper mapper = new ModelMapper();
		Page<Cliente> pageCliente;
		Type listType = new TypeToken<Page<ClienteDto>>() {}.getType();
		
		pageCliente = this.customClienteRepository.findByIdAndNombreAndPoblacionAndCodigoPostal(nif, nombre, poblacion, codigoPostal, page);
		
		return mapper.map(pageCliente, listType);
		
	}
	
	public ClienteDto save(ClienteDto cliente) throws Exception {
		this.validateCliente(cliente);
		
		try {
			Cliente clienteDto = this.clienteRepository.save(cliente);
			return new ModelMapper().map(clienteDto, ClienteDto.class);
		}catch(Exception e) {
			throw new Exception("Error al guardar el cliente");
		}
	}
	
	private void validateCliente(ClienteDto cliente) throws Exception{
		
	}
		
}
