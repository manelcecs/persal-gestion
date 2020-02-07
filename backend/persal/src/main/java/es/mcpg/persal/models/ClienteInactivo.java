package es.mcpg.persal.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="clientes_inactivos")
public class ClienteInactivo {

	@Id 					//No habia niguna columna en el diagrama que fuera Primary key,como no se si hace falta especificarlo he puesto que sea el nif
	@ManyToOne
    @JoinColumn(name="nif", nullable=false)
    private String nif_cliente;
	
	@Column(name="fecha_inactivo")
	private Date fecha_inactivo;
	

}
