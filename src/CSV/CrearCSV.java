package CSV;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import vos.Cliente;
import vos.Pedido;
import vos.Producto;
import vos.ProductoPedido;
import vos.Restaurante;
import vos.RestauranteProducto;
import vos.Servicio;
import vos.Usuario;

public class CrearCSV {
	
	public static void main(String[] args) {
		final String nombreDeArchivo = "./files/archivo.csv";
		crearArchivoCSV(nombreDeArchivo);
	}

	private static void crearArchivoCSV(String nombreDeArchivo) {
		// this also can be "\t" for tab delimitador
		crearArchivoCSV(nombreDeArchivo, ",");
	}

	private static void crearArchivoCSV(String file, String delim) {
		final String NEXT_LINE = "\n";
		PodamFactory factory = new PodamFactoryImpl();
		Long cantRestUsuCli=100L;
		Long cantProductoPorRestaurante=100L;
		Long cantPedidosCliente=100L;
		Long cantPedidosUsuario=100L;
		
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
			
			Restaurante restaurante = factory.manufacturePojo(Restaurante.class);
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
			clientes.add(cliente);	
			usuarios.add(cliente.darUsuario());

			
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
			usuarios.add(usuario);
					

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
		
		try {
			FileWriter fw = new FileWriter(file);

			fw.append("restaurantes").append(delim);
			fw.append("productos").append(delim);
			fw.append("restauranteProductos").append(delim);
			fw.append("clientes").append(delim);
			fw.append("usuarios").append(delim);
			fw.append("pedidos").append(delim);
			fw.append("servicios").append(delim);
			fw.append("productosPedidos").append(NEXT_LINE);
			

			fw.append(restaurantes.size()+"");
			fw.append(delim);
			fw.append(productos.size()+"");
			fw.append(delim);
			fw.append(restauranteProductos.size()+"");
			fw.append(delim);
			fw.append(clientes.size()+"");
			fw.append(delim);
			fw.append(usuarios.size()+"");
			fw.append(delim);
			fw.append(pedidos.size()+"");
			fw.append(delim);
			fw.append(servicios.size()+"");
			fw.append(delim);
			fw.append(productosPedidos.size()+"");			
			fw.append(NEXT_LINE);

			

			fw.flush();
			fw.close();
		} catch (IOException e) {
			// Error al crear el archivo, por ejemplo, el archivo 
			// está actualmente abierto.
			e.printStackTrace();
		}
		
	}
}