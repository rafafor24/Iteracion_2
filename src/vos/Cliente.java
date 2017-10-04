package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente {
////Atributos
	
	/**
	 * Nombre del cliente
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Identificacion del cliente
	 */
	@JsonProperty(value="identificacion")
	private Integer identificacion;
	
	/**
	 * rol del cliente
	 */
	@JsonProperty(value="rol")
	private String rol;
	
	/**
	 * correo electronico del cliente
	 */
	@JsonProperty(value="correoElectronico")
	private String correoElectronico;
	
	
	


	/**
	 * Metodo constructor de la clase Cliente
	 * <b>post: </b> Crea el Cliente con los valores que entran como parametro
	 * @param nombre - Nombre del Cliente.
	 * @param identificacion - Identificacion del Cliente.
	 * @param rol - rol del Cliente.
	 * @param correoElectronico - correo electronico del Cliente.
	 */
	public Cliente( @JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="identificacion")Integer identificacion,
			@JsonProperty(value="rol")String rol, 
			@JsonProperty(value="correoElectronico")String correoElectronico) 
	{
		super();
		this.nombre = nombre;
		this.identificacion= identificacion;
		this.rol= rol;
		this.correoElectronico= correoElectronico;
	}
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Integer getIdentificacion() {
		return identificacion;
	}


	public void setIdentificacion(Integer identificacion) {
		this.identificacion = identificacion;
	}


	public String getRol() {
		return rol;
	}


	public void setRol(String rol) {
		this.rol = rol;
	}


	public String getCorreoElectronico() {
		return correoElectronico;
	}


	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
}
