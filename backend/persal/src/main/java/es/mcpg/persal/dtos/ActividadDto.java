package es.mcpg.persal.dtos;

import java.util.Date;
import java.util.List;


import es.mcpg.persal.models.Expediente;
import es.mcpg.persal.models.ObraNueva;
import lombok.Data;

@Data
public class ActividadDto {

	private Integer id;
	
	private String nif_cliente;
	
	private Integer tipo_actividad;
	
	private Date fecha_inicio;
	
	private Date fecha_fin;
	
	private List<ObraNueva> obrasNuevas;
	
	private List<Expediente> expedientes;

		
}
