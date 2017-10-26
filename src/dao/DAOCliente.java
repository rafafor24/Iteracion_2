package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;


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
			Long id = rs.getLong("ID");
			Long id_usuario = rs.getLong("ID_USUARIO");
			clientes.add(new Cliente(id,id_usuario));
		}
		return clientes;
	}


	/**
	 * Metodo que busca el/los clientes con el nombre que entra como parametro.
	 * @param pId - Nombre de el/los clientes a buscar
	 * @return ArrayList con los clientes encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Cliente> buscarClientesPorId(Long pId) throws SQLException, Exception {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		String sql = "SELECT * FROM CLIENTES WHERE ID =" + pId;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			Long id_usuario = rs.getLong("ID_USUARIO");
			clientes.add(new Cliente(id,id_usuario));
		}

		return clientes;
	}
	


	/**
	 * Metodo que agrega el cliente que entra como parametro a la base de datos.
	 * @param cliente - el cliente a agregar. ingrediente !=  null
	 * <b> post: </b> se ha agregado el ingrediente a la base de datos en la transaction actual. pendiente que el ingrediente master
	 * haga commit para que el ingrediente baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCliente(Cliente cliente) throws SQLException, Exception {

		String sql = "INSERT INTO CLIENTES VALUES (";
		sql += cliente.getId() +",";
		sql += cliente.getId_usuario() + ")";

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
		sql += "ID_USUARIO="+cliente.getId_usuario();
		sql += " WHERE ID ='" + cliente.getId()+"'";


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
		sql += "  WHERE ID ='" + cliente.getId()+"'";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
