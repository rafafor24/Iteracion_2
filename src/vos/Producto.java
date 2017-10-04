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
			@JsonProperty(value="tiempo_preparacion")Integer tiempoPreparacion,
			@JsonProperty(value="costo_produccion")Integer costoProduccion,
			@JsonProperty(value="precio_venta") Integer precioVenta,
			@JsonProperty(value="tipo") Long tipo)
	{
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.traduccion = traduccion;
		this.costo_produccion=costoProduccion;
		this.precio_venta=precioVenta;
		this.tiempo_preparacion=tiempoPreparacion;
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

	public Integer getTiempoPreparacion() {
		return tiempo_preparacion;
	}

	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempo_preparacion = tiempoPreparacion;
	}

	public Integer getCostoProduccion() {
		return costo_produccion;
	}

	public void setCostoProduccion(Integer costoProduccion) {
		this.costo_produccion = costoProduccion;
	}

	public Integer getPrecioVenta() {
		return precio_venta;
	}

	public void setPrecioVenta(Integer precioVenta) {
		this.precio_venta = precioVenta;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}
	
	
}
