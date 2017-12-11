package vos;


import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa una arreglo de Rentabilidad
 * @author Juan
 */
public class ListaRentabilidad {
	
	/**
	 * List con los videos
	 */
	@JsonProperty(value="rentabilidades")
	private List<Rentabilidad> rentabilidades;
	
	/**
	 * Constructor de la clase ListaRentabilidad
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaRentabilidad( @JsonProperty(value="rentabilidades")List<Rentabilidad> videos){
		this.rentabilidades = videos;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Rentabilidad> getRentabilidad() {
		return rentabilidades;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setRentabilidad(List<Rentabilidad> videos) {
		this.rentabilidades = videos;
	}
	
}