package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ProductoPedido;

public class DAOProductoPedido {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOProductoPedido
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOProductoPedido() {
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
	 * <b>SQL Statement:</b> SELECT * FROM PRODUCTO_PEDIDO;
	 * @return Arraylist con los productoPedidos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ProductoPedido> darProductoPedidos() throws SQLException, Exception {
		ArrayList<ProductoPedido> productoPedidos = new ArrayList<ProductoPedido>();

		String sql = "SELECT * FROM PRODUCTO_PEDIDO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre_producto = rs.getString("NOMBRE_PRODUCTO");
			Long id_pedido = rs.getLong("ID_PEDIDO");
			
			productoPedidos.add(new ProductoPedido(nombre_producto,id_pedido));
		}
		return productoPedidos;
	}


	/**
	 * Metodo que agrega el productoPedido que entra como parametro a la base de datos.
	 * @param productoPedido - el productoPedido a agregar. productoPedido !=  null
	 * <b> post: </b> se ha agregado el productoPedido a la base de datos en la transaction actual. pendiente que el productoPedido master
	 * haga commit para que el productoPedido baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProductoPedido(ProductoPedido productoPedido) throws SQLException, Exception {

		String sql = "INSERT INTO PRODUCTO_PEDIDO VALUES (";
		sql += "'"+productoPedido.getNombre_producto()+ "',";
		sql += productoPedido.getId_pedido()+")";
		

		System.out.println("RPRPRPRPRPRPRPRPRPRPRPR:"+sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	
	/**
	 * Metodo que elimina el productoPedido que entra como parametro en la base de datos.
	 * @param productoPedido - el productoPedido a borrar. productoPedido !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteProductoPedido(ProductoPedido productoPedido) throws SQLException, Exception {

		String sql = "DELETE FROM PRODUCTO_PEDIDO";
		sql += " WHERE NOMBRE_PRODUCTO ='" + productoPedido.getNombre_producto()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
