package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Zona;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/RotondAndes/rest/
 * @author
 */

@Path("zonas")
public class ZonaServices {

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
	 * Metodo que expone servicio REST usando GET que da todos los zonas de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/zonas
	 * @return Json con todos los zonas de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getZonas() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Zona> zonas;
		try {
			zonas = tm.darZonas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zonas).build();
	}
	

    /**
     * Metodo que expone servicio REST usando GET que busca el zona con el nombre que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/ZonaAndes/rest/zonas/nombre/nombre?nombre=<<nombre>>" para la busqueda"
     * @param name - Nombre del zona a buscar que entra en la URL como parametro 
     * @return Json con el/los zonas encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getZonaName( @QueryParam("nombre") String name) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Zona> zonas;
		try {
			if (name == null || name.length() == 0)
				throw new Exception("Nombre del zona no valido");
			zonas = tm.buscarZonasPorName(name);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zonas).build();
	}



	 /**
     * Metodo que expone servicio REST usando POST que agrega el Zona que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param zona - Zona a agregar
     * @return Json con el zona que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addZona(Zona zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addZona(zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
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
     * Metodo que expone servicio REST usando PUT que actualiza el zona que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/zonas
     * @param zona - zona a actualizar. 
     * @return Json con el zona que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateZona(Zona zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateZona(zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el zona que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
     * @param zona - zona a aliminar. 
     * @return Json con el zona que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteZona(Zona zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteZona(zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}

}


