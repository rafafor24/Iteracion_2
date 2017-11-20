package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.MenuProducto;

public class DAOMenuProducto {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOMenuProducto
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOMenuProducto() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los menuProductos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM MENU_PRODUCTO;
	 * @return Arraylist con los menuProductos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<MenuProducto> darMenuProductos() throws SQLException, Exception {
		ArrayList<MenuProducto> menuProductos = new ArrayList<MenuProducto>();

		String sql = "SELECT * FROM MENU_PRODUCTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre_menu= rs.getString("nombre_producto");
			String nombre_producto= rs.getString("nombre_producto");
			menuProductos.add(new MenuProducto(nombre_menu,nombre_producto));
		}
		return menuProductos;
	}

	/**
	 * Metodo que agrega el menuProducto que entra como parametro a la base de datos.
	 * @param menu - el menuProducto a agregar. menuProducto !=  null
	 * <b> post: </b> se ha agregado el menuProducto a la base de datos en la transaction actual. pendiente que el menuProducto master
	 * haga commit para que el menuProducto baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el menuProducto a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addMenuProducto(MenuProducto menuProducto) throws SQLException, Exception {

		String sql = "INSERT INTO MENU_PRODUCTO VALUES ('";
		sql += menuProducto.getNombre_menu() + "','";
		sql += menuProducto.getNombre_producto()+ "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el menuProducto que entra como parametro en la base de datos.
	 * @param menu - el menuProducto a actualizar. menu !=  null
	 * <b> post: </b> se ha actualizado el menuProducto en la base de datos en la transaction actual. pendiente que el menuProducto master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el menuProducto.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateMenuProducto(MenuProducto menuProducto) throws SQLException, Exception {

		String sql = "UPDATE MENU_PRODUCTO SET ";
		sql += "NOMBRE_MENU='"+ menuProducto.getNombre_menu()+"',";
		sql += "NOMBRE_PRODUCTO='"+ menuProducto.getNombre_producto();
		sql += "' WHERE NOMBRE_MENU='" + menuProducto.getNombre_menu()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el menuProducto que entra como parametro en la base de datos.
	 * @param menu - el menuProducto a borrar. menuProducto!=  null
	 * <b> post: </b> se ha borrado el menuProducto en la base de datos en la transaction actual. pendiente que el menuProducto master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el menuProducto.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteMenuProducto(MenuProducto menuProducto) throws SQLException, Exception {

		String sql = "DELETE FROM MENU_PRODUCTO";
		sql += " WHERE NOMBRE_MENU ='" + menuProducto.getNombre_menu()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
