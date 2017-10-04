package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Restaurante {

////Atributos
	
	/**
	 * Nombre del restaurante
	 */
	@JsonProperty(value="name")
	private String nombre;

	/**
	 * Representante del restaurante
	 */
	@JsonProperty(value="representante")
	private String representante;
	
	/**
	 * Tipo de comida del restaurante
	 */
	@JsonProperty(value="tipo_comida")
	private String tipo_comida;
	
	/**
	 * Pagina web del restaurante
	 */
	@JsonProperty(value="pagina_web")
	private String pagina_web;
	
	/**
	 * Metodo constructor de la clase Restaurante
	 * <b>post: </b> Crea el Restaurante con los valores que entran como parametro
	 * @param id - Id del Restaurante.
	 * @param nombre - Nombre del Restaurante.
	 * @param representante - Representante del Restaurante.
	 * @param tipoComida - Tipo de comida del Restaurante.
	 * @param paginaWeb - PaginaWeb del Restaurante.
	 */
	public Restaurante(@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="representante")String representante,
			@JsonProperty(value="tipo_comida")String tipoComida,
			@JsonProperty(value="pagina_web")String paginaWeb) 
	{
		super();
		this.nombre = nombre;
		this.representante = representante;
		this.tipo_comida = tipoComida;
		this.pagina_web = paginaWeb;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getTipoComida() {
		return tipo_comida;
	}

	public void setTipoComida(String tipoComida) {
		this.tipo_comida = tipoComida;
	}

	public String getPaginaWeb() {
		return pagina_web;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.pagina_web = paginaWeb;
	}
	
	
}
