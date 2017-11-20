package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductoPedido {

	

	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value="nombre_producto")
	private String nombre_producto;
	
	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value="id_pedido")
	private Long id_pedido;
	
	/**
	 * Metodo constructor de la clase Usuario
	 * <b>post: </b> Crea el Usuario con los valores que entran como parametro
	 * @param nombre - Nombre del Usuario.
	 * @param identificacion - Identificacion del Usuario.
	 * @param rol - rol del Usuario.
	 * @param correo_electronico - correo electronico del Usuario.
	 */
	public ProductoPedido( @JsonProperty(value="nombre_producto")String nombre_producto,
			@JsonProperty(value="id_pedido")Long id_pedido) 
	{
		super();
		this.nombre_producto = nombre_producto;
		this.id_pedido= id_pedido;
		
	}
	
	public String getNombre_producto() {
		return nombre_producto;
	}

	public void setNombre_producto(String nombre_usuario) {
		this.nombre_producto = nombre_usuario;
	}

	public Long getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Long id_pedido) {
		this.id_pedido = id_pedido;
	}
}
