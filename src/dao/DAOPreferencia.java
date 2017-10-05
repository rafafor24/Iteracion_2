package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingrediente;
import vos.Preferencia;

public class DAOPreferencia {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOPreferencia
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOPreferencia() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos las Preferencias de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM PREFERENCIAS;
	 * @return Arraylist con las preferencias de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Preferencia> darPreferencias() throws SQLException, Exception {
		ArrayList<Preferencia> preferencias = new ArrayList<Preferencia>();

		String sql = "SELECT * FROM PREFERENCIAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("ID");
			int precio = rs.getInt("PRECIO");
			String zona = rs.getString("ZONA");
			Integer tipoComida= rs.getInt("TIPO_COMIDA");
			preferencias.add(new Preferencia(id, precio, zona, tipoComida));
		}
		return preferencias;
	}


	/**
	 * Metodo que agrega la preferencia que entra como parametro a la base de datos.
	 * @param ingrediente - el preferencia a agregar. preferencia !=  null
	 * <b> post: </b> se ha agregado el preferencia a la base de datos en la transaction actual. pendiente que el Preferencia master
	 * haga commit para que el preferencia baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la preferencia a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPreferencia(Preferencia preferencia) throws SQLException, Exception {

		String sql = "INSERT INTO PREFERENCIAS VALUES (";
		sql += preferencia.getId()+ ",";
		sql += preferencia.getPrecio() + ",";
		sql += "'"+preferencia.getZona()+"',";
		sql += preferencia.getTipo_comida()+")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza la preferencia que entra como parametro en la base de datos.
	 * @param preferencia - el preferencia a actualizar. preferencia !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el preferencia  master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updatePreferencia(Preferencia preferencia) throws SQLException, Exception {

		String sql = "UPDATE PREFERENCIAS SET ";
		sql += "PRECIO=" + preferencia.getPrecio()+",";
		sql += "ZONA='" + preferencia.getZona()+"'";
		sql += "TIPO_COMIDA="+preferencia.getTipo_comida();
		sql += " WHERE ID='" + preferencia.getId()+"'";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina la preferencia que entra como parametro en la base de datos.
	 * @param preferencia - el preferencia a borrar. preferencia !=  null
	 * <b> post: </b> se ha borrado la preferencia en la base de datos en la transaction actual. pendiente que el preferencia master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deletePreferencia(Preferencia preferencia) throws SQLException, Exception {

		String sql = "DELETE FROM PREFERENCIAS";
		sql += " WHERE ID ='" + preferencia.getId()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
