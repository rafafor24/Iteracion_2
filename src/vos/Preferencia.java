package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import oracle.net.aso.p;

public class Preferencia {

	/**
	 * id de la preferencia
	 */
	private int id;
	
	/**
	 * precio de preferencia por el cliente
	 */
	private int precio;
	
	/**
	 * zona de preferencia del cliente
	 */
	private String zona;
	
	/**
	 * tipo de comida de preferencia del cliente
	 */
	private Integer tipo_comida;

	
	
	/**
	 * Metodo constructor de la clase Preferencia
	 * <b>post: </b> Crea la Preferencia con los valores que entran como parametro
	 * @param id - Id de la preferencia.
	 * @param precio -precio de preferencia.
	 * @param zona - Zona de preferencia.
	 * @param tipo_comida -tipo comida de preferencia.
	 */
	public Preferencia( @JsonProperty(value="id")Integer id,
			@JsonProperty(value="precio")Integer precio,
			@JsonProperty(value="zona")String zona,
			@JsonProperty(value="tipo_comida") Integer tipoComida) 
	{
		super();
		this.id= id;
		this.precio= precio;
		this.zona= zona;
		this.tipo_comida=tipoComida;
	}
	
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the precio
	 */
	public int getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(int precio) {
		this.precio = precio;
	}

	/**
	 * @return the zona
	 */
	public String getZona() {
		return zona;
	}

	/**
	 * @param zona the zona to set
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}

	/**
	 * @return the tipo_comida
	 */
	public Integer getTipo_comida() {
		return tipo_comida;
	}

	/**
	 * @param tipo_comida the tipo_comida to set
	 */
	public void setTipo_comida(Integer tipo_comida) {
		this.tipo_comida = tipo_comida;
	}
	
	
	
}
