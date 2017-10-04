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
	 * Entrada del Menu
	 */
	@JsonProperty(value="entrada")
	private String entrada;
	
	/**
	 * Plato fuerte del Menu
	 */
	@JsonProperty(value="plato_fuerte")
	private String plato_fuerte;
	
	/**
	 * Postre del Menu
	 */
	@JsonProperty(value="postre")
	private String postre;
	
	/**
	 * Bebida del Menu
	 */
	@JsonProperty(value="bebida")
	private String bebida;
	
	/**
	 * Acompanamiento del Menu
	 */
	@JsonProperty(value="acompanamiento")
	private String acompanamiento;

	
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
			@JsonProperty(value="precio_venta") Integer precio_venta,
			@JsonProperty(value="entrada") String entrada,
			@JsonProperty(value="plato_fuerte") String plato_fuerte,
			@JsonProperty(value="postre") String postre,
			@JsonProperty(value="bebida") String bebida,
			@JsonProperty(value="acompanamiento") String acompanamiento)
	{
		super();
		this.nombre = nombre;
		this.costo_produccion=costo_produccion;
		this.precio_venta=precio_venta;
		this.entrada=entrada;
		this.plato_fuerte=plato_fuerte;
		this.postre=postre;
		this.bebida=bebida;
		this.acompanamiento=acompanamiento;
		
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


	public String getEntrada() {
		return entrada;
	}


	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}


	public String getPlato_fuerte() {
		return plato_fuerte;
	}


	public void setPlato_fuerte(String plato_fuerte) {
		this.plato_fuerte = plato_fuerte;
	}


	public String getPostre() {
		return postre;
	}


	public void setPostre(String postre) {
		this.postre = postre;
	}


	public String getBebida() {
		return bebida;
	}


	public void setBebida(String bebida) {
		this.bebida = bebida;
	}


	public String getAcompanamiento() {
		return acompanamiento;
	}


	public void setAcompanamiento(String acompanamiento) {
		this.acompanamiento = acompanamiento;
	}
	
}
