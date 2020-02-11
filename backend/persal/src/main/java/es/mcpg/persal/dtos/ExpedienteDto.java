package es.mcpg.persal.dtos;

import java.util.Date;
import java.util.List;

import es.mcpg.persal.models.CuentaExpediente;
import es.mcpg.persal.models.Presupuesto;

public class ExpedienteDto {
private Integer id;
	
	
	private Integer id_actividad;
	

	private Date fecha_apertura;
	
	
	private Date fecha_cierre;
	

	private Boolean aprob_num;
	
	
	private List<Presupuesto> presupuestos;
	
	
	private List<CuentaExpediente> cuentasExpedientes;
}
