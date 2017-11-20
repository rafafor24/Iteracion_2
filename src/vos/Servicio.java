package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Servicio {

	/**
	 * identificador del pedido
	 */
	@JsonProperty(value="id_pedido")
	private Long id_pedido;
	
	/**
	 * identificador del pedido
	 */
	@JsonProperty(value="nombre_usuario")
	private String nombre_usuario;
	
	/**
	 * identificador del pedido
	 */
	@JsonProperty(value="representante_restaurante")
	private String representante_restaurante;
	
	/**
	 * Metodo constructor de la clase Pedido
	 * <b>post: </b> Crea el Pedido con los valores que entran como parametro
	 * @param id - Id del Pedido.
	 * @param fecha - fecha del pedido.
	 * @param hora - hora del pedido.
	 * @param aceptado- Estado del pedido.
	 */
	public Servicio( @JsonProperty(value="id_pedido")Long id_pedido,
			@JsonProperty(value="nombre_usuario")String nombre_usuario,
			@JsonProperty(value="representante_restaurante")String representante_restaurante) 
	{
		super();
		this.id_pedido= id_pedido;
		this.nombre_usuario= nombre_usuario;
		this.representante_restaurante= representante_restaurante;
		
	}

	public Long getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Long id_pedido) {
		this.id_pedido = id_pedido;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public String getRepresentante_restaurante() {
		return representante_restaurante;
	}

	public void setRepresentante_restaurante(String representante_restaurante) {
		this.representante_restaurante = representante_restaurante;
	}
}
