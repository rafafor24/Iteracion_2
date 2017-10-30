package vos;



import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Pedido {
/**
 * fecha en la que se realizo el pedido
 */
	@JsonProperty(value="fecha")
	private Date fecha;
	/**
	 * hora en la que se realizo el pedido
	 */
	@JsonProperty(value="hora")
	private Integer hora;
	/**
	 * Estado del pedido
	 */
	@JsonProperty(value="aceptado")
	private Integer aceptado;
	/**
	 * identificador del pedido
	 */
	@JsonProperty(value="id")
	private Long id;
	
	/**
	 * Metodo constructor de la clase Pedido
	 * <b>post: </b> Crea el Pedido con los valores que entran como parametro
	 * @param id - Id del Pedido.
	 * @param fecha - fecha del pedido.
	 * @param hora - hora del pedido.
	 * @param aceptado- Estado del pedido.
	 */
	public Pedido( @JsonProperty(value="id")Long id,
			@JsonProperty(value="fecha")Date fecha,
			@JsonProperty(value="hora")Integer hora,
			@JsonProperty(value="aceptado") Integer aceptado) 
	{
		super();
		this.id= id;
		this.fecha= fecha;
		this.hora= hora;
		this.aceptado= aceptado;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the hora
	 */
	public Integer getHora() {
		return hora;
	}

	/**
	 * @param hora the hora to set
	 */
	public void setHora(Integer hora) {
		this.hora = hora;
	}

	/**
	 * @return the aceptado
	 */
	public Integer isAceptado() {
		return aceptado;
	}

	/**
	 * @param aceptado the aceptado to set
	 */
	public void setAceptado(Integer aceptado) {
		this.aceptado = aceptado;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
