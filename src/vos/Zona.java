package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Zona {

////Atributos
	
	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Identificacion del usuario
	 */
	@JsonProperty(value="espacio_abierto")
	private Integer espacio_abierto;
	
	/**
	 * Identificacion del usuario
	 */
	@JsonProperty(value="capacidad")
	private Integer capacidad;
	
	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value="apto_discapacitados")
	private String apto_discapacitados;
	
	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value="especializacion")
	private String especializacion;
	
	/**
	 * Metodo constructor de la clase Usuario
	 * <b>post: </b> Crea el Usuario con los valores que entran como parametro
	 * @param nombre - Nombre del Usuario.
	 * @param identificacion - Identificacion del Usuario.
	 * @param rol - rol del Usuario.
	 * @param correo_electronico - correo electronico del Usuario.
	 */
	public Zona( @JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="espacio_abierto") Integer espacio_abierto,
			@JsonProperty(value="capacidad") Integer capacidad,
			@JsonProperty(value="apto_discapacitados") String apto_discapacitados,
			@JsonProperty(value="descripcion") String descripcion,
			@JsonProperty(value="especializacion") String especializacion) 
	{
		super();
		this.nombre = nombre;
		this.espacio_abierto= espacio_abierto;
		this.capacidad=capacidad;
		this.apto_discapacitados=apto_discapacitados;
		this.descripcion=descripcion;
		this.especializacion=especializacion;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getEspacio_abierto() {
		return espacio_abierto;
	}

	public void setEspacio_abierto(Integer espacio_abierto) {
		this.espacio_abierto = espacio_abierto;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public String getApto_discapacitados() {
		return apto_discapacitados;
	}

	public void setApto_discapacitados(String apto_discapacitados) {
		this.apto_discapacitados = apto_discapacitados;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEspecializacion() {
		return especializacion;
	}

	public void setEspecializacion(String especializacion) {
		this.especializacion = especializacion;
	}
	
	
}
