package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoProductosMesa {
	
	@JsonProperty(value="idCliente")
	private Long idCliente;
	
	@JsonProperty(value="idMesa")
	private Long idMesa;
	
	@JsonProperty(value="pedido")
	private Pedido pedido;
	
	@JsonProperty(value="nombreProductos")
	private List<String> nombreProductos;
	
	public PedidoProductosMesa(@JsonProperty(value="idCliente") Long idCliente,@JsonProperty(value="idMesa") 
	Long idMesa,@JsonProperty(value="pedido") Pedido pedido,@JsonProperty(value="nombreProductos") List<String> nombreProductos)
	{
		this.idCliente=idCliente;
		this.idMesa=idMesa;
		this.setPedido(pedido);
		this.setNombreProductos(nombreProductos);
	} 
	/**
	 * @return the idMesa
	 */
	public Long getIdMesa() {
		return idMesa;
	}
	/**
	 * @param idMesa the idMesa to set
	 */
	public void setIdMesa(Long idMesa) {
		this.idMesa = idMesa;
	}
	
	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	/**
	 * @return the pedido
	 */
	public Pedido getPedido() {
		return pedido;
	}
	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	/**
	 * @return the nombreProductos
	 */
	public List<String> getNombreProductos() {
		return nombreProductos;
	}
	/**
	 * @param nombreProductos the nombreProductos to set
	 */
	public void setNombreProductos(List<String> nombreProductos) {
		this.nombreProductos = nombreProductos;
	}
	

}
