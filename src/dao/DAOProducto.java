package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Producto;

public class DAOProducto {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOProducto
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOProducto() {
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
	 * <b>SQL Statement:</b> SELECT * FROM PRODUCTOS;
	 * @return Arraylist con los productos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> darProductos() throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			Integer tiempo_preparacion = rs.getInt("TIEMPO_PREPARACION");
			Integer costo_produccion = rs.getInt("COSTO_PRODUCCION");
			Integer precio_venta = rs.getInt("PRECIO_VENTA");
			Long tipo = rs.getLong("TIPO");
			productos.add(new Producto(name, descripcion,traduccion,tiempo_preparacion,costo_produccion,precio_venta,tipo));
		}
		return productos;
	}


//	/**
//	 * Metodo que busca el/los videos con el nombre que entra como parametro.
//	 * @param name - Nombre de el/los videos a buscar
//	 * @return ArrayList con los videos encontrados
//	 * @throws SQLException - Cualquier error que la base de datos arroje.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public ArrayList<Video> buscarVideosPorName(String name) throws SQLException, Exception {
//		ArrayList<Video> videos = new ArrayList<Video>();
//
//		String sql = "SELECT * FROM VIDEO WHERE NAME ='" + name + "'";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			String name2 = rs.getString("NAME");
//			Long id = rs.getLong("ID");
//			Integer duration = rs.getInt("DURATION");
//			videos.add(new Video(id, name2, duration));
//		}
//
//		return videos;
//	}
//	
//	/**
//	 * Metodo que busca el video con el id que entra como parametro.
//	 * @param name - Id de el video a buscar
//	 * @return Video encontrado
//	 * @throws SQLException - Cualquier error que la base de datos arroje.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public Video buscarVideoPorId(Long id) throws SQLException, Exception 
//	{
//		Video video = null;
//
//		String sql = "SELECT * FROM VIDEO WHERE ID =" + id;
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		if(rs.next()) {
//			String name = rs.getString("NAME");
//			Long id2 = rs.getLong("ID");
//			Integer duration = rs.getInt("DURATION");
//			video = new Video(id2, name, duration);
//		}
//
//		return video;
//	}

	/**
	 * Metodo que agrega el producto que entra como parametro a la base de datos.
	 * @param producto - el producto a agregar. producto !=  null
	 * <b> post: </b> se ha agregado el producto a la base de datos en la transaction actual. pendiente que el producto master
	 * haga commit para que el producto baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProducto(Producto producto) throws SQLException, Exception {

		String sql = "INSERT INTO PRODUCTOS VALUES (";
		sql += "'"+producto.getNombre() + "',";
		sql += "'"+producto.getDescripcion() + "',";
		sql += "'"+producto.getTraduccion()+"',";
		sql += producto.getTiempoPreparacion()+",";
		sql += producto.getCostoProduccion()+",";
		sql += producto.getPrecioVenta()+",";
		sql += producto.getTipo()+")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el producto que entra como parametro en la base de datos.
	 * @param producto - el producto a actualizar. producto !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateProducto(Producto producto) throws SQLException, Exception {

		String sql = "UPDATE PRODUCTOS SET ";
		sql += "DESCRIPCION='" + producto.getDescripcion()+"',";
		sql += "TRADUCCION='" + producto.getTraduccion()+"'";		
		sql += "TIEMPO_PREPARACION="+producto.getTiempoPreparacion()+",";
		sql += "COSTO_PRODUCCION="+producto.getCostoProduccion()+",";
		sql += "PRECIO_VENTA="+producto.getPrecioVenta()+",";
		sql += "TIPO="+producto.getTipo()+",";
		sql += " WHERE NOMBRE='" + producto.getNombre()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el producto que entra como parametro en la base de datos.
	 * @param producto - el producto a borrar. producto !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteProducto(Producto producto) throws SQLException, Exception {

		String sql = "DELETE FROM PRODUCTOS";
		sql += " WHERE NOMBRE ='" + producto.getNombre()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}

