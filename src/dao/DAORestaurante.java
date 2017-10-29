package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Restaurante;


public class DAORestaurante {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAORestaurante
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAORestaurante() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los restaurantes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM RESTAURANTES;
	 * @return Arraylist con los restaurantes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Restaurante> darRestaurantes() throws SQLException, Exception {
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTES INNER JOIN USUARIOS ON RESTAURANTES.NOMBRE_USUARIO=USUARIOS.NOMBRE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String representante = rs.getString("REPRESENTANTE");
			String tipo_comida = rs.getString("TIPO_COMIDA");
			String pagina_web = rs.getString("PAGINA_WEB");
			String nombre_usuario = rs.getString("NOMBRE_USUARIO");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			String correo_electronico= rs.getString("CORREO_ELECTRONICO");
			restaurantes.add(new Restaurante(nombre_usuario,identificacion,correo_electronico,representante,tipo_comida,pagina_web));
		}
		return restaurantes;
	}


	/**
	 * Metodo que busca el/los restaurantes con el nombre que entra como parametro.
	 * @param name - Nombre de el/los restaurantes a buscar
	 * @return ArrayList con los restaurantes encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Restaurante> buscarRestaurantesPorName(String name) throws SQLException, Exception {
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTES INNER JOIN USUARIOS ON RESTAURANTES.NOMBRE_USUARIO=USUARIOS.NOMBRE WHERE REPRESENTANTE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String representante = rs.getString("REPRESENTANTE");
			String tipo_comida = rs.getString("TIPO_COMIDA");
			String pagina_web = rs.getString("PAGINA_WEB");
			String nombre_usuario = rs.getString("NOMBRE_USUARIO");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			String correo_electronico= rs.getString("CORREO_ELECTRONICO");
			restaurantes.add(new Restaurante(nombre_usuario,identificacion,correo_electronico,representante,tipo_comida,pagina_web));
		}

		return restaurantes;
	}
	


	/**
	 * Metodo que agrega el restaurante que entra como parametro a la base de datos.
	 * @param restaurante - el restaurante a agregar. ingrediente !=  null
	 * <b> post: </b> se ha agregado el ingrediente a la base de datos en la transaction actual. pendiente que el ingrediente master
	 * haga commit para que el ingrediente baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "INSERT INTO RESTAURANTES VALUES (";
		sql += "'"+restaurante.getRepresentante() + "',";
		sql += "'"+restaurante.getTipo_comida() + "',";
		sql += "'"+restaurante.getPagina_web()+"',";
		sql += "'"+restaurante.getNombre()+"')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
//		String sql2 = "INSERT INTO USUARIOS VALUES (";
//		sql2 += "'"+restaurante.getNombre() + "',";
//		sql2 += restaurante.getIdentificacion() + ",";
//		sql2 += "'"+restaurante.getRol() + "',";
//		sql2 += "'"+restaurante.getCorreo_electronico()+"'" + ")";
//
//		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
//		recursos.add(prepStmt2);
//		prepStmt2.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el restaurante que entra como parametro en la base de datos.
	 * @param restaurante - el restaurante a actualizar. restaurante !=  null
	 * <b> post: </b> se ha actualizado el restaurante en la base de datos en la transaction actual. pendiente que el restaurante master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "UPDATE RESTAURANTES SET ";
		sql += "TIPO_COMIDA='"+restaurante.getTipo_comida() + "',";
		sql += "PAGINA_WEB='"+restaurante.getPagina_web()+"',";
		sql += "NOMBRE_USUARIO='"+restaurante.getNombre()+"'";
		sql += " WHERE REPRESENTANTE ='" + restaurante.getRepresentante()+"'";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el restaurante que entra como parametro en la base de datos.
	 * @param restaurante - el restaurante a borrar. restaurante !=  null
	 * <b> post: </b> se ha borrado el restaurante en la base de datos en la transaction actual. pendiente que el restaurante master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "DELETE FROM RESTAURANTES";
		sql += "  WHERE REPRESENTANTE ='" + restaurante.getRepresentante()+"'";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que busca el/los restaurantes con el nombre que entra como parametro.
	 * @param name - Nombre de el/los restaurantes a buscar
	 * @return ArrayList con los restaurantes encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void surtirRestaurante(String representante) throws SQLException, Exception {

		String sql = "UPDATE RESTAURANTE_PRODUCTO SET CANTIDAD_ACTUAL=CANTIDAD_MAXIMA WHERE REPRESENTANTE = ";
		sql +="'"+representante+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
}
