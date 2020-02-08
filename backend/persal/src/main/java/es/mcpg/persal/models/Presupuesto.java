package es.mcpg.persal.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "presupuestos")
public class Presupuesto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	private Integer id_expediente;

	@Column(name = "fecha_creacion")
	private Date fecha_creacion;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "cantidad")
	private Double cantidad;

	@Column(name = "descuento")
	private Double descuento;

	@Column(name = "impuestos")
	private Double impuestos;

	@Column(name = "cantidad_total")
	private Double cantidad_total;
	
}