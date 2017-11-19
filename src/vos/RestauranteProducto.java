package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RestauranteProducto {

////Atributos
	
	/**
	 * Nombre del Producto
	 */
	@JsonProperty(value="representante_restaurante")
	private String representante_restaurante;
	
	/**
	 * Descripcion del Producto
	 */
	@JsonProperty(value="nombre_producto")
	private String nombre_producto;
	
	/**
	 * Traduccion del Producto
	 */
	@JsonProperty(value="cantidad_maxima")
	private Integer cantidad_maxima;	
	
	/**
	 * Tiempo de preparacion del Producto
	 */
	@JsonProperty(value="cantidad_actual")
	private Integer cantidad_actual;
	
	

	/**
	 * Metodo constructor de la clase Ingrediente
	 * <b>post: </b> Crea el Ingrediente con los valores que entran como parametro
	 * @param id - Id del Ingrediente.
	 * @param nombre - Nombre del Ingrediente.
	 * @param descripcion - Descripcion del Ingrediente.
	 * @param traduccion - Traduccion del Ingrediente.
	 */
	public RestauranteProducto(@JsonProperty(value="representante_restaurante")String representante_restaurante,
			@JsonProperty(value="nombre_producto")String nombre_producto,
			@JsonProperty(value="cantidad_maxima")Integer cantidad_maxima,	
			@JsonProperty(value="cantidad_actual")Integer cantidad_actual)
	{
		super();
		this.representante_restaurante = representante_restaurante;
		this.nombre_producto = nombre_producto;
		this.cantidad_maxima = cantidad_maxima;
		this.cantidad_actual=cantidad_actual;
		
	}
	

	public String getRepresentante_restaurante() {
		return representante_restaurante;
	}



	public void setRepresentante_restaurante(String representante_restaurante) {
		this.representante_restaurante = representante_restaurante;
	}



	public String getNombre_producto() {
		return nombre_producto;
	}



	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}



	public Integer getCantidad_maxima() {
		return cantidad_maxima;
	}



	public void setCantidad_maxima(Integer cantidad_maxima) {
		this.cantidad_maxima = cantidad_maxima;
	}



	public Integer getCantidad_actual() {
		return cantidad_actual;
	}



	public void setCantidad_actual(Integer cantidad_actual) {
		this.cantidad_actual = cantidad_actual;
	}
}
