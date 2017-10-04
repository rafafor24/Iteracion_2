package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;
import vos.Ingrediente;

public class DAOCliente {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOCliente
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOCliente() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los clientes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CLIENTES;
	 * @return Arraylist con los clientes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Cliente> darClientes() throws SQLException, Exception {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		String sql = "SELECT * FROM CLIENTES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			String rol = rs.getString("ROL");
			String correoElectronico= rs.getString("CORREOELECTRONICO");
			clientes.add(new Cliente(name, identificacion,rol, correoElectronico));
		}
		return clientes;
	}


//	/**
//	 * Metodo que busca el/los videos con el nombre que entra como parametro.
//	 * @param name - Nombre de el/los videos a buscar
//	 * @return ArrayList con los videos encontrados
//	 * @throws SQLException - Cualquier error que la base de datos arroje.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public ArrayList<Video> buscarVideosPorName(String name) throws SQLException, Exception {
//		ArrayList<Video> videos = new ArrayList<Video>();
//
//		String sql = "SELECT * FROM VIDEO WHERE NAME ='" + name + "'";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			String name2 = rs.getString("NAME");
//			Long id = rs.getLong("ID");
//			Integer duration = rs.getInt("DURATION");
//			videos.add(new Video(id, name2, duration));
//		}
//
//		return videos;
//	}
//	
//	/**
//	 * Metodo que busca el video con el id que entra como parametro.
//	 * @param name - Id de el video a buscar
//	 * @return Video encontrado
//	 * @throws SQLException - Cualquier error que la base de datos arroje.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public Video buscarVideoPorId(Long id) throws SQLException, Exception 
//	{
//		Video video = null;
//
//		String sql = "SELECT * FROM VIDEO WHERE ID =" + id;
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		if(rs.next()) {
//			String name = rs.getString("NAME");
//			Long id2 = rs.getLong("ID");
//			Integer duration = rs.getInt("DURATION");
//			video = new Video(id2, name, duration);
//		}
//
//		return video;
//	}

	/**
	 * Metodo que agrega el cliente que entra como parametro a la base de datos.
	 * @param cliente - el cliente a agregar. cliente !=  null
	 * <b> post: </b> se ha agregado el cliente a la base de datos en la transaction actual. pendiente que el cliente master
	 * haga commit para que el cliente baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el cliente a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCliente(Cliente cliente) throws SQLException, Exception {

		String sql = "INSERT INTO CLIENTES VALUES (";
		sql += "'"+cliente.getNombre() + "',";
		sql += cliente.getIdentificacion() + ",";
		sql += "'"+cliente.getRol() + "',";
		sql += "'"+cliente.getCorreoElectronico()+"'" + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el cliente que entra como parametro en la base de datos.
	 * @param cliente - el cliente a actualizar. cliente !=  null
	 * <b> post: </b> se ha actualizado el cliente en la base de datos en la transaction actual. pendiente que el cliente master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el cliente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateCliente(Cliente cliente) throws SQLException, Exception {

		String sql = "UPDATE CLIENTES SET ";
		sql += "IDENTIFICACION=" + cliente.getIdentificacion()+",";
		sql += "ROL='" + cliente.getRol()+"',";
		sql += "CORREOELECTRONICO='" + cliente.getCorreoElectronico()+"'";
		sql += " WHERE NOMBRE='" + cliente.getNombre()+"';";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el cliente que entra como parametro en la base de datos.
	 * @param cliente - el cliente a borrar. cliente !=  null
	 * <b> post: </b> se ha borrado el cliente en la base de datos en la transaction actual. pendiente que el cliente master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el cliente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteCliente(Cliente cliente) throws SQLException, Exception {

		String sql = "DELETE FROM CLIENTES";
		sql += " WHERE NOMBRE ='" + cliente.getNombre()+"';";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
