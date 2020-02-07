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
@Table(name="obras_nuevas")
public class ObraNueva {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name="id", nullable=false)
	private Integer id_actividad;
	
	@Column(name="domicilio")
	private String domicilio;
	
	@Column(name="poblacion")
	private String poblacion;
	
	@Column(name="superficie")
	private Double superficie;
	
	@Column(name="aprob_col_arq")
	private Boolean aprob_col_arq;
	
	@Column(name="dir_obra")
	private Boolean dir_obra;
	
	@OneToMany(mappedBy="id_obra")
	private List<ImagenObra> imagenesObra;
	
	@OneToMany(mappedBy="id_obra")
	private List<PlanoObra> planosObra;
	

}
