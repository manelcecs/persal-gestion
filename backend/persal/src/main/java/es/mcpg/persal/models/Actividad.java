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
@Table(name="actividades")

public class Actividad {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name="nif", nullable=false)
	private String nif_cliente;
	
	@Column(name="tipo_actividad")
	private Integer tipo_actividad;
	
	@Column(name="fecha_inicio")
	private Date fecha_inicio;
	
	@Column(name="fecha_fin")
	private Date fecha_fin;
	
	@OneToMany(mappedBy="id_actividad")
	private List<ObraNueva> ObrasNuevas;
	

}
