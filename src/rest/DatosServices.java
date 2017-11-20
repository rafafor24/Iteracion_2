package rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import vos.Cliente;
import vos.Equivalencia;
import vos.Pedido;
import vos.Producto;
import vos.Restaurante;
import vos.RestauranteProducto;
import vos.Usuario;

@Path("datos")
public class DatosServices {
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
	@Path("{cantidad: \\d+}/{productos: \\d+}/{pedidosUsuario: \\d+}/{pedidosCliente: \\d+}")
	public void getEquivalencias(@PathParam("cantidad") Long cantidad, @PathParam("productos") Long productos,
			@PathParam("pedidosUsuario") Long pedidosUsuario,@PathParam("pedidosCliente") Long pedidosCliente) 
	{		
		RotondAndesTM tm = new RotondAndesTM(getPath());
		PodamFactory factory = new PodamFactoryImpl();
		System.out.println("cantidad =" + cantidad);
		System.out.println("cantidad2 =" + productos);
		for (int i = 0; i < cantidad; i++)
		{
			//cantidad RESTAURANTES cantidad RESTAURANTES cantidad RESTAURANTES cantidad RESTAURANTES cantidad RESTAURANTES
			Restaurante restaurante = factory.manufacturePojo(Restaurante.class);

			try 
			{
				tm.addRestaurante(restaurante);
			} catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ArrayList<String> nombresProductos=new ArrayList<>();

			for (int e = 0; e < productos; e++) 
			{
				//productos PRODUCTOS productos PRODUCTOS productos PRODUCTOS productos PRODUCTOS productos PRODUCTOS productos PRODUCTOS
				Producto producto = factory.manufacturePojo(Producto.class);
				nombresProductos.add(producto.getNombre());
				int a = factory.manufacturePojo(Integer.class);
				int b = factory.manufacturePojo(Integer.class);
				int CantidadMaxima = 0;
				int CantidadActual = 0;
				if (a > b) 
				{
					CantidadMaxima = a;
					CantidadActual = b;
				} else 
				{
					CantidadMaxima = b;
					CantidadActual = a;
				}

				RestauranteProducto relacion = new RestauranteProducto(restaurante.getRepresentante(),
						producto.getNombre(), CantidadMaxima, CantidadActual);
				try {


					tm.addProducto(producto, relacion);

				} 
				catch (Exception w) 
				{
					// TODO Auto-generated catch block
					w.printStackTrace();
				}

			}


			// cantidad CLIENTES cantidad CLIENTES cantidad CLIENTES cantidad CLIENTES cantidad CLIENTES cantidad CLIENTES
			Cliente cliente = factory.manufacturePojo(Cliente.class);

			try 
			{
				tm.addCliente(cliente);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int contadorCantProductos=0;

			for(int o=0;o<pedidosCliente;o++)
			{
				// pedidosCliente PEDIDOS pedidosCliente PEDIDOS pedidosCliente PEDIDOS pedidosCliente PEDIDOS pedidosCliente PEDIDOS
				Pedido pedido = factory.manufacturePojo(Pedido.class);

				if(contadorCantProductos==productos)
				{
					contadorCantProductos=0;
				}
				String nombreProductoElejido=nombresProductos.get(contadorCantProductos);
				contadorCantProductos++;

				try 
				{
					tm.addPedido(pedido, cliente.getNombre(), nombreProductoElejido);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			contadorCantProductos=0;

			//cantidad USUARIOS cantidad USUARIOS cantidad USUARIOS cantidad USUARIOS cantidad USUARIOS cantidad USUARIOS cantidad USUARIOS
			Usuario usuario = factory.manufacturePojo(Usuario.class);

			try 
			{
				tm.addUsuario(usuario);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}

			for(int o=0;o<pedidosUsuario;o++)
			{
				//pedidosUsuario PEDIDOS pedidosUsuario PEDIDOS pedidosUsuario PEDIDOS pedidosUsuario PEDIDOS pedidosUsuario PEDIDOS
				Pedido pedido = factory.manufacturePojo(Pedido.class);

				if(contadorCantProductos==productos)
				{
					contadorCantProductos=0;
				}
				String nombreProductoElejido=nombresProductos.get(contadorCantProductos);
				contadorCantProductos++;

				try 
				{
					tm.addPedido(pedido, cliente.getNombre(), nombreProductoElejido);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

	}
}
