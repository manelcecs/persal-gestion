package es.mcpg.persal.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="clientes")
public class Cliente {

	@Id
	@Column(name="nif")
	private String id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellidos")
	private String apellidos;
	
	@Column(name="domicilio")
	private String domicilio;
	
	@Column(name="poblacion")
	private String poblacion;
	
	@Column(name="codigoPostal")
	private String codigoPostal;
	
	@Column(name="fechaCreacion")
	private Date fechaCreacion;
	
	@Column(name="fechaUltimaActividad")
	private Date fechaUltimaActividad;
	
	@OneToMany(mappedBy="nif_cliente")
	private List<Telefono> telefonos;
	
	@OneToMany(mappedBy="nif_cliente")
	private List<Email> emails;
	
}
