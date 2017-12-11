package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.RestauranteProducto;

public class DAORestauranteProducto {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAORestauranteProducto
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAORestauranteProducto() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM RESTAURANTE_PRODUCTO;
	 * @return Arraylist con los restauranteProductos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<RestauranteProducto> darRestauranteProductos() throws SQLException, Exception {
		ArrayList<RestauranteProducto> restauranteProductos = new ArrayList<RestauranteProducto>();

		String sql = "SELECT * FROM RESTAURANTE_PRODUCTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String representante_restaurante = rs.getString("REPRESENTANTE_RESTAURANTE");
			String nombre_producto = rs.getString("NOMBRE_PRODUCTO");
			Integer cantidad_maxima = rs.getInt("CANTIDAD_MAXIMA");
			Integer cantidad_actual = rs.getInt("CANTIDAD_ACTUAL");
			restauranteProductos.add(new RestauranteProducto(representante_restaurante,nombre_producto,cantidad_maxima,cantidad_actual));
		}
		return restauranteProductos;
	}


	/**
	 * Metodo que busca el/los restauranteProductos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los restauranteProductos a buscar
	 * @return ArrayList con los restauranteProductos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<RestauranteProducto> buscarRestauranteProductosPorName(String name) throws SQLException, Exception {
		ArrayList<RestauranteProducto> restauranteProductos = new ArrayList<RestauranteProducto>();

		String sql = "SELECT * FROM RESTAURANTE_PRODUCTO WHERE NOMBRE_PRODUCTO ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String representante_restaurante = rs.getString("REPRESENTANTE_RESTAURANTE");
			String nombre_producto = rs.getString("NOMBRE_PRODUCTO");
			Integer cantidad_maxima = rs.getInt("CANTIDAD_MAXIMA");
			Integer cantidad_actual = rs.getInt("CANTIDAD_ACTUAL");
			restauranteProductos.add(new RestauranteProducto(representante_restaurante,nombre_producto,cantidad_maxima,cantidad_actual));
		}

		return restauranteProductos;
	}


	/**
	 * Metodo que agrega el restauranteProducto que entra como parametro a la base de datos.
	 * @param restauranteProducto - el restauranteProducto a agregar. restauranteProducto !=  null
	 * <b> post: </b> se ha agregado el restauranteProducto a la base de datos en la transaction actual. pendiente que el restauranteProducto master
	 * haga commit para que el restauranteProducto baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addRestauranteProducto(RestauranteProducto restauranteProducto) throws SQLException, Exception {

		String sql = "INSERT INTO RESTAURANTE_PRODUCTO VALUES (";
		sql += "'"+restauranteProducto.getRepresentante_restaurante()+ "',";
		sql += "'"+restauranteProducto.getNombre_producto()+ "',";
		sql += ""+restauranteProducto.getCantidad_maxima()+",";
		sql += ""+restauranteProducto.getCantidad_actual()+")";

		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el restauranteProducto que entra como parametro en la base de datos.
	 * @param restauranteProducto - el restauranteProducto a actualizar. restauranteProducto !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateRestauranteProducto(RestauranteProducto restauranteProducto) throws SQLException, Exception {

		String sql = "UPDATE RESTAURANTE_PRODUCTO SET ";
		sql += "REPRESENTANTE_RESTAURANTE='" + restauranteProducto.getRepresentante_restaurante()+"',";
		sql += "NOMBRE_PRODUCTO='" + restauranteProducto.getNombre_producto()+"',";
		sql += "CANTIDAD_MAXIMA=" + restauranteProducto.getCantidad_maxima()+",";
		sql += "CANTIDAD_ACTUAL=" + restauranteProducto.getCantidad_actual()+",";
		sql += " WHERE REPRESENTANTE_RESTAURANTE='" + restauranteProducto.getRepresentante_restaurante()+"'";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el restauranteProducto que entra como parametro en la base de datos.
	 * @param restauranteProducto - el restauranteProducto a borrar. restauranteProducto !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteRestauranteProducto(RestauranteProducto restauranteProducto) throws SQLException, Exception {

		String sql = "DELETE FROM RESTAURANTE_PRODUCTO";
		sql += " WHERE RESTAURANTE_PRODUCTO ='" + restauranteProducto.getRepresentante_restaurante()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
