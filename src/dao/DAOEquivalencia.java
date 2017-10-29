package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Menu;
import vos.Equivalencia;

public class DAOEquivalencia {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOEquivalencia
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOEquivalencia() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los equivalencias de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM EQUIVALENCIAS;
	 * @return Arraylist con los equivalencias de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Equivalencia> darEquivalencias() throws SQLException, Exception {
		ArrayList<Equivalencia> equivalencias = new ArrayList<Equivalencia>();

		String sql = "SELECT * FROM EQUIVALENCIAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			long id1= rs.getLong("ID1");
			long id2= rs.getLong("ID2");
			String tipo= rs.getString("TIPO");
			equivalencias.add(new Equivalencia(id1,id2,tipo));
		}
		return equivalencias;
	}

	/**
	 * Metodo que agrega el equivalencia que entra como parametro a la base de datos.
	 * @param menu - el equivalencia a agregar. equivalencia !=  null
	 * <b> post: </b> se ha agregado el equivalencia a la base de datos en la transaction actual. pendiente que el equivalencia master
	 * haga commit para que el equivalencia baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el equivalencia a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addEquivalencia(Equivalencia equivalencia) throws SQLException, Exception {

		String sql = "INSERT INTO EQUIVALENCIAS VALUES (";
		sql += equivalencia.getId1() + ",";
		sql += equivalencia.getId2() + ",";
		sql += "'"+equivalencia.getTipo() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el equivalencia que entra como parametro en la base de datos.
	 * @param menu - el equivalencia a actualizar. menu !=  null
	 * <b> post: </b> se ha actualizado el equivalencia en la base de datos en la transaction actual. pendiente que el equivalencia master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el equivalencia.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateEquivalencia(Equivalencia equivalencia) throws SQLException, Exception {

		String sql = "UPDATE EQUIVALENCIAS SET ";
		sql += equivalencia.getId1() + ",";
		sql += equivalencia.getId2() + ",";
		sql += "'"+equivalencia.getTipo() + "' ";
		sql += " WHERE ID1=" + equivalencia.getId1();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el equivalencia que entra como parametro en la base de datos.
	 * @param menu - el equivalencia a borrar. equivalencia!=  null
	 * <b> post: </b> se ha borrado el equivalencia en la base de datos en la transaction actual. pendiente que el equivalencia master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el equivalencia.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteEquivalencia(Equivalencia equivalencia) throws SQLException, Exception {

		String sql = "DELETE FROM EQUIVALENCIAS";
		sql += " WHERE ID1 =" + equivalencia.getId1();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
