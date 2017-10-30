package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoCM {

	/**
	 * identificador del pedido
	 */
	@JsonProperty(value="id_pedido")
	private Long id_pedido;
	
	/**
	 * identificador del cliente o la mesa
	 */
	@JsonProperty(value="id_cm")
	private Long id_cm;
	
	/**
	 * indica si es mesa o cliente
	 */
	@JsonProperty(value="tipo")
	private String tipo;
	
	/**
	 * Metodo constructor de la clase Pedido
	 * <b>post: </b> Crea el Pedido con los valores que entran como parametro
	 */
	public PedidoCM( @JsonProperty(value="id_pedido")Long id_pedido,
			@JsonProperty(value="id_cm")Long id_CM,
			@JsonProperty(value="tipo")String tipo) 
	{
		this.setId_cm(id_CM);
		this.setId_pedido(id_pedido);
		this.setTipo(tipo);
	}

	public Long getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Long id_pedido) {
		this.id_pedido = id_pedido;
	}

	public Long getId_cm() {
		return id_cm;
	}

	public void setId_cm(Long id_cm) {
		this.id_cm = id_cm;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
