package es.mcpg.persal.service;

import java.lang.reflect.Type;
import java.util.stream.IntStream;

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

	public Page<ClienteDto> getAllClientes(String nif, String nombre, String poblacion, String codigoPostal,
			Pageable page) {
		ModelMapper mapper = new ModelMapper();
		Page<Cliente> pageCliente;
		Type listType = new TypeToken<Page<ClienteDto>>() {
		}.getType();

		boolean checkInit = this.checkInit(nif, nombre, poblacion, codigoPostal);

		if (checkInit) {
			pageCliente = this.customClienteRepository.findAllActivos();
		} else {
			pageCliente = this.customClienteRepository.findByCriteria(nif, nombre, poblacion, codigoPostal, page);
		}

		return mapper.map(pageCliente, listType);

	}

	private boolean checkInit(String nif, String nombre, String poblacion, String codigoPostal) {
		boolean res = true;

		res = res & !nif.equals("");
		res = res & !nombre.equals("");
		res = res & !poblacion.equals("");
		res = res & !codigoPostal.equals("");

		return res;
	}

	public ClienteDto save(ClienteDto cliente) throws Exception {
		this.validateCliente(cliente);

		try {
			Cliente clienteDto = this.clienteRepository.save(cliente);
			return new ModelMapper().map(clienteDto, ClienteDto.class);
		} catch (Exception e) {
			throw new Exception("Error al guardar el cliente");
		}
	}

	public ClienteDto update(String nif, ClienteDto cliente) throws Exception {
		this.validateCliente(cliente);
		try {
			Cliente clienteDB = this.clienteRepository.findById(nif).orElse(null);
			if (clienteDB.equals(null)) {
				throw new Exception("El cliente con nif: " + nif + ", no existe.");
			} else {
				Cliente clienteDto = this.clienteRepository.save(cliente);
				return new ModelMapper().map(clienteDto, ClienteDto.class);
			}
		} catch (Exception e) {
			throw new Exception("Error al actualizar el cliente");
		}
	}

	private void validateCliente(ClienteDto cliente) throws Exception {
		//validamos que el nif del cliente sea válido
		this.validateNif(cliente.getNif());
		
		boolean valido = true;
		
		//desactivado hasta que encuentre como instalar lombok plugin en eclipse
		//valido = valido && cliente.getNombre() == null && cliente.getNombre() == "";
		
		//se pueden contemplar más validaciones
		if(!valido) {
			throw new Exception("El cliente no es válido");
		}
	}

	private void validateNif(String nif) throws Exception {

		// Guardaremos la letra introducida en formato mayúscula
		String letraMayuscula = ""; 
		
		// Aquí excluimos cadenas distintas a 9 caracteres que debe tener un dni y también si el último caracter no es una letra
		if (nif.length() != 9 || Character.isLetter(nif.charAt(8)) == false) {
			throw new Exception("Error al validar el nif");
		}

		// Al superar la primera restricción, la letra la pasamos a mayúscula
		letraMayuscula = (nif.substring(8)).toUpperCase();

		// Por último validamos que sólo tengo 8 dígitos entre los 8 primeros caracteres y que la letra introducida es igual a la de la ecuación
		// Llamamos a los métodos privados de la clase soloNumeros() y letraDNI()
		if (soloNumeros(nif) == true && letraDNI(nif).equals(letraMayuscula)) {
			throw new Exception("Error al validar el nif");
		} else {
			throw new Exception("Error al validar el nif");
		}
	}

	private boolean soloNumeros(String nif) {

		int i, j = 0;
		String numero = ""; 
		// Es el número que se comprueba uno a uno por si hay alguna letra entre los 8
		// primeros dígitos
		String miDNI = ""; 
		// Guardamos en una cadena los números para después calcular la letra
		String[] unoNueve = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		for (i = 0; i < nif.length() - 1; i++) {
			numero = nif.substring(i, i + 1);

			for (j = 0; j < unoNueve.length; j++) {
				if (numero.equals(unoNueve[j])) {
					miDNI += unoNueve[j];
				}
			}
		}

		if (miDNI.length() != 8) {
			return false;
		} else {
			return true;
		}
	}

	private String letraDNI(String nif) {
		// El método es privado porque lo voy a usar internamente en esta clase, no se necesita fuera de ella
		// pasar miNumero a integer
		int miDNI = Integer.parseInt(nif.substring(0, 8));
		int resto = 0;
		String miLetra = "";
		String[] asignacionLetra = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S",
				"Q", "V", "H", "L", "C", "K", "E" };

		resto = miDNI % 23;

		miLetra = asignacionLetra[resto];

		return miLetra;
	}
}
