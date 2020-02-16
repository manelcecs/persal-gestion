package es.mcpg.persal.service;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.mcpg.persal.customRepository.CustomClienteRepository;
import es.mcpg.persal.dtos.ClienteDto;
import es.mcpg.persal.dtos.EmailDto;
import es.mcpg.persal.dtos.TelefonoDto;
import es.mcpg.persal.models.Cliente;
import es.mcpg.persal.repository.ClienteRepository;

@Service
public class ClienteService {
	
	private static String[] letrasNif = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	CustomClienteRepository customClienteRepository;
		
	//Método para obtener un clinete según si nif
	public ClienteDto getCliente(String nif) {
		ModelMapper mapper = new ModelMapper();
		Cliente clienteDB = this.clienteRepository.findById(nif).orElse(null);
		return mapper.map(clienteDB, ClienteDto.class);
	}
	
	//Metodo que devuelve una lista de clientes paginada
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
	
	//Método que guarda un cliente en BBDD (solo cuando se crea)
	public ClienteDto save(ClienteDto cliente) throws Exception {
		this.validateCliente(cliente);
		
		try {
			cliente.setFechaCreacion(new Date());
			cliente.setFechaUltimaActividad(new Date());
			Cliente clienteDB = this.clienteRepository.save(cliente);
			return new ModelMapper().map(clienteDB, ClienteDto.class);
		}catch(Exception e) {
			throw new Exception("Error al guardar el cliente");
		}
	}
	
	//Método que actualiza un cliente de bbdd
	public ClienteDto update(ClienteDto cliente, String id) throws Exception{
		try {
			Cliente clienteDB = this.clienteRepository.getOne(id);
			if(clienteDB != null) {
				clienteDB = this.fillCliente(clienteDB, cliente);
				Cliente save = this.clienteRepository.save(clienteDB); 
				
				return new ModelMapper().map(save, ClienteDto.class);
			}
		}catch(Exception e) {
			throw new Exception("Error al actualizar el cliente");
		}
		return null;
	}
	
	//Metodo que settea la ultima fecha de actividad de un cliente
	//Sólo para uso interno desde otros servicios
	public void setUltimaFechaActividad(String clienteId) throws Exception{
		try {
			Cliente clienteDB = this.clienteRepository.getOne(clienteId);
			clienteDB.setFechaUltimaActividad(new Date());
			this.clienteRepository.save(clienteDB);
		}catch(Exception e) {
			throw new Exception("Error al acualizar la fecha de actividad");
		}
	}
	
	//Método que devuelve un cliente con los campos editables actualizados
	private Cliente fillCliente(Cliente clienteDB, ClienteDto clienteDto) {
		clienteDB.setApellidos(clienteDto.getApellidos());
		clienteDB.setCodigoPostal(clienteDto.getCodigoPostal());
		clienteDB.setDomicilio(clienteDto.getDomicilio());
		clienteDB.setNombre(clienteDto.getNombre());
		clienteDB.setPoblacion(clienteDto.getPoblacion());
		return clienteDB;
	}
	
	
	//Método que valida los campos de un cliente
	private void validateCliente(ClienteDto cliente) throws Exception{
		if(validateFormatoNifCliente(cliente) || validateValorNIFCliente(cliente) || validateDatosDeContacto(cliente)) {
			throw new Exception("Error al validar los campos introducidos del cliente");
		}
	}
	
	//Método que valida el formato del nif/nie de un cliente
	private boolean validateFormatoNifCliente(ClienteDto cliente) {
		return Pattern.compile("((([X-Z])|(LM)){1}((\\d){7})([A-Z]{1}))|((\\d{8})([a-zA-Z]))").matcher(cliente.getNif()).matches();
	}
	
	//Método que valida el nif de un cliente
	private boolean validateValorNIFCliente(ClienteDto cliente) {
		if(Character.isAlphabetic(cliente.getNif().substring(0, 1).toCharArray()[0])) {
			//Es un NIE, no lo validamos
			return true;
		}else {
			//es un nif
			Integer numeros = Integer.valueOf(cliente.getNif().substring(0, 8));
			int modulo = numeros % 23;
			return letrasNif[modulo].equals(cliente.getNif().substring(8, 9));
		}
	}
	
	//Método que valida los datos de contacto de un cliente
	private boolean validateDatosDeContacto(ClienteDto cliente) {
		return validateTelefonos(cliente.getTelefonos()) && validateEmails(cliente.getEmails());
	}
	
	//Método que valida la lista de teléfonos de un cliente
	private boolean validateTelefonos(List<TelefonoDto> telefonos) {
		boolean res = true;
		for(TelefonoDto tel : telefonos) {
			res = Pattern.compile("((\\d){9})").matcher(tel.getNumero()).matches();
		}
		return res;
	}
	
	//Método que valida los emails de un cliente
	private boolean validateEmails(List<EmailDto> emails) {
		boolean res = true;
		for(EmailDto mail : emails) {
			res = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])").matcher(mail.getEmail()).matches();
		}
		return res;
	}
		
}
