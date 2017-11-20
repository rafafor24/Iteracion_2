package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Restaurante extends Usuario 
{

////Atributos

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
	 * @param tipo_comida - Tipo de comida del Restaurante.
	 * @param pagina_web - PaginaWeb del Restaurante.
	 */
	public Restaurante(@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="identificacion")Integer identificacion, 
			@JsonProperty(value="correo_electronico")String correo_electronico,
			@JsonProperty(value="representante")String representante,
			@JsonProperty(value="tipo_comida")String tipo_comida,
			@JsonProperty(value="pagina_web")String pagina_web) 
	{
		super(nombre, identificacion, "Restaurante", correo_electronico);
		this.representante = representante;
		this.tipo_comida = tipo_comida;
		this.pagina_web = pagina_web;
	}
	
//	public Restaurante(@JsonProperty(value="representante")String representante,
//			@JsonProperty(value="tipo_comida")String tipo_comida,
//			@JsonProperty(value="pagina_web")String pagina_web) 
//	{
//		super(" ",0,"Restaurante"," ");
//		this.representante = representante;
//		this.tipo_comida = tipo_comida;
//		this.pagina_web = pagina_web;
//	}
	
	public Usuario darUsuario()
	{
		return new Usuario(this.getNombre(),this.getIdentificacion(),this.getRol(),this.getCorreo_electronico());
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getTipo_comida() {
		return tipo_comida;
	}

	public void setTipo_comida(String tipo_comida) {
		this.tipo_comida = tipo_comida;
	}

	public String getPagina_web() {
		return pagina_web;
	}

	public void setPagina_web(String pagina_web) {
		this.pagina_web = pagina_web;
	}
	
	
	public String toString()
	{
//		String nombre;
//        int identificacion;
//        String correo_electronico;
//        String representante;
//        int tipo_comida;
//        String pagina_web;
		return "nombre="+this.getNombre()+", identificacion="+this.getIdentificacion()+", correo="+
		this.getCorreo_electronico()+", representante="+this.representante+", tipocomida="+this.tipo_comida+", paginaweb="+this.pagina_web;
	}
}
