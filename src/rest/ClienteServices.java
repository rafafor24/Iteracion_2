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
import vos.Cliente;

@Path("clientes")
public class ClienteServices {

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
	 * Metodo que expone servicio REST usando GET que da todos los clientes de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes
	 * @return Json con todos los clientes de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientes() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Cliente> clientes;
		try {
			clientes = tm.darClientes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(clientes).build();
	}

	/**
     * Metodo que expone servicio REST usando GET que busca el cliente con el nombre que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/ClienteAndes/rest/clientes/nombre/nombre?nombre=<<nombre>>" para la busqueda"
     * @param id - Nombre del cliente a buscar que entra en la URL como parametro 
     * @return Json con el/los clientes encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{id: \\d+}")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getClienteName( @QueryParam("id") Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Cliente> clientes;
		try {
			if (id == null)
				throw new Exception("Id del cliente no valido");
			clientes = tm.buscarClientesPorName(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(clientes).build();
	}


	
    /**
     * Metodo que expone servicio REST usando POST que agrega el cliente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param cliente - cliente a agregar
     * @return Json con el clientes que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCliente(Cliente cliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addCliente(cliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cliente).build();
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
     * Metodo que expone servicio REST usando PUT que actualiza el cliente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/ClienteAndes/rest/clientes
     * @param cliente - cliente a actualizar. 
     * @return Json con el cliente que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCliente(Cliente cliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateCliente(cliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cliente).build();
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el cliente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/ClienteAndes/rest/clientes
     * @param cliente - cliente a aliminar. 
     * @return Json con el cliente que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCliente(Cliente cliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteCliente(cliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cliente).build();
	}

}
