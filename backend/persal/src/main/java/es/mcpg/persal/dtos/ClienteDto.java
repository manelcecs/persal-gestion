package es.mcpg.persal.dtos;

import java.util.Date;
import java.util.List;

import es.mcpg.persal.models.Email;
import es.mcpg.persal.models.Telefono;
import lombok.Data;

@Data
public class ClienteDto {

	private String nif;
	
	private String nombre;
	
	private String apellidos;
	
	private String domicilio;
	
	private String poblacion;
	
	private String codigoPostal;
	
	private Date fechaCreacion;
	
	private Date fechaUltimaActividad;
	
	private List<Telefono> telefonos;
	
	private List<Email> emails;
	
}
