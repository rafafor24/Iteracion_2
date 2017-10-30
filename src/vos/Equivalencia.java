package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Equivalencia
{

	/**
	 * nombre de lA equivalencia
	 */
	@JsonProperty(value="nombre1")
	private String nombre1;
	
	/**
	 * id2 de lA equivalencia
	 */
	@JsonProperty(value="nombre2")
	private String nombre2;
	
	/**
	 * id2 de lA equivalencia
	 */
	@JsonProperty(value="tipo")
	private String tipo;
	
	public Equivalencia(@JsonProperty(value="nombre2")String nombre2,
			@JsonProperty(value="nombre1")String nombre1,
			@JsonProperty(value="tipo")String tipo)
	{
		
		this.setNombre1(nombre1);
		this.setNombre2(nombre2);
		this.setTipo(tipo);
	}

	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getNombre1() {
		return nombre1;
	}



	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}



	public String getNombre2() {
		return nombre2;
	}



	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}
}
