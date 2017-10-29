package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Menu;
import vos.Mesa;

public class DAOMesa {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOMesa
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOMesa() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los mesas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM MESAS;
	 * @return Arraylist con los mesas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Mesa> darMesas() throws SQLException, Exception {
		ArrayList<Mesa> mesas = new ArrayList<Mesa>();

		String sql = "SELECT * FROM MESAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			long id= rs.getLong("ID");
			long id_cliente= rs.getLong("ID_CLIENTE");
			mesas.add(new Mesa(id,id_cliente));
		}
		return mesas;
	}

	/**
	 * Metodo que agrega el mesa que entra como parametro a la base de datos.
	 * @param menu - el mesa a agregar. mesa !=  null
	 * <b> post: </b> se ha agregado el mesa a la base de datos en la transaction actual. pendiente que el mesa master
	 * haga commit para que el mesa baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el mesa a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addMesa(Mesa mesa) throws SQLException, Exception {

		String sql = "INSERT INTO MESAS VALUES (";
		sql += mesa.getId() + ",";
		sql += mesa.getIdcliente()+ ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el mesa que entra como parametro en la base de datos.
	 * @param menu - el mesa a actualizar. menu !=  null
	 * <b> post: </b> se ha actualizado el mesa en la base de datos en la transaction actual. pendiente que el mesa master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el mesa.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateMesa(Mesa mesa) throws SQLException, Exception {

		String sql = "UPDATE MESAS SET ";
		sql += "ID="+ mesa.getId()+",";
		sql += "ID_CLIENTE="+ mesa.getIdcliente();
		sql += " WHERE ID='" + mesa.getId()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el mesa que entra como parametro en la base de datos.
	 * @param menu - el mesa a borrar. mesa!=  null
	 * <b> post: </b> se ha borrado el mesa en la base de datos en la transaction actual. pendiente que el mesa master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el mesa.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteMesa(Mesa mesa) throws SQLException, Exception {

		String sql = "DELETE FROM MESAS";
		sql += " WHERE ID ='" + mesa.getId()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
