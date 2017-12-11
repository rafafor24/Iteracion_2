package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Rentabilidad {


	/**
	 * id del cliente
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="ingresos")
	private Long ingresos;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	public Rentabilidad(@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="ingresos")Long ingresos, 
			@JsonProperty(value="tipo")String tipo)
	{		
		this.ingresos=ingresos;
		this.nombre=nombre;
		this.tipo=tipo;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIngresos() {
		return ingresos;
	}

	public void setIngresos(Long ingresos) {
		this.ingresos = ingresos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	

}
