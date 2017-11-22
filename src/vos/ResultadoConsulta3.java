package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ResultadoConsulta3 {
////Atributos

	/**
	 * Representante del restaurante
	 */
	@JsonProperty(value="dia")
	private String dia;
	
	/**
	 * Tipo de comida del restaurante
	 */
	@JsonProperty(value="producto_mas")
	private String producto_mas;
	
	/**
	 * Pagina web del restaurante
	 */
	@JsonProperty(value="producto_menos")
	private String producto_menos;
	
	/**
	 * Tipo de comida del restaurante
	 */
	@JsonProperty(value="restaurante_mas")
	private String restaurante_mas;
	
	/**
	 * Pagina web del restaurante
	 */
	@JsonProperty(value="restaurante_menos")
	private String restaurante_menos;
	/**
	 * Metodo constructor de la clase Restaurante
	 * <b>post: </b> Crea el Restaurante con los valores que entran como parametro
	 * @param id - Id del Restaurante.
	 * @param nombre - Nombre del Restaurante.
	 * @param representante - Representante del Restaurante.
	 * @param tipo_comida - Tipo de comida del Restaurante.
	 * @param pagina_web - PaginaWeb del Restaurante.
	 */
	public ResultadoConsulta3(
			@JsonProperty(value="dia")String dia,
			@JsonProperty(value="producto_mas")String producto_mas,
			@JsonProperty(value="producto_menos")String producto_menos,
			@JsonProperty(value="restaurante_mas")String restaurante_mas,
			@JsonProperty(value="restaurante_menos")String restaurante_menos) 
	{
		
		this.dia = dia;
		this.producto_mas = producto_mas;
		this.producto_menos = producto_menos;
		this.restaurante_mas = restaurante_mas;
		this.restaurante_menos=restaurante_menos;
	}
	
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getProducto_mas() {
		return producto_mas;
	}
	public void setProducto_mas(String producto_mas) {
		this.producto_mas = producto_mas;
	}
	public String getProducto_menos() {
		return producto_menos;
	}
	public void setProducto_menos(String producto_menos) {
		this.producto_menos = producto_menos;
	}
	public String getRestaurante_mas() {
		return restaurante_mas;
	}
	public void setRestaurante_mas(String restaurante_mas) {
		this.restaurante_mas = restaurante_mas;
	}
	public String getRestaurante_menos() {
		return restaurante_menos;
	}
	public void setRestaurante_menos(String restaurante_menos) {
		this.restaurante_menos = restaurante_menos;
	}
	

	
	
	
	

}
