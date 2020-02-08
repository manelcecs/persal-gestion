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

	@Column(name="nif_cliente")
    private String nif_cliente;
	
	@Column(name="fecha_inactivo")
	private Date fecha_inactivo;
	

}
