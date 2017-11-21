package rest;

import vos.ProductoPedido;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;

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
import vos.Servicio;
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
	public void get() 
	{	
		String delim=",";
	final String NEXT_LINE = "\n";
	PodamFactory factory = new PodamFactoryImpl();
	Long cantRestUsuCli=500L;
	Long cantProductoPorRestaurante=20L;
	Long cantPedidosCliente=20L;
	Long cantPedidosUsuario=20L;
	
	ArrayList<Restaurante> restaurantes= new ArrayList<>();
	ArrayList<Producto> productos= new ArrayList<>();
	ArrayList<RestauranteProducto> restauranteProductos= new ArrayList<>();
	ArrayList<Cliente> clientes= new ArrayList<>();
	ArrayList<Usuario> usuarios= new ArrayList<>();
	ArrayList<Pedido> pedidos= new ArrayList<>();
	ArrayList<Servicio> servicios= new ArrayList<>();
	ArrayList<ProductoPedido> productosPedidos= new ArrayList<>();
	
	for (long i = 0; i < cantRestUsuCli; i++)
	{
		long syso=i%500;
		if(syso==0){System.out.println("Entrada No: "+i);}
		
		
		Restaurante restaurante = factory.manufacturePojo(Restaurante.class);
		restaurante.setRol("Restaurante");
		restaurantes.add(restaurante);
		usuarios.add(restaurante.darUsuario());

		for (long e = 0; e < cantProductoPorRestaurante; e++) 
		{
			Producto producto = factory.manufacturePojo(Producto.class);
			productos.add(producto);
			int a = factory.manufacturePojo(Integer.class);
			int b = factory.manufacturePojo(Integer.class);
			int CantidadMaxima = 0;
			int CantidadActual = 0;
			if (a > b) {
				CantidadMaxima = a;
				CantidadActual = b;
			} else {
				CantidadMaxima = b;
				CantidadActual = a;
			}

			RestauranteProducto relacion = new RestauranteProducto(restaurante.getRepresentante(),
					producto.getNombre(), CantidadMaxima, CantidadActual);
			restauranteProductos.add(relacion);

		}
	
	


		
		Cliente cliente = factory.manufacturePojo(Cliente.class);
		cliente.setRol("Cliente");
		clientes.add(cliente);	
		usuarios.add(cliente.darUsuario());
		
		Cliente cliente2 = factory.manufacturePojo(Cliente.class);
		cliente2.setRol("Cliente");
		clientes.add(cliente2);	
		usuarios.add(cliente2.darUsuario());
		
		int contadorCantProductos=0;
		for(int o=0;o<cantPedidosCliente;o++)
		{
			// pedidosCliente PEDIDOS pedidosCliente PEDIDOS pedidosCliente PEDIDOS pedidosCliente PEDIDOS pedidosCliente PEDIDOS
			Pedido pedido = factory.manufacturePojo(Pedido.class);
			
			pedidos.add(pedido);
			
			if(contadorCantProductos==productos.size())
			{
				contadorCantProductos=0;
			}
			String nombreProductoElejido=productos.get(contadorCantProductos).getNombre();
			ProductoPedido productoPedido=new ProductoPedido(nombreProductoElejido, pedido.getId()); 
			productosPedidos.add(productoPedido);
			contadorCantProductos++;
		
			Servicio servicio=new Servicio(pedido.getId(),cliente.getNombre(),restaurante.getRepresentante());
			servicios.add(servicio);
		}

		

		
		Usuario usuario = factory.manufacturePojo(Usuario.class);
		usuario.setRol("Usuario No Registrado");
		usuarios.add(usuario);
		
		Usuario usuario2 = factory.manufacturePojo(Usuario.class);
		usuario2.setRol("Usuario No Registrado");
		usuarios.add(usuario2);

		for(int o=0;o<cantPedidosUsuario;o++)
		{
			//pedidosUsuario PEDIDOS pedidosUsuario PEDIDOS pedidosUsuario PEDIDOS pedidosUsuario PEDIDOS pedidosUsuario PEDIDOS
			Pedido pedido = factory.manufacturePojo(Pedido.class);
			
			pedidos.add(pedido);
			if(contadorCantProductos==productos.size())
			{
				contadorCantProductos=0;
			}
			String nombreProductoElejido=productos.get(contadorCantProductos).getNombre();
			ProductoPedido productoPedido=new ProductoPedido(nombreProductoElejido, pedido.getId());
			productosPedidos.add(productoPedido);
			contadorCantProductos++;				
		
			
			Servicio servicio=new Servicio(pedido.getId(),usuario.getNombre(),restaurante.getRepresentante());
			servicios.add(servicio);
		
		}
	}
	
	System.out.println("RESTAURANTES: "+restaurantes.size());
	System.out.println("PRODUCTOS: "+productos.size());
	System.out.println("RP: "+restauranteProductos.size()+"");
	System.out.println("CLIENTES: "+clientes.size()+"");
	System.out.println("USUARIOS: "+usuarios.size()+"");
	System.out.println("PEDIDOS: "+pedidos.size()+"");
	System.out.println("SERVICIOS: "+servicios.size()+"");
	System.out.println("PP: "+productosPedidos.size()+"");	

	
	
	
	try {
		FileWriter fw = new FileWriter("C:/Users/User/Documents/Iteracion_4/files/2restaurantes.csv");

		fw.append("REPRESENTANTE").append(delim);
		fw.append("TIPO_COMIDA").append(delim);
		fw.append("PAGINA_WEB").append(delim);
		fw.append("NOMBRE_USUARIO").append(NEXT_LINE);
		

		for(int i=0;i<restaurantes.size();i++)
		{
			fw.append(restaurantes.get(i).getRepresentante());
			fw.append(delim);
			fw.append(restaurantes.get(i).getTipo_comida());
			fw.append(delim);
			fw.append(restaurantes.get(i).getPagina_web());
			fw.append(delim);
			fw.append(restaurantes.get(i).getNombre());					
			fw.append(NEXT_LINE);
		}
		

		

		fw.flush();
		fw.close();
	} catch (IOException e) {
		// Error al crear el archivo, por ejemplo, el archivo 
		// está actualmente abierto.
		e.printStackTrace();
	}
	
	try {
		FileWriter fw = new FileWriter("C:/Users/User/Documents/Iteracion_4/files/1usuarios.csv");

		fw.append("NOMBRE").append(delim);
		fw.append("IDENTIFICACION").append(delim);
		fw.append("ROL").append(delim);
		fw.append("CORREO_ELECTRONICO").append(NEXT_LINE);
		

		for(int i=0;i<usuarios.size();i++)
		{
			fw.append(usuarios.get(i).getNombre());
			fw.append(delim);
			fw.append(usuarios.get(i).getIdentificacion()+"");
			fw.append(delim);
			fw.append(usuarios.get(i).getRol());
			fw.append(delim);
			fw.append(usuarios.get(i).getCorreo_electronico());					
			fw.append(NEXT_LINE);
		}
		

		

		fw.flush();
		fw.close();
	} catch (IOException e) {
		// Error al crear el archivo, por ejemplo, el archivo 
		// está actualmente abierto.
		e.printStackTrace();
	}
	
	try {
		FileWriter fw = new FileWriter("C:/Users/User/Documents/Iteracion_4/files/3clientes.csv");

		fw.append("ID").append(delim);
		fw.append("NOMBRE_USUARIO").append(NEXT_LINE);
		

		for(int i=0;i<clientes.size();i++)
		{
			fw.append(clientes.get(i).getId()+"");
			fw.append(delim);
			fw.append(clientes.get(i).getNombre()+"");		
			fw.append(NEXT_LINE);
		}
		

		

		fw.flush();
		fw.close();
	} catch (IOException e) {
		// Error al crear el archivo, por ejemplo, el archivo 
		// está actualmente abierto.
		e.printStackTrace();
	}
	
	try {
		FileWriter fw = new FileWriter("C:/Users/User/Documents/Iteracion_4/files/4productos.csv");

		fw.append("NOMBRE").append(delim);
		fw.append("DESCRIPCION").append(delim);
		fw.append("TRADUCCION").append(delim);
		fw.append("COSTO_PRODUCCION").append(delim);
		fw.append("PRECIO_VENTA").append(delim);
		fw.append("TIEMPO_PREPARACION").append(delim);
		fw.append("TIPO").append(NEXT_LINE);
		

		for(int i=0;i<productos.size();i++)
		{
			fw.append(productos.get(i).getNombre());
			fw.append(delim);
			fw.append(productos.get(i).getDescripcion());
			fw.append(delim);
			fw.append(productos.get(i).getTraduccion());
			fw.append(delim);
			fw.append(productos.get(i).getCosto_produccion()+"");
			fw.append(delim);
			fw.append(productos.get(i).getPrecio_venta()+"");
			fw.append(delim);
			fw.append(productos.get(i).getTiempo_preparacion()+"");
			fw.append(delim);
			fw.append(productos.get(i).getTipo()+"");		
			fw.append(NEXT_LINE);
		}
		

		

		fw.flush();
		fw.close();
	} catch (IOException e) {
		// Error al crear el archivo, por ejemplo, el archivo 
		// está actualmente abierto.
		e.printStackTrace();
	}
	
	try 
	{
		FileWriter fw = new FileWriter("C:/Users/User/Documents/Iteracion_4/files/5restauranteproductos.csv");

		fw.append("REPRESENTANTE_RESTAURANTE").append(delim);
		fw.append("NOMBRE_PRODUCTO").append(delim);
		fw.append("CANTIDAD_MAXIMA").append(delim);
		fw.append("CANTIDAD_ACTUAL").append(NEXT_LINE);
		

		for(int i=0;i<restauranteProductos.size();i++)
		{			
			fw.append(restauranteProductos.get(i).getRepresentante_restaurante());			
			fw.append(delim);
			fw.append(restauranteProductos.get(i).getNombre_producto());
			fw.append(delim);
			fw.append(restauranteProductos.get(i).getCantidad_maxima()+"");
			fw.append(delim);
			fw.append(restauranteProductos.get(i).getCantidad_actual()+"");
			fw.append(NEXT_LINE);
		}
		

		

		fw.flush();
		fw.close();
	} 
	catch (IOException e) 
	{
		// Error al crear el archivo, por ejemplo, el archivo 
		// está actualmente abierto.
		e.printStackTrace();
	}
	
	try 
	{
		FileWriter fw = new FileWriter("C:/Users/User/Documents/Iteracion_4/files/6pedidos.csv");

		fw.append("ID").append(delim);
		fw.append("FECHA").append(delim);
		fw.append("HORA").append(delim);
		fw.append("NOMBRE_USUARIO").append(NEXT_LINE);
		

		for(int i=0;i<pedidos.size();i++)
		{			
			fw.append(pedidos.get(i).getId()+"");			
			fw.append(delim);
			int	dia = (int) (Math.random() * 27) + 1;
			int	mes = (int) (Math.random() * 12) + 1;
			int	anio = (int) (Math.random() *17 ) + 2000;
		
			String fechita=dia+"/"+mes+"/"+(anio);
			
			fw.append(fechita);
			fw.append(delim);
			fw.append(pedidos.get(i).getHora()+"");
			fw.append(delim);
			fw.append(pedidos.get(i).getNombre_usuario());
			fw.append(NEXT_LINE);
		}
		

		

		fw.flush();
		fw.close();
	} 
	catch (IOException e) 
	{
		// Error al crear el archivo, por ejemplo, el archivo 
		// está actualmente abierto.
		e.printStackTrace();
	}
	
	try 
	{
		FileWriter fw = new FileWriter("C:/Users/User/Documents/Iteracion_4/files/8servicios.csv");

		fw.append("ID_PEDIDO").append(delim);
		fw.append("NOMBRE_USUARIO").append(delim);
		fw.append("REPRESENTANTE_RESTAURANTE").append(NEXT_LINE);
		

		for(int i=0;i<servicios.size();i++)
		{			
			fw.append(servicios.get(i).getId_pedido()+"");			
			fw.append(delim);
			fw.append(servicios.get(i).getNombre_usuario());
			fw.append(delim);
			fw.append(servicios.get(i).getRepresentante_restaurante());
			fw.append(NEXT_LINE);
		}
		

		

		fw.flush();
		fw.close();
	} 
	catch (IOException e) 
	{
		// Error al crear el archivo, por ejemplo, el archivo 
		// está actualmente abierto.
		e.printStackTrace();
	}
	
	
	try 
	{
		FileWriter fw = new FileWriter("C:/Users/User/Documents/Iteracion_4/files/7productosPedidos.csv");

		fw.append("NOMBRE_PRODUCTO").append(delim);
		fw.append("ID_PEDIDO").append(NEXT_LINE);
		

		for(int i=0;i<productosPedidos.size();i++)
		{			
			fw.append(productosPedidos.get(i).getNombre_producto());			
			fw.append(delim);
			fw.append(productosPedidos.get(i).getId_pedido()+"");
			fw.append(NEXT_LINE);
		}		

		fw.flush();
		fw.close();
	} 
	catch (IOException e) 
	{
		// Error al crear el archivo, por ejemplo, el archivo 
		// está actualmente abierto.
		e.printStackTrace();
	}
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
				Servicio servicios=new Servicio(pedido.getId(),cliente.getNombre(),restaurante.getRepresentante());
				
				try 
				{
					tm.addServicio(servicios);
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
					tm.addPedido(pedido, usuario.getNombre(), nombreProductoElejido);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				
				Servicio servicios=new Servicio(pedido.getId(),usuario.getNombre(),restaurante.getRepresentante());
				
				try 
				{
					tm.addServicio(servicios);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			System.out.println("fin");
		}

	}
}
