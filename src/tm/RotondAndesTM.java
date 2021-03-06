package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import dao.DAOUsuario;
import dao.DAOZona;
import dtm.RotondAndesDistributed;
import dao.DAOCliente;
import dao.DAOConsultas;
import dao.DAOEquivalencia;
import dao.DAOIngrediente;
import dao.DAOMenu;
import dao.DAOMenuProducto;
import dao.DAOMesa;
import dao.DAOPedido;

import dao.DAOPreferencia;
import dao.DAOProducto;
import dao.DAOProductoPedido;
import dao.DAORestaurante;
import dao.DAORestauranteProducto;
import dao.DAOServicio;
import vos.Usuario;
import vos.Zona;
import vos.Cliente;
import vos.Consulta1y2;
import vos.Equivalencia;
import vos.Ingrediente;
import vos.ListaProductos;
import vos.ListaRentabilidad;
import vos.Menu;
import vos.MenuProducto;
import vos.Mesa;
import vos.Pedido;
import vos.PedidoProductosMesa;
import vos.Preferencia;
import vos.Producto;
import vos.ProductoPedido;
import vos.Rentabilidad;
import vos.Restaurante;
import vos.RestauranteProducto;
import vos.ResultadoConsulta3;
import vos.Servicio;

public class RotondAndesTM {


	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;
	
	private RotondAndesDistributed dtm;


	/**
	 * Metodo constructor de la clase RotondAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto RotondAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
		System.out.println("Instancing DTM...");
		dtm = RotondAndesDistributed.getInstance(this);
		System.out.println("Done!");
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////

	public PedidoProductosMesa darPrueba() throws Exception {
		
		Pedido pedido= new Pedido(3L, new Date(System.currentTimeMillis()), 0, 1, "usuarioPrueba");
		
		List<String> productos = new ArrayList<>();
		
		productos.add("prodname1");
		productos.add("prodname2");
		productos.add("prodname3");
		productos.add("prodname4");
				return new PedidoProductosMesa(1L, 2L, pedido, productos);
	}
	

	/**
	 * Metodo que modela la transaccion que retorna todos los Ingredientes de la base de datos.
	 * @return ListaIngredientes - objeto que modela  un arreglo de Ingredientes. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Ingrediente> darIngredientes() throws Exception {
		List<Ingrediente> ingredientes;
		DAOIngrediente daoIngredientes = new DAOIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingredientes = daoIngredientes.darIngredientes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredientes;
	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Ingrediente a la base de datos.
	 * <b> post: </b> se ha agregado el Ingrediente que entra como parametro
	 * @param ingrediente - el ingrediente a agregar. ingrediente != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addIngrediente(Ingrediente ingrediente) throws Exception {
		DAOIngrediente daoIngredientes = new DAOIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.addIngrediente(ingrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que actualiza el ingrediente que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el ingrediente que entra como parametro
	 * @param ingrediente - ingrediente a actualizar. ingrediente != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateIngrediente(Ingrediente video) throws Exception {
		DAOIngrediente daoIngredientes = new DAOIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.updateIngrediente(video);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el ingrediente que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el ingrediente que entra como parametro
	 * @param ingrediente - ingrediente a eliminar. ingrediente != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteIngrediente(Ingrediente ingrediente) throws Exception {
		DAOIngrediente daoIngredientes = new DAOIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.deleteIngrediente(ingrediente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que retorna todos los Pedidos de la base de datos.
	 * @return ListaPedidos- objeto que modela  un arreglo de Pedidos. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Pedido> darPedidos() throws Exception {
		List<Pedido> pedidos;
		DAOPedido daoPedidos= new DAOPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			pedidos = daoPedidos.darPedidos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedidos;
	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Pedido a la base de datos.
	 * <b> post: </b> se ha agregado el Pedido que entra como parametro
	 * @param pedido - el Pedido a agregar. pedido != null
	 * @throws Exception - cualquier error que se genere agregando el Pedido
	 */
	public void addPedido(Pedido pedido,Long id_cliente,String nombre_producto) throws Exception {
		
		System.out.println("Una vez a addPedido");
		DAOPedido daoPedidos = new DAOPedido();
		DAOProductoPedido daoPP= new DAOProductoPedido();
		ProductoPedido productoPedido= new ProductoPedido(nombre_producto, pedido.getId());
		DAOCliente daocliente = new DAOCliente();
		try 
		{			
			//////transaccion
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoPP.setConn(conn);
			daoPedidos.setConn(conn);
			daocliente.setConn(conn);
			Cliente cliente =daocliente.buscarClientesPorId(id_cliente).get(0);
			if(cliente!=null)
			{
				pedido.setNombre_usuario(cliente.getNombre());
				daoPedidos.addPedido(pedido);
				daoPP.addProductoPedido(productoPedido);


				conn.commit();	
			}
			else
			{
				conn.rollback();
			}
			

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void addPedidoMesa(PedidoProductosMesa pedido) throws Exception 
	{
		Long idMesa=pedido.getIdMesa();
		Long idCliente =pedido.getIdCliente();
		System.out.println("idcliente:" +idCliente);
		DAORestauranteProducto daoRP= new DAORestauranteProducto();
		DAOMesa daoMesa = new DAOMesa();
		DAOPedido daoPedidos = new DAOPedido();
		DAOProductoPedido daoPP= new DAOProductoPedido();
		try
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoRP.setConn(conn);
			daoMesa.setConn(conn);
			daoPedidos.setConn(conn);
			daoPP.setConn(conn);
			daoPedidos.addPedido(pedido.getPedido());
			conn.commit();
			daoMesa.addMesa(new Mesa(idCliente,idMesa,pedido.getPedido().getId()));
			
			for(int i =0;i<pedido.getNombreProductos().size();i++)				
			{
				System.out.println("enfor");
				System.out.println(daoRP.buscarRestauranteProductosPorName(pedido.getNombreProductos().get(i)).get(0).getCantidad_actual());
				if(daoRP.buscarRestauranteProductosPorName(pedido.getNombreProductos().get(i)).get(0).getCantidad_actual()>0)
				{					
					ProductoPedido productoPedido= new ProductoPedido(pedido.getNombreProductos().get(i), pedido.getPedido().getId());
					daoPP.addProductoPedido(productoPedido);
				}
				
			}
		}
		catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				daoMesa.cerrarRecursos();
				daoRP.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * Metodo que modela la transaccion que actualiza el pedido que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el pedido que entra como parametro
	 * @param pedido - pedido a actualizar. pedido != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updatePedido(Pedido pedido) throws Exception {
		DAOPedido daoPedidos = new DAOPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			daoPedidos.updatePedido(pedido);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el Pedido que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Pedido que entra como parametro
	 * @param Pedido - Pedido a eliminar. ingrediente != null
	 * @throws Exception - cualquier error que se genera actualizando los pedidos
	 */
	public void deletePedido(Pedido pedido,Long id_cm,String tipo) throws Exception {
		DAOPedido daoPedidos = new DAOPedido();
		
		try 
		{
			
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			daoPedidos.deletePedido(pedido);
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los Restaurantes de la base de datos.
	 * @return ListaRestaurantes - objeto que modela  un arreglo de Restaurantes. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Restaurante> darRestaurantes() throws Exception {
		List<Restaurante> restaurantes;
		DAORestaurante daoRestaurantes = new DAORestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			restaurantes = daoRestaurantes.darRestaurantes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurantes;
	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Restaurante a la base de datos.
	 * <b> post: </b> se ha agregado el Restaurante que entra como parametro
	 * @param restaurante - el Restaurante a agregar. ingrediente != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addRestaurante(Restaurante restaurante) throws Exception {
		DAORestaurante daoRestaurantes = new DAORestaurante();
		DAOUsuario daoUsuario = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			
			daoUsuario.setConn(conn);
			daoUsuario.addUsuario(restaurante.darUsuario());
			daoRestaurantes.setConn(conn);
			daoRestaurantes.addRestaurante(restaurante);
			
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que actualiza el restaurante que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el restaurante que entra como parametro
	 * @param restaurante - restaurante a actualizar. restaurante != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateRestaurante(Restaurante video) throws Exception {
		DAORestaurante daoRestaurantes = new DAORestaurante();
		DAOUsuario daoUsuario = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoUsuario.setConn(conn);
			daoRestaurantes.updateRestaurante(video);
			daoUsuario.updateUsuario(video.darUsuario());

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el restaurante que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el restaurante que entra como parametro
	 * @param restaurante - restaurante a eliminar. restaurante != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteRestaurante(Restaurante restaurante) throws Exception {
		DAORestaurante daoRestaurantes = new DAORestaurante();
		DAOUsuario daoUsuario = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoUsuario.setConn(conn);
			daoRestaurantes.deleteRestaurante(restaurante);
			daoUsuario.deleteUsuario(restaurante.darUsuario());

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los Usuarios de la base de datos.
	 * @return ListaUsuarios - objeto que modela  un arreglo de Usuarios. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Usuario> darUsuarios() throws Exception {
		List<Usuario> usuarios;
		DAOUsuario daoUsuarios= new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuarios = daoUsuarios.darUsuarios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo Usuario a la base de datos.
	 * <b> post: </b> se ha agregado el Usuario que entra como parametro
	 * @param usuario - el usuario a agregar. usuario != null
	 * @throws Exception - cualquier error que se genere agregando el usuario
	 */
	public void addUsuario(Usuario usuario) throws Exception {
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.addUsuario(usuario);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que actualiza el usuario que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el usuario que entra como parametro
	 * @param ingrediente - usuario a actualizar. usuario != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateUsuario(Usuario usuario) throws Exception {
		DAOUsuario daoUsuarios= new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.updateUsuario(usuario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el usuario que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el usuario que entra como parametro
	 * @param ingrediente - usuario a eliminar. usuario != null
	 * @throws Exception - cualquier error que se genera actualizando los usuarios
	 */
	public void deleteUsuario(Usuario usuario) throws Exception {
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.deleteUsuario(usuario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que retorna todos los Productos de la base de datos.
	 * @return ListaProductos - objeto que modela  un arreglo de Productos. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Producto> darProductos() throws Exception {
		List<Producto> productos;
		DAOProducto daoProductos = new DAOProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.darProductos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}
	
	
	/**
	 * Metodo que modela la transaccion que retorna todos los Productos de la base de datos.
	 * @return ListaProductos - objeto que modela  un arreglo de Productos. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public ListaProductos darProductosDisponibles() throws Exception {
		System.out.println("Entra a TM");
		ListaProductos productos;
		DAOProducto daoProductos = new DAOProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.darProductosDisponibles();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Producto a la base de datos.
	 * <b> post: </b> se ha agregado el Producto que entra como parametro
	 * @param producto - el producto a agregar. producto != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addProducto(Producto producto,RestauranteProducto rp) throws Exception {
		DAOProducto daoProductos = new DAOProducto();
		DAORestauranteProducto daoRP=new DAORestauranteProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoProductos.setConn(conn);			
			daoProductos.addProducto(producto);
			daoRP.setConn(conn);
			daoRP.addRestauranteProducto(rp);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el producto que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el producto que entra como parametro
	 * @param producto - producto a actualizar. producto != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateProducto(Producto video) throws Exception {
		DAOProducto daoProductos = new DAOProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.updateProducto(video);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el producto que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el producto que entra como parametro
	 * @param producto - producto a eliminar. producto != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteProducto(Producto producto) throws Exception {
		DAOProducto daoProductos = new DAOProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.deleteProducto(producto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * Metodo que modela la transaccion que retorna todos los Menus de la base de datos.
	 * @return ListaMenus - objeto que modela  un arreglo de Menus. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Menu> darMenus() throws Exception {
		List<Menu> menus;
		DAOMenu daoMenus = new DAOMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			menus = daoMenus.darMenus();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menus;
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo Menu a la base de datos.
	 * <b> post: </b> se ha agregado el Menu que entra como parametro
	 * @param menu - el menu a agregar. menu != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addMenu(Menu menu) throws Exception {
		DAOMenu daoMenus = new DAOMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			daoMenus.addMenu(menu);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el menu que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el menu que entra como parametro
	 * @param menu - menu a actualizar. menu != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateMenu(Menu video) throws Exception {
		DAOMenu daoMenus = new DAOMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			daoMenus.updateMenu(video);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que elimina el menu que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el menu que entra como parametro
	 * @param menu - menu a eliminar. menu != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteMenu(Menu menu) throws Exception {
		DAOMenu daoMenus = new DAOMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			daoMenus.deleteMenu(menu);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los Zonas de la base de datos.
	 * @return ListaZonas - objeto que modela  un arreglo de Zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Zona> darZonas() throws Exception {
		List<Zona> zonas;
		DAOZona daoZonas = new DAOZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			zonas = daoZonas.darZonas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zonas;
	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Zona a la base de datos.
	 * <b> post: </b> se ha agregado el Zona que entra como parametro
	 * @param zona - el zona a agregar. zona != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addZona(Zona zona) throws Exception {
		DAOZona daoZonas = new DAOZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			daoZonas.addZona(zona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el zona que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el zona que entra como parametro
	 * @param zona - zona a actualizar. zona != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateZona(Zona video) throws Exception {
		DAOZona daoZonas = new DAOZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			daoZonas.updateZona(video);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el zona que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el zona que entra como parametro
	 * @param zona - zona a eliminar. zona != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteZona(Zona zona) throws Exception {
		DAOZona daoZonas = new DAOZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			daoZonas.deleteZona(zona);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que retorna todos los Preferencias de la base de datos.
	 * @return ListaPreferencias - objeto que modela  un arreglo de Preferencias. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Preferencia> darPreferencias() throws Exception {
		List<Preferencia> preferencias;
		DAOPreferencia daoPreferencias = new DAOPreferencia();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			preferencias = daoPreferencias.darPreferencias();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencias;
	}
	/**
	 * Metodo que modela la transaccion que agrega un solo Preferencia a la base de datos.
	 * <b> post: </b> se ha agregado el Preferencia que entra como parametro
	 * @param preferencia - el preferencia a agregar. preferencia != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addPreferencia(Preferencia preferencia) throws Exception {
		DAOPreferencia daoPreferencias = new DAOPreferencia();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			daoPreferencias.addPreferencia(preferencia);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el preferencia que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el preferencia que entra como parametro
	 * @param preferencia - preferencia a actualizar. preferencia != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updatePreferencia(Preferencia video) throws Exception {
		DAOPreferencia daoPreferencias = new DAOPreferencia();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			daoPreferencias.updatePreferencia(video);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el preferencia que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el preferencia que entra como parametro
	 * @param preferencia - preferencia a eliminar. preferencia != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deletePreferencia(Preferencia preferencia) throws Exception {
		DAOPreferencia daoPreferencias = new DAOPreferencia();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			daoPreferencias.deletePreferencia(preferencia);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que retorna todos los Clientes de la base de datos.
	 * @return ListaClientes - objeto que modela  un arreglo de Clientes. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Cliente> darClientes() throws Exception {
		List<Cliente> clientes;
		DAOCliente daoClientes = new DAOCliente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoClientes.setConn(conn);
			clientes = daoClientes.darClientes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}
























	/**
	 * Metodo que modela la transaccion que agrega un solo Cliente a la base de datos.
	 * <b> post: </b> se ha agregado el Cliente que entra como parametro
	 * @param cliente - el Cliente a agregar. ingrediente != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addCliente(Cliente cliente) throws Exception {
		DAOCliente daoClientes = new DAOCliente();
		DAOUsuario daoUsuario = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoClientes.setConn(conn);
			daoUsuario.setConn(conn);
			
			daoUsuario.addUsuario(cliente);
			daoClientes.addCliente(cliente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}





	//	/**
	//	 * Metodo que modela la transaccion que agrega los videos que entran como parametro a la base de datos.
	//	 * <b> post: </b> se han agregado los videos que entran como parametro
	//	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	//	 * @throws Exception - cualquier error que se genera agregando los videos
	//	 */
	//	public void addVideos(List<Video> videos) throws Exception {
	//		DAOTablaVideos daoVideos = new DAOTablaVideos();
	//		try 
	//		{
	//			//////transaccion - ACID Example
	//			this.conn = darConexion();
	//			conn.setAutoCommit(false);
	//			daoVideos.setConn(conn);
	//			Iterator<Video> it = videos.iterator();
	//			while(it.hasNext())
	//			{
	//				daoVideos.addVideo(it.next());
	//			}
	//			
	//			conn.commit();
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			conn.rollback();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			conn.rollback();
	//			throw e;
	//		} finally {
	//			try {
	//				daoVideos.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}







	/**
	 * Metodo que modela la transaccion que actualiza el cliente que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el cliente que entra como parametro
	 * @param cliente - cliente a actualizar. cliente != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateCliente(Cliente cliente) throws Exception {
		DAOCliente daoClientes = new DAOCliente();
		DAOUsuario daoUsuario = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoClientes.setConn(conn);
			daoUsuario.setConn(conn);
			daoClientes.updateCliente(cliente);
			daoUsuario.updateUsuario(cliente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el cliente que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el cliente que entra como parametro
	 * @param cliente - cliente a eliminar. cliente != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteCliente(Cliente cliente) throws Exception {
		DAOCliente daoClientes = new DAOCliente();
		DAOUsuario daoUsuario = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoClientes.setConn(conn);
			daoUsuario.setConn(conn);
			daoClientes.deleteCliente(cliente);
			daoUsuario.deleteUsuario(cliente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que retorna todos los Equivalencias de la base de datos.
	 * @return ListaEquivalencias- objeto que modela  un arreglo de Equivalencias. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Equivalencia> darEquivalencias() throws Exception {
		List<Equivalencia> equivalencias;
		DAOEquivalencia daoEquivalencias= new DAOEquivalencia();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoEquivalencias.setConn(conn);
			equivalencias = daoEquivalencias.darEquivalencias();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return equivalencias;
	}


	/**
	 * Metodo que modela la transaccion que agrega un solo Equivalencia a la base de datos.
	 * <b> post: </b> se ha agregado el Equivalencia que entra como parametro
	 * @param equivalencia - el Equivalencia a agregar. equivalencia != null
	 * @param name 
	 * @throws Exception - cualquier error que se genere agregando el Equivalencia
	 */
	public void addEquivalencia(Equivalencia equivalencia, String name) throws Exception {
		DAOEquivalencia daoEquivalencias = new DAOEquivalencia();
		DAORestaurante daoRestaurantes = new DAORestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoEquivalencias.setConn(conn);
			daoRestaurantes.setConn(conn);
			ArrayList array= daoRestaurantes.buscarRestaurantesPorName(name);
			if((array!=null)&&(!array.isEmpty()))
			{
				daoEquivalencias.addEquivalencia(equivalencia);	
			}
			else
			{
				throw new SQLException("No existe el usuario restaurante");
			}

			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el equivalencia que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el equivalencia que entra como parametro
	 * @param equivalencia - equivalencia a actualizar. equivalencia != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateEquivalencia(Equivalencia equivalencia) throws Exception {
		DAOEquivalencia daoEquivalencias = new DAOEquivalencia();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoEquivalencias.setConn(conn);
			daoEquivalencias.updateEquivalencia(equivalencia);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el Equivalencia que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Equivalencia que entra como parametro
	 * @param Equivalencia - Equivalencia a eliminar. ingrediente != null
	 * @throws Exception - cualquier error que se genera actualizando los equivalencias
	 */
	public void deleteEquivalencia(Equivalencia equivalencia) throws Exception {
		DAOEquivalencia daoEquivalencias = new DAOEquivalencia();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoEquivalencias.setConn(conn);
			daoEquivalencias.deleteEquivalencia(equivalencia);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los Mesas de la base de datos.
	 * @return ListaMesas- objeto que modela  un arreglo de Mesas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Mesa> darMesas() throws Exception {
		List<Mesa> mesas;
		DAOMesa daoMesas= new DAOMesa();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMesas.setConn(conn);
			mesas = daoMesas.darMesas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMesas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return mesas;
	}


	/**
	 * Metodo que modela la transaccion que agrega un solo Mesa a la base de datos.
	 * <b> post: </b> se ha agregado el Mesa que entra como parametro
	 * @param mesa - el Mesa a agregar. mesa != null
	 * @param name 
	 * @throws Exception - cualquier error que se genere agregando el Mesa
	 */
	public void addMesa(Mesa mesa) throws Exception {
		DAOMesa daoMesas = new DAOMesa();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMesas.setConn(conn);
			daoMesas.addMesa(mesa);	
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMesas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el mesa que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el mesa que entra como parametro
	 * @param mesa - mesa a actualizar. mesa != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateMesa(Mesa mesa) throws Exception {
		DAOMesa daoMesas = new DAOMesa();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMesas.setConn(conn);
			daoMesas.updateMesa(mesa);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMesas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el Mesa que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Mesa que entra como parametro
	 * @param Mesa - Mesa a eliminar. ingrediente != null
	 * @throws Exception - cualquier error que se genera actualizando los mesas
	 */
	public void deleteMesa(Mesa mesa) throws Exception {
		DAOMesa daoMesas = new DAOMesa();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMesas.setConn(conn);
			daoMesas.deleteMesa(mesa);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMesas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * Metodo que modela la transaccion que retorna todos los MenuProductos de la base de datos.
	 * @return ListaMenuProductos - objeto que modela  un arreglo de MenuProductos. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<MenuProducto> darMenuProductos() throws Exception {
		List<MenuProducto> menuProductos;
		DAOMenuProducto daoMenuProductos = new DAOMenuProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenuProductos.setConn(conn);
			menuProductos = daoMenuProductos.darMenuProductos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenuProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menuProductos;
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo MenuProducto a la base de datos.
	 * <b> post: </b> se ha agregado el MenuProducto que entra como parametro
	 * @param menuProducto - el menuProducto a agregar. menuProducto != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addMenuProducto(MenuProducto menuProducto) throws Exception {
		DAOMenuProducto daoMenuProductos = new DAOMenuProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenuProductos.setConn(conn);
			daoMenuProductos.addMenuProducto(menuProducto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenuProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Metodo que modela la transaccion que elimina el menuProducto que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el menuProducto que entra como parametro
	 * @param menuProducto - menuProducto a eliminar. menuProducto != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteMenuProducto(MenuProducto menuProducto) throws Exception {
		DAOMenuProducto daoMenuProductos = new DAOMenuProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenuProductos.setConn(conn);
			daoMenuProductos.deleteMenuProducto(menuProducto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenuProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que retorna todos los Servicios de la base de datos.
	 * @return ListaServicios- objeto que modela  un arreglo de Servicios. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Servicio> darServicios() throws Exception {
		List<Servicio> servicios;
		DAOServicio daoServicios= new DAOServicio();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoServicios.setConn(conn);
			servicios = daoServicios.darServicios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoServicios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return servicios;
	}


	/**
	 * Metodo que modela la transaccion que agrega un solo Servicio a la base de datos.
	 * <b> post: </b> se ha agregado el Servicio que entra como parametro
	 * @param servicio - el Servicio a agregar. servicio != null
	 * @param name 
	 * @throws Exception - cualquier error que se genere agregando el Servicio
	 */
	public void addServicio(Servicio servicio) throws Exception {
		DAOServicio daoServicios = new DAOServicio();
		
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoServicios.setConn(conn);
			
			
				daoServicios.addServicio(servicio);	
			

			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoServicios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el servicio que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el servicio que entra como parametro
	 * @param servicio - servicio a actualizar. servicio != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateServicio(Servicio servicio) throws Exception {
		DAOServicio daoServicios = new DAOServicio();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoServicios.setConn(conn);
			daoServicios.updateServicio(servicio);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoServicios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el Servicio que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el Servicio que entra como parametro
	 * @param Servicio - Servicio a eliminar. ingrediente != null
	 * @throws Exception - cualquier error que se genera actualizando los servicios
	 */
	public void deleteServicio(Servicio servicio) throws Exception {
		DAOServicio daoServicios = new DAOServicio();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoServicios.setConn(conn);
			daoServicios.deleteServicio(servicio);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoServicios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Metodo que modela la transaccion que busca el/los productos en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del producto a buscar. name != null
	 * @return ListaProductos - objeto que modela  un arreglo de productos. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Producto> buscarProductosPorName(String name) throws Exception {
		List<Producto> productos;
		DAOProducto daoProductos = new DAOProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.buscarProductosPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}

	/**
	 * Metodo que modela la transaccion que busca el/los menus en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del menu a buscar. name != null
	 * @return ListaMenus - objeto que modela  un arreglo de menus. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Menu> buscarMenusPorName(String name) throws Exception {
		List<Menu> menus;
		DAOMenu daoMenus = new DAOMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			menus = daoMenus.buscarMenusPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menus;
	}

	/**
	 * Metodo que modela la transaccion que busca el/los ingredientes en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del ingrediente a buscar. name != null
	 * @return ListaIngredientes - objeto que modela  un arreglo de ingredientes. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Ingrediente> buscarIngredientesPorName(String name) throws Exception {
		List<Ingrediente> ingredientes;
		DAOIngrediente daoIngredientes = new DAOIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingredientes = daoIngredientes.buscarIngredientesPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredientes;
	}

	/**
	 * Metodo que modela la transaccion que busca el/los restaurantes en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del restaurante a buscar. name != null
	 * @return ListaRestaurantes - objeto que modela  un arreglo de restaurantes. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Restaurante> buscarRestaurantesPorName(String name) throws Exception {
		List<Restaurante> restaurantes;
		DAORestaurante daoRestaurantes = new DAORestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			restaurantes = daoRestaurantes.buscarRestaurantesPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurantes;
	}

	/**
	 * Metodo que modela la transaccion que busca el/los usuarios en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del usuario a buscar. name != null
	 * @return ListaUsuarios - objeto que modela  un arreglo de usuarios. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Usuario> buscarUsuariosPorName(String name) throws Exception {
		List<Usuario> usuarios;
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuarios = daoUsuarios.buscarUsuariosPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}


	/**
	 * Metodo que modela la transaccion que busca el/los zonas en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del zona a buscar. name != null
	 * @return ListaZonas - objeto que modela  un arreglo de zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Zona> buscarZonasPorName(String name) throws Exception {
		List<Zona> zonas;
		DAOZona daoZonas = new DAOZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			zonas = daoZonas.buscarZonasPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zonas;
	}

	/**
	 * Metodo que modela la transaccion que busca el/los clientes en la base de datos con el nombre entra como parametro.
	 * @param pId - Nombre del cliente a buscar. name != null
	 * @return ListaClientes - objeto que modela  un arreglo de clientes. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Cliente> buscarClientesPorName(Long pId) throws Exception {
		List<Cliente> clientes;
		DAOCliente daoClientes = new DAOCliente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoClientes.setConn(conn);
			clientes = daoClientes.buscarClientesPorId(pId);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}


	/**
	 * Metodo que modela la transaccion que actualiza el restaurante que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el restaurante que entra como parametro
	 * @param restaurante - restaurante a actualizar. restaurante != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void surtirRestaurante(String representante) throws Exception {
		DAORestaurante daoRestaurantes = new DAORestaurante();

		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.surtirRestaurante(representante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que retorna todos los Usuarios segun consulta1.
	 * @return ListaUsuarios - objeto que modela  un arreglo de Usuarios. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Usuario> consulta1(Consulta1y2 consulta,Long cantidad) throws Exception {
		List<Usuario> usuarios;
		DAOConsultas daoConsultas= new DAOConsultas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoConsultas.setConn(conn);
			usuarios = daoConsultas.consultar(consulta,cantidad);
			conn.commit();
			
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoConsultas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}
	
	/**
	 * Metodo que modela la transaccion que retorna todos los Usuarios segun consulta1.
	 * @return ListaUsuarios - objeto que modela  un arreglo de Usuarios. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Usuario> consulta4() throws Exception {
		List<Usuario> usuarios;
		DAOConsultas daoConsultas= new DAOConsultas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoConsultas.setConn(conn);
			usuarios = daoConsultas.consultarClientesBuenos();
			conn.commit();
			
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoConsultas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}
	
	/**
	 * Metodo que modela la transaccion que retorna todos los Usuarios segun consulta1.
	 * @return ListaUsuarios - objeto que modela  un arreglo de Usuarios. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<ResultadoConsulta3> consulta3() throws Exception {
		List<ResultadoConsulta3> usuarios;
		DAOConsultas daoConsultas= new DAOConsultas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoConsultas.setConn(conn);
			usuarios = daoConsultas.consultarDias();
			conn.commit();
			
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoConsultas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}
	
	public ListaRentabilidad darRentabilidad(Consulta1y2 cons) throws Exception {
		ListaRentabilidad rentabilidad;
		DAOConsultas daoConsultas= new DAOConsultas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoConsultas.setConn(conn);
			rentabilidad = daoConsultas.darRentabilidad(cons);
			conn.commit();
			
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoConsultas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rentabilidad;
	}
	
	/**
	 * Metodo que modela la transaccion que actualiza el restaurante que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el restaurante que entra como parametro
	 * @param restaurante - restaurante a actualizar. restaurante != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void retirarRestaurante(String representate) throws Exception {
		DAORestaurante daoRestaurantes = new DAORestaurante();
		
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			
			daoRestaurantes.retirarRestaurante(representate);
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
}

