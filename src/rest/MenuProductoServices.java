package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Ingrediente;
import vos.MenuProducto;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/RotondAndes/rest/
 * @author
 */

@Path("menuProductos")
public class MenuProductoServices {
	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	

	/**
	 * Metodo que expone servicio REST usando GET que da todos los ingredientes de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ingredientes
	 * @return Json con todos los ingredientes de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMenuProductos() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<MenuProducto> menuProductos;
		try {
			menuProductos = tm.darMenuProductos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menuProductos).build();
	}
	




	 /**
     * Metodo que expone servicio REST usando POST que agrega el Ingrediente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param ingrediente - Ingrediente a agregar
     * @return Json con el ingrediente que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenuProducto(MenuProducto menuProducto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addMenuProducto(menuProducto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menuProducto).build();
	}	
	
	
//    /**
//     * Metodo que expone servicio REST usando POST que agrega los videos que recibe en Json
//     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/varios
//     * @param videos - videos a agregar. 
//     * @return Json con el video que agrego o Json con el error que se produjo
//     */
//	@POST
//	@Path("/varios")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response addVideo(List<Video> videos) {
//		VideoAndesTM tm = new VideoAndesTM(getPath());
//		try {
//			tm.addVideos(videos);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(videos).build();
//	}
	
   
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el menuProducto que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/menuProductos
     * @param menuProducto - menuProducto a aliminar. 
     * @return Json con el menuProducto que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMenuProducto(MenuProducto menuProducto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteMenuProducto(menuProducto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menuProducto).build();
	}
}
