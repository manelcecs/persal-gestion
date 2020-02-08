package es.mcpg.persal.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="cuentas_expediente")
public class CuentaExpediente {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id", nullable= false)
	private Integer id_expediente;
	
	@Column(name="cantidad")
	private Double cantidad;

	@Column(name="descuento")
	private Double descuento;
	
	@Column(name="impuestos")
	private Double impuestos;
	
	@Column(name="cantidad_total")
	private Double cantidad_total;
	
	@OneToMany(mappedBy="id_cuenta")
	private List<Factura> facturas;
	
	
}
