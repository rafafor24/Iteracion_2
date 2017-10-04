package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import dao.DAOUsuario;
import dao.DAOIngrediente;
import dao.DAOMenu;
import dao.DAOProducto;
import dao.DAORestaurante;
import vos.Usuario;
import vos.Ingrediente;
import vos.Menu;
import vos.Producto;
import vos.Restaurante;

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
	 * Metodo que modela la transaccion que agrega un solo Restaurante a la base de datos.
	 * <b> post: </b> se ha agregado el Restaurante que entra como parametro
	 * @param restaurante - el Restaurante a agregar. ingrediente != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addRestaurante(Restaurante restaurante) throws Exception {
		DAORestaurante daoRestaurantes = new DAORestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
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
	 * Metodo que modela la transaccion que agrega un solo Producto a la base de datos.
	 * <b> post: </b> se ha agregado el Producto que entra como parametro
	 * @param producto - el producto a agregar. producto != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addProducto(Producto producto) throws Exception {
		DAOProducto daoProductos = new DAOProducto();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.addProducto(producto);
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
	 * Metodo que modela la transaccion que actualiza el restaurante que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el restaurante que entra como parametro
	 * @param restaurante - restaurante a actualizar. restaurante != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateRestaurante(Restaurante video) throws Exception {
		DAORestaurante daoRestaurantes = new DAORestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.updateRestaurante(video);

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
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.deleteRestaurante(restaurante);

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

}

