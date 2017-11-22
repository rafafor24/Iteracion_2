package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Menu {
	/**
	 * Nombre del Menu
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Costo de produccion del Menu
	 */
	@JsonProperty(value="costo_produccion")
	private Integer costo_produccion;
	
	/**
	 * Precio de venta del Menu
	 */
	@JsonProperty(value="precio_venta")
	private Integer precio_venta;
	



	/**
	 * Metodo constructor de la clase Ingrediente
	 * <b>post: </b> Crea el Ingrediente con los valores que entran como parametro
	 * @param id - Id del Ingrediente.
	 * @param nombre - Nombre del Ingrediente.
	 * @param descripcion - Descripcion del Ingrediente.
	 * @param traduccion - Traduccion del Ingrediente.
	 */
	public Menu(@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="costo_produccion")Integer costo_produccion,
			@JsonProperty(value="precio_venta") Integer precio_venta
			
			)
	{
		super();
		this.nombre = nombre;
		this.costo_produccion=costo_produccion;
		this.precio_venta=precio_venta;
		
		
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	

	


}
