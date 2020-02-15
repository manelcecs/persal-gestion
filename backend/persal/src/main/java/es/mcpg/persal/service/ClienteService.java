package es.mcpg.persal.service;

import java.lang.reflect.Type;
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
		if(validateFormatoNifCliente(cliente) || validateValorNIFCliente(cliente) || validateDatosDeContacto(cliente)) {
			throw new Exception("Error al validar los campos introducidos del cliente");
		}
	}
	
	private boolean validateFormatoNifCliente(ClienteDto cliente) {
		return Pattern.compile("((([X-Z])|(LM)){1}((\\d){7})([A-Z]{1}))|((\\d{8})([a-zA-Z]))").matcher(cliente.getNif()).matches();
	}
	
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
	
	
	private boolean validateDatosDeContacto(ClienteDto cliente) {
		return validateTelefonos(cliente.getTelefonos()) && validateEmails(cliente.getEmails());
	}
	
	private boolean validateTelefonos(List<TelefonoDto> telefonos) {
		boolean res = true;
		for(TelefonoDto tel : telefonos) {
			res = Pattern.compile("((\\d){9})").matcher(tel.getNumero()).matches();
		}
		return res;
	}
	
	private boolean validateEmails(List<EmailDto> emails) {
		boolean res = true;
		for(EmailDto mail : emails) {
			res = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])").matcher(mail.getEmail()).matches();
		}
		return res;
	}
		
}
