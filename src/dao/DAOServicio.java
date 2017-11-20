package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Menu;
import vos.Servicio;

public class DAOServicio {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOServicio
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOServicio() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los servicios de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM SERVICIOS;
	 * @return Arraylist con los servicios de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Servicio> darServicios() throws SQLException, Exception {
		ArrayList<Servicio> servicios = new ArrayList<Servicio>();

		String sql = "SELECT * FROM SERVICIOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id_pedido= rs.getLong("id_pedido");
			String nombre_usuario= rs.getString("nombre_usuario");
			String representante_restaurante= rs.getString("representante_restaurante");
			servicios.add(new Servicio(id_pedido, nombre_usuario, representante_restaurante));
		}
		return servicios;
	}

	/**
	 * Metodo que agrega el servicio que entra como parametro a la base de datos.
	 * @param menu - el servicio a agregar. servicio !=  null
	 * <b> post: </b> se ha agregado el servicio a la base de datos en la transaction actual. pendiente que el servicio master
	 * haga commit para que el servicio baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el servicio a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addServicio(Servicio servicio) throws SQLException, Exception {

		String sql = "INSERT INTO SERVICIOS VALUES (";
		sql += servicio.getId_pedido() + ",";
		sql += "'"+servicio.getNombre_usuario() + "',";
		sql += "'"+servicio.getRepresentante_restaurante() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el servicio que entra como parametro en la base de datos.
	 * @param menu - el servicio a actualizar. menu !=  null
	 * <b> post: </b> se ha actualizado el servicio en la base de datos en la transaction actual. pendiente que el servicio master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el servicio.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateServicio(Servicio servicio) throws SQLException, Exception {

		String sql = "UPDATE SERVICIOS SET ";
		sql += "ID_PEDIDO="+servicio.getId_pedido() + ",";
		sql += "NOMBRE_USUARIO='"+servicio.getNombre_usuario() + "',";
		sql += "REPRESENTANTE_RESTAURANTE='"+servicio.getRepresentante_restaurante() + "' ";
		sql += " WHERE ID_PEDIDO=" + servicio.getNombre_usuario();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el servicio que entra como parametro en la base de datos.
	 * @param menu - el servicio a borrar. servicio!=  null
	 * <b> post: </b> se ha borrado el servicio en la base de datos en la transaction actual. pendiente que el servicio master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el servicio.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteServicio(Servicio servicio) throws SQLException, Exception {

		String sql = "DELETE FROM SERVICIOS";
		sql += " WHERE ID_PEDIDO =" + servicio.getId_pedido();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
