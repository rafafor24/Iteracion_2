package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {
////Atributos
	
	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Identificacion del usuario
	 */
	@JsonProperty(value="identificacion")
	private Integer identificacion;
	
	/**
	 * rol del usuario
	 */
	@JsonProperty(value="rol")
	private String rol;
	
	/**
	 * correo electronico del usuario
	 */
	@JsonProperty(value="correo_electronico")
	private String correo_electronico;
	
	
	


	/**
	 * Metodo constructor de la clase Usuario
	 * <b>post: </b> Crea el Usuario con los valores que entran como parametro
	 * @param nombre - Nombre del Usuario.
	 * @param identificacion - Identificacion del Usuario.
	 * @param rol - rol del Usuario.
	 * @param correo_electronico - correo electronico del Usuario.
	 */
	public Usuario( @JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="identificacion")Integer identificacion,
			@JsonProperty(value="rol")String rol, 
			@JsonProperty(value="correo_electronico")String correo_electronico) 
	{
		super();
		this.nombre = nombre;
		this.identificacion= identificacion;
		this.rol= rol;
		this.correo_electronico= correo_electronico;
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

	public String getCorreo_electronico() {
		return correo_electronico;
	}

	public void setCorreo_electronico(String correo_electronico) {
		this.correo_electronico = correo_electronico;
	}


	
}

