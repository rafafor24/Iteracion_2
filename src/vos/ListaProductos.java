package vos;


import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa una arreglo de Producto
 * @author Juan
 */
public class ListaProductos {
	
	/**
	 * List con los videos
	 */
	@JsonProperty(value="productos")
	private List<Producto> productos;
	
	/**
	 * Constructor de la clase ListaProductos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaProductos( @JsonProperty(value="productos")List<Producto> videos){
		this.productos = videos;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Producto> getProductos() {
		return productos;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setProducto(List<Producto> videos) {
		this.productos = videos;
	}
	
}