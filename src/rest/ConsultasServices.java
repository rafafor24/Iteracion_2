package rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Consulta1y2;
import vos.ListaRentabilidad;
import vos.Rentabilidad;
import vos.ResultadoConsulta3;
import vos.Usuario;

@Path("consultar")
public class ConsultasServices {
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
	@POST
	@Path("{cantidad: \\d+}")
//	@PathParam("fecha1") String fecha1,@PathParam("fecha2") String fecha2,@PathParam("representante") String representante
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response consultar(Consulta1y2 consulta,@PathParam("cantidad") Long cantidad) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> usuarios;
		try {
			usuarios = tm.consulta1(consulta,cantidad);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}
	
	/**
	 * Metodo que expone servicio REST usando GET que da todos los ingredientes de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ingredientes
	 * @return Json con todos los ingredientes de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Path("cons4")
//	@PathParam("fecha1") String fecha1,@PathParam("fecha2") String fecha2,@PathParam("representante") String representante
	@Produces({ MediaType.APPLICATION_JSON })
	public Response consultar4() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> usuarios;
		try {
			
				usuarios = tm.consulta4();	
			
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}
	
	@GET
	@Path("cons3")
//	@PathParam("fecha1") String fecha1,@PathParam("fecha2") String fecha2,@PathParam("representante") String representante
	@Produces({ MediaType.APPLICATION_JSON })
	public Response consultar3() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<ResultadoConsulta3> usuarios;
		try {
				usuarios = tm.consulta3();
			
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}
	
	
	@POST
	@Path("rentabilidad")
//	@PathParam("fecha1") String fecha1,@PathParam("fecha2") String fecha2,@PathParam("representante") String representante
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response darRentabilidad(Consulta1y2 cons) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		ListaRentabilidad rentabilidad;
		try {
				rentabilidad = tm.darRentabilidad(cons);
			
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(rentabilidad).build();
	}
	
}
