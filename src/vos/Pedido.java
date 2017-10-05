package vos;

import java.util.Date;

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
	private int hora;
	/**
	 * Estado del pedido
	 */
	@JsonProperty(value="aceptado")
	private int aceptado;
	/**
	 * identificador del pedido
	 */
	@JsonProperty(value="id")
	private long id;
	
	/**
	 * Metodo constructor de la clase Pedido
	 * <b>post: </b> Crea el Pedido con los valores que entran como parametro
	 * @param id - Id del Pedido.
	 * @param fecha - fecha del pedido.
	 * @param hora - hora del pedido.
	 * @param aceptado- Estado del pedido.
	 */
	public Pedido( @JsonProperty(value="id")long id,
			@JsonProperty(value="fecha")Date fecha,
			@JsonProperty(value="hora")int hora,
			@JsonProperty(value="aceptado") int aceptado) 
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
	public int getHora() {
		return hora;
	}

	/**
	 * @param hora the hora to set
	 */
	public void setHora(int hora) {
		this.hora = hora;
	}

	/**
	 * @return the aceptado
	 */
	public int isAceptado() {
		return aceptado;
	}

	/**
	 * @param aceptado the aceptado to set
	 */
	public void setAceptado(int aceptado) {
		this.aceptado = aceptado;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
