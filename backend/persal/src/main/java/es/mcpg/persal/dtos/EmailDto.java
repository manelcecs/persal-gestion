package es.mcpg.persal.dtos;

import es.mcpg.persal.models.Cliente;
import lombok.Data;

@Data
public class EmailDto {

	private Cliente nif_cliente;
	
	private String email;
	
}
