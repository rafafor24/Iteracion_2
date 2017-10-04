package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import dao.DAOCliente;
import dao.DAOIngrediente;
import dao.DAORestaurante;
import vos.Cliente;
import vos.Ingrediente;
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
	 * Metodo que modela la transaccion que retorna todos los Clientes de la base de datos.
	 * @return ListaClientes - objeto que modela  un arreglo de Clientes. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Cliente> darClientes() throws Exception {
		List<Cliente> clientes;
		DAOCliente daoClientes= new DAOCliente();
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

//	/**
//	 * Metodo que modela la transaccion que busca el/los videos en la base de datos con el nombre entra como parametro.
//	 * @param name - Nombre del video a buscar. name != null
//	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
//	 * @throws Exception -  cualquier error que se genere durante la transaccion
//	 */
//	public List<Video> buscarVideosPorName(String name) throws Exception {
//		List<Video> videos;
//		DAOTablaVideos daoVideos = new DAOTablaVideos();
//		try 
//		{
//			//////transaccion
//			this.conn = darConexion();
//			daoVideos.setConn(conn);
//			videos = daoVideos.buscarVideosPorName(name);
//
//		} catch (SQLException e) {
//			System.err.println("SQLException:" + e.getMessage());
//			e.printStackTrace();
//			throw e;
//		} catch (Exception e) {
//			System.err.println("GeneralException:" + e.getMessage());
//			e.printStackTrace();
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
//		return videos;
//	}
//	
//	/**
//	 * Metodo que modela la transaccion que busca el video en la base de datos con el id que entra como parametro.
//	 * @param name - Id del video a buscar. name != null
//	 * @return Video - Resultado de la busqueda
//	 * @throws Exception -  cualquier error que se genere durante la transaccion
//	 */
//	public Video buscarVideoPorId(Long id) throws Exception {
//		Video video;
//		DAOTablaVideos daoVideos = new DAOTablaVideos();
//		try 
//		{
//			//////transaccion
//			this.conn = darConexion();
//			daoVideos.setConn(conn);
//			video = daoVideos.buscarVideoPorId(id);
//
//		} catch (SQLException e) {
//			System.err.println("SQLException:" + e.getMessage());
//			e.printStackTrace();
//			throw e;
//		} catch (Exception e) {
//			System.err.println("GeneralException:" + e.getMessage());
//			e.printStackTrace();
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
//		return video;
//	}
	
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
	 * Metodo que modela la transaccion que agrega un solo Cliente a la base de datos.
	 * <b> post: </b> se ha agregado el Cliente que entra como parametro
	 * @param cliente - el cliente a agregar. cliente != null
	 * @throws Exception - cualquier error que se genere agregando el cliente
	 */
	public void addCliente(Cliente cliente) throws Exception {
		DAOCliente daoClientes = new DAOCliente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoClientes.setConn(conn);
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
	 * Metodo que modela la transaccion que actualiza el cliente que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado el cliente que entra como parametro
	 * @param ingrediente - cliente a actualizar. cliente != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateCliente(Cliente cliente) throws Exception {
		DAOCliente daoClientes= new DAOCliente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoClientes.setConn(conn);
			daoClientes.updateCliente(cliente);

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
	 * Metodo que modela la transaccion que elimina el cliente que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado el cliente que entra como parametro
	 * @param ingrediente - cliente a eliminar. cliente != null
	 * @throws Exception - cualquier error que se genera actualizando los clientes
	 */
	public void deleteCliente(Cliente cliente) throws Exception {
		DAOCliente daoClientes = new DAOCliente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoClientes.setConn(conn);
			daoClientes.deleteCliente(cliente);

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


}
