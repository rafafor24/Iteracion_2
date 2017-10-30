package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Menu;
import vos.Pedido;
import vos.PedidoCM;

public class DAOPedidoCM {
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOPedido
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOPedidoCM() {
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
	 * Metodo que agrega el pedido que entra como parametro a la base de datos.
	 * @param menu - el pedido a agregar. pedido !=  null
	 * <b> post: </b> se ha agregado el pedido a la base de datos en la transaction actual. pendiente que el pedido master
	 * haga commit para que el pedido baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el pedido a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPedidoCM(PedidoCM pedido_cm) throws SQLException, Exception {

		String sql = "INSERT INTO PEDIDO_CLIENTEMESA VALUES (";
		sql += pedido_cm.getId_pedido() + ",";
		sql += pedido_cm.getId_cm()+",'";
		sql += pedido_cm.getTipo()+"')";

		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que elimina el pedido que entra como parametro en la base de datos.
	 * @param menu - el pedido a borrar. pedido!=  null
	 * <b> post: </b> se ha borrado el pedido en la base de datos en la transaction actual. pendiente que el pedido master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el pedido.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deletePedidoCM(PedidoCM pedido_cm) throws SQLException, Exception {

		String sql = "DELETE FROM PEDIDO_CLIENTEMESA";
		sql += " WHERE ID_PEDIDO ='" + pedido_cm.getId_pedido()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
}
