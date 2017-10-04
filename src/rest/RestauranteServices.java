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
import vos.Restaurante;

@Path("restaurantes")
public class RestauranteServices {

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
	 * Metodo que expone servicio REST usando GET que da todos los restaurantes de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes
	 * @return Json con todos los restaurantes de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRestaurantes() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Restaurante> restaurantes;
		try {
			restaurantes = tm.darRestaurantes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurantes).build();
	}

	/**
     * Metodo que expone servicio REST usando GET que busca el restaurante con el nombre que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RestauranteAndes/rest/restaurantes/nombre/nombre?nombre=<<nombre>>" para la busqueda"
     * @param name - Nombre del restaurante a buscar que entra en la URL como parametro 
     * @return Json con el/los restaurantes encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getRestauranteName( @QueryParam("nombre") String name) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Restaurante> restaurantes;
		try {
			if (name == null || name.length() == 0)
				throw new Exception("Nombre del restaurante no valido");
			restaurantes = tm.buscarRestaurantesPorName(name);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurantes).build();
	}


	
    /**
     * Metodo que expone servicio REST usando POST que agrega el restaurante que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param restaurante - restaurante a agregar
     * @return Json con el restaurantes que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRestaurante(Restaurante restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addRestaurante(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
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
     * Metodo que expone servicio REST usando PUT que actualiza el restaurante que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RestauranteAndes/rest/restaurantes
     * @param restaurante - restaurante a actualizar. 
     * @return Json con el restaurante que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRestaurante(Restaurante restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateRestaurante(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el restaurante que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RestauranteAndes/rest/restaurantes
     * @param restaurante - restaurante a aliminar. 
     * @return Json con el restaurante que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRestaurante(Restaurante restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteRestaurante(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}

}
