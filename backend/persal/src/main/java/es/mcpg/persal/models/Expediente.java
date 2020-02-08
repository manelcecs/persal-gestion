package es.mcpg.persal.models;

import java.util.Date;
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
@Table(name="expedientes")
public class Expediente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id",nullable= false)
	private Integer id_actividad;
	
	@Column(name="fecha_apertura")
	private Date fecha_apertura;
	
	@Column(name="fecha_cierre")
	private Date fecha_cierre;
	
	@Column(name="aprob_num")
	private Boolean aprob_num;
	
	@OneToMany(mappedBy="id_expediente")
	private List<Presupuesto> presupuestos;
	
	@OneToMany(mappedBy="id_expediente")
	private List<CuentaExpediente> cuentasExpedientes;
}
