package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Producto {

////Atributos
	
	/**
	 * Nombre del Producto
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Descripcion del Producto
	 */
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	/**
	 * Traduccion del Producto
	 */
	@JsonProperty(value="traduccion")
	private String traduccion;	
	
	/**
	 * Tiempo de preparacion del Producto
	 */
	@JsonProperty(value="tiempo_preparacion")
	private Integer tiempo_preparacion;
	
	/**
	 * Costo de produccion del Producto
	 */
	@JsonProperty(value="costo_produccion")
	private Integer costo_produccion;
	
	/**
	 * Precio de venta del Producto
	 */
	@JsonProperty(value="precio_venta")
	private Integer precio_venta;
	
	/**
	 * Traduccion del Producto
	 */
	@JsonProperty(value="tipo")
	private Long tipo;

	/**
	 * Metodo constructor de la clase Ingrediente
	 * <b>post: </b> Crea el Ingrediente con los valores que entran como parametro
	 * @param id - Id del Ingrediente.
	 * @param nombre - Nombre del Ingrediente.
	 * @param descripcion - Descripcion del Ingrediente.
	 * @param traduccion - Traduccion del Ingrediente.
	 */
	public Producto(@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="descripcion")String descripcion,
			@JsonProperty(value="traduccion")String traduccion,	
			@JsonProperty(value="tiempo_preparacion")Integer tiempo_preparacio,
			@JsonProperty(value="costo_produccion")Integer costo_produccion,
			@JsonProperty(value="precio_venta") Integer precio_venta,
			@JsonProperty(value="tipo") Long tipo)
	{
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.traduccion = traduccion;
		this.costo_produccion=costo_produccion;
		this.precio_venta=precio_venta;
		this.tiempo_preparacion=tiempo_preparacio;
		this.tipo=tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTraduccion() {
		return traduccion;
	}

	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}


	public Integer getTiempo_preparacion() {
		return tiempo_preparacion;
	}

	public void setTiempo_preparacion(Integer tiempo_preparacion) {
		this.tiempo_preparacion = tiempo_preparacion;
	}

	public Integer getCosto_produccion() {
		return costo_produccion;
	}

	public void setCosto_produccion(Integer costo_produccion) {
		this.costo_produccion = costo_produccion;
	}

	public Integer getPrecio_venta() {
		return precio_venta;
	}

	public void setPrecio_venta(Integer precio_venta) {
		this.precio_venta = precio_venta;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}
	
	
}
