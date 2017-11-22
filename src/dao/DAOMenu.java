package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Menu;

public class DAOMenu {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOMenu
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOMenu() {
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
	 * <b>SQL Statement:</b> SELECT * FROM MENUS;
	 * @return Arraylist con los menus de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Menu> darMenus() throws SQLException, Exception {
		ArrayList<Menu> menus = new ArrayList<Menu>();

		String sql = "SELECT * FROM MENUS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			Integer costo_produccion = rs.getInt("COSTO_PRODUCCION");
			Integer precio_venta = rs.getInt("PRECIO_VENTA");
			
			menus.add(new Menu(name,costo_produccion,precio_venta));
		}
		return menus;
	}


	/**
	 * Metodo que busca el/los menus con el nombre que entra como parametro.
	 * @param name - Nombre de el/los menus a buscar
	 * @return ArrayList con los menus encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Menu> buscarMenusPorName(String name) throws SQLException, Exception {
		ArrayList<Menu> menus = new ArrayList<Menu>();

		String sql = "SELECT * FROM MENUS WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Integer costo_produccion = rs.getInt("COSTO_PRODUCCION");
			Integer precio_venta = rs.getInt("PRECIO_VENTA");
			
			menus.add(new Menu(nombre,costo_produccion,precio_venta));
		}

		return menus;
	}
	



	/**
	 * Metodo que agrega el menu que entra como parametro a la base de datos.
	 * @param menu - el menu a agregar. menu !=  null
	 * <b> post: </b> se ha agregado el menu a la base de datos en la transaction actual. pendiente que el menu master
	 * haga commit para que el menu baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addMenu(Menu menu) throws SQLException, Exception {

		String sql = "INSERT INTO MENUS VALUES (";
		sql += "'"+menu.getNombre() + "',";
		sql += menu.getCosto_produccion()+",";
		sql += menu.getPrecio_venta()+")";
		
				

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el menu que entra como parametro en la base de datos.
	 * @param menu - el menu a actualizar. menu !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateMenu(Menu menu) throws SQLException, Exception {

		String sql = "UPDATE MENUS SET ";
		sql += "COSTO_PRODUCCION="+menu.getCosto_produccion()+",";
		sql += "PRECIO_VENTA="+menu.getPrecio_venta()+",";
		sql += " WHERE NOMBRE='" + menu.getNombre()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el menu que entra como parametro en la base de datos.
	 * @param menu - el menu a borrar. menu !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteMenu(Menu menu) throws SQLException, Exception {

		String sql = "DELETE FROM MENUS";
		sql += " WHERE NOMBRE ='" + menu.getNombre()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}


