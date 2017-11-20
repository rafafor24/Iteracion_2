package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class MenuProducto {

	/**
	 * nombre del menu
	 */
	@JsonProperty(value="nombre_menu")
	private String nombre_menu;
	
	
	/**
	 * nombre del [rpducto
	 */
	@JsonProperty(value="nombre_producto")
	private String nombre_producto;
	
	public MenuProducto(@JsonProperty(value="nombre_menu")String nombre_menu, 
			@JsonProperty(value="nombre_producto")String nombre_producto)
	{
		this.nombre_menu=nombre_menu;
		this.nombre_producto=nombre_producto;
	}

	public String getNombre_menu() {
		return nombre_menu;
	}

	public void setNombre_menu(String nombre_menu) {
		this.nombre_menu = nombre_menu;
	}

	public String getNombre_producto() {
		return nombre_producto;
	}

	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}

	
}
