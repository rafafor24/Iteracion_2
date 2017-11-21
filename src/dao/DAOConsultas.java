package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;
import vos.Consulta1y2;
import vos.Usuario;

public class DAOConsultas {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOCliente
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOConsultas() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los clientes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CLIENTES;
	 * @return Arraylist con los clientes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Usuario> consultar(Consulta1y2 consulta,Long cantidad) throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String poner="!";
		if(cantidad==1)
		{
			poner=" ";
		}

		String sql = "select nombre,identificacion ,rol,CORREO_ELECTRONICO from pedidos"
				+ " inner join (select servicios.representante_restaurante as represt, servicios.id_pedido as idped,"
				+ " usuarios.nombre as nombre,usuarios.identificacion as identificacion,usuarios.rol as rol,"
				+ "usuarios.CORREO_ELECTRONICO as CORREO_ELECTRONICO from servicios inner join usuarios "
				+ "on (servicios.NOMBRE_USUARIO=usuarios.NOMBRE)) x on(x.idped=pedidos.ID) "
				+ "where (pedidos.FECHA BETWEEN '"+consulta.getFecha1()+"' AND '"+consulta.getFecha2()+"') AND x.represt"+poner+"='"+consulta.getRepresentante()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			String rol = rs.getString("ROL");
			String correo_electronico= rs.getString("CORREO_ELECTRONICO");
			usuarios.add(new Usuario(name, identificacion,rol, correo_electronico));
		}
		return usuarios;
	}

}
