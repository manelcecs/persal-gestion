package es.mcpg.persal.dtos;


import es.mcpg.persal.models.Cliente;
import lombok.Data;

@Data
public class TelefonoDto {

	private Cliente nif_cliente;
	
	private String numero;
	
}
