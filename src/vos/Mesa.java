package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Mesa
{

	/**
	 * id de la mesa
	 */
	@JsonProperty(value="id")
	private Long id;
	
	
	/**
	 * nombre de cliente en la mesa
	 */
	@JsonProperty(value="id_cliente")
	private Long idcliente;
	
	@JsonProperty(value="id_pedido")
	private Long idPedido;
	
	public Mesa(@JsonProperty(value="id_cliente")Long idcliente, 
			@JsonProperty(value="id")Long id,
			@JsonProperty(value="id_pedido") Long idPedido)
	{
		this.setId(id);
		this.setIdcliente(idcliente);
		this.setIdPedido(idPedido);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Long idcliente) {
		this.idcliente = idcliente;
	}

	/**
	 * @return the idPedido
	 */
	public Long getIdPedido() {
		return idPedido;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

}